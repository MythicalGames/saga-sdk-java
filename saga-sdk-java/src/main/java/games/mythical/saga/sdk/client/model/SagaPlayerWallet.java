package games.mythical.saga.sdk.client.model;

import games.mythical.proto_util.ProtoUtil;
import games.mythical.saga.sdk.proto.api.playerwallet.PlayerWalletProto;
import games.mythical.saga.sdk.util.ConversionUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import games.mythical.proto_util.dto.DtoExclude;
import java.time.Instant;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaPlayerWallet {
    private String traceId;
    private String oauthId;
    private String address;
    private String balanceInWei;
    @DtoExclude
    private Instant createdAt;
    @DtoExclude
    private Instant updatedAt;

    public static SagaPlayerWallet fromProto(PlayerWalletProto proto) {
        var playerwallet = ProtoUtil.toDto(proto, SagaPlayerWallet.class);

        var createdAt = ConversionUtils.protoTimestampToInstant(proto.getCreatedAt());
        playerwallet.setCreatedAt(createdAt);

        var updatedAt = ConversionUtils.protoTimestampToInstant(proto.getUpdatedAt());
        playerwallet.setUpdatedAt(updatedAt);

        return playerwallet;
    }
}
