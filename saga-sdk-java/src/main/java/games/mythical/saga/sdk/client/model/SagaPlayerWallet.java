package games.mythical.saga.sdk.client.model;

import games.mythical.proto_util.ProtoUtil;
import games.mythical.saga.sdk.proto.api.playerwallet.PlayerWalletProto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaPlayerWallet {
    private String traceId;
    private String oauthId;
    private String address;

    public static SagaPlayerWallet fromProto(PlayerWalletProto proto) {
        return ProtoUtil.toDto(proto, SagaPlayerWallet.class);
    }
}
