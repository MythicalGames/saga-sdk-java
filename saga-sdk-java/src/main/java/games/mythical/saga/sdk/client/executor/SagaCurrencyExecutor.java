package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.proto.common.currency.CurrencyState;

import java.math.BigDecimal;

public interface SagaCurrencyExecutor extends BaseSagaExecutor {
    void updateCurrency(String currencyId,
                        BigDecimal amount,
                        String oauthId,
                        String traceId,
                        CurrencyState coinState) throws Exception;
}
