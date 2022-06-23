package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.MockOrderExecutor;
import games.mythical.saga.sdk.client.model.SagaCreditCardData;
import games.mythical.saga.sdk.proto.api.order.*;
import games.mythical.saga.sdk.proto.common.ReceivedResponse;
import games.mythical.saga.sdk.proto.common.order.OrderState;
import games.mythical.saga.sdk.proto.streams.StatusUpdate;
import games.mythical.saga.sdk.proto.streams.order.OrderStatusUpdate;
import games.mythical.saga.sdk.proto.streams.order.OrderUpdate;
import games.mythical.saga.sdk.server.MockServer;
import games.mythical.saga.sdk.server.stream.MockStatusStreamingImpl;
import games.mythical.saga.sdk.util.ConcurrentFinisher;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SagaOrderClientTest extends AbstractClientTest {
    private static final String OAUTH_ID = UUID.randomUUID().toString();

    private final MockOrderExecutor executor = MockOrderExecutor.builder().build();
    private MockServer orderServer;
    private SagaOrderClient orderClient;

    @Mock
    private OrderServiceGrpc.OrderServiceBlockingStub mockServiceBlockingStub;

    @BeforeEach
    void setUp() throws Exception {
        orderServer = new MockServer(new MockStatusStreamingImpl());
        orderServer.start();
        port = orderServer.getPort();

        orderClient = setUpFactory().createSagaOrderClient(executor);
        // mocking the service blocking stub clients are connected to
        FieldUtils.writeField(orderClient, "serviceBlockingStub", mockServiceBlockingStub, true);
    }

    @AfterEach
    void tearDown() throws Exception {
        orderClient.stop();
        // client shutdown is not immediate
        Thread.sleep(500);
        orderServer.stop();
    }

    @Test
    public void createQuote() throws Exception {
        final var QUOTE_ID = RandomStringUtils.randomAlphanumeric(30);
        final var expectedResponse = genReceived();
        when(mockServiceBlockingStub.createOrderQuote(any())).thenReturn(expectedResponse);

        final var sagaCreditCardData = SagaCreditCardData.builder()
            .addressLine1(RandomStringUtils.randomAlphanumeric(30))
            .postalCode(RandomStringUtils.randomAlphanumeric(30))
            .build();

        final var trace = orderClient.createQuote(
            OAUTH_ID,
            BigDecimal.TEN,
            sagaCreditCardData,
            RandomStringUtils.randomAlphanumeric(30),
            null,
            false,
            false,
            false,
            null
        );
        checkTraceAndStart(expectedResponse, trace);

        final var update = OrderStatusUpdate.newBuilder()
            .setOauthId(OAUTH_ID)
            .setQuoteId(QUOTE_ID)
            .setOrderId(QUOTE_ID)
            .setTotal(String.valueOf(RandomUtils.nextInt(1, 1000)))
            .setOrderState(OrderState.COMPLETE);
        var statusUpdate = StatusUpdate.newBuilder()
            .setTraceId(trace)
            .setOrderUpdate(OrderUpdate.newBuilder().setStatusUpdate(update))
            .build();
        orderServer.getStatusStream().sendStatus(titleId, statusUpdate);

        ConcurrentFinisher.wait(trace);

        assertEquals(OAUTH_ID, executor.getOauthId());
        assertEquals(QUOTE_ID, executor.getQuoteId());
        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertEquals(OrderState.COMPLETE, executor.getOrderState());
        assertEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        orderServer.verifyCalls("StatusStream", 1);
        orderServer.verifyCalls("StatusConfirmation", 1);
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.MINUTES)
    public void confirmOrder() throws Exception {
        final var QUOTE_ID = RandomStringUtils.randomAlphanumeric(30);
        final var expectedResponse = ReceivedResponse.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(mockServiceBlockingStub.confirmOrder(any())).thenReturn(expectedResponse);

        final var upholdCardId = RandomStringUtils.randomAlphanumeric(30);
        final var traceId = orderClient.confirmOrder(
                OAUTH_ID,
                QUOTE_ID,
                upholdCardId,
                RandomStringUtils.randomAlphanumeric(30)
        );
        checkTraceAndStart(expectedResponse, traceId);

        final var update = OrderStatusUpdate.newBuilder()
            .setOauthId(OAUTH_ID)
            .setQuoteId(QUOTE_ID)
            .setOrderId(QUOTE_ID)
            .setTotal(String.valueOf(RandomUtils.nextInt(1, 1000)))
            .setOrderState(OrderState.COMPLETE);
        var statusUpdate = StatusUpdate.newBuilder()
                .setTraceId(traceId)
                .setOrderUpdate(OrderUpdate.newBuilder().setStatusUpdate(update))
                .build();
        orderServer.getStatusStream().sendStatus(titleId, statusUpdate);

        ConcurrentFinisher.wait(traceId);

        assertEquals(OAUTH_ID, executor.getOauthId());
        assertEquals(QUOTE_ID, executor.getQuoteId());
        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertEquals(OrderState.COMPLETE, executor.getOrderState());
        assertEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        orderServer.verifyCalls("StatusStream", 1);
        orderServer.verifyCalls("StatusConfirmation", 1);
    }
}