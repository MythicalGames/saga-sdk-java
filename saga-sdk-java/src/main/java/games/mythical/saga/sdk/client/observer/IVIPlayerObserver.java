package games.mythical.saga.sdk.client.observer;

import games.mythical.saga.sdk.client.executor.IVIPlayerExecutor;
import games.mythical.saga.sdk.config.IVIConfiguration;
import games.mythical.ivi.sdk.proto.common.player.PlayerState;
import games.mythical.ivi.sdk.proto.streams.player.PlayerStatusConfirmRequest;
import games.mythical.ivi.sdk.proto.streams.player.PlayerStatusUpdate;
import games.mythical.ivi.sdk.proto.streams.player.PlayerStreamGrpc;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
public class IVIPlayerObserver extends AbstractObserver<PlayerStatusUpdate> {
    private final IVIPlayerExecutor playerExecutor;
    private final PlayerStreamGrpc.PlayerStreamBlockingStub streamBlockingStub;
    private final Consumer<IVIPlayerObserver> resubscribe;

    public IVIPlayerObserver(IVIPlayerExecutor playerExecutor,
                             PlayerStreamGrpc.PlayerStreamBlockingStub playerStreamBlockingStub,
                             Consumer<IVIPlayerObserver> resubscribe) {
        this.playerExecutor = playerExecutor;
        this.streamBlockingStub = playerStreamBlockingStub;
        this.resubscribe = resubscribe;
    }

    @Override
    public void onNext(PlayerStatusUpdate message) {
        log.trace("PlayerObserver.onNext for player id: {}", message.getPlayerId());
        resetConnectionRetry();
        try {
            playerExecutor.updatePlayer(message.getPlayerId(),
                    message.getTrackingId(),
                    message.getPlayerState());
            updatePlayerConfirmation(message.getPlayerId(), message.getTrackingId(), message.getPlayerState());
        } catch (Exception e) {
            log.error("Exception calling updatePlayer", e);
        }
    }

    @Override
    public void onError(Throwable t) {
        log.error("PlayerObserver.onError", t);
        sleepBetweenReconnects();
        resubscribe.accept(this);
    }

    @Override
    public void onCompleted() {
        log.info("PlayerObserver stream closed");
        sleepBetweenReconnects();
        resubscribe.accept(this);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void updatePlayerConfirmation(String playerId, String trackingId, PlayerState playerState) {
        var request = PlayerStatusConfirmRequest.newBuilder()
                .setEnvironmentId(IVIConfiguration.getEnvironmentId())
                .setPlayerId(playerId)
                .setTrackingId(trackingId)
                .setPlayerState(playerState)
                .build();
        streamBlockingStub.playerStatusConfirmation(request);
    }
}
