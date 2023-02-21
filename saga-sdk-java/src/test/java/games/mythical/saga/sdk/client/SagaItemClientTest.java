package games.mythical.saga.sdk.client;

import com.google.protobuf.util.Timestamps;
import games.mythical.saga.sdk.client.executor.MockItemExecutor;
import games.mythical.saga.sdk.client.model.SagaIssueItem;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.api.item.ItemProto;
import games.mythical.saga.sdk.proto.api.item.ItemServiceGrpc;
import games.mythical.saga.sdk.proto.common.ReceivedResponse;
import games.mythical.saga.sdk.proto.common.item.BlockChains;
import games.mythical.saga.sdk.proto.common.item.ItemState;
import games.mythical.saga.sdk.proto.streams.StatusUpdate;
import games.mythical.saga.sdk.proto.streams.item.ItemStatusUpdate;
import games.mythical.saga.sdk.proto.streams.item.ItemStatusUpdates;
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
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SagaItemClientTest extends AbstractClientTest {
    private static final String INVENTORY_ID = RandomStringUtils.randomAlphanumeric(30);

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
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .setInventoryId(INVENTORY_ID)
                .setOauthId("owner")
                .setTokenId(RandomUtils.nextLong(10, 100))
                .setFinalized(true)
                .setBlockExplorerUrl("block-explorer-url")
                .setMetadataUrl("metadata-url")
                .setCreatedAt(Timestamps.fromMillis(Instant.now().toEpochMilli() - 86400))
                .setUpdatedAt(Timestamps.fromMillis(Instant.now().toEpochMilli()))
                .build();
        when(mockServiceBlockingStub.getItem(any())).thenReturn(expectedResponse);
        var itemResponse = itemClient.getItem(INVENTORY_ID);

        assertNotNull(itemResponse);
        assertEquals(INVENTORY_ID, itemResponse.getInventoryId());

        when(mockServiceBlockingStub.getItem(any())).thenThrow(new StatusRuntimeException(Status.NOT_FOUND));
        assertThrows(SagaException.class, () -> itemClient.getItem("INVALID-ITEM-ID"));
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
                Collections.singletonList(SagaIssueItem.builder().inventoryId(INVENTORY_ID).metadata(EXPECTED_METADATA).build()),
                EXPECTED_OAUTH_ID,
                RandomStringUtils.randomAlphanumeric(30));
        checkTraceAndStart(expectedResponse, traceId);

        final var update = ItemStatusUpdate.newBuilder()
                .setInventoryId(INVENTORY_ID)
                .setOauthId(EXPECTED_OAUTH_ID)
                .setItemState(ItemState.ISSUED);
        final var updates = ItemStatusUpdates.newBuilder().addStatusUpdates(update).build();
        itemServer.getStatusStream().sendStatus(titleId, StatusUpdate.newBuilder()
                .setTraceId(traceId)
                .setItemUpdate(ItemUpdate.newBuilder().setStatusUpdates(updates))
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
        final var DEST = RandomStringUtils.randomAlphanumeric(30);

        final var expectedResponse = ReceivedResponse.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(mockServiceBlockingStub.transferItem(any())).thenReturn(expectedResponse);
        final var traceId = itemClient.transferItem(INVENTORY_ID, DEST, false);
        checkTraceAndStart(expectedResponse, traceId);

        final var update = ItemStatusUpdate.newBuilder()
            .setInventoryId(INVENTORY_ID)
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
        final var traceId = itemClient.burnItem(INVENTORY_ID, false);
        checkTraceAndStart(expectedResponse, traceId);

        final var update = ItemStatusUpdate.newBuilder()
                .setInventoryId(INVENTORY_ID)
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
                INVENTORY_ID,
                RandomStringUtils.random(30),
                RandomStringUtils.randomAlphanumeric(30),
                RandomStringUtils.randomAlphanumeric(30),
                BlockChains.ETH,
                RandomStringUtils.randomAlphanumeric(30)
        );

        checkTraceAndStart(expectedResponse, traceId);


        final var update = ItemStatusUpdate.newBuilder()
                .setInventoryId(INVENTORY_ID)
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
}