package games.mythical.saga.sdk.client.executor;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MockBridgeExecutor extends MockBaseExecutor implements SagaBridgeExecutor {
    private String oauthId;
    private String inventoryId;
    private String itemTypeId;
    private String destinationAddress;
    private String destinationChain;
    private String originAddress;
    private String mythicalTransactionId;
    private String mainnetTransactionId;

    private String feeInOriginChainNativeToken;
    private String feeInOriginChainNativeTokenUnit;
    private String feeInUsd;
    private String expiresAt;
    private String gasPriceOriginChain;
    private String gasPriceOriginChainUnit;
    private String gasPriceTargetChain;
    private String gasPriceTargetChainUnit;
    private String signature;

    @Override
    public void updateItem(String oauthId,
                           String inventoryId,
                           String itemTypeId,
                           String destinationAddress,
                           String destinationChain,
                           String originAddress,
                           String mythicalTransactionId,
                           String mainnetTransactionId,
                           String traceId) {
        this.oauthId = oauthId;
        this.inventoryId = inventoryId;
        this.itemTypeId = itemTypeId;
        this.destinationAddress = destinationAddress;
        this.destinationChain = destinationChain;
        this.originAddress = originAddress;
        this.mythicalTransactionId = mythicalTransactionId;
        this.mainnetTransactionId = mainnetTransactionId;
        this.traceId = traceId;
    }

    @Override
    public void bridgeQuoteUpdate(
            String feeInOriginChainNativeToken,
            String feeInOriginChainNativeTokenUnit,
            String feeInUsd,
            String expiresAt,
            String gasPriceOriginChain,
            String gasPriceOriginChainUnit,
            String gasPriceTargetChain,
            String gasPriceTargetChainUnit,
            String signature
    ) {
        this.feeInOriginChainNativeToken = feeInOriginChainNativeToken;
        this.feeInOriginChainNativeTokenUnit = feeInOriginChainNativeTokenUnit;
        this.feeInUsd = feeInUsd;
        this.expiresAt = expiresAt;
        this.gasPriceOriginChain = gasPriceOriginChain;
        this.gasPriceOriginChainUnit = gasPriceOriginChainUnit;
        this.gasPriceTargetChain = gasPriceTargetChain;
        this.gasPriceTargetChainUnit = gasPriceTargetChainUnit;
        this.signature = signature;
    }
}
