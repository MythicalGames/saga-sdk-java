package games.mythical.saga.sdk.client.model;

import games.mythical.proto_util.ProtoUtil;
import games.mythical.saga.sdk.proto.api.currency.BalanceProto;
import games.mythical.saga.sdk.proto.api.currency.BalancesOfPlayerProto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaBalancesOfPlayer {
    private String oauthId;
    private List<BalanceProto> balances;
    private String traceId;

    public static SagaBalancesOfPlayer fromProto(BalancesOfPlayerProto proto) {
        var user = ProtoUtil.toDto(proto, SagaBalancesOfPlayer.class);
        return user;
    }
}
