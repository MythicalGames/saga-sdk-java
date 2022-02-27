package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.model.SagaUser;
import games.mythical.saga.sdk.config.SagaConfiguration;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.api.user.UserProto;
import games.mythical.saga.sdk.proto.common.user.UserState;
import io.grpc.ManagedChannel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public abstract class AbstractClientTest {
    protected static final String host = "localhost";
    protected static final String apiKey = "MOCK_API_KEY";
    protected static final String environmentId = "MOCK_ENV_ID";
    protected static final String currency = "BB";

    protected int port;
    protected ManagedChannel channel;

    protected void setUpConfig() {
        SagaConfiguration.setHost(host);
        SagaConfiguration.setPort(port);
        SagaConfiguration.setApiKey(apiKey);
        SagaConfiguration.setEnvironmentId(environmentId);
    }

    protected Map<String, SagaUser> generateUsers(int count) throws SagaException {
        var result = new HashMap<String, SagaUser>();
        for (var i = 0; i < count; i++) {
            UserState state;
            if (RandomUtils.nextBoolean()) {
                state = UserState.FAILED;
            } else {
                state = UserState.LINKED;
            }

            var user = generateUser(state);
            result.put(user.getOauthId(), user);
        }
        return result;
    }

    protected SagaUser generateUser(UserState state) {
        var user = UserProto.newBuilder()
                .setOauthId(RandomStringUtils.randomAlphanumeric(30))
                .setChainAddress(RandomStringUtils.randomAlphanumeric(30))
                .setUserState(state)
                .setCreatedTimestamp(Instant.now().getEpochSecond() - 86400)
                .build();

        return SagaUser.fromProto(user);
    }
}
