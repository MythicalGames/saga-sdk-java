package games.mythical.saga.sdk.client.executor;

public interface SagaPlayerWalletExecutor extends BaseSagaExecutor {
    void updatePlayerWallet(String traceId, String oauthId) throws Exception;
}
