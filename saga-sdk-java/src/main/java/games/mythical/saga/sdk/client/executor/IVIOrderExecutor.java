package games.mythical.saga.sdk.client.executor;

import games.mythical.ivi.sdk.proto.common.order.OrderState;

public interface IVIOrderExecutor {
    void updateOrder(String orderId, OrderState orderState) throws Exception;
}
