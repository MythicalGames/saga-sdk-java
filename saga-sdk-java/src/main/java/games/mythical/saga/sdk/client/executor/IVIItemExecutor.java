package games.mythical.saga.sdk.client.executor;

import games.mythical.ivi.sdk.proto.common.item.ItemState;

public interface IVIItemExecutor {
    void updateItem(String gameInventoryId,
                    String itemTypeId,
                    String playerId,
                    long dGoodsId,
                    int serialNumber,
                    String metadataUri,
                    String trackingId,
                    ItemState itemState) throws Exception;

    void updateItemState(String gameInventoryId,
                         String trackingId,
                         ItemState itemState) throws Exception;
}
