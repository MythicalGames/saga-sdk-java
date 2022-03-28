package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.proto.common.listing.ListingState;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import lombok.Getter;

@Getter
@Builder
public class MockListingExecutor extends MockBaseExecutor implements SagaListingExecutor {
    private String oauthId;
    private String traceId;
    private String quoteId;
    private String listingId;
    private BigDecimal total;
    private ListingState listingState;

    @Override
    public void updateListing(String oauthId,
                              String traceId,
                              String quoteId,
                              String listingId,
                              BigDecimal total,
                              ListingState listingState) throws Exception {
        this.oauthId = oauthId;
        this.traceId = traceId;
        this.quoteId = quoteId;
        this.listingId = listingId;
        this.total = total;
        this.listingState = listingState;
    }

    @Override
    public void emitReceived(String quoteId, String traceId) throws Exception {
        this.quoteId = quoteId;
        this.traceId = traceId;
    }
}
