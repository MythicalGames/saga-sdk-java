package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.config.SagaConfiguration;
import io.grpc.ManagedChannel;
import lombok.extern.slf4j.Slf4j;

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
}
