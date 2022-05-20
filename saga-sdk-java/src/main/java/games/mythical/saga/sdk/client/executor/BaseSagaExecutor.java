package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.exception.ErrorData;

public interface BaseSagaExecutor {
    void onError(ErrorData errorData);
}
