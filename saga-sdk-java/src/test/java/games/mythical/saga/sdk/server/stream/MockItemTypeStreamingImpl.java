package games.mythical.saga.sdk.server.stream;

import com.google.protobuf.Empty;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.ProtocolMessageEnum;
import games.mythical.saga.sdk.proto.api.itemtype.ItemTypeProto;
import games.mythical.saga.sdk.proto.common.itemtype.ItemTypeState;
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
    public void sendStatus(String environmentId, GeneratedMessageV3 genericProto, ProtocolMessageEnum genericState) {
        var proto = (ItemTypeProto) genericProto;
        var state = (ItemTypeState) genericState;
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

    @Override
    public void reset() {
        streamObservers.values().forEach(StreamObserver::onCompleted);
        streamObservers.clear();
    }
}
