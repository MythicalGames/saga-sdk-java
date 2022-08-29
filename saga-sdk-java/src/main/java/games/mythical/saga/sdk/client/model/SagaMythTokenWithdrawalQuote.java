package games.mythical.saga.sdk.client.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaMythTokenWithdrawalQuote {
    private BigDecimal totalAmount;
    private BigDecimal gasFee;
    private String quoteId;
    private long ttl;
}
