package games.mythical.saga.sdk.client.model;

import games.mythical.saga.sdk.proto.api.user.CybersourceAccount;
import games.mythical.saga.sdk.proto.api.user.UpholdAccount;
import games.mythical.saga.sdk.proto.api.user.UserProto;
import games.mythical.saga.sdk.proto.common.user.UserState;
import games.mythical.shared.util.ProtoUtil;
import games.mythical.shared.util.dto.DtoExclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaUser {
    private String traceId;
    private String oauthId;
    private String chainAddress;
    private UserState userState;
    private List<UpholdAccount> upholdAccounts;
    private CybersourceAccount cybersourceAccount;
    @DtoExclude
    private Instant createdTimestamp;

    public static SagaUser fromProto(UserProto proto) {
        var user = ProtoUtil.toDto(proto, SagaUser.class);

        var createdTimestamp = Instant.ofEpochMilli(proto.getCreatedTimestamp());
        user.setCreatedTimestamp(createdTimestamp);

        return user;
    }
}
