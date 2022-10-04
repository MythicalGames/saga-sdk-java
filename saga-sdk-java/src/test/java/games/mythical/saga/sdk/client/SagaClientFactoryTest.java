package games.mythical.saga.sdk.client;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import games.mythical.saga.sdk.client.executor.MockCurrencyExecutor;
import games.mythical.saga.sdk.client.executor.MockItemExecutor;
import games.mythical.saga.sdk.client.executor.MockItemTypeExecutor;
import games.mythical.saga.sdk.client.executor.MockMetadataExecutor;
import games.mythical.saga.sdk.client.executor.MockMythTokenExecutor;
import games.mythical.saga.sdk.client.executor.MockPlayerWalletExecutor;
import games.mythical.saga.sdk.client.executor.MockReservationExecutor;
import games.mythical.saga.sdk.exception.SagaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SagaClientFactoryTest extends AbstractClientTest {

    private SagaClientFactory factory;

    @BeforeEach
    public void beforeEach() throws SagaException {
        port = 443;
        factory = setUpFactory();
    }

    @Test
    public void testCreateCurrencyClient() throws SagaException {
        assertNotNull(factory.createSagaCurrencyClient());
        assertNotNull(factory.createSagaCurrencyClient(MockCurrencyExecutor.builder().build()));
    }

    @Test
    public void testCreateCurrencyTypeClient() throws SagaException {
        assertNotNull(factory.createSagaCurrencyTypeClient());
    }

    @Test
    public void testCreateItemClient() throws SagaException {
        assertNotNull(factory.createSagaItemClient());
        assertNotNull(factory.createSagaItemClient(MockItemExecutor.builder().build()));
    }

    @Test
    public void testCreateItemTypeClient() throws SagaException {
        assertNotNull(factory.createSagaItemTypeClient());
        assertNotNull(factory.createSagaItemTypeClient(MockItemTypeExecutor.builder().build()));
    }

    @Test
    public void testCreateMetadataClient() throws SagaException {
        assertNotNull(factory.createSagaMetadataClient());
        assertNotNull(factory.createSagaMetadataClient(MockMetadataExecutor.builder().build()));
    }

    @Test
    public void testCreateMythTokenClient() throws SagaException {
        assertNotNull(factory.createSagaMythTokenClient());
        assertNotNull(factory.createSagaMythTokenClient(MockMythTokenExecutor.builder().build()));
    }

    @Test
    public void testCreatePlayerWalletClient() throws SagaException {
        assertNotNull(factory.createSagaPlayerWalletClient());
        assertNotNull(factory.createSagaPlayerWalletClient(new MockPlayerWalletExecutor()));
    }

    @Test
    public void testCreateReservationClient() throws SagaException {
        assertNotNull(factory.createSagaReservationClient());
        assertNotNull(factory.createSagaReservationClient(new MockReservationExecutor()));
    }

    @Test
    public void testCreateTitleClient() throws SagaException {
        assertNotNull(factory.createSagaTitleClient());
    }

    @Test
    public void testCreateTransactionClient() throws SagaException {
        assertNotNull(factory.createSagaTransactionClient());
    }
}
