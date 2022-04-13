package games.mythical.saga.sdk.client.model;

import games.mythical.proto_util.ProtoUtil;
import games.mythical.saga.sdk.proto.api.payment.Address;
import games.mythical.saga.sdk.proto.api.payment.PaymentMethodData;
import games.mythical.saga.sdk.proto.api.payment.PaymentMethodProto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaPaymentMethod {
    private String traceId;
    private String oauthId;
    private PaymentMethodData paymentMethodData;
    private Address address;

    public static SagaPaymentMethod fromProto(PaymentMethodProto proto) {
        var paymentMethod = ProtoUtil.toDto(proto, SagaPaymentMethod.class);
        return paymentMethod;
    }
}
