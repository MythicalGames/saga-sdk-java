package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.MockNftBridgeExecutor;
import games.mythical.saga.sdk.proto.api.nftbridge.NftBridgeProto;
import games.mythical.saga.sdk.proto.api.nftbridge.NftBridgeServiceGrpc;
import games.mythical.saga.sdk.proto.api.nftbridge.QuoteBridgeNFTResponse;
import games.mythical.saga.sdk.proto.common.ReceivedResponse;
import games.mythical.saga.sdk.proto.streams.StatusUpdate;
import games.mythical.saga.sdk.proto.streams.nftbridge.NftBridgeStatusUpdate;
import games.mythical.saga.sdk.proto.streams.nftbridge.NftBridgeUpdate;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SagaNftBridgeClientTest extends AbstractClientTest {
    private static final String OAUTH_ID = UUID.randomUUID().toString();

    private final MockNftBridgeExecutor executor = MockNftBridgeExecutor.builder().build();
    private MockServer bridgeServer;
    private SagaNftBridgeClient bridgeClient;

    @Mock
    private NftBridgeServiceGrpc.NftBridgeServiceBlockingStub mockServiceBlockingStub;

    @BeforeEach
    void setUp() throws Exception {
        bridgeServer = new MockServer(new MockStatusStreamingImpl());
        bridgeServer.start();
        port = bridgeServer.getPort();

        bridgeClient = setUpFactory().createSagaBridgeClient(executor);
        // mocking the service blocking stub clients are connected to
        FieldUtils.writeField(bridgeClient, "serviceBlockingStub", mockServiceBlockingStub, true);
    }

    @AfterEach
    void tearDown() throws Exception {
        bridgeClient.stop();
        // client shutdown is not immediate
        Thread.sleep(500);

        bridgeServer.stop();
    }

    @Test
    public void getBridge() throws Exception {
        var expectedResponse = NftBridgeProto.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .setMythicalAddress(RandomStringUtils.randomAlphanumeric(30))
                .setMainnetAddress(RandomStringUtils.randomAlphanumeric(30))
                .setChainName(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(mockServiceBlockingStub.getBridge(any())).thenReturn(expectedResponse);

        var bridgeResponse = bridgeClient.getNftBridge();

        assertNotNull(bridgeResponse);
        assertEquals(expectedResponse.getTraceId(), bridgeResponse.getTraceId());
        assertEquals(expectedResponse.getMythicalAddress(), bridgeResponse.getMythicalAddress());
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.MINUTES)
    public void withdrawItem() throws Exception {
        final var expectedResponse = ReceivedResponse.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(mockServiceBlockingStub.withdrawItem(any())).thenReturn(expectedResponse);

        final var traceId = bridgeClient.withdrawItem(
                10,
                11,
                RandomStringUtils.randomAlphanumeric(30),
                RandomStringUtils.randomAlphanumeric(30),
                RandomStringUtils.randomAlphanumeric(30),
                RandomStringUtils.randomAlphanumeric(30),
                RandomStringUtils.randomAlphanumeric(30),
                RandomStringUtils.randomAlphanumeric(30),
                RandomStringUtils.randomAlphanumeric(30),
                RandomStringUtils.randomAlphanumeric(30)
        );
        checkTraceAndStart(expectedResponse, traceId);

        final var update = NftBridgeStatusUpdate.newBuilder()
            .setOauthId(OAUTH_ID)
            .setItemTypeId(RandomStringUtils.randomAlphanumeric(30))
            .setInventoryId(RandomStringUtils.randomAlphanumeric(30))
            .setDestinationAddress(RandomStringUtils.randomAlphanumeric(30))
            .setDestinationChain(RandomStringUtils.randomAlphanumeric(30))
            .setOriginAddress(RandomStringUtils.randomAlphanumeric(30))
            .setMythicalTransactionId(RandomStringUtils.randomAlphanumeric(30))
            .setMainnetTransactionId(RandomStringUtils.randomAlphanumeric(30))
            .build();
        var statusUpdate = StatusUpdate.newBuilder()
                .setTraceId(traceId)
                .setNftBridgeUpdate(NftBridgeUpdate.newBuilder()
                            .setStatusUpdate(update)
                            .build())
                .build();
        bridgeServer.getStatusStream().sendStatus(titleId, statusUpdate);

        ConcurrentFinisher.wait(traceId);

        assertEquals(OAUTH_ID, executor.getOauthId());
        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        bridgeServer.verifyCalls("StatusStream", 1);
        bridgeServer.verifyCalls("StatusConfirmation", 1);
    }

    @Test
    public void getBridgeQuote() throws Exception {
        var expectedResponse = QuoteBridgeNFTResponse.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .setFeeInOriginchainNativeToken("100")
                .setFeeInOriginchainNativeTokenUnit("wei")
                .setExpiresAt("100000")
                .setFeeInUsd("100")
                .setGasPriceTargetchain("100")
                .setGasPriceOriginchainUnit("gwei")
                .setGasPriceTargetchainUnit("100")
                .setGasPriceTargetchainUnit("gwei")
                .setSignature(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(mockServiceBlockingStub.getBridgeQuote(any())).thenReturn(expectedResponse);

        var bridgeResponse = bridgeClient.getNftBridgeQuote(
                100,
                100,
                RandomStringUtils.randomAlphanumeric(30),
                RandomStringUtils.randomAlphanumeric(30),
                RandomStringUtils.randomAlphanumeric(30)
        );

        assertNotNull(bridgeResponse);
        assertEquals(expectedResponse.getFeeInOriginchainNativeToken(), bridgeResponse.getFeeInOriginchainNativeToken());
    }
}