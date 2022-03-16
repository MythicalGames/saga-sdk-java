package games.mythical.saga.sdk.client.model;

import games.mythical.proto_util.ProtoUtil;
import games.mythical.saga.sdk.proto.api.payments.Address;
import games.mythical.saga.sdk.proto.api.payments.CardPaymentData;
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
    private String traceId;
    private String oauthId;
    private CardPaymentData cardPaymentData;
    private Address address;

    public static SagaPaymentMethod fromProto(PaymentMethodProto proto) {
        var paymentMethod = ProtoUtil.toDto(proto, SagaPaymentMethod.class);
        return paymentMethod;
    }
}
