package games.mythical.saga.sdk.client.executor;

public interface SagaBridgeExecutor extends BaseSagaExecutor {
    void updateItem(String oauthId,
                    String gameInventoryId,
                    String itemTypeId,
                    String destinationAddress,
                    String destinationChain,
                    String originAddress,
                    String mythicalTransactionId,
                    String mainnetTransactionId,
                    String traceId) throws Exception;
}
