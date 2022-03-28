package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.proto.common.payment.PaymentMethodUpdateStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MockPaymentExecutor extends MockBaseExecutor implements SagaPaymentExecutor {
    private String oauthId;
    private boolean isDefault;
    private PaymentMethodUpdateStatus methodUpdateStatus;
    private String traceId;

    @Override
    public void updatePaymentMethod(String oauthId,
                                    boolean isDefault,
                                    PaymentMethodUpdateStatus methodUpdateStatus) throws Exception {
        this.oauthId = oauthId;
        this.isDefault = isDefault;
        this.methodUpdateStatus = methodUpdateStatus;
    }

    @Override
    public void emitReceived(String oauthId, String traceId) throws Exception {
        this.oauthId = oauthId;
        this.traceId = traceId;
    }
}
