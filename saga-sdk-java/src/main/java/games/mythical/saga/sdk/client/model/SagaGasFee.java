package games.mythical.saga.sdk.client.model;

import games.mythical.proto_util.ProtoUtil;
import games.mythical.saga.sdk.proto.api.myth.GasFeeProto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaGasFee {
    private BigDecimal gweiAmount;
    private BigDecimal ethAmount;
    private BigDecimal convertedAmount;
    private String convertedCurrency;

    public static SagaGasFee fromProto(GasFeeProto proto) {
        var gasFee = ProtoUtil.toDto(proto, SagaGasFee.class);
        return gasFee;
    }
}
