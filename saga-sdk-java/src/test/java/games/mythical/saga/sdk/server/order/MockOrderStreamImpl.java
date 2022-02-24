package games.mythical.saga.sdk.server.order;

import com.google.protobuf.Empty;
import games.mythical.ivi.sdk.proto.common.order.OrderState;
import games.mythical.ivi.sdk.proto.streams.Subscribe;
import games.mythical.ivi.sdk.proto.streams.order.OrderStatusConfirmRequest;
import games.mythical.ivi.sdk.proto.streams.order.OrderStatusUpdate;
import games.mythical.ivi.sdk.proto.streams.order.OrderStreamGrpc;
import games.mythical.saga.sdk.util.ConcurrentFinisher;
import io.grpc.stub.StreamObserver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MockOrderStreamImpl extends OrderStreamGrpc.OrderStreamImplBase {
    private final Map<String, StreamObserver<OrderStatusUpdate>> streamObservers = new ConcurrentHashMap<>();
    private MockOrderServiceImpl orderService;

    @Override
    public void orderStatusStream(Subscribe request, StreamObserver<OrderStatusUpdate> responseObserver) {
        streamObservers.putIfAbsent(request.getEnvironmentId(), responseObserver);
    }

    @Override
    public void orderStatusConfirmation(OrderStatusConfirmRequest request, StreamObserver<Empty> responseObserver) {
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();

        ConcurrentFinisher.finish(request.getOrderId());
    }

    public void setOrderService(MockOrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    public void sendStatus(String environmentId, String orderId, OrderState state) {
        if(streamObservers.containsKey(environmentId)) {
            var observer = streamObservers.get(environmentId);

            if(orderService != null) {
                orderService.updateOrder(orderId, state);
            }

            var orderStatus = OrderStatusUpdate.newBuilder()
                    .setOrderId(orderId)
                    .setOrderState(state)
                    .build();

            observer.onNext(orderStatus);
        }
    }

    public void reset() {
        for (var observer : streamObservers.values()) {
            observer.onCompleted();
        }

        streamObservers.clear();
    }
}
