package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.config.SagaConfiguration;
import games.mythical.saga.sdk.exception.SagaErrorCode;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.security.CredentialsFactory;
import io.grpc.CallCredentials;
import io.grpc.ManagedChannel;
import io.grpc.Metadata;
import java.util.concurrent.atomic.AtomicReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.Executor;

@Slf4j
public abstract class AbstractSagaClient {
    // IVI settings
    protected final String host;
    protected final int port;
    protected final String environmentId;
    protected final String apiKey;
    protected final CredentialsFactory credentialsFactory = CredentialsFactory.getInstance();

    // gRPC settings
    protected final int keepAlive;
    protected ManagedChannel channel;

    protected AbstractSagaClient() throws SagaException {
        if (StringUtils.isEmpty(SagaConfiguration.getEnvironmentId())) {
            throw new SagaException("Environment Id not set!", SagaErrorCode.ENVIRONMENT_ID_NOT_SET);
        }
        environmentId = SagaConfiguration.getEnvironmentId();

        if (StringUtils.isEmpty(SagaConfiguration.getApiKey())) {
            throw new SagaException("API Key not set!", SagaErrorCode.APIKEY_NOT_SET);
        }
        apiKey = SagaConfiguration.getApiKey();

        if (StringUtils.isEmpty(SagaConfiguration.getHost())) {
            throw new SagaException("Host not set!", SagaErrorCode.HOST_NOT_SET);
        }
        host = SagaConfiguration.getHost();

        if (SagaConfiguration.getPort() == null) {
            throw new SagaException("Port not set!", SagaErrorCode.PORT_NOT_SET);
        }
        port = SagaConfiguration.getPort();
        keepAlive = SagaConfiguration.getKeepAlive();
    }

    abstract void initStub();

    public CallCredentials addAuthentication() {
        return new CallCredentials() {
            @Override
            public void applyRequestMetadata(RequestInfo requestInfo, Executor appExecutor, MetadataApplier applier) {
                var metadata = new Metadata();
                metadata.put(Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER), "Bearer " + credentialsFactory.getToken());
                // metadata.put(Metadata.Key.of("API-KEY", Metadata.ASCII_STRING_MARSHALLER), SagaConfiguration.getApiKey());
                applier.apply(metadata);
            }

            @Override
            public void thisUsesUnstableApi() {
            }
        };
    }
}
