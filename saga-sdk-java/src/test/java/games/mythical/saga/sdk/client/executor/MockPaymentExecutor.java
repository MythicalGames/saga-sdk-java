package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.client.model.SagaPaymentMethod;
import games.mythical.saga.sdk.proto.common.payment.PaymentMethodUpdateStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MockPaymentExecutor extends MockBaseExecutor implements SagaPaymentExecutor {
    private SagaPaymentMethod paymentMethod;
    private PaymentMethodUpdateStatus methodUpdateStatus;
    private String traceId;

    @Override
    public void updatePaymentMethod(String traceId,
                                    SagaPaymentMethod paymentMethod,
                                    PaymentMethodUpdateStatus methodUpdateStatus) {
        this.traceId = traceId;
        this.paymentMethod = paymentMethod;
        this.methodUpdateStatus = methodUpdateStatus;
    }
}
