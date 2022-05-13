package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.proto.common.currency.CurrencyState;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MockCurrencyExecutor extends MockBaseExecutor implements SagaCurrencyExecutor {
    private String currencyId;
    private int coinCount;
    private String oauthId;
    private String traceId;
    private CurrencyState currencyState;

    @Override
    public void updateCurrency(String currencyId,
                               int coinCount,
                               String oauthId,
                               String traceId,
                               CurrencyState currencyState) {
        this.currencyId = currencyId;
        this.coinCount = coinCount;
        this.oauthId = oauthId;
        this.traceId = traceId;
        this.currencyState = currencyState;
    }

    @Override
    public void emitReceived(String currencyId, String oauthId, String traceId) {
        this.currencyId = currencyId;
        this.oauthId = oauthId;
        this.traceId = traceId;
    }
}
