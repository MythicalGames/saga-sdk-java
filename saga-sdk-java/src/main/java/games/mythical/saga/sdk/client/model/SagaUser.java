package games.mythical.saga.sdk.client.model;

import games.mythical.proto_util.ProtoUtil;
import games.mythical.proto_util.dto.DtoExclude;
import games.mythical.saga.sdk.proto.api.user.UserProto;
import games.mythical.saga.sdk.proto.common.user.UserState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaUser {
    private String traceId;
    private String oauthId;
    private String chainAddress;
    private UserState userState;
    @DtoExclude
    private Instant createdTimestamp;

    public static SagaUser fromProto(UserProto proto) {
        var user = ProtoUtil.toDto(proto, SagaUser.class);

        var createdTimestamp = Instant.ofEpochMilli(proto.getCreatedAt());
        user.setCreatedTimestamp(createdTimestamp);

        return user;
    }
}
