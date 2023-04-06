package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.*;
import games.mythical.saga.sdk.exception.SagaException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SagaClientFactoryTest extends AbstractClientTest {

    private SagaClientFactory factory;
    private static final List<AbstractSagaStreamClient> clientCleanupTargets = new LinkedList<>();

    @BeforeEach
    public void beforeEach() throws SagaException {
        port = 443;
        factory = setUpFactory();
    }

    @AfterAll
    public static void cleanup() throws InterruptedException {
        for (var client : clientCleanupTargets) {
            client.stop();
            client.channel.awaitTermination(5, TimeUnit.SECONDS);
        }
    }

    @Test
    public void testCreateCurrencyClient() throws SagaException {
        var client_1 = factory.createSagaCurrencyClient();
        clientCleanupTargets.add(client_1);
        assertNotNull(client_1);
        var client_2 = factory.createSagaCurrencyClient(MockCurrencyExecutor.builder().build());
        clientCleanupTargets.add(client_2);
        assertNotNull(client_2);
    }

    @Test
    public void testCreateCurrencyTypeClient() throws SagaException {
        var client_1 = factory.createSagaCurrencyTypeClient();
        clientCleanupTargets.add(client_1);
        assertNotNull(client_1);
    }

    @Test
    public void testCreateItemClient() throws SagaException {
        var client_1 = factory.createSagaItemClient();
        clientCleanupTargets.add(client_1);
        assertNotNull(client_1);
        var client_2 = factory.createSagaItemClient(MockItemExecutor.builder().build());
        clientCleanupTargets.add(client_2);
        assertNotNull(client_2);
    }

    @Test
    public void testCreateItemTypeClient() throws SagaException {
        var client_1 = factory.createSagaItemTypeClient();
        clientCleanupTargets.add(client_1);
        assertNotNull(client_1);
        var client_2 = factory.createSagaItemTypeClient(MockItemTypeExecutor.builder().build());
        clientCleanupTargets.add(client_2);
        assertNotNull(client_2);
    }

    @Test
    public void testCreateMetadataClient() throws SagaException {
        var client_1 = factory.createSagaMetadataClient();
        clientCleanupTargets.add(client_1);
        assertNotNull(client_1);
        var client_2 = factory.createSagaMetadataClient(MockMetadataExecutor.builder().build());
        clientCleanupTargets.add(client_2);
        assertNotNull(client_2);
    }

    @Test
    public void testCreatePlayerWalletClient() throws SagaException {
        var client_1 = factory.createSagaPlayerWalletClient();
        clientCleanupTargets.add(client_1);
        assertNotNull(client_1);
        var client_2 = factory.createSagaPlayerWalletClient(new MockPlayerWalletExecutor());
        clientCleanupTargets.add(client_2);
        assertNotNull(client_2);
    }

    @Test
    public void testCreateReservationClient() throws SagaException {
        var client_1 = factory.createSagaReservationClient();
        clientCleanupTargets.add(client_1);
        assertNotNull(client_1);
        var client_2 = factory.createSagaReservationClient(new MockReservationExecutor());
        clientCleanupTargets.add(client_2);
        assertNotNull(client_2);
    }
}
