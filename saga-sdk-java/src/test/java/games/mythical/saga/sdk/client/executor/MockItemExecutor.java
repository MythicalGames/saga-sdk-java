package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.proto.common.item.ItemState;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MockItemExecutor extends MockBaseExecutor implements SagaItemExecutor {
    private String inventoryId;
    private String itemTypeId;
    private String oauthId;
    private Long tokenId;
    private String metadataUri;
    private String traceId;
    private ItemState itemState;

    @Override
    public void updateItem(String inventoryId,
                           String itemTypeId,
                           String oauthId,
                           Long tokenId,
                           String metadataUrl,
                           String traceId,
                           ItemState itemState) throws Exception {
        this.inventoryId = inventoryId;
        this.itemTypeId = itemTypeId;
        this.oauthId = oauthId;
        this.tokenId = tokenId;
        this.metadataUri = metadataUrl;
        this.traceId = traceId;
        this.itemState = itemState;
    }

    @Override
    public void updateItemState(String inventoryId, String traceId, ItemState itemState) throws Exception {
        this.inventoryId = inventoryId;
        this.traceId = traceId;
        this.itemState = itemState;
    }
}
