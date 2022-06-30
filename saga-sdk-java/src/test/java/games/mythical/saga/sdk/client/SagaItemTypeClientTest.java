package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.MockItemTypeExecutor;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.api.itemtype.ItemTypeProto;
import games.mythical.saga.sdk.proto.api.itemtype.ItemTypeServiceGrpc;
import games.mythical.saga.sdk.proto.api.itemtype.ItemTypesProto;
import games.mythical.saga.sdk.proto.common.ReceivedResponse;
import games.mythical.saga.sdk.proto.common.itemtype.ItemTypeState;
import games.mythical.saga.sdk.proto.streams.StatusUpdate;
import games.mythical.saga.sdk.proto.streams.itemtype.ItemTypeStatusUpdate;
import games.mythical.saga.sdk.proto.streams.itemtype.ItemTypeUpdate;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SagaItemTypeClientTest extends AbstractClientTest {
    private static final String ITEM_TYPE_ID = RandomStringUtils.randomAlphanumeric(30);

    private final MockItemTypeExecutor executor = MockItemTypeExecutor.builder().build();
    private MockServer itemTypeServer;
    private SagaItemTypeClient itemTypeClient;

    @Mock
    private ItemTypeServiceGrpc.ItemTypeServiceBlockingStub mockServiceBlockingStub;

    @BeforeEach
    void setUp() throws Exception {
        itemTypeServer = new MockServer(new MockStatusStreamingImpl());
        itemTypeServer.start();
        port = itemTypeServer.getPort();

        itemTypeClient = setUpFactory().createSagaItemTypeClient(executor);
        // mocking the service blocking stub clients are connected to
        FieldUtils.writeField(itemTypeClient, "serviceBlockingStub", mockServiceBlockingStub, true);
    }

    @AfterEach
    void tearDown() throws Exception {
        itemTypeClient.stop();
        // client shutdown is not immediate
        Thread.sleep(500);
        itemTypeServer.stop();
    }

    private ItemTypeProto generateItemTypeProto(String itemTypeId) {
        return ItemTypeProto.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .setItemTypeId(itemTypeId)
                .setName(RandomStringUtils.randomAlphanumeric(30))
                .setSymbol("symbol")
                .setMaxSupply(1000L)
                .setContractAddress("contract_address")
                .setBlockExplorerUrl("url_to_contract_address")
                .setFinalized(true)
                .setWithdrawable(false)
                .build();
    }

    @Test
    public void getItemType() throws Exception {
        var expectedResponse = generateItemTypeProto(ITEM_TYPE_ID);
        when(mockServiceBlockingStub.getItemType(any())).thenReturn(expectedResponse);
        var itemTypeResponse = itemTypeClient.getItemType(ITEM_TYPE_ID);

        assertNotNull(itemTypeResponse);
        assertEquals(ITEM_TYPE_ID, itemTypeResponse.getItemTypeId());
        assertEquals(expectedResponse.getName(), itemTypeResponse.getName());

        when(mockServiceBlockingStub.getItemType(any())).thenThrow(new StatusRuntimeException(Status.NOT_FOUND));
        assertThrows(SagaException.class, () -> itemTypeClient.getItemType("INVALID-ITEM-TYPE-ID"));
    }

    @Test
    public void getItemTypes() throws Exception {
        var proto_1 = generateItemTypeProto(ITEM_TYPE_ID);
        var proto_2 = generateItemTypeProto("item-type-2");
        var proto_3 = generateItemTypeProto("item-type-3");
        var expectedResponse = ItemTypesProto.newBuilder()
                .addAllItemTypes(List.of(proto_1, proto_2, proto_3))
                .build();

        when(mockServiceBlockingStub.getItemTypes(any())).thenReturn(expectedResponse);
        var itemTypeResponseList = itemTypeClient.getItemTypes("game-title", "");

        assertFalse(itemTypeResponseList.isEmpty());
        var itemType = itemTypeResponseList.get(0);
        assertEquals(ITEM_TYPE_ID, itemType.getItemTypeId());
        assertEquals(proto_1.getName(), itemType.getName());

        when(mockServiceBlockingStub.getItemTypes(any())).thenReturn(ItemTypesProto.getDefaultInstance());
        itemTypeResponseList = itemTypeClient.getItemTypes("", "");

        assertTrue(itemTypeResponseList.isEmpty());
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.MINUTES)
    public void createItemType() throws Exception {
        final var expectedResponse = ReceivedResponse.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(mockServiceBlockingStub.createItemType(any())).thenReturn(expectedResponse);
        final var traceId = itemTypeClient.createItemType(
                ITEM_TYPE_ID,
                RandomStringUtils.randomAlphanumeric(30),
                RandomStringUtils.randomAlphanumeric(30),
                RandomUtils.nextInt(0, 1000),
                false,
                null,
                false
        );

        checkTraceAndStart(expectedResponse, traceId);

        final var update = ItemTypeStatusUpdate.newBuilder()
                .setItemTypeId(ITEM_TYPE_ID)
                .setItemTypeState(ItemTypeState.CREATED);
        itemTypeServer.getStatusStream().sendStatus(titleId, StatusUpdate.newBuilder()
                .setTraceId(traceId)
                .setItemTypeUpdate(ItemTypeUpdate.newBuilder().setStatusUpdate(update))
                .build());

        ConcurrentFinisher.wait(traceId);

        assertEquals(ITEM_TYPE_ID, executor.getItemTypeId());
        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertEquals(ItemTypeState.CREATED, executor.getItemTypeState());
        assertEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        itemTypeServer.verifyCalls("StatusStream", 1);
        itemTypeServer.verifyCalls("StatusConfirmation", 1);
    }

    @Test
    public void updateItemType() throws Exception {
        final var expectedResponse = ReceivedResponse.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(mockServiceBlockingStub.updateItemType(any())).thenReturn(expectedResponse);
        final var traceId = itemTypeClient.updateItemType(ITEM_TYPE_ID, true);

    }

    @Test
    public void freezeItemType() throws Exception {
        final var expectedResponse = ReceivedResponse.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(mockServiceBlockingStub.freezeItemType(any())).thenReturn(expectedResponse);
        final var traceId = itemTypeClient.freezeItemType(ITEM_TYPE_ID);
    }
}