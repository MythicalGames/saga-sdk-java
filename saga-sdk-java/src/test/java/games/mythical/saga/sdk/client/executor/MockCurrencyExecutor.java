package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.client.model.SagaUserAmount;
import games.mythical.saga.sdk.proto.common.currency.CurrencyState;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MockCurrencyExecutor extends MockBaseExecutor implements SagaCurrencyExecutor {
    private String currencyTypeId;
    private String transactionId;
    private String oauthId;
    private String balanceInWei;
    private String idempotencyId;
    private String traceId;
    private CurrencyState currencyState;

    @Override
    public void currencyIssued(String currencyTypeId,
                               String transactionId,
                               List<SagaUserAmount> userBalances,
                               String idempotencyId,
                               String traceId) throws Exception {
        this.currencyTypeId = currencyTypeId;
        this.transactionId = transactionId;
        this.oauthId = userBalances.get(0).getOauthId();
        this.balanceInWei = userBalances.get(0).getAmountInWei();
        this.idempotencyId = idempotencyId;
        this.traceId = traceId;
    }

    @Override
    public void updateCurrency(String currencyTypeId,
                               String transactionId,
                               String oauthId,
                               String balanceInWei,
                               String idempotencyId,
                               String traceId,
                               CurrencyState currencyState) throws Exception {
        this.currencyTypeId = currencyTypeId;
        this.transactionId = transactionId;
        this.oauthId = oauthId;
        this.balanceInWei = balanceInWei;
        this.idempotencyId = idempotencyId;
        this.traceId = traceId;
        this.currencyState = currencyState;
    }
}
