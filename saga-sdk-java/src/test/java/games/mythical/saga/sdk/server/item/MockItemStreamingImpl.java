package games.mythical.saga.sdk.server.item;

import com.google.protobuf.Empty;
import games.mythical.saga.sdk.proto.api.item.ItemProto;
import games.mythical.saga.sdk.proto.common.item.ItemState;
import games.mythical.saga.sdk.proto.streams.Subscribe;
import games.mythical.saga.sdk.proto.streams.item.ItemStatusConfirmRequest;
import games.mythical.saga.sdk.proto.streams.item.ItemStatusUpdate;
import games.mythical.saga.sdk.proto.streams.item.ItemStreamGrpc;
import games.mythical.saga.sdk.util.ConcurrentFinisher;
import io.grpc.stub.StreamObserver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MockItemStreamingImpl extends ItemStreamGrpc.ItemStreamImplBase {
    private final Map<String, StreamObserver<ItemStatusUpdate>> streamObservers = new ConcurrentHashMap<>();

    @Override
    public void itemStatusStream(Subscribe request, StreamObserver<ItemStatusUpdate> responseObserver) {
        streamObservers.putIfAbsent(request.getEnvironmentId(), responseObserver);
    }

    @Override
    public void itemStatusConfirmation(ItemStatusConfirmRequest request, StreamObserver<Empty> responseObserver) {
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();

        ConcurrentFinisher.finish(request.getTraceId());
    }

    public void sendStatus(String environmentId, ItemProto proto, ItemState state) {
        if (streamObservers.containsKey(environmentId)) {
            var observer = streamObservers.get(environmentId);
            var itemStatus = ItemStatusUpdate.newBuilder()
                    .setGameInventoryId(proto.getGameInventoryId())
                    .setOauthId(proto.getOauthId())
                    .setSerialNumber(proto.getSerialNumber())
                    .setGameItemTypeId(proto.getGameItemTypeId())
                    .setMetadataUri(proto.getMetadataUri())
                    .setTraceId(proto.getTraceId())
                    .setItemState(state)
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
