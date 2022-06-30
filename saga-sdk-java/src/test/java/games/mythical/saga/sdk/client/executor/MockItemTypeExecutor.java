package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.proto.common.itemtype.ItemTypeState;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class MockItemTypeExecutor extends MockBaseExecutor implements SagaItemTypeExecutor {
    private String itemTypeId;
    private String traceId;
    private ItemTypeState itemTypeState;

    @Override
    public void updateItemType(String itemTypeId, String traceId, ItemTypeState itemTypeState) throws Exception {
        this.itemTypeId = itemTypeId;
        this.traceId = traceId;
        this.itemTypeState = itemTypeState;
    }

    @Override
    public void updateItemTypeState(String itemTypeId,
                                    String traceId,
                                    ItemTypeState itemTypeState) throws Exception {
        this.itemTypeId = itemTypeId;
        this.traceId = traceId;
        this.itemTypeState = itemTypeState;
    }
}
