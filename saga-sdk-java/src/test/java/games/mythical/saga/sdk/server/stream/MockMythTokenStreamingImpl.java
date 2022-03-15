package games.mythical.saga.sdk.server.stream;

import com.google.protobuf.Empty;
import com.google.protobuf.GeneratedMessageV3;
import games.mythical.saga.sdk.proto.streams.Subscribe;
import games.mythical.saga.sdk.proto.streams.myth.MythTokenStatusConfirmRequest;
import games.mythical.saga.sdk.proto.streams.myth.MythTokenStatusUpdate;
import games.mythical.saga.sdk.proto.streams.myth.MythTokenStreamGrpc;
import games.mythical.saga.sdk.server.StreamingService;
import games.mythical.saga.sdk.util.ConcurrentFinisher;
import io.grpc.stub.StreamObserver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MockMythTokenStreamingImpl extends MythTokenStreamGrpc.MythTokenStreamImplBase implements StreamingService {
    private final Map<String, StreamObserver<MythTokenStatusUpdate>> streamObserverMap = new ConcurrentHashMap<>();

    @Override
    public void mythTokenStatusStream(Subscribe request, StreamObserver<MythTokenStatusUpdate> responseObserver) {
        streamObserverMap.putIfAbsent(request.getTitleId(), responseObserver);
    }

    @Override
    public void mythTokenStatusConfirmation(MythTokenStatusConfirmRequest request, StreamObserver<Empty> responseObserver) {
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();

        ConcurrentFinisher.finish(request.getTraceId());
    }

    @Override
    public void sendStatus(String environmentId, GeneratedMessageV3 genericStatusUpdateProto) {
        if (streamObserverMap.containsKey(environmentId)) {
            var observer = streamObserverMap.get(environmentId);
            observer.onNext((MythTokenStatusUpdate) genericStatusUpdateProto);
        }
    }

    @Override
    public void reset() {
        streamObserverMap.values().forEach(StreamObserver::onCompleted);
        streamObserverMap.clear();
    }
}
