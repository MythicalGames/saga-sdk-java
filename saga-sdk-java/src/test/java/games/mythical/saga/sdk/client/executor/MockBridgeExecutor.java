package games.mythical.saga.sdk.client.executor;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MockBridgeExecutor extends MockBaseExecutor implements SagaBridgeExecutor {
    private String oauthId;
    private String gameInventoryId;
    private String itemTypeId;
    private String destinationAddress;
    private String destinationChain;
    private String originAddress;
    private String mythicalTransactionId;
    private String mainnetTransactionId;

    @Override
    public void updateItem(String oauthId,
                           String gameInventoryId,
                           String itemTypeId,
                           String destinationAddress,
                           String destinationChain,
                           String originAddress,
                           String mythicalTransactionId,
                           String mainnetTransactionId,
                           String traceId) {
        this.oauthId = oauthId;
        this.gameInventoryId = gameInventoryId;
        this.itemTypeId = itemTypeId;
        this.destinationAddress = destinationAddress;
        this.destinationChain = destinationChain;
        this.originAddress = originAddress;
        this.mythicalTransactionId = mythicalTransactionId;
        this.mainnetTransactionId = mainnetTransactionId;
        this.traceId = traceId;
    }
}
