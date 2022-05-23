package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.proto.common.playerwallet.PlayerWalletState;

public interface SagaPlayerWalletExecutor extends BaseSagaExecutor {
    void updatePlayerWallet(String traceId, String oauthId, String address, PlayerWalletState playerWalletState) throws Exception;
}
