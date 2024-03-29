package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.MockCurrencyExecutor;
import games.mythical.saga.sdk.client.model.SagaUserAmount;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.api.currency.BalanceOfPlayerProto;
import games.mythical.saga.sdk.proto.api.currency.CurrencyServiceGrpc;
import games.mythical.saga.sdk.proto.common.ReceivedResponse;
import games.mythical.saga.sdk.proto.common.currency.CurrencyState;
import games.mythical.saga.sdk.proto.streams.StatusUpdate;
import games.mythical.saga.sdk.proto.streams.currency.BalanceProto;
import games.mythical.saga.sdk.proto.streams.currency.CurrencyStatusUpdate;
import games.mythical.saga.sdk.proto.streams.currency.CurrencyUpdate;
import games.mythical.saga.sdk.server.MockServer;
import games.mythical.saga.sdk.server.stream.MockStatusStreamingImpl;
import games.mythical.saga.sdk.util.ConcurrentFinisher;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static games.mythical.saga.sdk.proto.api.currency.BalanceProto.newBuilder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SagaCurrencyClientTest extends AbstractClientTest {
    private static final String CURRENCY_TYPE_ID = RandomStringUtils.randomAlphanumeric(30);
    private static final String OAUTH_ID = RandomStringUtils.randomAlphanumeric(30);
    private final MockCurrencyExecutor executor = MockCurrencyExecutor.builder().build();
    private MockServer currencyServer;
    private SagaCurrencyClient currencyClient;

    @Mock
    private CurrencyServiceGrpc.CurrencyServiceBlockingStub mockServiceBlockingStub;

    @BeforeEach
    void setUp() throws Exception {
        currencyServer = new MockServer(new MockStatusStreamingImpl());
        currencyServer.start();
        port = currencyServer.getPort();

        currencyClient = setUpFactory().createSagaCurrencyClient(executor);
        // mocking the service blocking stub clients are connected to
        FieldUtils.writeField(currencyClient, "serviceBlockingStub", mockServiceBlockingStub, true);
    }

    @AfterEach
    void tearDown() throws Exception {
        currencyClient.stop();
        // client shutdown is not immediate
        Thread.sleep(500);
        currencyServer.stop();
    }

    @Test
    public void getBalanceOfPlayer() throws Exception {
        var expectedResponse = BalanceOfPlayerProto.newBuilder()
                .setBalance(newBuilder()
                        .setCurrencyTypeId(CURRENCY_TYPE_ID)
                        .setBalanceInWei(RandomStringUtils.randomNumeric(10))
                        .build())
                .setOauthId(OAUTH_ID)
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(mockServiceBlockingStub.getBalanceOfPlayer(any())).thenReturn(expectedResponse);
        var currencyResponse = currencyClient.getBalanceOfPlayer(CURRENCY_TYPE_ID, OAUTH_ID);

        assertNotNull(currencyResponse);
        assertEquals(CURRENCY_TYPE_ID, currencyResponse.getBalance().getCurrencyTypeId());
        assertEquals(expectedResponse.getOauthId(), currencyResponse.getOauthId());
        assertEquals(expectedResponse.getBalance().getBalanceInWei(), currencyResponse.getBalance().getBalanceInWei());

        when(mockServiceBlockingStub.getBalanceOfPlayer(any())).thenThrow(new StatusRuntimeException(Status.NOT_FOUND));
        assertThrows(SagaException.class, () -> currencyClient.getBalanceOfPlayer("INVALID-CURRENCY-ID", "INVALID-USER"));
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.MINUTES)
    public void issueCurrency() throws Exception {
        final var expectedResponse = ReceivedResponse.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(mockServiceBlockingStub.issueCurrency(any())).thenReturn(expectedResponse);
        final var traceId = currencyClient.issueCurrency(
                CURRENCY_TYPE_ID,
                List.of(SagaUserAmount.builder()
                        .amountInWei("1000")
                        .oauthId("oauth")
                        .build()
                ),
                "idempotencyId");
        checkTraceAndStart(expectedResponse, traceId);

        final var update = CurrencyStatusUpdate.newBuilder()
                .addBalances(BalanceProto.newBuilder()
                        .setOauthId(OAUTH_ID)
                        .setCurrencyTypeId(CURRENCY_TYPE_ID)
                        .build())
                .setCurrencyState(CurrencyState.ISSUED);
        currencyServer.getStatusStream().sendStatus(titleId, StatusUpdate.newBuilder()
                .setTraceId(traceId)
                .setCurrencyUpdate(CurrencyUpdate.newBuilder().setStatusUpdate(update))
                .build());

        ConcurrentFinisher.wait(traceId);

        assertEquals(OAUTH_ID, executor.getOauthId());
        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        currencyServer.verifyCalls("StatusStream", 1);
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.MINUTES)
    public void transferCurrency() throws Exception {
        final var SOURCE = RandomStringUtils.randomAlphanumeric(30);
        final var DEST = RandomStringUtils.randomAlphanumeric(30);

        final var expectedResponse = ReceivedResponse.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(mockServiceBlockingStub.transferCurrency(any())).thenReturn(expectedResponse);
        final var traceId = currencyClient.transferCurrency(
                CURRENCY_TYPE_ID,
                SOURCE,
                DEST,
                "1000",
                false,
                "idempotencyId");
        checkTraceAndStart(expectedResponse, traceId);

        final var update = CurrencyStatusUpdate.newBuilder()
                .addBalances(BalanceProto.newBuilder()
                        .setOauthId(DEST)
                        .setCurrencyTypeId(CURRENCY_TYPE_ID)
                        .build())
                .setCurrencyState(CurrencyState.TRANSFERRED);
        currencyServer.getStatusStream().sendStatus(titleId, StatusUpdate.newBuilder()
                .setTraceId(traceId)
                .setCurrencyUpdate(CurrencyUpdate.newBuilder().setStatusUpdate(update))
                .build());

        ConcurrentFinisher.wait(traceId);

        assertEquals(DEST, executor.getOauthId());
        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertEquals(CurrencyState.TRANSFERRED, executor.getCurrencyState());
        assertEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        currencyServer.verifyCalls("StatusStream", 1);
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.MINUTES)
    public void burnCurrency() throws Exception {
        final var expectedResponse = ReceivedResponse.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(mockServiceBlockingStub.burnCurrency(any())).thenReturn(expectedResponse);
        final var traceId = currencyClient.burnCurrency(
                CURRENCY_TYPE_ID,
                OAUTH_ID,
                "10000",
                false,
                "idempotencyId");
        checkTraceAndStart(expectedResponse, traceId);

        final var update = CurrencyStatusUpdate.newBuilder()
                .addBalances(BalanceProto.newBuilder()
                        .setOauthId(OAUTH_ID)
                        .setCurrencyTypeId(CURRENCY_TYPE_ID)
                        .build())
                .setCurrencyState(CurrencyState.BURNED);
        currencyServer.getStatusStream().sendStatus(titleId, StatusUpdate.newBuilder()
                .setTraceId(traceId)
                .setCurrencyUpdate(CurrencyUpdate.newBuilder().setStatusUpdate(update))
                .build());

        ConcurrentFinisher.wait(traceId);

        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertEquals(CurrencyState.BURNED, executor.getCurrencyState());
        assertEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        currencyServer.verifyCalls("StatusStream", 1);
    }
}