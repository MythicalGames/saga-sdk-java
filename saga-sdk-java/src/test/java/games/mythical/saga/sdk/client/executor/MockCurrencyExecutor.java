package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.proto.common.currency.CurrencyState;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MockCurrencyExecutor extends MockBaseExecutor implements SagaCurrencyExecutor {
    private String gameCurrencyTypeId;
    private String quantity;
    private String ownerAddress;
    private String traceId;
    private CurrencyState currencyState;

    @Override
    public void updateCurrency(String gameCurrencyTypeId,
                               String quantity,
                               String ownerAddress,
                               String traceId,
                               CurrencyState currencyState) {
        this.gameCurrencyTypeId = gameCurrencyTypeId;
        this.quantity = quantity;
        this.ownerAddress = ownerAddress;
        this.traceId = traceId;
        this.currencyState = currencyState;
    }

    @Override
    public void emitReceived(String currencyId, String oauthId, String traceId) {
        this.gameCurrencyTypeId = currencyId;
        this.ownerAddress = oauthId;
        this.traceId = traceId;
    }
}
