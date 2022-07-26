package games.mythical.saga.sdk.client.executor;

public interface SagaBridgeExecutor extends BaseSagaExecutor {
    void updateItem(String oauthId,
                    String inventoryId,
                    String itemTypeId,
                    String destinationAddress,
                    String destinationChain,
                    String originAddress,
                    String mythicalTransactionId,
                    String mainnetTransactionId,
                    String traceId) throws Exception;

    void requestQuote() throws Exception;
}
