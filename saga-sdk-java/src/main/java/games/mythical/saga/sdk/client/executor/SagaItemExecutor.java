package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.proto.common.item.ItemState;

public interface SagaItemExecutor extends BaseSagaExecutor {
    void updateItem(String gameInventoryId,
                    String gameItemTypeId,
                    String oauthId,
                    int serialNumber,
                    String metadataUri,
                    String traceId,
                    ItemState itemState) throws Exception;

    void updateItemState(String gameInventoryId, String traceId, ItemState itemState) throws Exception;
}
