package games.mythical.saga.sdk.client.observer;

import games.mythical.saga.sdk.client.executor.SagaOrderExecutor;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.proto.common.order.OrderState;
import games.mythical.saga.sdk.proto.streams.order.OrderStatusConfirmRequest;
import games.mythical.saga.sdk.proto.streams.order.OrderStatusUpdate;
import games.mythical.saga.sdk.proto.streams.order.OrderStreamGrpc;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.function.Consumer;

@Slf4j
public class SagaOrderObserver extends AbstractObserver<OrderStatusUpdate> {
    private final SagaSdkConfig config;
    private final SagaOrderExecutor sagaOrderExecutor;
    private final OrderStreamGrpc.OrderStreamBlockingStub streamBlockingStub;
    private final Consumer<SagaOrderObserver> resubscribe;

    public SagaOrderObserver(SagaSdkConfig config,
                             SagaOrderExecutor sagaOrderExecutor,
                             OrderStreamGrpc.OrderStreamBlockingStub streamBlockingStub,
                             Consumer<SagaOrderObserver> resubscribe) {
        this.config = config;
        this.sagaOrderExecutor = sagaOrderExecutor;
        this.streamBlockingStub = streamBlockingStub;
        this.resubscribe = resubscribe;
    }

    @Override
    public void onNext(OrderStatusUpdate message) {
        log.trace("OrderObserver.onNext for user: {}", message.getOauthId());
        resetConnectionRetry();
        try {
            sagaOrderExecutor.updateOrder(
                    message.getOauthId(),
                    message.getTraceId(),
                    message.getQuoteId(),
                    message.getOrderId(),
                    new BigDecimal(message.getTotal()),
                    message.getOrderState()
            );
            updateOrderConfirmation(message.getOauthId(), message.getTraceId(), message.getOrderState());
        } catch (Exception e) {
            log.error("Exception calling updateOrder for {}. {}", message.getOauthId(), e);
        }
    }

    @Override
    public void onError(Throwable t) {
        log.error("OrderObserver.onError", t);
        sleepBetweenReconnects();
        resubscribe.accept(this);
    }

    @Override
    public void onCompleted() {
        log.info("OrderObserver stream closed");
        sleepBetweenReconnects();
        resubscribe.accept(this);
    }

    private void updateOrderConfirmation(String oauthId, String traceId, OrderState orderState) {
        var request = OrderStatusConfirmRequest.newBuilder()
                .setOauthId(oauthId)
                .setTraceId(traceId)
                .setOrderState(orderState)
                .build();
        streamBlockingStub.orderStatusConfirmation(request);
    }
}
