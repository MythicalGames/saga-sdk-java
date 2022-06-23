package games.mythical.saga.sdk.client.model;

import games.mythical.proto_util.ProtoUtil;
import games.mythical.proto_util.dto.DtoExclude;
import games.mythical.saga.sdk.factory.PmtDataFactory;
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
    @DtoExclude
    private SagaPaymentData sagaPaymentData;
    @DtoExclude
    private SagaAddress address;

    public static SagaPaymentMethod fromProto(PaymentMethodProto proto) {
        var paymentMethod = ProtoUtil.toDto(proto, SagaPaymentMethod.class);

        if (proto.hasAddress()) {
            paymentMethod.setAddress(ProtoUtil.toDto(proto.getAddress(), SagaAddress.class));
        }

        if (proto.hasPaymentMethodData()) {
            paymentMethod.setSagaPaymentData(PmtDataFactory.fromProto(proto.getPaymentMethodData()));
        }

        return paymentMethod;
    }

    public static List<SagaPaymentMethod> fromProtos(PaymentMethodProtos protos) {
        return protos.getPaymentMethodsList().stream()
                .map(SagaPaymentMethod::fromProto)
                .collect(Collectors.toList());
    }
}
