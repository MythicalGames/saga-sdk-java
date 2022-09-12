package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.proto.common.myth.MythTokenState;

public interface SagaMythTokenExecutor extends BaseSagaExecutor {
    void updateMythToken(String traceId, MythTokenState mythTokenState) throws Exception;

    void updateMythTokenState(String traceId, MythTokenState mythTokenState) throws Exception;

    void onWithdrawalCompleted(String traceId, MythTokenState mythTokenState) throws Exception;
}