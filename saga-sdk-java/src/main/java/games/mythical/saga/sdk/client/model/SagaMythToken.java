package games.mythical.saga.sdk.client.model;

import games.mythical.saga.sdk.proto.common.myth.MythTokenState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaMythToken {
    private String traceId;
    private String quoteId;
    private String originSubAccount;
    private BigDecimal totalAmount;
    private BigDecimal gasFee;
    private MythTokenState tokenState;

}
