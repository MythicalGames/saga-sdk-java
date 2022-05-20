package games.mythical.saga.sdk.client.model;

import games.mythical.proto_util.ProtoUtil;
import games.mythical.saga.sdk.proto.api.currency.CurrencyProto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaCurrency {
    private String traceId;
    private BigDecimal quantity;
    private String gameCurrencyTypeId;
    private String ownerAddress;
    private String titleId;

    public static SagaCurrency fromProto(CurrencyProto proto) {
        var user = ProtoUtil.toDto(proto, SagaCurrency.class);

        return user;
    }
}
