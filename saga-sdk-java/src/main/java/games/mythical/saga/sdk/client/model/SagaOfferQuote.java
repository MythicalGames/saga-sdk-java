package games.mythical.saga.sdk.client.model;

import games.mythical.proto_util.ProtoUtil;
import games.mythical.proto_util.dto.DtoExclude;
import games.mythical.saga.sdk.proto.api.offer.OfferQuoteProto;
import games.mythical.saga.sdk.util.ConversionUtils;
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
    private String inventoryId;
    private BigDecimal tax;
    private String taxCurrency;
    private BigDecimal total;
    private String currency;
    @DtoExclude
    private Instant createdAt;

    public static SagaOfferQuote fromProto(OfferQuoteProto proto) {
        var quote = ProtoUtil.toDto(proto, SagaOfferQuote.class);

        var createdAt = ConversionUtils.protoTimestampToInstant(proto.getCreatedAt());
        quote.setCreatedAt(createdAt);

        return quote;
    }
}
