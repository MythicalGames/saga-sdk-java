package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.proto.common.gamecoin.GameCoinState;

public interface SagaGameCoinExecutor extends BaseSagaExecutor {
    // TODO: should there be a List<String> gameCoinIds?
    void updateGameCoin(String currencyId,
                        int coinCount,
                        String oauthId,
                        String traceId,
                        GameCoinState coinState) throws Exception;

    void emitReceived(String currencyId, String oauthId, String traceId) throws Exception;
}
