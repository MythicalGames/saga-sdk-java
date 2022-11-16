package games.mythical.saga.sdk.client.model;

import games.mythical.proto_util.ProtoUtil;
import games.mythical.proto_util.dto.DtoExclude;
import games.mythical.saga.sdk.proto.common.item.ItemState;
import games.mythical.saga.sdk.proto.streams.item.ItemStatusUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SagaItemUpdate {
    private String inventoryId;
    private String itemTypeId;
    private String oauthId;
    private long tokenId;
    private String metadataUrl;
    @DtoExclude
    private ItemState itemState;

    public static SagaItemUpdate fromProto(ItemStatusUpdate proto) {
        var itemUpdate = ProtoUtil.toDto(proto, SagaItemUpdate.class);
        itemUpdate.setItemState(proto.getItemState());
        return itemUpdate;
    }
}
