package games.mythical.saga.sdk.client.executor;

import games.mythical.ivi.sdk.proto.common.player.PlayerState;

public interface IVIPlayerExecutor {
    void updatePlayer(String playerId,
                      String trackingId,
                      PlayerState playerState) throws Exception;
}
