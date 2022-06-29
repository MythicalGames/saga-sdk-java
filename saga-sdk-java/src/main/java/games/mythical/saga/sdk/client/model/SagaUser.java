package games.mythical.saga.sdk.client.model;

import games.mythical.proto_util.ProtoUtil;
import games.mythical.proto_util.dto.DtoExclude;
import games.mythical.saga.sdk.proto.api.user.UserProto;
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
public class SagaUser {
    private String traceId;
    private String oauthId;
    private String chainAddress;
    @DtoExclude
    private Instant createdAt;

    public static SagaUser fromProto(UserProto proto) {
        var user = ProtoUtil.toDto(proto, SagaUser.class);

        var createdAt = ConversionUtils.protoTimestampToInstant(proto.getCreatedAt());
        user.setCreatedAt(createdAt);

        return user;
    }
}
