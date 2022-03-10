package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.proto.common.order.OrderState;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class MockOrderExecutor implements SagaOrderExecutor {
    private String oauthId;
    private String traceId;
    private String quoteId;
    private String orderId;
    private BigDecimal total;
    private OrderState orderState;

    @Override
    public void updateOrder(String oauthId,
                            String traceId,
                            String quoteId,
                            String orderId,
                            BigDecimal total,
                            OrderState orderState) throws Exception {
        this.oauthId = oauthId;
        this.traceId = traceId;
        this.quoteId = quoteId;
        this.orderId = orderId;
        this.total = total;
        this.orderState = orderState;
    }

    @Override
    public void emitReceived(String quoteId, String traceId) throws Exception {
        this.quoteId = quoteId;
        this.traceId = traceId;
    }
}
