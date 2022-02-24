package games.mythical.saga.sdk.server.itemtype;

import com.google.protobuf.Empty;
import games.mythical.ivi.sdk.proto.api.itemtype.ItemType;
import games.mythical.ivi.sdk.proto.common.itemtype.ItemTypeState;
import games.mythical.ivi.sdk.proto.streams.Subscribe;
import games.mythical.ivi.sdk.proto.streams.itemtype.ItemTypeStatusConfirmRequest;
import games.mythical.ivi.sdk.proto.streams.itemtype.ItemTypeStatusStreamGrpc;
import games.mythical.ivi.sdk.proto.streams.itemtype.ItemTypeStatusUpdate;
import games.mythical.saga.sdk.util.ConcurrentFinisher;
import io.grpc.stub.StreamObserver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MockItemTypeStreamImpl extends ItemTypeStatusStreamGrpc.ItemTypeStatusStreamImplBase {
    private final Map<String, StreamObserver<ItemTypeStatusUpdate>> streamObservers = new ConcurrentHashMap<>();

    @Override
    public void itemTypeStatusStream(Subscribe request, StreamObserver<ItemTypeStatusUpdate> responseObserver) {
        streamObservers.putIfAbsent(request.getEnvironmentId(), responseObserver);
    }

    @Override
    public void itemTypeStatusConfirmation(ItemTypeStatusConfirmRequest request, StreamObserver<Empty> responseObserver) {
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();

        ConcurrentFinisher.finish(request.getTrackingId());
    }

    public void sendStatus(String environmentId, ItemType itemType, ItemTypeState state) {
        if(streamObservers.containsKey(environmentId)) {
            var observer = streamObservers.get(environmentId);
            var itemTypeStatus = ItemTypeStatusUpdate.newBuilder()
                    .setGameItemTypeId(itemType.getGameItemTypeId())
                    .setCurrentSupply(itemType.getCurrentSupply())
                    .setIssuedSupply(itemType.getIssuedSupply())
                    .setBaseUri(itemType.getBaseUri())
                    .setIssueTimeSpan(itemType.getIssueTimeSpan())
                    .setTrackingId(itemType.getTrackingId())
                    .setItemTypeState(state)
                    .build();
            observer.onNext(itemTypeStatus);
        }
    }

    public void reset() {
        for (var observer : streamObservers.values()) {
            observer.onCompleted();
        }

        streamObservers.clear();
    }
}
