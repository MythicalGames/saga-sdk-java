package games.mythical.saga.sdk.client.executor;

import lombok.Getter;

@Getter
public class MockPlayerWalletExecutor extends MockBaseExecutor implements SagaPlayerWalletExecutor {
    private String traceId;
    private String oauthId;

    @Override
    public void updatePlayerWallet(String traceId, String oauthId) throws Exception {
        this.traceId = traceId;
        this.oauthId = oauthId;
    }
}
