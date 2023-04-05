package games.mythical.saga.sdk.client.model;

import games.mythical.proto_util.ProtoUtil;
import games.mythical.saga.sdk.proto.api.currency.BalanceOfPlayerProto;
import games.mythical.saga.sdk.proto.api.currency.BalanceProto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaBalanceOfPlayer {
    private String oauthId;
    private BalanceProto balance;
    private String traceId;

    public static SagaBalanceOfPlayer fromProto(BalanceOfPlayerProto proto) {
        var user = ProtoUtil.toDto(proto, SagaBalanceOfPlayer.class);
        return user;
    }
}
