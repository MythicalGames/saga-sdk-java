package games.mythical.saga.sdk.server.itemtype;

import com.google.protobuf.Empty;
import games.mythical.saga.sdk.proto.api.itemtype.ItemTypeProto;
import games.mythical.saga.sdk.proto.common.itemtype.ItemTypeState;
import games.mythical.saga.sdk.proto.streams.Subscribe;
import games.mythical.saga.sdk.proto.streams.itemtype.ItemTypeStatusConfirmRequest;
import games.mythical.saga.sdk.proto.streams.itemtype.ItemTypeStatusUpdate;
import games.mythical.saga.sdk.proto.streams.itemtype.ItemTypeStreamGrpc;
import games.mythical.saga.sdk.util.ConcurrentFinisher;
import io.grpc.stub.StreamObserver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MockItemTypeStreamingImpl extends ItemTypeStreamGrpc.ItemTypeStreamImplBase {
    private final Map<String, StreamObserver<ItemTypeStatusUpdate>> streamObservers = new ConcurrentHashMap<>();

    @Override
    public void itemTypeStatusStream(Subscribe request, StreamObserver<ItemTypeStatusUpdate> responseObserver) {
        streamObservers.putIfAbsent(request.getEnvironmentId(), responseObserver);
    }

    @Override
    public void itemTypeStatusConfirmation(ItemTypeStatusConfirmRequest request, StreamObserver<Empty> responseObserver) {
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();

        ConcurrentFinisher.finish(request.getTraceId());
    }

    public void sendStatus(String environmentId, ItemTypeProto proto, ItemTypeState state) {
        if (streamObservers.containsKey(environmentId)) {
            var observer = streamObservers.get(environmentId);
            var itemStatus = ItemTypeStatusUpdate.newBuilder()
                    .setGameItemTypeId(proto.getGameItemTypeId())
                    .setTraceId(proto.getTraceId())
                    .setItemTypeState(state)
                    .build();
            observer.onNext(itemStatus);
        }
    }

    public void reset() {
        for (var observer : streamObservers.values()) {
            observer.onCompleted();
        }

        streamObservers.clear();
    }
}
