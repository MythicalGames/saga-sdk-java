package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.proto.common.currencytype.CurrencyTypeState;

public interface SagaCurrencyTypeExecutor extends BaseSagaExecutor {

    void updateCurrencyType(String currencyTypeId,
                            String transactionId,
                            String contractAddress,
                            String idempotencyId,
                            String traceId,
                            CurrencyTypeState currencyTypeState) throws Exception;
}
