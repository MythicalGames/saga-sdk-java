package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.MockPaymentExecutor;
import games.mythical.saga.sdk.client.model.SagaAddress;
import games.mythical.saga.sdk.proto.api.payment.*;
import games.mythical.saga.sdk.proto.common.payment.PaymentMethodUpdateStatus;
import games.mythical.saga.sdk.proto.common.payment.PaymentProviderId;
import games.mythical.saga.sdk.proto.streams.StatusUpdate;
import games.mythical.saga.sdk.proto.streams.payment.PaymentMethodStatusUpdate;
import games.mythical.saga.sdk.proto.streams.payment.PaymentUpdate;
import games.mythical.saga.sdk.server.MockServer;
import games.mythical.saga.sdk.server.stream.MockStatusStreamingImpl;
import games.mythical.saga.sdk.util.ConcurrentFinisher;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SagaPaymentClientTest extends AbstractClientTest {
    private static final String OAUTH_ID = UUID.randomUUID().toString();
    private final MockPaymentExecutor executor = MockPaymentExecutor.builder().build();
    private final CybersourcePaymentData cybersourcePaymentData = CybersourcePaymentData.newBuilder()
            .setCardType("VISA")
            .build();
    private final PaymentMethodData paymentMethodData = PaymentMethodData.newBuilder()
            .setMakeDefault(true)
            .setCybersource(cybersourcePaymentData)
            .build();
    private final SagaAddress address = SagaAddress.builder()
            .firstName("John")
            .lastName("Smith")
            .addressLine1("1234 Road")
            .build();
    private MockServer paymentServer;
    private SagaPaymentClient paymentClient;
    @Mock
    private PaymentServiceGrpc.PaymentServiceBlockingStub mockServiceBlockingStub;

    @BeforeEach
    void setup() throws Exception {
        paymentServer = new MockServer(new MockStatusStreamingImpl());
        paymentServer.start();
        port = paymentServer.getPort();
        paymentClient = setUpFactory().createSagaPaymentClient(executor);

        // mocking the service blocking stub clients are connected to
        FieldUtils.writeField(paymentClient, "serviceBlockingStub", mockServiceBlockingStub, true);
    }

    @AfterEach
    void tearDown() throws Exception {
        paymentClient.stop();
        // client shutdown is not immediate
        Thread.sleep(500);
        paymentServer.stop();
    }

    @Test
    public void createCybersourcePaymentMethod() throws Exception {
        final var expectedResponse = genReceived();
        when(mockServiceBlockingStub.createPaymentMethod(any())).thenReturn(expectedResponse);

        final var trace = paymentClient.createCybersourcePaymentMethod(
                OAUTH_ID,
                RandomStringUtils.randomAlphanumeric(30),
                RandomStringUtils.randomAlphanumeric(30),
                RandomStringUtils.randomAlphanumeric(30),
                RandomStringUtils.randomAlphanumeric(30),
                address
        );
        checkTraceAndStart(expectedResponse, trace);

        final var pmtStatusUpdate = PaymentMethodStatusUpdate.newBuilder()
                .setPaymentMethod(createPaymentMethodProto(OAUTH_ID))
                .setPaymentMethodStatus(PaymentMethodUpdateStatus.CREATED)
                .build();
        final var pmtUpdate = PaymentUpdate.newBuilder()
                .setStatusUpdate(pmtStatusUpdate);
        final var statusUpdate = StatusUpdate.newBuilder()
                .setTraceId(trace)
                .setPaymentUpdate(pmtUpdate)
                .build();
        paymentServer.getStatusStream().sendStatus(titleId, statusUpdate);

        ConcurrentFinisher.wait(trace);

        assertEquals(OAUTH_ID, executor.getPaymentMethod().getOauthId());
        assertTrue(executor.getPaymentMethod().getSagaPaymentData().isDefaultMethod());
        assertEquals(PaymentMethodUpdateStatus.CREATED, executor.getMethodUpdateStatus());

        paymentServer.verifyCalls("StatusStream", 1);
        paymentServer.verifyCalls("StatusConfirmation", 1);
    }

    @Test
    public void startUpholdPaymentMethodLink() throws Exception {
        final var expectedResponse = genReceived();
        when(mockServiceBlockingStub.createPaymentMethod(any())).thenReturn(expectedResponse);

        final var trace = paymentClient.startUpholdPaymentMethodLink(OAUTH_ID);
        checkTraceAndStart(expectedResponse, trace);

        final var pmtStatusUpdate = PaymentMethodStatusUpdate.newBuilder()
                .setPaymentMethod(createPaymentMethodProto(OAUTH_ID))
                .setPaymentMethodStatus(PaymentMethodUpdateStatus.CREATED)
                .build();
        final var pmtUpdate = PaymentUpdate.newBuilder()
                .setStatusUpdate(pmtStatusUpdate);
        final var statusUpdate = StatusUpdate.newBuilder()
                .setTraceId(trace)
                .setPaymentUpdate(pmtUpdate)
                .build();
        paymentServer.getStatusStream().sendStatus(titleId, statusUpdate);

        ConcurrentFinisher.wait(trace);

        assertEquals(OAUTH_ID, executor.getPaymentMethod().getOauthId());
        assertEquals(PaymentMethodUpdateStatus.CREATED, executor.getMethodUpdateStatus());

        paymentServer.verifyCalls("StatusStream", 1);
        paymentServer.verifyCalls("StatusConfirmation", 1);
    }

    @Test
    public void updateCybersourcePaymentMethod() throws Exception {
        final var expectedResponse = genReceived();
        when(mockServiceBlockingStub.updatePaymentMethod(any())).thenReturn(expectedResponse);
        final var trace = paymentClient.updateCybersourcePaymentMethod(
                OAUTH_ID,
                RandomStringUtils.randomAlphanumeric(30),
                RandomStringUtils.randomAlphanumeric(30),
                RandomStringUtils.randomAlphanumeric(30),
                RandomStringUtils.randomAlphanumeric(30),
                RandomStringUtils.randomAlphanumeric(30),
                address
        );
        checkTraceAndStart(expectedResponse, trace);

        final var pmtStatusUpdate = PaymentMethodStatusUpdate.newBuilder()
                .setPaymentMethod(createPaymentMethodProto(OAUTH_ID))
                .setPaymentMethodStatus(PaymentMethodUpdateStatus.UPDATED)
                .build();
        final var pmtUpdate = PaymentUpdate.newBuilder()
                .setStatusUpdate(pmtStatusUpdate);
        final var statusUpdate = StatusUpdate.newBuilder()
                .setTraceId(trace)
                .setPaymentUpdate(pmtUpdate)
                .build();
        paymentServer.getStatusStream().sendStatus(titleId, statusUpdate);

        ConcurrentFinisher.wait(trace);

        assertEquals(OAUTH_ID, executor.getPaymentMethod().getOauthId());
        assertTrue(executor.getPaymentMethod().getSagaPaymentData().isDefaultMethod());
        assertEquals(PaymentMethodUpdateStatus.UPDATED, executor.getMethodUpdateStatus());

        paymentServer.verifyCalls("StatusStream", 1);
        paymentServer.verifyCalls("StatusConfirmation", 1);
    }

    @Test
    public void finishUpholdPaymentMethodLink() throws Exception {
        final var expectedResponse = genReceived();
        when(mockServiceBlockingStub.updatePaymentMethod(any())).thenReturn(expectedResponse);
        final var trace = paymentClient.finishUpholdPaymentMethodLink(
                OAUTH_ID,
                RandomStringUtils.randomAlphanumeric(30),
                RandomStringUtils.randomAlphanumeric(30)
        );
        checkTraceAndStart(expectedResponse, trace);

        final var pmtStatusUpdate = PaymentMethodStatusUpdate.newBuilder()
                .setPaymentMethod(createPaymentMethodProto(OAUTH_ID))
                .setPaymentMethodStatus(PaymentMethodUpdateStatus.UPDATED)
                .build();
        final var pmtUpdate = PaymentUpdate.newBuilder()
                .setStatusUpdate(pmtStatusUpdate);
        final var statusUpdate = StatusUpdate.newBuilder()
                .setTraceId(trace)
                .setPaymentUpdate(pmtUpdate)
                .build();
        paymentServer.getStatusStream().sendStatus(titleId, statusUpdate);

        ConcurrentFinisher.wait(trace);

        assertEquals(OAUTH_ID, executor.getPaymentMethod().getOauthId());
        assertTrue(executor.getPaymentMethod().getSagaPaymentData().isDefaultMethod());
        assertEquals(PaymentMethodUpdateStatus.UPDATED, executor.getMethodUpdateStatus());

        paymentServer.verifyCalls("StatusStream", 1);
        paymentServer.verifyCalls("StatusConfirmation", 1);
    }

    @Test
    public void deletePaymentMethod() throws Exception {
        final var expectedResponse = genReceived();
        when(mockServiceBlockingStub.deletePaymentMethod(any())).thenReturn(expectedResponse);
        final var trace = paymentClient.deletePaymentMethod(
                OAUTH_ID,
                RandomStringUtils.randomAlphanumeric(30),
                PaymentProviderId.CYBERSOURCE
        );
        checkTraceAndStart(expectedResponse, trace);

        final var pmtStatusUpdate = PaymentMethodStatusUpdate.newBuilder()
                .setPaymentMethod(createPaymentMethodProto(OAUTH_ID))
                .setPaymentMethodStatus(PaymentMethodUpdateStatus.UPDATED)
                .build();
        final var pmtUpdate = PaymentUpdate.newBuilder()
                .setStatusUpdate(pmtStatusUpdate);
        final var statusUpdate = StatusUpdate.newBuilder()
                .setTraceId(trace)
                .setPaymentUpdate(pmtUpdate)
                .build();
        paymentServer.getStatusStream().sendStatus(titleId, statusUpdate);

        ConcurrentFinisher.wait(trace);

        assertEquals(OAUTH_ID, executor.getPaymentMethod().getOauthId());
        assertTrue(executor.getPaymentMethod().getSagaPaymentData().isDefaultMethod());
        assertEquals(PaymentMethodUpdateStatus.UPDATED, executor.getMethodUpdateStatus());

        paymentServer.verifyCalls("StatusStream", 1);
        paymentServer.verifyCalls("StatusConfirmation", 1);
    }

    @Test
    public void getPaymentMethods() throws Exception {
        var expectedResponse = PaymentMethodProtos.newBuilder()
                .addPaymentMethods(createPaymentMethodProto(OAUTH_ID))
                .build();
        when(mockServiceBlockingStub.getPaymentMethods(any())).thenReturn(expectedResponse);
        var paymentResponse = paymentClient.getPaymentMethods(OAUTH_ID, PaymentProviderId.CYBERSOURCE);

        assertFalse(paymentResponse.isEmpty());
        var payment = paymentResponse.get(0);
        assertEquals(expectedResponse.getPaymentMethodsList().get(0).getTraceId(), payment.getTraceId());
        assertNotNull(payment.getSagaPaymentData().asCybersource());
    }

    private PaymentMethodProto createPaymentMethodProto(String oauthId) {
        return PaymentMethodProto.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .setOauthId(oauthId)
                .setPaymentMethodData(paymentMethodData)
                .build();
    }
}
