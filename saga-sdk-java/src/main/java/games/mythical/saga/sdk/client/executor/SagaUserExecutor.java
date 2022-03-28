package games.mythical.saga.sdk.client.executor;

public interface SagaUserExecutor extends BaseSagaExecutor {
    void updateUser(String oauthId, String traceId) throws Exception;
}
