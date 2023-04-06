package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.model.SagaMetadata;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.common.ReceivedResponse;
import games.mythical.saga.sdk.util.ConcurrentFinisher;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@Slf4j
public abstract class AbstractClientTest {
    protected static final String host = "localhost";
    protected static final String titleSecret = "MOCK_TITLE_SECRET";
    protected static final String titleId = "MOCK_TITLE_ID";

    protected SagaSdkConfig config;
    protected int port;

    protected SagaClientFactory setUpFactory() throws SagaException {
        return SagaClientFactory.initialize(setUpConfig());
    }

    protected SagaSdkConfig setUpConfig() {
        config = SagaSdkConfig.builder()
                .host(host)
                .port(port)
                .titleId(titleId)
                .titleSecret(titleSecret)
                .plainText(true)
                .authenticated(false)
                .build();
        return config;
    }

    protected SagaMetadata generateItemMetadata() {
        return SagaMetadata.builder()
                .name(RandomStringUtils.randomAlphanumeric(30))
                .description(RandomStringUtils.randomAlphanumeric(30))
                .image(RandomStringUtils.randomAlphanumeric(30))
                .build();
    }

    protected void checkTraceAndStart(ReceivedResponse expectedResponse, String trace) throws Exception {
        assertEquals(expectedResponse.getTraceId(), trace);
        assertNotEquals(Boolean.TRUE, ConcurrentFinisher.get(trace));

        // a short wait is needed so the status stream can be hooked up
        // before the emitting the event from the sendStatus method
        Thread.sleep(500);
        ConcurrentFinisher.start(trace);
    }

}
