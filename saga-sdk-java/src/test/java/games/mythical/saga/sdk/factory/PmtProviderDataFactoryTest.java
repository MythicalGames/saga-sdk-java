package games.mythical.saga.sdk.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import games.mythical.saga.sdk.client.model.SagaCreditCardData;
import org.junit.jupiter.api.Test;

public class PmtProviderDataFactoryTest {
    public static final String CC_FIRST_NAME = "First";
    public static final String CC_LAST_NAME = "Last";
    public static final String CC_ADDRESS_LINE_1 = "Line1";
    public static final String CC_ADDRESS_LINE_2 = "Line2";
    public static final String CC_CITY = "City";
    public static final String CC_STATE = "ST";
    public static final String CC_POSTAL_CODE = "12345";
    public static final String CC_COUNTRY_ISO_ALPHA_2 = "CA";
    public static final String CC_EXPIRATION_MONTH = "07";
    public static final String CC_EXPIRATION_YEAR = "2031";
    public static final String CC_CARD_TYPE = "AMEX";
    public static final String CC_INSTRUMENT_ID = "12345654321";
    public static final String CC_PAYMENT_METHOD_TOKEN_ID = "doujop";

    public static final String UP_CARD_ID = "b2e36312-d0fa-4b82-a63d-9df7ab15a0af";

    @Test
    public void testFromCreditCard() {
        final var sagaCC = SagaCreditCardData.builder()
            .firstName(CC_FIRST_NAME)
            .lastName(CC_LAST_NAME)
            .addressLine1(CC_ADDRESS_LINE_1)
            .addressLine2(CC_ADDRESS_LINE_2)
            .city(CC_CITY)
            .state(CC_STATE)
            .postalCode(CC_POSTAL_CODE)
            .countryIsoAlpha2(CC_COUNTRY_ISO_ALPHA_2)
            .expirationMonth(CC_EXPIRATION_MONTH)
            .expirationYear(CC_EXPIRATION_YEAR)
            .cardType(CC_CARD_TYPE)
            .instrumentId(CC_INSTRUMENT_ID)
            .paymentMethodTokenId(CC_PAYMENT_METHOD_TOKEN_ID)
            .build();

        final var pmtData = PmtProviderDataFactory.fromCreditCard(sagaCC);
        assertTrue(pmtData.hasCreditCardData());
        final var ccData = pmtData.getCreditCardData();
        assertEquals(CC_FIRST_NAME, ccData.getFirstName());
        assertEquals(CC_LAST_NAME, ccData.getLastName());
        assertEquals(CC_ADDRESS_LINE_1, ccData.getAddressLine1());
        assertEquals(CC_ADDRESS_LINE_2, ccData.getAddressLine2());
        assertEquals(CC_CITY, ccData.getCity());
        assertEquals(CC_STATE, ccData.getState());
        assertEquals(CC_POSTAL_CODE, ccData.getPostalCode());
        assertEquals(CC_COUNTRY_ISO_ALPHA_2, ccData.getCountryIsoAlpha2());
        assertEquals(CC_EXPIRATION_MONTH, ccData.getExpirationMonth());
        assertEquals(CC_EXPIRATION_YEAR, ccData.getExpirationYear());
        assertEquals(CC_CARD_TYPE, ccData.getCardType());
        assertEquals(CC_INSTRUMENT_ID, ccData.getInstrumentId());
        assertEquals(CC_PAYMENT_METHOD_TOKEN_ID, ccData.getPaymentMethodTokenId());
    }

    @Test
    public void testFromUpholdCard() {
        final var pmtData = PmtProviderDataFactory.fromUpholdCard(UP_CARD_ID);
        assertTrue(pmtData.hasUpholdCardId());
        assertEquals(UP_CARD_ID, pmtData.getUpholdCardId());
    }
}
