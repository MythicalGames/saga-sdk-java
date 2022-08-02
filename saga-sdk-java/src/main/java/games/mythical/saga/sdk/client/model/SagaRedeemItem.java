package games.mythical.saga.sdk.client.model;

import games.mythical.proto_util.ProtoUtil;
import games.mythical.proto_util.dto.DtoExclude;
import games.mythical.proto_util.proto.ProtoExclude;
import games.mythical.saga.sdk.proto.api.reservation.RedeemItemProto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SagaRedeemItem {
    private String inventoryId;
    private String itemTypeId;
    @ProtoExclude
    @DtoExclude
    private SagaMetadata metadata;

    public static RedeemItemProto toProto(SagaRedeemItem item) {
        var builder = ProtoUtil.toProtoBuilder(item, RedeemItemProto.Builder.class);

        if (item.getMetadata() != null) {
            builder.setMetadata(SagaMetadata.toProto(item.getMetadata()));
        }

        return builder.build();
    }
}
