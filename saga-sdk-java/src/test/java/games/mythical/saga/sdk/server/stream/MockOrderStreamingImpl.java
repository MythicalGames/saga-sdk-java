package games.mythical.saga.sdk.server.stream;

import com.google.protobuf.Empty;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.ProtocolMessageEnum;
import games.mythical.saga.sdk.proto.api.order.QuoteProto;
import games.mythical.saga.sdk.proto.common.order.OrderState;
import games.mythical.saga.sdk.proto.streams.Subscribe;
import games.mythical.saga.sdk.proto.streams.order.OrderStatusConfirmRequest;
import games.mythical.saga.sdk.proto.streams.order.OrderStatusUpdate;
import games.mythical.saga.sdk.proto.streams.order.OrderStreamGrpc;
import games.mythical.saga.sdk.server.StreamingService;
import games.mythical.saga.sdk.util.ConcurrentFinisher;
import io.grpc.stub.StreamObserver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MockOrderStreamingImpl extends OrderStreamGrpc.OrderStreamImplBase implements StreamingService {
    private final Map<String, StreamObserver<OrderStatusUpdate>> streamObservers = new ConcurrentHashMap<>();

    @Override
    public void orderStatusStream(Subscribe request, StreamObserver<OrderStatusUpdate> responseObserver) {
        streamObservers.putIfAbsent(request.getTitleId(), responseObserver);
    }

    @Override
    public void orderStatusConfirmation(OrderStatusConfirmRequest request, StreamObserver<Empty> responseObserver) {
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();

        ConcurrentFinisher.finish(request.getTraceId());
    }

    @Override
    public void sendStatus(String titleId, GeneratedMessageV3 genericProto, ProtocolMessageEnum genericState) {
        var proto = (QuoteProto) genericProto;
        var state = (OrderState) genericState;
        if (streamObservers.containsKey(titleId)) {
            var observer = streamObservers.get(titleId);
            var orderStatus = OrderStatusUpdate.newBuilder()
                    .setOauthId(proto.getOauthId())
                    .setTraceId(proto.getTraceId())
                    .setQuoteId(proto.getQuoteId())
                    .setOrderId(proto.getQuoteId())
                    .setTotal(proto.getTotal())
                    .setOrderState(state)
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
