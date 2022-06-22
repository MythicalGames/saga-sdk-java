package games.mythical.saga.sdk.factory;

import games.mythical.proto_util.ProtoUtil;
import games.mythical.saga.sdk.client.model.SagaCreditCardData;
import games.mythical.saga.sdk.proto.api.order.CreditCardData;
import games.mythical.saga.sdk.proto.api.order.PaymentProviderData;

public class PmtProviderDataFactory {

    public static PaymentProviderData fromCreditCard(SagaCreditCardData creditCardData) {
        final var creditCardDataProto = ProtoUtil.toProto(creditCardData, CreditCardData.class);
        return PaymentProviderData.newBuilder()
            .setCreditCardData(creditCardDataProto)
            .build();
    }

    public static PaymentProviderData fromUpholdCard(String upholdCardId) {
        return PaymentProviderData.newBuilder()
            .setUpholdCardId(upholdCardId)
            .build();
    }
}
