package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.proto.common.currency.CurrencyState;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class MockCurrencyExecutor extends MockBaseExecutor implements SagaCurrencyExecutor {
    private String currencyTypeId;
    private long amount;
    private String oauthId;
    private String traceId;
    private CurrencyState currencyState;

    @Override
    public void updateCurrency(String currencyTypeId,
                               long amount,
                               String oauthId,
                               String traceId,
                               CurrencyState currencyState) {
        this.currencyTypeId = currencyTypeId;
        this.amount = amount;
        this.oauthId = oauthId;
        this.traceId = traceId;
        this.currencyState = currencyState;
    }
}
