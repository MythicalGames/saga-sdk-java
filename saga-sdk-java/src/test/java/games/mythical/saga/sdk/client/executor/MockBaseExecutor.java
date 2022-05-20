package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.exception.ErrorData;
import lombok.Getter;

@Getter
public class MockBaseExecutor implements BaseSagaExecutor {
    protected String errorCode;
    protected String errorMsg;
    protected String traceId;

    @Override
    public void onError(ErrorData errorData) {
        this.errorCode = errorData.getCode();
        this.errorMsg = errorData.getMessage();
        this.traceId = errorData.getTrace();
    }
}
