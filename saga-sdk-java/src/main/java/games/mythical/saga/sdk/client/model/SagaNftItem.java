package games.mythical.saga.sdk.client.model;

import games.mythical.saga.sdk.proto.api.user.NftItem;
import games.mythical.shared.util.ProtoUtil;
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
    private String itemTypeId;
    private String contractAddress;
    private String tokenId;

    public static SagaNftItem fromProto(NftItem proto) {
        var nftItem = ProtoUtil.toDto(proto, SagaNftItem.class);
        return nftItem;
    }
}
