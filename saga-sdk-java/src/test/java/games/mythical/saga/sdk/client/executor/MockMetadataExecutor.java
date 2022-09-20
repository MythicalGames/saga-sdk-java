package games.mythical.saga.sdk.client.executor;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MockMetadataExecutor extends MockBaseExecutor implements SagaMetadataExecutor {
    private String inventoryId;
    private String metadataUrl;
    private String traceId;

    @Override
    public void updateMetadata(String inventoryId, String metadataUrl, String traceId) throws Exception {
        this.inventoryId = inventoryId;
        this.metadataUrl = metadataUrl;
        this.traceId = traceId;
    }
}
