package games.mythical.saga.sdk.client.model;

import games.mythical.proto_util.ProtoUtil;
import games.mythical.proto_util.dto.DtoExclude;
import games.mythical.saga.sdk.proto.api.payment.PaymentMethodData;
import games.mythical.saga.sdk.proto.api.payment.PaymentMethodProto;
import games.mythical.saga.sdk.proto.api.payment.PaymentMethodProtos;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaPaymentMethod {
    private String traceId;
    private String oauthId;
    private PaymentMethodData paymentMethodData;
    @DtoExclude
    private SagaAddress address;

    public static SagaPaymentMethod fromProto(PaymentMethodProto proto) {
        var paymentMethod = ProtoUtil.toDto(proto, SagaPaymentMethod.class);

        if (proto.hasAddress()) {
            paymentMethod.setAddress(ProtoUtil.toDto(proto.getAddress(), SagaAddress.class));
        }

        return paymentMethod;
    }

    public static List<SagaPaymentMethod> fromProtos(PaymentMethodProtos protos) {
        return protos.getPaymentMethodProtosList().stream()
                .map(SagaPaymentMethod::fromProto)
                .collect(Collectors.toList());
    }
}
