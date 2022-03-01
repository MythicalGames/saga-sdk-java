package games.mythical.saga.sdk.client.executor;

public interface SagaUserExecutor {
    void updateUser(String oauthId, String traceId) throws Exception;
}
