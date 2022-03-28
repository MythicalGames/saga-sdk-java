package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.config.Constants;
import games.mythical.saga.sdk.proto.common.listing.ListingState;

import java.math.BigDecimal;

public interface SagaListingExecutor extends BaseSagaExecutor {
    String UNKNOWN_LISTING = Constants.UNKNOWN_ID;

    void updateListing(String oauthId,
                       String traceId,
                       String quoteId,
                       String listingId,
                       BigDecimal total,
                       ListingState listingState) throws Exception;

    void emitReceived(String listingId, String traceId) throws Exception;
}
