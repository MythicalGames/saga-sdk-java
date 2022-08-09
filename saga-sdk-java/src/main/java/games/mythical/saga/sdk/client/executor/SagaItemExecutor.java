package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.proto.common.item.ItemState;

public interface SagaItemExecutor extends BaseSagaExecutor {
    void updateItem(String inventoryId,
                    String itemTypeId,
                    String oauthId,
                    Long tokenId,
                    String metadataUrl,
                    String traceId,
                    ItemState itemState) throws Exception;

    void updateItemState(String inventoryId, String traceId, ItemState itemState) throws Exception;
}
