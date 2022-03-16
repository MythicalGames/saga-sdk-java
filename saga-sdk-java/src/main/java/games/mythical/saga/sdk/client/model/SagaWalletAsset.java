package games.mythical.saga.sdk.client.model;

import games.mythical.saga.sdk.proto.api.user.WalletAsset;
import games.mythical.shared.util.ProtoUtil;
import games.mythical.shared.util.dto.DtoExclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaWalletAsset {
    private String nmythToken;
    private BigDecimal nmythTokenQuantity;
    private String ausdToken;
    private BigDecimal ausdTokenQuantity;
    @DtoExclude
    private List<SagaNftItem> nftItems;
    @DtoExclude
    private List<SagaFungibleToken> fungibleTokens;

    public static SagaWalletAsset fromProto(WalletAsset proto) {
        var wallet = ProtoUtil.toDto(proto, SagaWalletAsset.class);

        wallet.setNftItems(proto.getNftItemsList().stream().map(SagaNftItem::fromProto).collect(Collectors.toList()));
        wallet.setFungibleTokens(proto.getFungibleTokensList().stream().map(SagaFungibleToken::fromProto).collect(Collectors.toList()));

        return wallet;
    }
}
