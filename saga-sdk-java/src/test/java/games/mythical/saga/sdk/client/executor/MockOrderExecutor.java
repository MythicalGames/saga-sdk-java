package games.mythical.saga.sdk.client.executor;

import games.mythical.ivi.sdk.proto.common.order.OrderState;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MockOrderExecutor implements IVIOrderExecutor {
    private String orderId;
    private OrderState orderStatus;

    @Override
    public void updateOrder(String orderId, OrderState orderState) throws Exception {
        this.orderId = orderId;
        this.orderStatus = orderState;
    }
}
