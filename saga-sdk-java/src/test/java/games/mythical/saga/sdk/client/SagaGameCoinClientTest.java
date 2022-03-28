package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.MockGameCoinExecutor;
import games.mythical.saga.sdk.proto.api.gamecoin.GameCoinProto;
import games.mythical.saga.sdk.proto.api.gamecoin.GameCoinServiceGrpc;
import games.mythical.saga.sdk.proto.common.ReceivedResponse;
import games.mythical.saga.sdk.proto.common.gamecoin.GameCoinState;
import games.mythical.saga.sdk.proto.streams.StatusUpdate;
import games.mythical.saga.sdk.proto.streams.gamecoin.GameCoinStatusUpdate;
import games.mythical.saga.sdk.proto.streams.gamecoin.GameCoinUpdate;
import games.mythical.saga.sdk.server.MockServer;
import games.mythical.saga.sdk.server.stream.MockStatusStreamingImpl;
import games.mythical.saga.sdk.util.ConcurrentFinisher;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
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

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SagaGameCoinClientTest extends AbstractClientTest {
    private static final String CURRENCY_ID = RandomStringUtils.randomAlphanumeric(30);
    private static final String OAUTH_ID = RandomStringUtils.randomAlphanumeric(30);

    private final MockGameCoinExecutor executor = MockGameCoinExecutor.builder().build();
    private MockServer gameCoinServer;
    private SagaGameCoinClient gameCoinClient;

    @Mock
    private GameCoinServiceGrpc.GameCoinServiceBlockingStub mockServiceBlockingStub;

    @BeforeEach
    void setUp() throws Exception {
        gameCoinServer = new MockServer(new MockStatusStreamingImpl());
        gameCoinServer.start();
        port = gameCoinServer.getPort();

        gameCoinClient = setUpFactory().createSagaGameCoinClient(executor);
        // mocking the service blocking stub clients are connected to
        FieldUtils.writeField(gameCoinClient, "serviceBlockingStub", mockServiceBlockingStub, true);
    }

    @AfterEach
    void tearDown() throws Exception {
        gameCoinClient.stop();
        // client shutdown is not immediate
        Thread.sleep(500);
        gameCoinServer.stop();
    }

    @Test
    public void getGameCoin() throws Exception {
        var expectedResponse = GameCoinProto.newBuilder()
                .setCurrencyId(CURRENCY_ID)
                .setCoinCount(RandomUtils.nextInt(0, 1000))
                .setOauthId(OAUTH_ID)
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .setCreatedTimestamp(Instant.now().toEpochMilli() - 86400)
                .setUpdatedTimestamp(Instant.now().toEpochMilli())
                .build();
        when(mockServiceBlockingStub.getGameCoin(any())).thenReturn(expectedResponse);
        var gameCoinResponse = gameCoinClient.getGameCoin(CURRENCY_ID, OAUTH_ID);

        assertTrue(gameCoinResponse.isPresent());
        var gameCoin = gameCoinResponse.get();
        assertEquals(CURRENCY_ID, gameCoin.getCurrencyId());
        assertEquals(expectedResponse.getOauthId(), gameCoin.getOauthId());
        assertEquals(expectedResponse.getCoinCount(), gameCoin.getCoinCount());

        when(mockServiceBlockingStub.getGameCoin(any())).thenThrow(new StatusRuntimeException(Status.NOT_FOUND));
        gameCoinResponse = gameCoinClient.getGameCoin("INVALID-CURRENCY-ID", "INVALID-USER");

        assertTrue(gameCoinResponse.isEmpty());
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.MINUTES)
    public void issueGameCoin() throws Exception {
        final var expectedResponse = ReceivedResponse.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(mockServiceBlockingStub.issueGameCoin(any())).thenReturn(expectedResponse);
        final var traceId = gameCoinClient.issueGameCoin(CURRENCY_ID, OAUTH_ID, RandomUtils.nextInt(1, 1000));

        assertEquals(expectedResponse.getTraceId(), traceId);
        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertNotEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        // a short wait is needed so the status stream can be hooked up
        // before the emitting the event from the sendStatus method
        Thread.sleep(500);
        ConcurrentFinisher.start(executor.getTraceId());

        final var update = GameCoinStatusUpdate.newBuilder()
                .setOauthId(OAUTH_ID)
                .setCurrencyId(CURRENCY_ID)
                .setGameCoinState(GameCoinState.ISSUED);
        gameCoinServer.getStatusStream().sendStatus(titleId, StatusUpdate.newBuilder()
                .setTraceId(executor.getTraceId())
                .setGameCoinUpdate(GameCoinUpdate.newBuilder().setStatusUpdate(update))
                .build());

        ConcurrentFinisher.wait(executor.getTraceId());

        assertEquals(OAUTH_ID, executor.getOauthId());
        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertEquals(GameCoinState.ISSUED, executor.getGameCoinState());
        assertEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        gameCoinServer.verifyCalls("StatusStream", 1);
        gameCoinServer.verifyCalls("StatusConfirmation", 1);
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.MINUTES)
    public void transferGameCoin() throws Exception {
        final var SOURCE = RandomStringUtils.randomAlphanumeric(30);
        final var DEST = RandomStringUtils.randomAlphanumeric(30);

        final var expectedResponse = ReceivedResponse.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(mockServiceBlockingStub.transferGameCoin(any())).thenReturn(expectedResponse);
        final var traceId = gameCoinClient.transferGameCoin(CURRENCY_ID, SOURCE, DEST, RandomUtils.nextInt(1, 1000));

        assertEquals(expectedResponse.getTraceId(), traceId);
        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertNotEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        // a short wait is needed so the status stream can be hooked up
        // before the emitting the event from the sendStatus method
        Thread.sleep(500);
        ConcurrentFinisher.start(executor.getTraceId());

        final var update = GameCoinStatusUpdate.newBuilder()
                .setOauthId(DEST)
                .setCurrencyId(CURRENCY_ID)
                .setGameCoinState(GameCoinState.TRANSFERRED);
        gameCoinServer.getStatusStream().sendStatus(titleId, StatusUpdate.newBuilder()
                .setTraceId(executor.getTraceId())
                .setGameCoinUpdate(GameCoinUpdate.newBuilder().setStatusUpdate(update))
                .build());

        ConcurrentFinisher.wait(executor.getTraceId());

        assertEquals(DEST, executor.getOauthId());
        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertEquals(GameCoinState.TRANSFERRED, executor.getGameCoinState());
        assertEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        gameCoinServer.verifyCalls("StatusStream", 1);
        gameCoinServer.verifyCalls("StatusConfirmation", 1);
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.MINUTES)
    public void burnGameCoin() throws Exception {
        final var expectedResponse = ReceivedResponse.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(mockServiceBlockingStub.burnGameCoin(any())).thenReturn(expectedResponse);
        final var traceId = gameCoinClient.burnGameCoin(CURRENCY_ID, OAUTH_ID, RandomUtils.nextInt(1, 1000));

        assertEquals(expectedResponse.getTraceId(), traceId);
        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertNotEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        // a short wait is needed so the status stream can be hooked up
        // before the emitting the event from the sendStatus method
        Thread.sleep(500);
        ConcurrentFinisher.start(executor.getTraceId());

        final var update = GameCoinStatusUpdate.newBuilder()
                .setOauthId(OAUTH_ID)
                .setCurrencyId(CURRENCY_ID)
                .setGameCoinState(GameCoinState.BURNED);
        gameCoinServer.getStatusStream().sendStatus(titleId, StatusUpdate.newBuilder()
                .setTraceId(executor.getTraceId())
                .setGameCoinUpdate(GameCoinUpdate.newBuilder().setStatusUpdate(update))
                .build());

        ConcurrentFinisher.wait(executor.getTraceId());

        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertEquals(GameCoinState.BURNED, executor.getGameCoinState());
        assertEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        gameCoinServer.verifyCalls("StatusStream", 1);
        gameCoinServer.verifyCalls("StatusConfirmation", 1);
    }
}