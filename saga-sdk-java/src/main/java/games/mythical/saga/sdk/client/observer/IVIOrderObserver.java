package games.mythical.saga.sdk.client.observer;

import games.mythical.saga.sdk.client.executor.IVIOrderExecutor;
import games.mythical.saga.sdk.config.IVIConfiguration;
import games.mythical.ivi.sdk.proto.common.order.OrderState;
import games.mythical.ivi.sdk.proto.streams.order.OrderStatusConfirmRequest;
import games.mythical.ivi.sdk.proto.streams.order.OrderStatusUpdate;
import games.mythical.ivi.sdk.proto.streams.order.OrderStreamGrpc;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
public class IVIOrderObserver extends AbstractObserver<OrderStatusUpdate> {
    private final IVIOrderExecutor orderExecutor;
    private final OrderStreamGrpc.OrderStreamBlockingStub streamBlockingStub;
    private final Consumer<IVIOrderObserver> resubscribe;

    public IVIOrderObserver(IVIOrderExecutor orderExecutor,
                            OrderStreamGrpc.OrderStreamBlockingStub streamBlockingStub,
                            Consumer<IVIOrderObserver> resubscribe) {
        this.orderExecutor = orderExecutor;
        this.streamBlockingStub = streamBlockingStub;
        this.resubscribe = resubscribe;
    }

    @Override
    public void onNext(OrderStatusUpdate message) {
        log.trace("IVIOrderObserver.onNext for order {}", message.getOrderId());
        resetConnectionRetry();
        try {
            orderExecutor.updateOrder(message.getOrderId(), message.getOrderState());
            updateOrderConfirmation(message.getOrderId(), message.getOrderState());
        } catch (Exception e) {
            log.error("Exception calling updateOrder for {}. {}", message.getOrderId(), e);
        }
    }

    @Override
    public void onError(Throwable t) {
        log.error("IVIOrderObserver.onError", t);
        sleepBetweenReconnects();
        resubscribe.accept(this);
    }

    @Override
    public void onCompleted() {
        log.info("IVIOrderObserver stream closed");
        sleepBetweenReconnects();
        resubscribe.accept(this);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void updateOrderConfirmation(String orderId, OrderState orderState) {
        var request = OrderStatusConfirmRequest.newBuilder()
                .setEnvironmentId(IVIConfiguration.getEnvironmentId())
                .setOrderId(orderId)
                .setOrderState(orderState)
                .build();
        streamBlockingStub.orderStatusConfirmation(request);
    }
}
