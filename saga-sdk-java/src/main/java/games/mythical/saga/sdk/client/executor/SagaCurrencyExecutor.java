package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.proto.common.currency.CurrencyState;

public interface SagaCurrencyExecutor extends BaseSagaExecutor {
    void updateCurrency(String currencyId,
                        String quantity,
                        String oauthId,
                        String traceId,
                        CurrencyState coinState) throws Exception;
}
