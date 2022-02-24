package games.mythical.saga.sdk.client.executor;

import games.mythical.ivi.sdk.proto.common.player.PlayerState;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MockPlayerExecutor implements IVIPlayerExecutor {
    private String playerId;
    private String trackingId;
    private PlayerState playerState;

    @Override
    public void updatePlayer(String playerId,
                             String trackingId,
                             PlayerState playerState) throws Exception {
        this.playerId = playerId;
        this.trackingId = trackingId;
        this.playerState = playerState;
    }
}
