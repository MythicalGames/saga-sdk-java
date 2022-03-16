package games.mythical.saga.sdk.client.model;

import games.mythical.proto_util.ProtoUtil;
import games.mythical.proto_util.dto.DtoExclude;
import games.mythical.saga.sdk.proto.api.gamecoin.GameCoinProto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaGameCoin {
    private String traceId;
    private int coinCount;
    private String currencyId;
    private String name;
    private String oauthId;
    private String address;
    private String titleId;
    @DtoExclude
    private Instant createdTimestamp;
    @DtoExclude
    private Instant updatedTimestamp;

    public static SagaGameCoin fromProto(GameCoinProto proto) {
        var user = ProtoUtil.toDto(proto, SagaGameCoin.class);

        var createdTimestamp = Instant.ofEpochMilli(proto.getCreatedTimestamp());
        user.setCreatedTimestamp(createdTimestamp);

        var updatedTimestamp = Instant.ofEpochMilli(proto.getUpdatedTimestamp());
        user.setUpdatedTimestamp(updatedTimestamp);

        return user;
    }
}
