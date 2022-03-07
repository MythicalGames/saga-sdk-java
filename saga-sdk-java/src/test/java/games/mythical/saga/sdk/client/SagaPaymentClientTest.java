package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.proto.api.payment.PaymentServiceGrpc;
import games.mythical.saga.sdk.proto.api.payments.Address;
import games.mythical.saga.sdk.proto.api.payments.CardPaymentData;
import games.mythical.saga.sdk.proto.api.payments.CybersourcePaymentData;
import games.mythical.saga.sdk.proto.api.payments.PaymentMethodProto;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SagaPaymentClientTest extends AbstractClientTest {

    private SagaPaymentClient paymentClient;

    private CybersourcePaymentData cybersourcePaymentData = CybersourcePaymentData.newBuilder()
            .setCardType("VISA")
            .build();
    private CardPaymentData cardPaymentData = CardPaymentData.newBuilder()
            .setCybersource(cybersourcePaymentData)
            .build();
    private Address address = Address.newBuilder()
            .setFirstName("John")
            .setLastName("Smith")
            .setAddressLine1("1234 Road")
            .build();
    @Mock
    private PaymentServiceGrpc.PaymentServiceBlockingStub mockServiceBlockingStub;

    @BeforeEach
    void setup() throws Exception {
        port = 65001; // Doesn't matter since there isn't anything to connect to.
        paymentClient = setUpFactory().createSagaPaymentClient();

        // mocking the service blocking stub clients are connected to
        FieldUtils.writeField(paymentClient, "serviceBlockingStub", mockServiceBlockingStub, true);
    }

    @Test
    public void createPaymentMethod() throws Exception {
        var oauthId = RandomStringUtils.randomAlphanumeric(30);

        var expectedResponse = PaymentMethodProto.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .setOauthId(oauthId)
                .build();
        when(mockServiceBlockingStub.createPaymentMethod(any())).thenReturn(expectedResponse);
        var paymentResponse = paymentClient.createPaymentMethod(oauthId, cardPaymentData, address);

        assertTrue(paymentResponse.isPresent());
        var payment = paymentResponse.get();
        compareObjectsByReflection(expectedResponse, payment, null);
    }

    @Test
    public void updateaymentMethod() throws Exception {
        var oauthId = RandomStringUtils.randomAlphanumeric(30);

        var expectedResponse = PaymentMethodProto.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .setOauthId(oauthId)
                .setCardPaymentData(cardPaymentData)
                .setAddress(address)
                .build();
        when(mockServiceBlockingStub.updatePaymentMethod(any())).thenReturn(expectedResponse);
        var paymentResponse = paymentClient.updatePaymentMethod(oauthId, cardPaymentData, address);

        assertTrue(paymentResponse.isPresent());
        var payment = paymentResponse.get();
        compareObjectsByReflection(expectedResponse, payment, null);
    }

    @Test
    public void deletePaymentMethod() throws Exception {
        var oauthId = RandomStringUtils.randomAlphanumeric(30);

        var expectedResponse = PaymentMethodProto.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .setOauthId(oauthId)
                .build();
        when(mockServiceBlockingStub.deletePaymentMethod(any())).thenReturn(expectedResponse);
        var paymentResponse = paymentClient.deletePaymentMethod(oauthId, cardPaymentData);

        assertTrue(paymentResponse.isPresent());
        var payment = paymentResponse.get();
        compareObjectsByReflection(expectedResponse, payment, null);
    }

    @Test
    public void getPaymentMethod() throws Exception {
        var oauthId = RandomStringUtils.randomAlphanumeric(30);

        var expectedResponse = PaymentMethodProto.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .setOauthId(oauthId)
                .build();
        when(mockServiceBlockingStub.getPaymentMethod(any())).thenReturn(expectedResponse);
        var paymentResponse = paymentClient.getPaymentMethod(oauthId);

        assertTrue(paymentResponse.isPresent());
        var payment = paymentResponse.get();
        compareObjectsByReflection(expectedResponse, payment, null);
    }
}
