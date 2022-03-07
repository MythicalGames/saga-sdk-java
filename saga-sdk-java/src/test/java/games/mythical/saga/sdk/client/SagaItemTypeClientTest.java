package games.mythical.saga.sdk.client;

import com.google.protobuf.Empty;
import games.mythical.saga.sdk.client.executor.MockItemTypeExecutor;
import games.mythical.saga.sdk.client.model.SagaMetadata;
import games.mythical.saga.sdk.proto.api.itemtype.*;
import games.mythical.saga.sdk.proto.common.ReceivedResponse;
import games.mythical.saga.sdk.proto.common.itemtype.ItemTypeState;
import games.mythical.saga.sdk.server.itemtype.MockItemTypeServer;
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
class SagaItemTypeClientTest extends AbstractClientTest {
    private static final String GAME_ITEM_TYPE_ID = RandomStringUtils.randomAlphanumeric(30);

    private final MockItemTypeExecutor executor = MockItemTypeExecutor.builder().build();
    private MockItemTypeServer itemTypeServer;
    private SagaItemTypeClient itemTypeClient;

    @Mock
    private ItemTypeServiceGrpc.ItemTypeServiceBlockingStub mockServiceBlockingStub;

    @BeforeEach
    void setUp() throws Exception {
        itemTypeServer = new MockItemTypeServer();
        itemTypeServer.start();
        port = itemTypeServer.getPort();

        itemTypeClient = setUpFactory().createSagaItemTypeClient(executor);
        // mocking the service blocking stub clients are connected to
        FieldUtils.writeField(itemTypeClient, "serviceBlockingStub", mockServiceBlockingStub, true);
    }

    @AfterEach
    void tearDown() {
        itemTypeServer.stop();
    }

    @Test
    public void getItemType() throws Exception {
        var expectedResponse = ItemTypeProto.newBuilder()
                .setGameItemTypeId(GAME_ITEM_TYPE_ID)
                .setName(RandomStringUtils.randomAlphanumeric(30))
                .setAddress(RandomStringUtils.randomAlphanumeric(30))
                .setTitleId(RandomStringUtils.randomAlphanumeric(30))
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .setPriRevShareSettings(PriRevShareSettings.newBuilder().build())
                .setSecRevShareSettings(SecRevShareSettings.newBuilder().build())
                .setWithdrawable(true)
                .setPriceMap(PriceMap.newBuilder().build())
                .setItemTypeState(ItemTypeState.forNumber(RandomUtils.nextInt(0, ItemTypeState.values().length)))
                .setMetadata(SagaMetadata.toProto(generateItemMetadata()))
                .setCreatedTimestamp(Instant.now().toEpochMilli() - 86400)
                .setUpdatedTimestamp(Instant.now().toEpochMilli())
                .build();
        when(mockServiceBlockingStub.getItemType(any())).thenReturn(expectedResponse);
        var itemTypeResponse = itemTypeClient.getItemType(GAME_ITEM_TYPE_ID);

        assertTrue(itemTypeResponse.isPresent());
        var itemType = itemTypeResponse.get();
        assertEquals(GAME_ITEM_TYPE_ID, itemType.getGameItemTypeId());
        assertEquals(expectedResponse.getName(), itemType.getName());
        assertEquals(expectedResponse.getTitleId(), itemType.getTitleId());

        when(mockServiceBlockingStub.getItemType(any())).thenThrow(new StatusRuntimeException(Status.NOT_FOUND));
        itemTypeResponse = itemTypeClient.getItemType("INVALID-ITEM-TYPE-ID");

        assertTrue(itemTypeResponse.isEmpty());
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.MINUTES)
    public void createItemType() throws Exception {
        final var EXPECTED_METADATA = generateItemMetadata();

        final var expectedResponse = ReceivedResponse.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(mockServiceBlockingStub.createItemType(any())).thenReturn(expectedResponse);
        itemTypeClient.createItemType(GAME_ITEM_TYPE_ID, false, EXPECTED_METADATA);

        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertNotEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        // a short wait is needed so the status stream can be hooked up
        // before the emitting the event from the sendStatus method
        Thread.sleep(500);
        ConcurrentFinisher.start(executor.getTraceId());

        itemTypeServer.getItemTypeStream().sendStatus(titleId, ItemTypeProto.newBuilder()
                .setGameItemTypeId(GAME_ITEM_TYPE_ID)
                .setTraceId(executor.getTraceId())
                .build(), ItemTypeState.CREATED);

        ConcurrentFinisher.wait(executor.getTraceId());

        assertEquals(GAME_ITEM_TYPE_ID, executor.getGameItemTypeId());
        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertEquals(ItemTypeState.CREATED, executor.getItemTypeState());
        assertEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        itemTypeServer.verifyCalls("ItemTypeStatusStream", 1);
        itemTypeServer.verifyCalls("ItemTypeStatusConfirmation", 1);
    }

    @Test
    public void updateItemType() throws Exception {
        when(mockServiceBlockingStub.updateItemType(any())).thenReturn(Empty.getDefaultInstance());
        itemTypeClient.updateItemType(GAME_ITEM_TYPE_ID, true);
    }

    @Test
    public void updateItemTypeMetadata() throws Exception {
        final var EXPECTED_METADATA = generateItemMetadata();

        when(mockServiceBlockingStub.updateItemTypeMetadata(any())).thenReturn(Empty.getDefaultInstance());
        itemTypeClient.updateItemTypeMetadata(GAME_ITEM_TYPE_ID, EXPECTED_METADATA);
    }
}