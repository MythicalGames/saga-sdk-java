package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.proto.common.item.ItemState;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MockItemExecutor extends MockBaseExecutor implements SagaItemExecutor {
    private String gameInventoryId;
    private String itemTypeId;
    private String oauthId;
    private int serialNumber;
    private String metadataUri;
    private String traceId;
    private ItemState itemState;

    @Override
    public void updateItem(String gameInventoryId,
                           String itemTypeId,
                           String oauthId,
                           int serialNumber,
                           String metadataUri,
                           String traceId,
                           ItemState itemState) throws Exception {
        this.gameInventoryId = gameInventoryId;
        this.itemTypeId = itemTypeId;
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
}
