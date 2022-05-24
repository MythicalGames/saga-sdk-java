package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.MockItemExecutor;
import games.mythical.saga.sdk.client.model.SagaMetadata;
import games.mythical.saga.sdk.proto.api.item.ItemProto;
import games.mythical.saga.sdk.proto.api.item.ItemServiceGrpc;
import games.mythical.saga.sdk.proto.common.ReceivedResponse;
import games.mythical.saga.sdk.proto.common.item.BlockChains;
import games.mythical.saga.sdk.proto.common.item.ItemState;
import games.mythical.saga.sdk.proto.streams.StatusUpdate;
import games.mythical.saga.sdk.proto.streams.item.ItemStatusUpdate;
import games.mythical.saga.sdk.proto.streams.item.ItemUpdate;
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
class SagaItemClientTest extends AbstractClientTest {
    private static final String GAME_INVENTORY_ID = RandomStringUtils.randomAlphanumeric(30);

    private final MockItemExecutor executor = MockItemExecutor.builder().build();
    private MockServer itemServer;
    private SagaItemClient itemClient;

    @Mock
    private ItemServiceGrpc.ItemServiceBlockingStub mockServiceBlockingStub;

    @BeforeEach
    void setUp() throws Exception {
        itemServer = new MockServer(new MockStatusStreamingImpl());
        itemServer.start();
        port = itemServer.getPort();

        itemClient = setUpFactory().createSagaItemClient(executor);
        // mocking the service blocking stub clients are connected to
        FieldUtils.writeField(itemClient, "serviceBlockingStub", mockServiceBlockingStub, true);
    }

    @AfterEach
    void tearDown() throws Exception {
        itemClient.stop();
        // client shutdown is not immediate
        Thread.sleep(500);
        itemServer.stop();
    }

