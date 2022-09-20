package games.mythical.saga.sdk.client.executor;

public interface SagaMetadataExecutor extends BaseSagaExecutor {
    void updateMetadata(String inventoryId,
                    String metadataUrl,
                    String traceId) throws Exception;
}
