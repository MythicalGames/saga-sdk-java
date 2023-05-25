package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.proto.common.currencytype.CurrencyTypeState;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MockCurrencyTypeExecutor extends MockBaseExecutor implements SagaCurrencyTypeExecutor {

    private String currencyTypeId;
    private String transactionId;
    private String contractAddress;
    private String idempotencyId;
    private String traceId;
    private CurrencyTypeState currencyTypeState;

    @Override
    public void updateCurrencyType(String currencyTypeId,
                                   String transactionId,
                                   String contractAddress,
                                   String idempotencyId,
                                   String traceId,
                                   CurrencyTypeState currencyTypeState) throws Exception {

        this.currencyTypeId = currencyTypeId;
        this.transactionId = transactionId;
        this.contractAddress = contractAddress;
        this.idempotencyId = idempotencyId;
        this.traceId = traceId;
        this.currencyTypeState = currencyTypeState;
    }
}
