package games.mythical.saga.sdk.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import games.mythical.saga.sdk.proto.api.payment.CybersourcePaymentData;
import games.mythical.saga.sdk.proto.api.payment.PaymentMethodData;
import games.mythical.saga.sdk.proto.api.payment.UpholdCardProto;
import games.mythical.saga.sdk.proto.api.payment.UpholdPaymentData;
import org.junit.jupiter.api.Test;

public class PmtDataFactoryTest {
    public static final String CARD_TYPE = "VISA";
    public static final String EXP_MON = "08";
    public static final String EXP_YEAR = "2034";
    public static final String INSTRUMENT_ID = "1234";
    public static final String PAYMENT_METHOD_TOKEN_ID = "4321";

    public static final String TEMP_STATE_CODE = "1234567890";
    public static final String EMAIL = "nobody@nowhere.nil";
    public static final String STATUS = "fine";
    public static final String VERIFICATIONS = "verify";
    public static final String BIRTH_DATE = "06/23/2022";

    public static final String CARD_1_UPHOLD_ID = "4208e346-66c7-47f3-8e9d-ff71861071b0";
    public static final String CARD_1_CURRENCY = "ARG";
    public static final String CARD_1_BALANCE = "45.67";
    public static final String CARD_1_NORMALIZED_CURRENCY = "NARG";
    public static final String CARD_1_NORMALIZED_BALANCE = "145.67";
    public static final String CARD_1_LABEL = "Alpha";

    public static final String CARD_2_UPHOLD_ID = "084281d8-c5dc-407a-8db8-12447b7c6d77";
    public static final String CARD_2_CURRENCY = "BRG";
    public static final String CARD_2_BALANCE = "25.67";
    public static final String CARD_2_NORMALIZED_CURRENCY = "NBRG";
    public static final String CARD_2_NORMALIZED_BALANCE = "125.67";
    public static final String CARD_2_LABEL = "Beta";

    public static final String CARD_3_UPHOLD_ID = "a0f39c79-b740-4399-8cc0-66f27f771018";
    public static final String CARD_3_CURRENCY = "CRG";
    public static final String CARD_3_BALANCE = "85.67";
    public static final String CARD_3_NORMALIZED_CURRENCY = "NCRG";
    public static final String CARD_3_NORMALIZED_BALANCE = "185.67";
    public static final String CARD_3_LABEL = "Charlie";

    @Test
    public void testConvertCybersourceMethodData() {
        final var cyberPmtMethodProto = CybersourcePaymentData.newBuilder()
            .setCardType(CARD_TYPE)
            .setExpirationMonth(EXP_MON)
            .setExpirationYear(EXP_YEAR)
            .setInstrumentId(INSTRUMENT_ID)
            .setPaymentMethodTokenId(PAYMENT_METHOD_TOKEN_ID)
            .build();
        final var pmtMethodProto = PaymentMethodData.newBuilder()
            .setMakeDefault(true)
            .setCybersource(cyberPmtMethodProto)
            .build();
        final var paymentData = PmtDataFactory.fromProto(pmtMethodProto);
        assertTrue(paymentData.isDefaultMethod());

        final var cyberData = paymentData.asCybersource();
        assertNotNull(cyberData);
        assertEquals(CARD_TYPE, cyberData.getCardType());
        assertEquals(EXP_MON, cyberData.getExpirationMonth());
        assertEquals(EXP_YEAR, cyberData.getExpirationYear());
        assertEquals(INSTRUMENT_ID, cyberData.getInstrumentId());
        assertEquals(PAYMENT_METHOD_TOKEN_ID, cyberData.getPaymentMethodTokenId());
    }

