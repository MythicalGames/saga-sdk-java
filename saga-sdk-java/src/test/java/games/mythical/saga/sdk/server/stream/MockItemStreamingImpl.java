package games.mythical.saga.sdk.server.stream;

import com.google.protobuf.Empty;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.ProtocolMessageEnum;
import games.mythical.saga.sdk.proto.api.item.ItemProto;
import games.mythical.saga.sdk.proto.common.item.ItemState;
import games.mythical.saga.sdk.proto.streams.Subscribe;
import games.mythical.saga.sdk.proto.streams.item.ItemStatusConfirmRequest;
import games.mythical.saga.sdk.proto.streams.item.ItemStatusUpdate;
import games.mythical.saga.sdk.proto.streams.item.ItemStreamGrpc;
import games.mythical.saga.sdk.server.StreamingService;
import games.mythical.saga.sdk.util.ConcurrentFinisher;
import io.grpc.stub.StreamObserver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MockItemStreamingImpl extends ItemStreamGrpc.ItemStreamImplBase implements StreamingService {
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

    @Override
    public void sendStatus(String titleId, GeneratedMessageV3 genericProto, ProtocolMessageEnum genericState) {
        var proto = (ItemProto) genericProto;
        var state = (ItemState) genericState;
        if (streamObservers.containsKey(titleId)) {
            var observer = streamObservers.get(titleId);
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

    @Override
    public void reset() {
        streamObservers.values().forEach(StreamObserver::onCompleted);
        streamObservers.clear();
    }
}
