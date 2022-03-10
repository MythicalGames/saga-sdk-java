package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.MockOrderExecutor;
import games.mythical.saga.sdk.proto.api.order.*;
import games.mythical.saga.sdk.proto.common.ReceivedResponse;
import games.mythical.saga.sdk.proto.common.order.OrderState;
import games.mythical.saga.sdk.server.MockServer;
import games.mythical.saga.sdk.server.stream.MockOrderStreamingImpl;
import games.mythical.saga.sdk.util.ConcurrentFinisher;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SagaOrderClientTest extends AbstractClientTest {
    private static final String OAUTH_ID = UUID.randomUUID().toString();
    private static final String CURRENCY = "USD";

    private final MockOrderExecutor executor = MockOrderExecutor.builder().build();
    private MockServer orderServer;
    private SagaOrderClient orderClient;

    @Mock
    private OrderServiceGrpc.OrderServiceBlockingStub mockServiceBlockingStub;

    @BeforeEach
    void setUp() throws Exception {
        orderServer = new MockServer(new MockOrderStreamingImpl());
        orderServer.start();
        port = orderServer.getPort();

        orderClient = setUpFactory().createSagaOrderClient(executor);
        // mocking the service blocking stub clients are connected to
        FieldUtils.writeField(orderClient, "serviceBlockingStub", mockServiceBlockingStub, true);
    }

    @AfterEach
    void tearDown() {
        orderServer.stop();
    }

    @Test
    public void createQuote() throws Exception {
        var expectedResponse = QuoteProto.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .setOauthId(OAUTH_ID)
                .setQuoteId(RandomStringUtils.randomAlphanumeric(30))
                .setTax(String.valueOf(RandomUtils.nextInt(1, 100)))
                .setTaxCurrency(CURRENCY)
                .setTotal(String.valueOf(RandomUtils.nextInt(1, 1000)))
                .setCurrency(CURRENCY)
                .setPaymentProviderId(PaymentProviderId.forNumber(RandomUtils.nextInt(0, PaymentProviderId.values().length - 1)))
                .setConversionRate(RandomStringUtils.randomAlphanumeric(30))
                .setCreatedTimestamp(Instant.now().toEpochMilli() - 86400)
                .build();
        when(mockServiceBlockingStub.createQuote(any())).thenReturn(expectedResponse);

        var paymentProviderData = PaymentProviderData.newBuilder()
                .setCreditCardData(CreditCardData.newBuilder()
                        .setAddressLine1(RandomStringUtils.randomAlphanumeric(30))
                        .setPostalCode(RandomStringUtils.randomAlphanumeric(30))
                        .build())
                .setUpholdCardId(RandomStringUtils.randomAlphanumeric(30))
                .build();
        var quoteResponse = orderClient.createQuote(
                OAUTH_ID,
                BigDecimal.TEN,
                paymentProviderData,
                RandomStringUtils.randomAlphanumeric(30),
                null,
                false,
                false,
                false,
                null
        );

        assertTrue(quoteResponse.isPresent());
        var quote = quoteResponse.get();
        assertEquals(expectedResponse.getTraceId(), quote.getTraceId());
        assertEquals(OAUTH_ID, quote.getOauthId());
        assertTrue(StringUtils.isNotBlank(expectedResponse.getQuoteId()));
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.MINUTES)
    public void confirmQuote() throws Exception {
        final var QUOTE_ID = RandomStringUtils.randomAlphanumeric(30);
        final var expectedResponse = ReceivedResponse.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(mockServiceBlockingStub.confirmQuote(any())).thenReturn(expectedResponse);

        var paymentProviderData = PaymentProviderData.newBuilder()
                .setCreditCardData(CreditCardData.newBuilder()
                        .setAddressLine1(RandomStringUtils.randomAlphanumeric(30))
                        .setPostalCode(RandomStringUtils.randomAlphanumeric(30))
                        .build())
                .setUpholdCardId(RandomStringUtils.randomAlphanumeric(30))
                .build();
        orderClient.confirmQuote(
                OAUTH_ID,
                QUOTE_ID,
                paymentProviderData,
                RandomStringUtils.randomAlphanumeric(30)
        );

        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertNotEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        // a short wait is needed so the status stream can be hooked up
        // before the emitting the event from the sendStatus method
        Thread.sleep(500);
        ConcurrentFinisher.start(executor.getTraceId());

        var statusUpdate = QuoteProto.newBuilder()
                .setTraceId(expectedResponse.getTraceId())
                .setOauthId(OAUTH_ID)
                .setQuoteId(QUOTE_ID)
                .setTax(String.valueOf(RandomUtils.nextInt(1, 100)))
                .setTaxCurrency(CURRENCY)
                .setTotal(String.valueOf(RandomUtils.nextInt(1, 1000)))
                .setCurrency(CURRENCY)
                .setPaymentProviderId(PaymentProviderId.forNumber(RandomUtils.nextInt(0, PaymentProviderId.values().length - 1)))
                .setConversionRate(RandomStringUtils.randomAlphanumeric(30))
                .setCreatedTimestamp(Instant.now().toEpochMilli() - 86400)
                .build();
        orderServer.getOrderStream().sendStatus(titleId, statusUpdate, OrderState.COMPLETE);

        ConcurrentFinisher.wait(executor.getTraceId());

        assertEquals(OAUTH_ID, executor.getOauthId());
        assertEquals(QUOTE_ID, executor.getQuoteId());
        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertEquals(OrderState.COMPLETE, executor.getOrderState());
        assertEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        orderServer.verifyCalls("OrderStatusStream", 1);
        orderServer.verifyCalls("OrderStatusConfirmation", 1);
    }
}