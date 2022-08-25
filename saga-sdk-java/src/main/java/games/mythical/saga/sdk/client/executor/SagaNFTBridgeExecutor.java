package games.mythical.saga.sdk.client.executor;

public interface SagaNFTBridgeExecutor extends BaseSagaExecutor {
    void updateItem(
            String oauthId,
            String inventoryId,
            String itemTypeId,
            String destinationAddress,
            String destinationChain,
            String originAddress,
            String mythicalTransactionId,
            String mainnetTransactionId,
            String traceId) throws Exception;
}
