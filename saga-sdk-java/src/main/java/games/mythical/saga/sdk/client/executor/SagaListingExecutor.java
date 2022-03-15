package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.proto.common.listing.ListingState;

import java.math.BigDecimal;

public interface SagaListingExecutor {
    void updateListing(String oauthId,
                       String traceId,
                       String quoteId,
                       String listingId,
                       BigDecimal total,
                       ListingState listingState) throws Exception;

    void emitReceived(String quoteId, String traceId) throws Exception;
}
