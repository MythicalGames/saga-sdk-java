package games.mythical.saga.sdk.client.model;

import games.mythical.proto_util.ProtoUtil;
import games.mythical.proto_util.dto.DtoExclude;
import games.mythical.saga.sdk.proto.api.currency.CurrencyProto;
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
public class SagaCurrency {
    private String traceId;
    private BigDecimal quantity;
    private String gameCurrencyTypeId;
    private String name;
    private String oauthId;
    private String address;
    private String titleId;
    @DtoExclude
    private Instant createdTimestamp;
    @DtoExclude
    private Instant updatedTimestamp;

    public static SagaCurrency fromProto(CurrencyProto proto) {
        var user = ProtoUtil.toDto(proto, SagaCurrency.class);

        var createdTimestamp = Instant.ofEpochMilli(proto.getCreatedTimestamp());
        user.setCreatedTimestamp(createdTimestamp);

        var updatedTimestamp = Instant.ofEpochMilli(proto.getUpdatedTimestamp());
        user.setUpdatedTimestamp(updatedTimestamp);

        return user;
    }
}
