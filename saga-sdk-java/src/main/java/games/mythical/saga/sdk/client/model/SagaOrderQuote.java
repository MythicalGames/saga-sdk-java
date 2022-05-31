package games.mythical.saga.sdk.client.model;

import games.mythical.proto_util.ProtoUtil;
import games.mythical.proto_util.dto.DtoExclude;
import games.mythical.saga.sdk.proto.api.order.QuoteProto;
import games.mythical.saga.sdk.proto.common.payment.PaymentProviderId;
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
public class SagaOrderQuote {
    private String traceId;
    private String oauthId;
    private String quoteId;
    private BigDecimal tax;
    private String taxCurrency;
    private BigDecimal total;
    private String currency;
    private PaymentProviderId paymentProviderId;
    private String buyerAddress;
    private String sellerAddress;
    private String conversionRate;
    @DtoExclude
    private Instant createdAt;

    public static SagaOrderQuote fromProto(QuoteProto proto) {
        var quote = ProtoUtil.toDto(proto, SagaOrderQuote.class);

        var createdAt = Instant.ofEpochMilli(proto.getCreatedAt());
        quote.setCreatedAt(createdAt);

        return quote;
    }
}