    @Test
    public void testConvertUpholdMethodData() {
        final var upholdPmtMethodProto = UpholdPaymentData.newBuilder()
            .setTempStateCode(TEMP_STATE_CODE)
            .setEmail(EMAIL)
            .setStatus(STATUS)
            .setVerifications(VERIFICATIONS)
            .setBirthDate(BIRTH_DATE)
            .addCards(makeCard(CARD_1_UPHOLD_ID, CARD_1_CURRENCY, CARD_1_BALANCE,
                               CARD_1_NORMALIZED_CURRENCY, CARD_1_NORMALIZED_BALANCE, CARD_1_LABEL))
            .addCards(makeCard(CARD_2_UPHOLD_ID, CARD_2_CURRENCY, CARD_2_BALANCE,
                               CARD_2_NORMALIZED_CURRENCY, CARD_2_NORMALIZED_BALANCE, CARD_2_LABEL))
            .addCards(makeCard(CARD_3_UPHOLD_ID, CARD_3_CURRENCY, CARD_3_BALANCE,
                               CARD_3_NORMALIZED_CURRENCY, CARD_3_NORMALIZED_BALANCE, CARD_3_LABEL))
            .build();
        final var pmtMethodProto = PaymentMethodData.newBuilder()
            .setMakeDefault(true)
            .setUphold(upholdPmtMethodProto)
            .build();
        final var paymentData = PmtDataFactory.fromProto(pmtMethodProto);
        assertTrue(paymentData.isDefaultMethod());

        final var upholdData = paymentData.asUphold();
        assertEquals(TEMP_STATE_CODE, upholdData.getTempStateCode());
        assertEquals(EMAIL, upholdData.getEmail());
        assertEquals(STATUS, upholdData.getStatus());
        assertEquals(VERIFICATIONS, upholdData.getVerifications());
        assertEquals(BIRTH_DATE, upholdData.getBirthDate());

        final var cards = upholdData.getCards();
        assertEquals(3, cards.size());

        final var card1 = cards.get(0);
        assertEquals(CARD_1_UPHOLD_ID, card1.getUpholdId());
        assertEquals(CARD_1_CURRENCY, card1.getCurrency());
        assertEquals(CARD_1_BALANCE, card1.getBalance().toString());
        assertEquals(CARD_1_NORMALIZED_CURRENCY, card1.getNormalizedCurrency());
        assertEquals(CARD_1_NORMALIZED_BALANCE, card1.getNormalizedBalance().toString());
        assertEquals(CARD_1_LABEL, card1.getLabel());

        final var card2 = cards.get(1);
        assertEquals(CARD_2_UPHOLD_ID, card2.getUpholdId());
        assertEquals(CARD_2_CURRENCY, card2.getCurrency());
        assertEquals(CARD_2_BALANCE, card2.getBalance().toString());
        assertEquals(CARD_2_NORMALIZED_CURRENCY, card2.getNormalizedCurrency());
        assertEquals(CARD_2_NORMALIZED_BALANCE, card2.getNormalizedBalance().toString());
        assertEquals(CARD_2_LABEL, card2.getLabel());

        final var card3 = cards.get(2);
        assertEquals(CARD_3_UPHOLD_ID, card3.getUpholdId());
        assertEquals(CARD_3_CURRENCY, card3.getCurrency());
        assertEquals(CARD_3_BALANCE, card3.getBalance().toString());
        assertEquals(CARD_3_NORMALIZED_CURRENCY, card3.getNormalizedCurrency());
        assertEquals(CARD_3_NORMALIZED_BALANCE, card3.getNormalizedBalance().toString());
        assertEquals(CARD_3_LABEL, card3.getLabel());
    }

    private UpholdCardProto makeCard(String upholdId,
                                     String currency,
                                     String balance,
                                     String normCurrency,
                                     String normBalance,
                                     String label) {
        return UpholdCardProto.newBuilder()
            .setUpholdId(upholdId)
            .setCurrency(currency)
            .setBalance(balance)
            .setNormalizedCurrency(normCurrency)
            .setNormalizedBalance(normBalance)
            .setLabel(label)
            .build();
    }
}
