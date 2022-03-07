package games.mythical.saga.sdk.client.model;

import games.mythical.saga.sdk.proto.api.myth.CurrencyExchangeProto;
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
public class SagaCurrencyExchange {
    BigDecimal ask;
    BigDecimal bid;

    public static SagaCurrencyExchange fromProto(CurrencyExchangeProto proto) {
        var currencyExchange = ProtoUtil.toDto(proto, SagaCurrencyExchange.class);

        return currencyExchange;
    }
}
