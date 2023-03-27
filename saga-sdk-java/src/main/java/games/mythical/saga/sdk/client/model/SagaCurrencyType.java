package games.mythical.saga.sdk.client.model;

import games.mythical.proto_util.ProtoUtil;
import games.mythical.proto_util.dto.DtoExclude;
import games.mythical.saga.sdk.proto.api.currencytype.CurrencyTypeProto;
import games.mythical.saga.sdk.util.ConversionUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaCurrencyType {
    private String traceId;
    private String currencyTypeId;
    private String name;
    private String symbol;
    private String imageUrl;
    private String contractAddress;
    private String transactionId;
    private long maxSupply;
    @DtoExclude
    private SagaBalance publisherBalance;
    @DtoExclude
    private Instant createdAt;
    @DtoExclude
    private Instant updatedAt;

    public static SagaCurrencyType fromProto(CurrencyTypeProto proto) {
        var currencyType = ProtoUtil.toDto(proto, SagaCurrencyType.class);

        if (proto.hasPublisherBalance()) {
            currencyType.setPublisherBalance(SagaBalance.fromProto(proto.getPublisherBalance()));
        }

        var createdAt = ConversionUtils.protoTimestampToInstant(proto.getCreatedAt());
        currencyType.setCreatedAt(createdAt);

        var updatedAt = ConversionUtils.protoTimestampToInstant(proto.getUpdatedAt());
        currencyType.setUpdatedAt(updatedAt);

        return currencyType;
    }
}
