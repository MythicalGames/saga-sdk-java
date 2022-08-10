package games.mythical.saga.sdk.client.model;

import games.mythical.proto_util.ProtoUtil;
import games.mythical.saga.sdk.proto.api.currency.CurrencyProto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaCurrency {
    private String traceId;
    private long amount;
    private String currencyTypeId;
    private String oauthId;
    private String titleId;

    public static SagaCurrency fromProto(CurrencyProto proto) {
        var user = ProtoUtil.toDto(proto, SagaCurrency.class);
        return user;
    }
}
