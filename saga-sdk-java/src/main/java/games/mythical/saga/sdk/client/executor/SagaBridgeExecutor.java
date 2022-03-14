package games.mythical.saga.sdk.client.executor;

public interface SagaBridgeExecutor {
    void updateItem(String oauthId,
                    String gameInventoryId,
                    String gameItemTypeId,
                    String destinationAddress,
                    String destinationChain,
                    String originAddress,
                    String mythicalTransactionId,
                    String mainnetTransactionId,
                    String traceId) throws Exception;

    void emitReceived(String gameInventoryId, String traceId) throws Exception;
}
