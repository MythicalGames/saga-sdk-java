package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.proto.common.itemtype.ItemTypeState;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class MockItemTypeExecutor extends MockBaseExecutor implements SagaItemTypeExecutor {
    private String gameItemTypeId;
    private String traceId;
    private ItemTypeState itemTypeState;

    @Override
    public void updateItemType(String gameItemTypeId, String traceId, ItemTypeState itemTypeState) throws Exception {
        this.gameItemTypeId = gameItemTypeId;
        this.traceId = traceId;
        this.itemTypeState = itemTypeState;
    }

    @Override
    public void updateItemTypeState(String gameItemTypeId,
                                    String traceId,
                                    ItemTypeState itemTypeState) throws Exception {
        this.gameItemTypeId = gameItemTypeId;
        this.traceId = traceId;
        this.itemTypeState = itemTypeState;
    }

    @Override
    public void emitReceived(String gameItemTypeId, String traceId) throws Exception {
        this.gameItemTypeId = gameItemTypeId;
        this.traceId = traceId;
    }
}
