package games.mythical.saga.sdk.client.model;

import games.mythical.proto_util.ProtoUtil;
import games.mythical.saga.sdk.proto.api.user.FungibleToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaFungibleToken {
    private String name;
    private String titleId;
    private BigDecimal balance;
    private String contractAddress;

    public static SagaFungibleToken fromProto(FungibleToken proto) {
        var fungibleToken = ProtoUtil.toDto(proto, SagaFungibleToken.class);
        return fungibleToken;
    }
}
