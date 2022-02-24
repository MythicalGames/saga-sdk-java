package games.mythical.saga.sdk.client.model;

import games.mythical.saga.sdk.exception.IVIException;
import games.mythical.ivi.sdk.proto.api.item.Item;
import games.mythical.ivi.sdk.proto.common.item.ItemState;
import lombok.Data;

import java.time.Instant;

@Data
public class IVIItem {
    private String gameInventoryId;
    private String gameItemTypeId;
    private long dGoodsId;
    private String itemName;
    private String playerId;
    private String ownerSidechainAccount;
    private int serialNumber;
    private String currencyBase;
    private String metadataUri;
    private String trackingId;
    private IVIMetadata metadata;
    private ItemState itemState;
    private Instant createdTimestamp;
    private Instant updatedTimestamp;

    IVIItem(String gameInventoryId,
                   String gameItemTypeId,
                   long dGoodsId,
                   String itemName,
                   String playerId,
                   String ownerSidechainAccount,
                   int serialNumber,
                   String currencyBase,
                   String metadataUri,
                   String trackingId,
                   IVIMetadata metadata,
                   ItemState itemState,
                   Instant createdTimestamp,
                   Instant updatedTimestamp) {
        this.gameInventoryId = gameInventoryId;
        this.gameItemTypeId = gameItemTypeId;
        this.dGoodsId = dGoodsId;
        this.itemName = itemName;
        this.playerId = playerId;
        this.ownerSidechainAccount = ownerSidechainAccount;
        this.serialNumber = serialNumber;
        this.currencyBase = currencyBase;
        this.metadataUri = metadataUri;
        this.trackingId = trackingId;
        this.metadata = metadata;
        this.itemState = itemState;
        this.createdTimestamp = createdTimestamp;
        this.updatedTimestamp = updatedTimestamp;
    }

    public static IVIItem fromProto(Item item) throws IVIException {
        var createdTimestamp = Instant.ofEpochSecond(item.getCreatedTimestamp());
        var updatedTimestamp = Instant.ofEpochSecond(item.getUpdatedTimestamp());

        return new IVIItem(item.getGameInventoryId(),
                item.getGameItemTypeId(),
                item.getDgoodsId(),
                item.getItemName(),
                item.getPlayerId(),
                item.getOwnerSidechainAccount(),
                item.getSerialNumber(),
                item.getCurrencyBase(),
                item.getMetadataUri(),
                item.getTrackingId(),
                IVIMetadata.fromProto(item.getMetadata()),
                item.getItemState(),
                createdTimestamp,
                updatedTimestamp);
    }
}
