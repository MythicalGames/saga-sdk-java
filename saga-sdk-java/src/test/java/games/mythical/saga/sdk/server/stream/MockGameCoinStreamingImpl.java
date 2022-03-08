package games.mythical.saga.sdk.server.stream;

import com.google.protobuf.Empty;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.ProtocolMessageEnum;
import games.mythical.saga.sdk.proto.api.gamecoin.GameCoinProto;
import games.mythical.saga.sdk.proto.common.gamecoin.GameCoinState;
import games.mythical.saga.sdk.proto.streams.Subscribe;
import games.mythical.saga.sdk.proto.streams.gamecoin.GameCoinStatusConfirmRequest;
import games.mythical.saga.sdk.proto.streams.gamecoin.GameCoinStatusUpdate;
import games.mythical.saga.sdk.proto.streams.gamecoin.GameCoinStreamGrpc;
import games.mythical.saga.sdk.server.StreamingService;
import games.mythical.saga.sdk.util.ConcurrentFinisher;
import io.grpc.stub.StreamObserver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MockGameCoinStreamingImpl extends GameCoinStreamGrpc.GameCoinStreamImplBase implements StreamingService {
    private final Map<String, StreamObserver<GameCoinStatusUpdate>> streamObservers = new ConcurrentHashMap<>();

    @Override
    public void gameCoinStatusStream(Subscribe request, StreamObserver<GameCoinStatusUpdate> responseObserver) {
        streamObservers.putIfAbsent(request.getEnvironmentId(), responseObserver);
    }

    @Override
    public void gameCoinStatusConfirmation(GameCoinStatusConfirmRequest request, StreamObserver<Empty> responseObserver) {
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();

        ConcurrentFinisher.finish(request.getTraceId());
    }

    @Override
    public void sendStatus(String environmentId, GeneratedMessageV3 genericProto, ProtocolMessageEnum genericState) {
        var proto = (GameCoinProto) genericProto;
        var state = (GameCoinState) genericState;
        if (streamObservers.containsKey(environmentId)) {
            var observer = streamObservers.get(environmentId);
            var itemStatus = GameCoinStatusUpdate.newBuilder()
                    .setOauthId(proto.getOauthId())
                    .setCurrencyId(proto.getCurrencyId())
                    .setCoinCount(proto.getCoinCount())
                    .setTraceId(proto.getTraceId())
                    .setGameCoinState(state)
                    .build();
            observer.onNext(itemStatus);
        }
    }

    @Override
    public void reset() {
        streamObservers.values().forEach(StreamObserver::onCompleted);
        streamObservers.clear();
    }
}