    @Test
    public void getItem() throws Exception {
        var expectedResponse = ItemProto.newBuilder()
                .setGameInventoryId(GAME_INVENTORY_ID)
                .setGameItemTypeId(RandomStringUtils.randomAlphanumeric(30))
                .setOauthId(RandomStringUtils.randomAlphanumeric(30))
                .setSerialNumber(RandomUtils.nextInt(10, 100))
                .setMetadataUri(RandomStringUtils.randomAlphanumeric(30))
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .setMetadata(SagaMetadata.toProto(generateItemMetadata()))
                .setItemState(ItemState.forNumber(RandomUtils.nextInt(0, ItemState.values().length - 1)))
                .setCreatedTimestamp(Instant.now().toEpochMilli() - 86400)
                .setUpdatedTimestamp(Instant.now().toEpochMilli())
                .build();
        when(mockServiceBlockingStub.getItem(any())).thenReturn(expectedResponse);
        var itemResponse = itemClient.getItem(GAME_INVENTORY_ID, false);

        assertTrue(itemResponse.isPresent());
        var item = itemResponse.get();
        assertEquals(GAME_INVENTORY_ID, item.getGameInventoryId());
        assertEquals(expectedResponse.getOauthId(), item.getOauthId());

        when(mockServiceBlockingStub.getItem(any())).thenThrow(new StatusRuntimeException(Status.NOT_FOUND));
        itemResponse = itemClient.getItem("INVALID-ITEM-ID", false);

        assertTrue(itemResponse.isEmpty());
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.MINUTES)
    public void issueItem() throws Exception {
        final var EXPECTED_OAUTH_ID = RandomStringUtils.randomAlphanumeric(30);
        final var EXPECTED_METADATA = generateItemMetadata();

        final var expectedResponse = ReceivedResponse.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(mockServiceBlockingStub.issueItem(any())).thenReturn(expectedResponse);
        final var traceId = itemClient.issueItem(
                GAME_INVENTORY_ID,
                EXPECTED_OAUTH_ID,
                RandomStringUtils.randomAlphanumeric(30),
                EXPECTED_METADATA,
                null, null, null
        );
        checkTraceAndStart(expectedResponse, traceId);

        final var update = ItemStatusUpdate.newBuilder()
                .setGameInventoryId(GAME_INVENTORY_ID)
                .setOauthId(EXPECTED_OAUTH_ID)
                .setItemState(ItemState.ISSUED);
        itemServer.getStatusStream().sendStatus(titleId, StatusUpdate.newBuilder()
                .setTraceId(traceId)
                .setItemUpdate(ItemUpdate.newBuilder().setStatusUpdate(update))
                .build());

        ConcurrentFinisher.wait(traceId);

        assertEquals(EXPECTED_OAUTH_ID, executor.getOauthId());
        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertEquals(ItemState.ISSUED, executor.getItemState());
        assertEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        itemServer.verifyCalls("StatusStream", 1);
        itemServer.verifyCalls("StatusConfirmation", 1);
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.MINUTES)
    public void transferItem() throws Exception {
        final var SOURCE = RandomStringUtils.randomAlphanumeric(30);
        final var DEST = RandomStringUtils.randomAlphanumeric(30);

        final var expectedResponse = ReceivedResponse.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(mockServiceBlockingStub.transferItem(any())).thenReturn(expectedResponse);
        final var traceId = itemClient.transferItem(GAME_INVENTORY_ID, SOURCE, DEST, null);
        checkTraceAndStart(expectedResponse, traceId);

        final var update = ItemStatusUpdate.newBuilder()
            .setGameInventoryId(GAME_INVENTORY_ID)
            .setOauthId(DEST)
            .setItemState(ItemState.TRANSFERRED);
        itemServer.getStatusStream().sendStatus(titleId, StatusUpdate.newBuilder()
                .setTraceId(traceId)
                .setItemUpdate(ItemUpdate.newBuilder().setStatusUpdate(update))
                .build());

        ConcurrentFinisher.wait(traceId);

        assertEquals(DEST, executor.getOauthId());
        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertEquals(ItemState.TRANSFERRED, executor.getItemState());
        assertEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        itemServer.verifyCalls("StatusStream", 1);
        itemServer.verifyCalls("StatusConfirmation", 1);
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.MINUTES)
    public void burnItem() throws Exception {
        final var expectedResponse = ReceivedResponse.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(mockServiceBlockingStub.burnItem(any())).thenReturn(expectedResponse);
        final var traceId = itemClient.burnItem(GAME_INVENTORY_ID);
        checkTraceAndStart(expectedResponse, traceId);

        final var update = ItemStatusUpdate.newBuilder()
                .setGameInventoryId(GAME_INVENTORY_ID)
                .setOauthId(RandomStringUtils.randomAlphanumeric(30))
                .setItemState(ItemState.BURNED);
        itemServer.getStatusStream().sendStatus(titleId, StatusUpdate.newBuilder()
                .setTraceId(traceId)
                .setItemUpdate(ItemUpdate.newBuilder().setStatusUpdate(update))
                .build());

        ConcurrentFinisher.wait(traceId);

        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertEquals(ItemState.BURNED, executor.getItemState());
        assertEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        itemServer.verifyCalls("StatusStream", 1);
        itemServer.verifyCalls("StatusConfirmation", 1);
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.MINUTES)
    public void depositItem() throws Exception {
        final var expectedResponse = ReceivedResponse.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(mockServiceBlockingStub.depositItem(any())).thenReturn(expectedResponse);
        final var traceId = itemClient.depositItem(
                GAME_INVENTORY_ID,
                RandomStringUtils.random(30),
                RandomStringUtils.randomAlphanumeric(30),
                RandomStringUtils.randomAlphanumeric(30),
                BlockChains.ETH,
                RandomStringUtils.randomAlphanumeric(30)
        );

        checkTraceAndStart(expectedResponse, traceId);


        final var update = ItemStatusUpdate.newBuilder()
                .setGameInventoryId(GAME_INVENTORY_ID)
                .setOauthId(RandomStringUtils.randomAlphanumeric(30))
                .setItemState(ItemState.DEPOSITED);
        itemServer.getStatusStream().sendStatus(titleId, StatusUpdate.newBuilder()
                .setTraceId(traceId)
                .setItemUpdate(ItemUpdate.newBuilder().setStatusUpdate(update))
                .build());

        ConcurrentFinisher.wait(traceId);

        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertEquals(ItemState.DEPOSITED, executor.getItemState());
        assertEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        itemServer.verifyCalls("StatusStream", 1);
        itemServer.verifyCalls("StatusConfirmation", 1);
    }

    @Test
    public void updateItemMetadata() throws Exception {
        final var EXPECTED_METADATA = generateItemMetadata();

        final var expectedResponse = ReceivedResponse.newBuilder()
            .setTraceId(RandomStringUtils.randomAlphanumeric(30))
            .build();
        when(mockServiceBlockingStub.updateItemsMetadata(any())).thenReturn(expectedResponse);
        itemClient.updateItemMetadata(GAME_INVENTORY_ID, EXPECTED_METADATA);
    }
}