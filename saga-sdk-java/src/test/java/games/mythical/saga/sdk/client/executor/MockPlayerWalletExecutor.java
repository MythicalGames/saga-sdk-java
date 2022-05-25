package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.proto.common.playerwallet.PlayerWalletState;
import lombok.Getter;

@Getter
public class MockPlayerWalletExecutor extends MockBaseExecutor implements SagaPlayerWalletExecutor {
    private String traceId;
    private String oauthId;
    private String address;
    private PlayerWalletState playerWalletState;

    @Override
    public void updatePlayerWallet(String traceId, String oauthId, String address, PlayerWalletState playerWalletState) throws Exception {
        this.traceId = traceId;
        this.oauthId = oauthId;
        this.address = address;
        this.playerWalletState = playerWalletState;
    }
}
