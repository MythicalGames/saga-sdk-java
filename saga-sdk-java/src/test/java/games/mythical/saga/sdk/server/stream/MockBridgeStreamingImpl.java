package games.mythical.saga.sdk.server.stream;

import com.google.protobuf.Empty;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.ProtocolMessageEnum;
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
    public void sendStatus(String titleId, GeneratedMessageV3 genericProto, ProtocolMessageEnum genericState) {
        var proto = (BridgeStatusUpdate) genericProto;
        if (streamObservers.containsKey(titleId)) {
            var observer = streamObservers.get(titleId);
            var orderStatus = BridgeStatusUpdate.newBuilder()
                    .setOauthId(proto.getOauthId())
                    .setTraceId(proto.getTraceId())
                    .setItemTypeAddress(proto.getItemTypeAddress())
                    .setItemAddress(proto.getItemAddress())
                    .setDestinationAddress(proto.getDestinationAddress())
                    .setDestinationChain(proto.getDestinationChain())
                    .setOriginAddress(proto.getOriginAddress())
                    .setMythicalTransactionId(proto.getMythicalTransactionId())
                    .setMainnetTransactionId(proto.getMainnetTransactionId())
                    .build();
            observer.onNext(orderStatus);
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
