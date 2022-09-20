package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.MockMetadataExecutor;
import games.mythical.saga.sdk.proto.api.metadata.MetadataServiceGrpc;
import games.mythical.saga.sdk.proto.common.ReceivedResponse;
import games.mythical.saga.sdk.server.MockServer;
import games.mythical.saga.sdk.server.stream.MockStatusStreamingImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SagaMetadataClientTest extends AbstractClientTest {
    private static final String INVENTORY_ID = RandomStringUtils.randomAlphanumeric(30);

    private final MockMetadataExecutor executor = MockMetadataExecutor.builder().build();
    private MockServer server;
    private SagaMetadataClient metadataClient;

    @Mock
    private MetadataServiceGrpc.MetadataServiceBlockingStub mockServiceBlockingStub;

    @BeforeEach
    void beforeEach() throws Exception {
        server = new MockServer(new MockStatusStreamingImpl());
        server.start();
        port = server.getPort();

        metadataClient = setUpFactory().createSagaMetadataClient(executor);
        // mocking the service blocking stub clients are connected to
        FieldUtils.writeField(metadataClient, "serviceBlockingStub", mockServiceBlockingStub, true);
    }

    @AfterEach
    void afterEach() throws Exception {
        metadataClient.stop();
        // client shutdown is not immediate
        Thread.sleep(500);
        metadataClient.stop();
    }

    @Test
    public void updateItemMetadata() throws Exception {
        final var EXPECTED_METADATA = generateItemMetadata();

        final var expectedResponse = ReceivedResponse.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(mockServiceBlockingStub.updateItemMetadata(any())).thenReturn(expectedResponse);
        metadataClient.updateItemMetadata(INVENTORY_ID, EXPECTED_METADATA);
    }

}
