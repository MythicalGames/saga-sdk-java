package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.MockPaymentExecutor;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
            .setCybersource(cybersourcePaymentData)
            .build();
    private final Address address = Address.newBuilder()
            .setFirstName("John")
            .setLastName("Smith")
            .setAddressLine1("1234 Road")
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
    public void createPaymentMethod() throws Exception {
        final var expectedResponse = genReceived();
        when(mockServiceBlockingStub.createPaymentMethod(any())).thenReturn(expectedResponse);

        final var trace = paymentClient.createPaymentMethod(OAUTH_ID, paymentMethodData, address);
        checkTraceAndStart(expectedResponse, executor, trace);

        final var pmtStatusUpdate = PaymentMethodStatusUpdate.newBuilder()
                .setOauthId(OAUTH_ID)
                .setDefault(true)
                .setPaymentMethodStatus(PaymentMethodUpdateStatus.UPDATED)
                .build();
        final var pmtUpdate = PaymentUpdate.newBuilder()
                .setStatusUpdate(pmtStatusUpdate);
        final var statusUpdate = StatusUpdate.newBuilder()
                .setTraceId(executor.getTraceId())
                .setPaymentUpdate(pmtUpdate)
                .build();
        paymentServer.getStatusStream().sendStatus(titleId, statusUpdate);

        ConcurrentFinisher.wait(executor.getTraceId());

        assertEquals(OAUTH_ID, executor.getOauthId());
        assertTrue(executor.isDefault());
        assertEquals(PaymentMethodUpdateStatus.UPDATED, executor.getMethodUpdateStatus());

        paymentServer.verifyCalls("StatusStream", 1);
        paymentServer.verifyCalls("StatusConfirmation", 1);
    }

    @Test
    public void updatePaymentMethod() throws Exception {
        final var expectedResponse = genReceived();
        when(mockServiceBlockingStub.updatePaymentMethod(any())).thenReturn(expectedResponse);
        final var trace = paymentClient.updatePaymentMethod(OAUTH_ID, paymentMethodData, address);
        checkTraceAndStart(expectedResponse, executor, trace);

        final var pmtStatusUpdate = PaymentMethodStatusUpdate.newBuilder()
                .setOauthId(OAUTH_ID)
                .setDefault(true)
                .setPaymentMethodStatus(PaymentMethodUpdateStatus.UPDATED)
                .build();
        final var pmtUpdate = PaymentUpdate.newBuilder()
                .setStatusUpdate(pmtStatusUpdate);
        final var statusUpdate = StatusUpdate.newBuilder()
                .setTraceId(executor.getTraceId())
                .setPaymentUpdate(pmtUpdate)
                .build();
        paymentServer.getStatusStream().sendStatus(titleId, statusUpdate);

        ConcurrentFinisher.wait(executor.getTraceId());

        assertEquals(OAUTH_ID, executor.getOauthId());
        assertTrue(executor.isDefault());
        assertEquals(PaymentMethodUpdateStatus.UPDATED, executor.getMethodUpdateStatus());

        paymentServer.verifyCalls("StatusStream", 1);
        paymentServer.verifyCalls("StatusConfirmation", 1);
    }

    @Test
    public void deletePaymentMethod() throws Exception {
        final var expectedResponse = genReceived();
        when(mockServiceBlockingStub.deletePaymentMethod(any())).thenReturn(expectedResponse);
        final var trace = paymentClient.deletePaymentMethod(OAUTH_ID, paymentMethodData);
        checkTraceAndStart(expectedResponse, executor, trace);

        final var pmtStatusUpdate = PaymentMethodStatusUpdate.newBuilder()
                .setOauthId(OAUTH_ID)
                .setDefault(true)
                .setPaymentMethodStatus(PaymentMethodUpdateStatus.UPDATED)
                .build();
        final var pmtUpdate = PaymentUpdate.newBuilder()
                .setStatusUpdate(pmtStatusUpdate);
        final var statusUpdate = StatusUpdate.newBuilder()
                .setTraceId(executor.getTraceId())
                .setPaymentUpdate(pmtUpdate)
                .build();
        paymentServer.getStatusStream().sendStatus(titleId, statusUpdate);

        ConcurrentFinisher.wait(executor.getTraceId());

        assertEquals(OAUTH_ID, executor.getOauthId());
        assertTrue(executor.isDefault());
        assertEquals(PaymentMethodUpdateStatus.UPDATED, executor.getMethodUpdateStatus());

        paymentServer.verifyCalls("StatusStream", 1);
        paymentServer.verifyCalls("StatusConfirmation", 1);
    }

    @Test
    public void getPaymentMethod() throws Exception {
        var oauthId = RandomStringUtils.randomAlphanumeric(30);

        var expectedResponse = PaymentMethodProto.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .setOauthId(oauthId)
                .build();
        when(mockServiceBlockingStub.getPaymentMethod(any())).thenReturn(expectedResponse);
        var paymentResponse = paymentClient.getPaymentMethod(oauthId, PaymentProviderId.CYBERSOURCE);

        assertTrue(paymentResponse.isPresent());
        var payment = paymentResponse.get();
        compareObjectsByReflection(expectedResponse, payment, null);
    }
}
