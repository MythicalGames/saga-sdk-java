package games.mythical.saga.sdk.client.model;

import games.mythical.saga.sdk.proto.api.user.FungibleToken;
import games.mythical.shared.util.ProtoUtil;
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
    private String gameId;
    private BigDecimal quantity;
    private String contractAddress;

    public static SagaFungibleToken fromProto(FungibleToken proto) {
        var fungibleToken = ProtoUtil.toDto(proto, SagaFungibleToken.class);
        return fungibleToken;
    }
}
