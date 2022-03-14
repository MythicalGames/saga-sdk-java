package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.MockBridgeExecutor;
import games.mythical.saga.sdk.proto.api.bridge.BridgeProto;
import games.mythical.saga.sdk.proto.api.bridge.BridgeServiceGrpc;
import games.mythical.saga.sdk.proto.common.ReceivedResponse;
import games.mythical.saga.sdk.proto.streams.bridge.BridgeStatusUpdate;
import games.mythical.saga.sdk.server.MockServer;
import games.mythical.saga.sdk.server.stream.MockBridgeStreamingImpl;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SagaBridgeClientTest extends AbstractClientTest {
    private static final String OAUTH_ID = UUID.randomUUID().toString();

    private final MockBridgeExecutor executor = MockBridgeExecutor.builder().build();
    private MockServer bridgeServer;
    private SagaBridgeClient bridgeClient;

    @Mock
    private BridgeServiceGrpc.BridgeServiceBlockingStub mockServiceBlockingStub;

    @BeforeEach
    void setUp() throws Exception {
        bridgeServer = new MockServer(new MockBridgeStreamingImpl());
        bridgeServer.start();
        port = bridgeServer.getPort();

        bridgeClient = setUpFactory().createSagaBridgeClient(executor);
        // mocking the service blocking stub clients are connected to
        FieldUtils.writeField(bridgeClient, "serviceBlockingStub", mockServiceBlockingStub, true);
    }

    @AfterEach
    void tearDown() {
        bridgeServer.stop();
    }

    @Test
    public void getBridge() throws Exception {
        var expectedResponse = BridgeProto.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .setMythicalAddress(RandomStringUtils.randomAlphanumeric(30))
                .setMainnetAddress(RandomStringUtils.randomAlphanumeric(30))
                .setChainName(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(mockServiceBlockingStub.getBridge(any())).thenReturn(expectedResponse);

        var bridgeResponse = bridgeClient.getBridge();

        assertTrue(bridgeResponse.isPresent());
        var bridge = bridgeResponse.get();
        assertEquals(expectedResponse.getTraceId(), bridge.getTraceId());
        assertEquals(expectedResponse.getMythicalAddress(), bridge.getMythicalAddress());
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.MINUTES)
    public void withdrawItem() throws Exception {
        final var expectedResponse = ReceivedResponse.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(mockServiceBlockingStub.withdrawItem(any())).thenReturn(expectedResponse);

        bridgeClient.withdrawItem(
                OAUTH_ID,
                RandomStringUtils.randomAlphanumeric(30),
                RandomStringUtils.randomAlphanumeric(30),
                RandomStringUtils.randomAlphanumeric(30),
                RandomStringUtils.randomAlphanumeric(30),
                RandomStringUtils.randomAlphanumeric(30)
        );

        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertNotEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        // a short wait is needed so the status stream can be hooked up
        // before the emitting the event from the sendStatus method
        Thread.sleep(500);
        ConcurrentFinisher.start(executor.getTraceId());

        var statusUpdate = BridgeStatusUpdate.newBuilder()
                .setTraceId(expectedResponse.getTraceId())
                .setOauthId(OAUTH_ID)
                .setItemTypeAddress(RandomStringUtils.randomAlphanumeric(30))
                .setItemAddress(RandomStringUtils.randomAlphanumeric(30))
                .setDestinationAddress(RandomStringUtils.randomAlphanumeric(30))
                .setDestinationChain(RandomStringUtils.randomAlphanumeric(30))
                .setOriginAddress(RandomStringUtils.randomAlphanumeric(30))
                .setMythicalTransactionId(RandomStringUtils.randomAlphanumeric(30))
                .setMainnetTransactionId(RandomStringUtils.randomAlphanumeric(30))
                .build();
        bridgeServer.getBridgeStream().sendStatus(titleId, statusUpdate, null);

        ConcurrentFinisher.wait(executor.getTraceId());

        assertEquals(OAUTH_ID, executor.getOauthId());
        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        bridgeServer.verifyCalls("BridgeStatusStream", 1);
        bridgeServer.verifyCalls("BridgeStatusConfirmation", 1);
    }
}