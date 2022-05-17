package games.mythical.saga.sdk.client.model;

import games.mythical.proto_util.ProtoUtil;
import games.mythical.proto_util.dto.DtoExclude;
import games.mythical.saga.sdk.proto.api.currencytype.CurrencyTypeProto;
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
public class SagaCurrencyType {
    String traceId;
    String id;
    String gameCurrencyTypeId;
    String gameTitleId;
    String publisherAddress;
    String name;
    String symbol;
    long decimalPlaces;
    String contractAddress;
    boolean finalized;
    long maxSupply;
    @DtoExclude
    private Instant createdTimestamp;
    @DtoExclude
    private Instant updatedTimestamp;

    public static SagaCurrencyType fromProto(CurrencyTypeProto proto) {
        var currencyType = ProtoUtil.toDto(proto, SagaCurrencyType.class);

        var createdTimestamp = Instant.ofEpochMilli(proto.getCreatedAt());
        currencyType.setCreatedTimestamp(createdTimestamp);

        var updatedTimestamp = Instant.ofEpochMilli(proto.getUpdatedAt());
        currencyType.setUpdatedTimestamp(updatedTimestamp);

        return currencyType;
    }
}
