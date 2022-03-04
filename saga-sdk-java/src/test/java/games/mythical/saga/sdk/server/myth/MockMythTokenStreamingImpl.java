package games.mythical.saga.sdk.server.myth;

import com.google.protobuf.Empty;
import games.mythical.saga.sdk.proto.api.myth.MythTokenProto;
import games.mythical.saga.sdk.proto.common.myth.MythTokenState;
import games.mythical.saga.sdk.proto.streams.Subscribe;
import games.mythical.saga.sdk.proto.streams.myth.MythTokenStatusConfirmRequest;
import games.mythical.saga.sdk.proto.streams.myth.MythTokenStatusUpdate;
import games.mythical.saga.sdk.proto.streams.myth.MythTokenStreamGrpc;
import games.mythical.saga.sdk.util.ConcurrentFinisher;
import io.grpc.stub.StreamObserver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MockMythTokenStreamingImpl extends MythTokenStreamGrpc.MythTokenStreamImplBase {
    private final Map<String, StreamObserver<MythTokenStatusUpdate>> streamObserverMap = new ConcurrentHashMap<>();

    @Override
    public void mythTokenStatusStream(Subscribe request, StreamObserver<MythTokenStatusUpdate> responseObserver) {
        streamObserverMap.putIfAbsent(request.getEnvironmentId(), responseObserver);
    }

    @Override
    public void mythTokenStatusConfirmation(MythTokenStatusConfirmRequest request, StreamObserver<Empty> responseObserver) {
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();

        ConcurrentFinisher.finish(request.getTraceId());
    }

    public void sendStatus(String environmentId, MythTokenProto mythToken, MythTokenState tokenState) {
        if (streamObserverMap.containsKey(environmentId)) {
            var observer = streamObserverMap.get(environmentId);
            var tokenStatus = MythTokenStatusUpdate.newBuilder()
                    .setTokenState(tokenState)
                    .setTraceId(mythToken.getTraceId())
                    .build();
            observer.onNext(tokenStatus);
        }
    }

    public void reset() {
        streamObserverMap.values().forEach(StreamObserver::onCompleted);
        streamObserverMap.clear();
    }
}
