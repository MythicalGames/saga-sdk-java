package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.MockBridgeExecutor;
import games.mythical.saga.sdk.client.executor.MockGameCoinExecutor;
import games.mythical.saga.sdk.proto.api.bridge.BridgeServiceGrpc;
import games.mythical.saga.sdk.proto.api.gamecoin.GameCoinServiceGrpc;
import games.mythical.saga.sdk.proto.streams.StatusUpdate;
import games.mythical.saga.sdk.proto.streams.bridge.BridgeStatusUpdate;
import games.mythical.saga.sdk.proto.streams.bridge.BridgeUpdate;
import games.mythical.saga.sdk.proto.streams.gamecoin.GameCoinStatusUpdate;
import games.mythical.saga.sdk.proto.streams.gamecoin.GameCoinUpdate;
import games.mythical.saga.sdk.server.MockServer;
import games.mythical.saga.sdk.server.stream.MockStatusStreamingImpl;
import games.mythical.saga.sdk.util.ConcurrentFinisher;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SagaMultiClientTest extends AbstractClientTest {
    private static final String OAUTH_ID = UUID.randomUUID().toString();

    private final MockBridgeExecutor bridgeExecutor = MockBridgeExecutor.builder().build();
    private final MockGameCoinExecutor gameCoinExecutor = MockGameCoinExecutor.builder().build();
    private MockServer mockServer;
    private SagaBridgeClient bridgeClient;
    private SagaGameCoinClient gameCoinClient;

    @Mock
    private BridgeServiceGrpc.BridgeServiceBlockingStub mockBridgeService;

    @Mock
    private GameCoinServiceGrpc.GameCoinServiceBlockingStub mockGameCoinService;

    @BeforeEach
    void setUp() throws Exception {
        mockServer = new MockServer(new MockStatusStreamingImpl());
        mockServer.start();
        port = mockServer.getPort();

        bridgeClient = setUpFactory().createSagaBridgeClient(bridgeExecutor);
        gameCoinClient = setUpFactory().createSagaGameCoinClient(gameCoinExecutor);

        FieldUtils.writeField(bridgeClient, "serviceBlockingStub", mockBridgeService, true);
        FieldUtils.writeField(gameCoinClient, "serviceBlockingStub", mockGameCoinService, true);
    }

    @AfterEach
    void tearDown() throws Exception {
        bridgeClient.stop();
        gameCoinClient.stop();
        // client shutdown is not immediate
        Thread.sleep(500);

        mockServer.stop();
    }

    /**
     * Here we have two clients that share a single event stream, the first client will establish the connection
     * while the second adds onto it new executor. From the single event to single observer, it'll proxy
     * the request to the correct executor.
     */
    @Test
    @Timeout(value = 1, unit = TimeUnit.MINUTES)
    public void multiClientEventTest() throws Exception {
        final var TRACE_ID_1 = RandomStringUtils.randomAlphanumeric(30);
        final var TRACE_ID_2 = RandomStringUtils.randomAlphanumeric(30);

        Thread.sleep(500);
        ConcurrentFinisher.start(TRACE_ID_1);
        ConcurrentFinisher.start(TRACE_ID_2);

        // firing a bridge event so bridge executor should catch it
        final var update = BridgeStatusUpdate.newBuilder()
            .setOauthId(OAUTH_ID)
            .setGameItemTypeId(RandomStringUtils.randomAlphanumeric(30));
        var statusUpdate = StatusUpdate.newBuilder()
                .setTraceId(TRACE_ID_2)
                .setBridgeUpdate(BridgeUpdate.newBuilder().setStatusUpdate(update))
                .build();
        mockServer.getStatusStream().sendStatus(titleId, statusUpdate);

        ConcurrentFinisher.wait(TRACE_ID_2);

        assertEquals(OAUTH_ID, bridgeExecutor.getOauthId());
        assertEquals(TRACE_ID_2, bridgeExecutor.getTraceId());
        assertEquals(Boolean.TRUE, ConcurrentFinisher.get(bridgeExecutor.getTraceId()));
        assertNull(gameCoinExecutor.getTraceId());

        // make sure the network calls all align
        mockServer.verifyCalls("StatusStream", 1);
        mockServer.verifyCalls("StatusConfirmation", 1);

        // make sure no other stream is catching this game coin event
        final var update2 = GameCoinStatusUpdate.newBuilder()
            .setOauthId(OAUTH_ID)
            .setCurrencyId(RandomStringUtils.randomAlphanumeric(30));
        var statusUpdate2 = StatusUpdate.newBuilder()
                .setTraceId(TRACE_ID_1)
                .setGameCoinUpdate(GameCoinUpdate.newBuilder().setStatusUpdate(update2))
                .build();
        mockServer.getStatusStream().sendStatus(titleId, statusUpdate2);

        ConcurrentFinisher.wait(TRACE_ID_1);

        assertEquals(OAUTH_ID, gameCoinExecutor.getOauthId());
        assertEquals(TRACE_ID_1, gameCoinExecutor.getTraceId());
        assertEquals(Boolean.TRUE, ConcurrentFinisher.get(gameCoinExecutor.getTraceId()));
        assertNotEquals(bridgeExecutor.getTraceId(), gameCoinExecutor.getTraceId());

        // using the existing stream, confirm the game coin event
        mockServer.verifyCalls("StatusStream", 1);
        mockServer.verifyCalls("StatusConfirmation", 2);
    }
}