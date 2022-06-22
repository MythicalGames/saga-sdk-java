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
public class SagaUpholdCard {
    private String upholdId;
    private String currency;
    private BigDecimal balance;
    private String normalizedCurrency;
    private BigDecimal normalizedBalance;
    private String label;
}
