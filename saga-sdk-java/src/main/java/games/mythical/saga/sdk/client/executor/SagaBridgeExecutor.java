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

    void bridgeQuoteUpdate(
            String feeInOriginChainNativeToken,
            String feeInOriginChainNativeTokenUnit,
            String feeInUsd,
            String expiresAt,
            String gasPriceOriginChain,
            String gasPriceOriginChainUnit,
            String gasPriceTargetChain,
            String gasPriceTargetChainUnit,
            String signature
    ) throws Exception;
}
