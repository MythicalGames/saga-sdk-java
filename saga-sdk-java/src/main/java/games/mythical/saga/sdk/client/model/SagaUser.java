package games.mythical.saga.sdk.client.model;

import games.mythical.saga.sdk.proto.api.user.CybersourceAccount;
import games.mythical.saga.sdk.proto.api.user.UpholdAccount;
import games.mythical.saga.sdk.proto.api.user.UserProto;
import games.mythical.saga.sdk.proto.common.user.UserState;
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
    private String oauthId;
    private String chainAddress;
    private UserState userState;
    private List<UpholdAccount> upholdAccounts;
    private CybersourceAccount cybersourceAccount;
    private Instant createdTimestamp;

    public static SagaUser fromProto(UserProto proto) {
        // TODO: this will the Erik's enterprise protoutils come into play :D
        return SagaUser.builder()
                .oauthId(proto.getOauthId())
                .chainAddress(proto.getChainAddress())
                .build();
    }
}
