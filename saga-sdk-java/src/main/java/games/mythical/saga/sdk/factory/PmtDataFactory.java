package games.mythical.saga.sdk.factory;

import games.mythical.proto_util.ProtoUtil;
import games.mythical.saga.sdk.client.model.SagaCybersourcePaymentData;
import games.mythical.saga.sdk.client.model.SagaPaymentData;
import games.mythical.saga.sdk.client.model.SagaUpholdPaymentData;
import games.mythical.saga.sdk.proto.api.payment.PaymentMethodData;

public class PmtDataFactory {
    public static SagaPaymentData fromProto(PaymentMethodData pmtMethodData) {
        if (pmtMethodData.hasCybersource()) {
            final var sagaPmtDataBuilder = ProtoUtil.toDtoBuilder(
                pmtMethodData.getCybersource(),
                SagaCybersourcePaymentData.SagaCybersourcePaymentDataBuilder.class);
            sagaPmtDataBuilder.defaultMethod(pmtMethodData.getMakeDefault());
            return sagaPmtDataBuilder.build();
        }
        if (pmtMethodData.hasUphold()) {
            final var sagaPmtDataBuilder = ProtoUtil.toDtoBuilder(
                pmtMethodData.getUphold(),
                SagaUpholdPaymentData.SagaUpholdPaymentDataBuilder.class);
            sagaPmtDataBuilder.defaultMethod(pmtMethodData.getMakeDefault());
            return sagaPmtDataBuilder.build();
        }
        return null;
    }
}
