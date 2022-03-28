package games.mythical.saga.sdk.client.executor;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MockBridgeExecutor extends MockBaseExecutor implements SagaBridgeExecutor {
    private String oauthId;
    private String gameInventoryId;
    private String gameItemTypeId;
    private String destinationAddress;
    private String destinationChain;
    private String originAddress;
    private String mythicalTransactionId;
    private String mainnetTransactionId;

    @Override
    public void updateItem(String oauthId,
                           String gameInventoryId,
                           String gameItemTypeId,
                           String destinationAddress,
                           String destinationChain,
                           String originAddress,
                           String mythicalTransactionId,
                           String mainnetTransactionId,
                           String traceId) {
        this.oauthId = oauthId;
        this.gameInventoryId = gameInventoryId;
        this.gameItemTypeId = gameItemTypeId;
        this.destinationAddress = destinationAddress;
        this.destinationChain = destinationChain;
        this.originAddress = originAddress;
        this.mythicalTransactionId = mythicalTransactionId;
        this.mainnetTransactionId = mainnetTransactionId;
        this.traceId = traceId;
    }

    @Override
    public void emitReceived(String gameInventoryId, String traceId) {
        this.gameInventoryId = gameInventoryId;
        this.traceId = traceId;
    }
}
