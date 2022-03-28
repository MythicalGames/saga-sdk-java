package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.proto.common.offer.OfferState;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import lombok.Getter;

@Getter
@Builder
public class MockOfferExecutor extends MockBaseExecutor implements SagaOfferExecutor {
    private String oauthId;
    private String traceId;
    private String quoteId;
    private String offerId;
    private BigDecimal total;
    private OfferState offerState;

    @Override
    public void updateOffer(String oauthId,
                            String traceId,
                            String quoteId,
                            String offerId,
                            BigDecimal total,
                            OfferState offerState) throws Exception {
        this.oauthId = oauthId;
        this.traceId = traceId;
        this.quoteId = quoteId;
        this.offerId = offerId;
        this.total = total;
        this.offerState = offerState;
    }

    @Override
    public void emitReceived(String quoteId, String traceId) throws Exception {
        this.quoteId = quoteId;
        this.traceId = traceId;
    }
}
