package games.mythical.saga.sdk.server.stream;

import com.google.protobuf.Empty;
import com.google.protobuf.GeneratedMessageV3;
import games.mythical.saga.sdk.proto.streams.Subscribe;
import games.mythical.saga.sdk.proto.streams.itemtype.ItemTypeStatusConfirmRequest;
import games.mythical.saga.sdk.proto.streams.itemtype.ItemTypeStatusUpdate;
import games.mythical.saga.sdk.proto.streams.itemtype.ItemTypeStreamGrpc;
import games.mythical.saga.sdk.server.StreamingService;
import games.mythical.saga.sdk.util.ConcurrentFinisher;
import io.grpc.stub.StreamObserver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MockItemTypeStreamingImpl extends ItemTypeStreamGrpc.ItemTypeStreamImplBase implements StreamingService {
    private final Map<String, StreamObserver<ItemTypeStatusUpdate>> streamObservers = new ConcurrentHashMap<>();

    @Override
    public void itemTypeStatusStream(Subscribe request, StreamObserver<ItemTypeStatusUpdate> responseObserver) {
        streamObservers.putIfAbsent(request.getTitleId(), responseObserver);
    }

    @Override
    public void itemTypeStatusConfirmation(ItemTypeStatusConfirmRequest request, StreamObserver<Empty> responseObserver) {
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();

        ConcurrentFinisher.finish(request.getTraceId());
    }

    @Override
    public void sendStatus(String environmentId, GeneratedMessageV3 genericStatusUpdateProto) {
        if (streamObservers.containsKey(environmentId)) {
            var observer = streamObservers.get(environmentId);
            observer.onNext((ItemTypeStatusUpdate) genericStatusUpdateProto);
        }
    }

    @Override
    public void reset() {
        streamObservers.values().forEach(StreamObserver::onCompleted);
        streamObservers.clear();
    }
}
