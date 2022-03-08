package games.mythical.saga.sdk.client.observer;

import games.mythical.saga.sdk.client.executor.SagaGameCoinExecutor;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.proto.common.gamecoin.GameCoinState;
import games.mythical.saga.sdk.proto.streams.gamecoin.GameCoinStatusConfirmRequest;
import games.mythical.saga.sdk.proto.streams.gamecoin.GameCoinStatusUpdate;
import games.mythical.saga.sdk.proto.streams.gamecoin.GameCoinStreamGrpc;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
public class SagaGameCoinObserver extends AbstractObserver<GameCoinStatusUpdate> {
    private final SagaSdkConfig config;
    private final SagaGameCoinExecutor sagaGameCoinExecutor;
    private final GameCoinStreamGrpc.GameCoinStreamBlockingStub streamBlockingStub;
    private final Consumer<SagaGameCoinObserver> resubscribe;

    public SagaGameCoinObserver(SagaSdkConfig config,
                                SagaGameCoinExecutor sagaGameCoinExecutor,
                                GameCoinStreamGrpc.GameCoinStreamBlockingStub streamBlockingStub,
                                Consumer<SagaGameCoinObserver> resubscribe) {
        this.config = config;
        this.sagaGameCoinExecutor = sagaGameCoinExecutor;
        this.streamBlockingStub = streamBlockingStub;
        this.resubscribe = resubscribe;
    }

    @Override
    public void onNext(GameCoinStatusUpdate message) {
        log.trace("GameCoinObserver.onNext for user {} with coin {}", message.getOauthId(), message.getCurrencyId());
        resetConnectionRetry();
        try {
            sagaGameCoinExecutor.updateGameCoin(
                    message.getCurrencyId(),
                    message.getCoinCount(),
                    message.getOauthId(),
                    message.getTraceId(),
                    message.getGameCoinState()
            );
            updateGameCoinConfirmation(message.getCurrencyId(), message.getTraceId(), message.getGameCoinState());
        } catch (Exception e) {
            log.error("Exception calling updateGameCoin for {}:{}. {}", message.getOauthId(), message.getCurrencyId(), e);
        }
    }

    @Override
    public void onError(Throwable t) {
        log.error("GameCoinObserver.onError", t);
        sleepBetweenReconnects();
        resubscribe.accept(this);
    }

    @Override
    public void onCompleted() {
        log.info("GameCoinObserver stream closed");
        sleepBetweenReconnects();
        resubscribe.accept(this);
    }

    private void updateGameCoinConfirmation(String currencyId, String traceId, GameCoinState gameCoinState) {
        var request = GameCoinStatusConfirmRequest.newBuilder()
                .setEnvironmentId(config.getTitleId())
                .setCurrencyId(currencyId)
                .setTraceId(traceId)
                .setGameCoinState(gameCoinState)
                .build();
        streamBlockingStub.gameCoinStatusConfirmation(request);
    }
}
