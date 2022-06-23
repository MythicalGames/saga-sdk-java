package games.mythical.saga.sdk.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaCybersourcePaymentData implements SagaPaymentData {
    private boolean defaultMethod;
    private String expirationMonth;
    private String expirationYear;
    private String cardType;
    private String instrumentId;
    private String paymentMethodTokenId;

    @Override
    public Type getType() { return Type.CYBERSOURCE; }

    @Override
    public SagaCybersourcePaymentData asCybersource() { return this; }
}
