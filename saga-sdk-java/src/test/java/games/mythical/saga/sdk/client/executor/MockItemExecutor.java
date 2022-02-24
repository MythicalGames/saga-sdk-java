package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.client.model.IVIItem;
import games.mythical.ivi.sdk.proto.common.item.ItemState;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MockItemExecutor implements IVIItemExecutor {
    private String gameInventoryId;
    private String playerId;
    private long dGoodsId;
    private int serialNumber;
    private String gameItemTypeId;
    private String metadataUri;
    private String trackingId;
    private ItemState itemState;

    @Override
    public void updateItem(String gameInventoryId,
                           String gameItemTypeId,
                           String playerId,
                           long dGoodsId,
                           int serialNumber,
                           String metadataUri,
                           String trackingId,
                           ItemState itemState) {
        this.gameInventoryId = gameInventoryId;
        this.playerId = playerId;
        this.dGoodsId = dGoodsId;
        this.serialNumber = serialNumber;
        this.gameItemTypeId = gameItemTypeId;
        this.metadataUri = metadataUri;
        this.trackingId = trackingId;
        this.itemState = itemState;
    }

    @Override
    public void updateItemState(String gameInventoryId, String trackingId, ItemState itemState) {
        this.gameInventoryId = gameInventoryId;
        this.trackingId = trackingId;
        this.itemState = itemState;
    }

    public void setFromItem(IVIItem item) {
        updateItem(item.getGameInventoryId(),
                item.getGameItemTypeId(),
                item.getPlayerId(),
                item.getDGoodsId(),
                item.getSerialNumber(),
                item.getMetadataUri(),
                item.getTrackingId(),
                item.getItemState());
    }
}
