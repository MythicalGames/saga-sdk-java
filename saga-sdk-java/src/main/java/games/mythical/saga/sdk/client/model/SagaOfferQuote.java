package games.mythical.saga.sdk.client.model;

import games.mythical.saga.sdk.proto.api.offer.OfferQuoteProto;
import games.mythical.shared.util.ProtoUtil;
import games.mythical.shared.util.dto.DtoExclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaOfferQuote {
    private String traceId;
    private String oauthId;
    private String quoteId;
    private String gameInventoryId;
    private BigDecimal tax;
    private String taxCurrency;
    private BigDecimal total;
    private String currency;
    @DtoExclude
    private Instant createdTimestamp;

    public static SagaOfferQuote fromProto(OfferQuoteProto proto) {
        var quote = ProtoUtil.toDto(proto, SagaOfferQuote.class);

        var createdTimestamp = Instant.ofEpochMilli(proto.getCreatedTimestamp());
        quote.setCreatedTimestamp(createdTimestamp);

        return quote;
    }
}
