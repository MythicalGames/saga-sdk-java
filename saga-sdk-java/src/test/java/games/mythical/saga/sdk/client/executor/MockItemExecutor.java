package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.proto.common.item.ItemState;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MockItemExecutor implements SagaItemExecutor {
    private String gameInventoryId;
    private String gameItemTypeId;
    private String oauthId;
    private int serialNumber;
    private String metadataUri;
    private String traceId;
    private ItemState itemState;

    @Override
    public void updateItem(String gameInventoryId,
                           String gameItemTypeId,
                           String oauthId,
                           int serialNumber,
                           String metadataUri,
                           String traceId,
                           ItemState itemState) throws Exception {
        this.gameInventoryId = gameInventoryId;
        this.gameItemTypeId = gameItemTypeId;
        this.oauthId = oauthId;
        this.serialNumber = serialNumber;
        this.metadataUri = metadataUri;
        this.traceId = traceId;
        this.itemState = itemState;
    }

    @Override
    public void updateItemState(String gameInventoryId, String traceId, ItemState itemState) throws Exception {
        this.gameInventoryId = gameInventoryId;
        this.traceId = traceId;
        this.itemState = itemState;
    }

    @Override
    public void emitReceived(String gameInventoryId, String traceId) throws Exception {
        this.gameInventoryId = gameInventoryId;
        this.traceId = traceId;
    }
}
