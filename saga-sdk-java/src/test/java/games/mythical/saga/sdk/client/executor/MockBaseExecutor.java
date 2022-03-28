package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.proto.common.ErrorCode;
import lombok.Getter;

@Getter
public class MockBaseExecutor implements BaseSagaExecutor {
    protected ErrorCode errorCode;
    protected String errorMsg;
    protected String traceId;

    @Override
    public void onError(ErrorCode errorCode, String errorMsg, String traceId) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.traceId = traceId;
    }
}
