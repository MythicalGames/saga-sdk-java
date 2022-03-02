package games.mythical.saga.sdk.client.model;

import games.mythical.saga.sdk.proto.api.payments.PaymentMethodProto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaPaymentMethod {
    private String oauthId;

    public static SagaPaymentMethod fromProto(PaymentMethodProto proto) {
        var paymentMethod = ProtoUtil.toDto(proto, SagaPaymentMethod.class);
        return paymentMethod;
    }
}
