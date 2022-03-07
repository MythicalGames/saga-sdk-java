package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.model.SagaMetadata;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.exception.SagaException;
import io.grpc.ManagedChannel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public abstract class AbstractClientTest {
    protected static final String host = "localhost";
    protected static final String titleSecret = "MOCK_TITLE_SECRET";
    protected static final String titleId = "MOCK_TITLE_ID";

    protected SagaSdkConfig config;
    protected int port;
    protected ManagedChannel channel;

    protected SagaClientFactory setUpFactory() throws SagaException {
        return SagaClientFactory.initialize(setUpConfig());
    }

    protected SagaSdkConfig setUpConfig() {
        return SagaSdkConfig.builder()
                .host(host)
                .port(port)
                .titleId(titleId)
                .titleSecret(titleSecret)
                .plainText(true)
                .authenticated(false)
                .build();
    }

    protected SagaMetadata generateItemMetadata() {
        return SagaMetadata.builder()
                .name(RandomStringUtils.randomAlphanumeric(30))
                .description(RandomStringUtils.randomAlphanumeric(30))
                .image(RandomStringUtils.randomAlphanumeric(30))
                .properties(generateProperties())
                .build();
    }

    protected Map<String, Object> generateProperties() {
        return Map.of(
                "str_field", RandomStringUtils.randomAlphanumeric(30),
                "int_field", RandomUtils.nextInt(0, 1000),
                "list_field", List.of("one", "two"),
                "obj_field", new Object()
        );
    }

    /**
     * Compares returned toString values of getter methods from the passed in Objects. Only compares methods that start with
     * 'get' or 'is', public, have no parameters, and not void.
     *
     * @param expected         expected Object values
     * @param actual           actual Object values
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
