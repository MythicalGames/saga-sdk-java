package games.mythical.saga.sdk.server.stream;

import com.google.protobuf.Empty;
import com.google.protobuf.GeneratedMessageV3;
import games.mythical.saga.sdk.proto.streams.Subscribe;
import games.mythical.saga.sdk.proto.streams.bridge.BridgeStatusConfirmRequest;
import games.mythical.saga.sdk.proto.streams.bridge.BridgeStatusUpdate;
import games.mythical.saga.sdk.proto.streams.bridge.BridgeStreamGrpc;
import games.mythical.saga.sdk.server.StreamingService;
import games.mythical.saga.sdk.util.ConcurrentFinisher;
import io.grpc.stub.StreamObserver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MockBridgeStreamingImpl extends BridgeStreamGrpc.BridgeStreamImplBase implements StreamingService {
    private final Map<String, StreamObserver<BridgeStatusUpdate>> streamObservers = new ConcurrentHashMap<>();

    @Override
    public void bridgeStatusStream(Subscribe request, StreamObserver<BridgeStatusUpdate> responseObserver) {
        streamObservers.putIfAbsent(request.getTitleId(), responseObserver);
    }

    @Override
    public void bridgeStatusConfirmation(BridgeStatusConfirmRequest request, StreamObserver<Empty> responseObserver) {
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();

        ConcurrentFinisher.finish(request.getTraceId());
    }

    @Override
    public void sendStatus(String titleId, GeneratedMessageV3 genericStatusUpdateProto) {
        if (streamObservers.containsKey(titleId)) {
            var observer = streamObservers.get(titleId);
            observer.onNext((BridgeStatusUpdate) genericStatusUpdateProto);
        }
    }

    @Override
    public void reset() {
        for (var observer : streamObservers.values()) {
            observer.onCompleted();
        }

        streamObservers.clear();
    }
}
