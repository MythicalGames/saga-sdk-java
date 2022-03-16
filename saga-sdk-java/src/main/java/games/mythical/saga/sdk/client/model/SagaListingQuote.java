package games.mythical.saga.sdk.client.model;

import games.mythical.proto_util.ProtoUtil;
import games.mythical.proto_util.dto.DtoExclude;
import games.mythical.saga.sdk.proto.api.listing.ListingQuoteProto;
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
public class SagaListingQuote {
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

    public static SagaListingQuote fromProto(ListingQuoteProto proto) {
        var quote = ProtoUtil.toDto(proto, SagaListingQuote.class);

        var createdTimestamp = Instant.ofEpochMilli(proto.getCreatedTimestamp());
        quote.setCreatedTimestamp(createdTimestamp);

        return quote;
    }
}
