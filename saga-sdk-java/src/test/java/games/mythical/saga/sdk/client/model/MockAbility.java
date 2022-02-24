package games.mythical.saga.sdk.client.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class MockAbility {
    private String ability1;
    private int ability2;
    private BigDecimal ability3;
}
