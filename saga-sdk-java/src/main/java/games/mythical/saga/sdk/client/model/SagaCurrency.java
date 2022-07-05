package games.mythical.saga.sdk.client.model;

import games.mythical.proto_util.ProtoUtil;
import games.mythical.proto_util.dto.DtoExclude;
import games.mythical.proto_util.proto.ProtoExclude;
import games.mythical.saga.sdk.proto.api.currency.CurrencyProto;
import games.mythical.saga.sdk.util.ConversionUtils;
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
    @ProtoExclude
    @DtoExclude
    private BigDecimal amount;
    private String currencyTypeId;
    private String oauthId;
    private String titleId;

    public static SagaCurrency fromProto(CurrencyProto proto) {
        var user = ProtoUtil.toDto(proto, SagaCurrency.class);

        user.setAmount(ConversionUtils.protoDecimalToBigDecimal(proto.getAmount()));

        return user;
    }
}
