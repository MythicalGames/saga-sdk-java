package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.config.Constants;
import games.mythical.saga.sdk.proto.common.order.OrderState;

import java.math.BigDecimal;

public interface SagaOrderExecutor extends BaseSagaExecutor {
    String UNKNOWN_ORDER = Constants.UNKNOWN_ID;

    void updateOrder(String oauthId,
                     String traceId,
                     String quoteId,
                     String orderId,
                     BigDecimal total,
                     OrderState orderState) throws Exception;
}
