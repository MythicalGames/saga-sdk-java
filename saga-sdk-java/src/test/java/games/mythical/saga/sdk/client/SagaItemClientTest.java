package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.MockItemExecutor;
import games.mythical.saga.sdk.client.model.SagaObject;
import games.mythical.saga.sdk.server.item.MockItemServer;
import io.grpc.ManagedChannelBuilder;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SagaItemClientTest extends AbstractClientTest {
    private MockItemServer itemServer;
    private MockItemExecutor itemExecutor;
    private SagaItemClient itemClient;
    private Map<String, SagaObject> items;

    @BeforeEach
    void setUp() throws Exception {
        itemServer = new MockItemServer();
        itemServer.start();
        port = itemServer.getPort();
        setUpConfig();

//        items = generateItems(3);
        items = new HashMap<>();
        items.put("temp", new SagaObject());
        itemServer.getItemService().setItems(items.values());

        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();

        itemExecutor = MockItemExecutor.builder().build();
        itemClient = new SagaItemClient(itemExecutor, channel);
    }

    @AfterEach
    void tearDown() {
        itemServer.stop();
    }

    @Test
    public void getItem() throws Exception {
        var gameInventoryId = items.keySet().iterator().next();

        var itemResponse = itemClient.getItem(gameInventoryId, false);

        assertTrue(itemResponse.isPresent());
        var item = itemResponse.get();
//        assertEquals(gameInventoryId, item.getGameInventoryId());

        gameInventoryId = RandomStringUtils.randomAlphanumeric(30);
        itemResponse = itemClient.getItem(gameInventoryId, false);

        assertTrue(itemResponse.isEmpty());

        itemServer.verifyCalls("GetItem", 2);
    }
}