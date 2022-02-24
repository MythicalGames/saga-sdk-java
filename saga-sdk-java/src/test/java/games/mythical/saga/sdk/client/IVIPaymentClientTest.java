package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.model.IVIToken;
import games.mythical.saga.sdk.exception.IVIException;
import games.mythical.ivi.sdk.proto.api.order.PaymentProviderId;
import games.mythical.saga.sdk.server.payment.order.MockPaymentServer;
import io.grpc.ManagedChannelBuilder;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class IVIPaymentClientTest extends AbstractClientTest {
    private MockPaymentServer paymentServer;
    private IVIPaymentClient paymentClient;
    private Map<String, IVIToken> tokens;

    @BeforeEach
    void setUp() throws Exception {
        paymentServer = new MockPaymentServer();
        paymentServer.start();
        port = paymentServer.getPort();
        setUpConfig();


        tokens = generateTokens(3);
        paymentServer.getPaymentService().setTokens(tokens.values());

        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();

        paymentClient = new IVIPaymentClient(channel);
    }

    @AfterEach
    void tearDown() {
        paymentServer.stop();
    }

    protected Map<String, IVIToken> generateTokens(int count) {
        var result = new HashMap<String, IVIToken>();
        for(var i = 0; i < count; i++) {
            var braintreeToken = RandomStringUtils.randomAlphanumeric(30);

            var token = IVIToken.builder()
                    .braintreeToken(braintreeToken)
                    .build();
            result.put(String.valueOf(i), token);
        }
        return result;
    }

    @Test
    void createBraintreeToken() throws IVIException {
        var playerId = RandomStringUtils.randomAlphanumeric(30);
        var token = paymentClient.getToken(PaymentProviderId.BRAINTREE, playerId);

        assertNotNull(token.getBraintreeToken());
    }

}