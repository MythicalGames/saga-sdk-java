package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.proto.common.ErrorCode;

public interface BaseSagaExecutor {
    void onError(ErrorCode errorCode, String errorMsg, String traceId);
}
