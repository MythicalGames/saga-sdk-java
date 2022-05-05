package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.client.model.SagaPaymentMethod;
import games.mythical.saga.sdk.proto.common.payment.PaymentMethodUpdateStatus;

public interface SagaPaymentExecutor extends BaseSagaExecutor {
    void updatePaymentMethod(String traceId,
                             SagaPaymentMethod paymentMethod,
                             PaymentMethodUpdateStatus methodStatus) throws Exception;
}
