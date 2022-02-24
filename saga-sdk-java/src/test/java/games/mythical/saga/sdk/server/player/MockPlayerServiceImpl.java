package games.mythical.saga.sdk.server.player;

import games.mythical.ivi.sdk.proto.api.player.*;
import games.mythical.ivi.sdk.proto.common.player.PlayerState;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MockPlayerServiceImpl extends PlayerServiceGrpc.PlayerServiceImplBase {
    private final Map<String, IVIPlayer> players = new ConcurrentHashMap<>();

    @Override
    public void linkPlayer(LinkPlayerRequest request, StreamObserver<LinkPlayerAsyncResponse> responseObserver) {
        var response = LinkPlayerAsyncResponse.newBuilder()
                .setTrackingId(RandomStringUtils.randomAlphanumeric(30))
                .setPlayerState(PlayerState.PENDING_LINKED)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getPlayers(GetPlayersRequest request, StreamObserver<IVIPlayers> responseObserver) {
        responseObserver.onNext(IVIPlayers.newBuilder().addAllIviPlayers(players.values()).build());
        responseObserver.onCompleted();
    }

    @Override
    public void getPlayer(GetPlayerRequest request, StreamObserver<IVIPlayer> responseObserver) {
        if(players.containsKey(request.getPlayerId())) {
            responseObserver.onNext(players.get(request.getPlayerId()));
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(Status.NOT_FOUND.asRuntimeException());
        }
    }

    public void setPlayers(Collection<IVIPlayer> players) {
        for (var player : players) {
            this.players.putIfAbsent(player.getPlayerId(), player);
        }
    }

    public void reset() {
        players.clear();
    }
}
