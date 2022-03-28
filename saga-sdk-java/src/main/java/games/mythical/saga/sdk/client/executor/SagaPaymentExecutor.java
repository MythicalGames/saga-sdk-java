package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.proto.common.payment.PaymentMethodUpdateStatus;

public interface SagaPaymentExecutor extends BaseSagaExecutor {
    void updatePaymentMethod(String oauthId,
                             boolean isDefault,
                             PaymentMethodUpdateStatus methodStatus) throws Exception;

    void emitReceived(String oauthId, String traceId) throws Exception;
}
