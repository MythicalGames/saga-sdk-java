package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.proto.common.order.OrderState;

import java.math.BigDecimal;

public interface SagaOrderExecutor {
    void updateOrder(String oauthId,
                     String traceId,
                     String quoteId,
                     String orderId,
                     BigDecimal total,
                     OrderState orderState) throws Exception;

    void emitReceived(String quoteId, String traceId) throws Exception;
}
