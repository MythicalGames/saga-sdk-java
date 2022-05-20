package games.mythical.saga.sdk.client.model;

import games.mythical.proto_util.ProtoUtil;
import games.mythical.saga.sdk.proto.api.user.NftItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaNftItem {
    private String itemTypeName;
    private String gameItemTypeId;
    private String contractAddress;
    private String tokenId;

    public static SagaNftItem fromProto(NftItem proto) {
        var nftItem = ProtoUtil.toDto(proto, SagaNftItem.class);
        return nftItem;
    }
}
