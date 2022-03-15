package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.proto.common.offer.OfferState;

import java.math.BigDecimal;

public interface SagaOfferExecutor {
    void updateOffer(String oauthId,
                     String traceId,
                     String quoteId,
                     String offerId,
                     BigDecimal total,
                     OfferState offerState) throws Exception;

    void emitReceived(String quoteId, String traceId) throws Exception;
}
