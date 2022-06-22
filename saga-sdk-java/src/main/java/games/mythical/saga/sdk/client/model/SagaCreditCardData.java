package games.mythical.saga.sdk.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaCreditCardData {
    private String firstName;
    private String lastName;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String countryName;
    private String countryIsoAlpha2;
    private String expirationMonth;
    private String expirationYear;
    private String cardType;
    private String instrumentId;
    private String paymentMethodTokenId;
}
