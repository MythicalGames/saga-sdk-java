package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.proto.common.myth.MythTokenState;

public interface SagaMythTokenExecutor {
    void updateMythToken(String traceId, MythTokenState mythTokenState) throws Exception;

    void updateMythTokenState(String traceId, MythTokenState mythTokenState) throws Exception;

    void emitReceived(String traceId) throws Exception;
}
