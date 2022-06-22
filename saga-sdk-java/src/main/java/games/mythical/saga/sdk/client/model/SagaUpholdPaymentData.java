package games.mythical.saga.sdk.client.model;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaUpholdPaymentData implements SagaPaymentData {
    private boolean defaultMethod;
    private String tempStateCode;
    private String email;
    private String status;
    private String verifications;
    private String birthDate;
    private List<SagaUpholdCard> cards;

    @Override
    public Type getType() { return Type.UPHOLD; }

    @Override
    public SagaUpholdPaymentData asUphold() { return this; }
}
