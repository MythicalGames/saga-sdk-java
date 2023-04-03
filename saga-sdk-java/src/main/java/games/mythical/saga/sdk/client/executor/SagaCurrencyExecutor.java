package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.client.model.SagaUserAmount;
import games.mythical.saga.sdk.proto.common.currency.CurrencyState;

import java.util.List;

public interface SagaCurrencyExecutor extends BaseSagaExecutor {
    void currencyIssued(String currencyTypeId,
                        String transactionId,
                        List<SagaUserAmount> userBalances,
                        String idempotencyId,
                        String traceId) throws Exception;

    void updateCurrency(String currencyTypeId,
                        String transactionId,
                        String oauthId,
                        String balanceInWei,
                        String idempotencyId,
                        String traceId,
                        CurrencyState coinState) throws Exception;
}
