package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.proto.common.itemtype.ItemTypeState;

public interface SagaItemTypeExecutor extends BaseSagaExecutor {
    void updateItemType(String gameItemTypeId, String traceId, ItemTypeState itemTypeState) throws Exception;

    void updateItemTypeState(String gameItemTypeId, String traceId, ItemTypeState itemTypeState) throws Exception;

    void emitReceived(String gameItemTypeId, String traceId) throws Exception;
}
