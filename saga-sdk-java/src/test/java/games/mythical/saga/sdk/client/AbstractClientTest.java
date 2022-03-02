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

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.Instant;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    /**
     * Compares returned toString values of getter methods from the passed in Objects. Only compares methods that start with
     * 'get' or 'is', public, have no parameters, and not void.
     * @param expected expected Object values
     * @param actual actual Object values
     * @param alternateMapping map of expected method name to actual method name in lowercase.
     * @throws Exception
     */
    public void compareObjectsByReflection(Object expected, Object actual, Map<String, String> alternateMapping) throws Exception {
        var expectedGetters = getMapOfGetterMethods(expected);
        var actualGetters = getMapOfGetterMethods(actual);
        if (alternateMapping == null) {
            alternateMapping = new HashMap<>();
        }

        for (var methodSet : expectedGetters.entrySet()) {
            var expectedValue = methodSet.getValue().invoke(expected);
            var actualMethod = actualGetters.get(methodSet.getKey());
            if (actualMethod == null) {
                // try to find using alternateMapping
                actualMethod = actualGetters.get(alternateMapping.get(methodSet.getKey()));
            }
            if (actualMethod != null) {
                var actualValue = actualMethod.invoke(actual);

                if (expectedValue != null && actualValue != null) {
                    assertEquals(expectedValue.toString(), actualValue.toString(), String.format("Comparing for expected method [%s]", methodSet.getKey()));
                } else {
                    assertEquals(expectedValue, actualValue, String.format("Comparing for expected method: %s", methodSet.getKey()));
                }
            }
        }
    }

    private Map<String, Method> getMapOfGetterMethods(Object object) {
        Map<String, Method> map = new HashMap<>();
        for (var method : object.getClass().getDeclaredMethods()) {
            if (Modifier.isPublic(method.getModifiers())
                    && method.getParameterTypes().length == 0
                    && method.getReturnType() != void.class
                    && (method.getName().startsWith("get") || method.getName().startsWith("is"))
            ) {
                map.put(method.getName().toLowerCase(Locale.ROOT), method);
            }
        }

        return map;
    }
}
