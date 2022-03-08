package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.proto.common.gamecoin.GameCoinState;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MockGameCoinExecutor implements SagaGameCoinExecutor {
    private String currencyId;
    private int coinCount;
    private String oauthId;
    private String traceId;
    private GameCoinState gameCoinState;

    @Override
    public void updateGameCoin(String currencyId,
                               int coinCount,
                               String oauthId,
                               String traceId,
                               GameCoinState gameCoinState) throws Exception {
        this.currencyId = currencyId;
        this.coinCount = coinCount;
        this.oauthId = oauthId;
        this.traceId = traceId;
        this.gameCoinState = gameCoinState;
    }

    @Override
    public void emitReceived(String currencyId, String oauthId, String traceId) throws Exception {
        this.currencyId = currencyId;
        this.oauthId = oauthId;
        this.traceId = traceId;
    }
}
