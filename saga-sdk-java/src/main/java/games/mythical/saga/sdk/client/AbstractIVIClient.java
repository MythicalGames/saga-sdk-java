package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.config.IVIConfiguration;
import games.mythical.saga.sdk.exception.IVIErrorCode;
import games.mythical.saga.sdk.exception.IVIException;
import io.grpc.CallCredentials;
import io.grpc.ManagedChannel;
import io.grpc.Metadata;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.Executor;

@Slf4j
public abstract class AbstractIVIClient {
    // IVI settings
    protected final String host;
    protected final int port;
    protected final String environmentId;
    protected final String apiKey;

    // gRPC settings
    protected final int keepAlive;
    protected ManagedChannel channel;

    protected AbstractIVIClient() throws IVIException {
        if(StringUtils.isEmpty(IVIConfiguration.getEnvironmentId())) {
            throw new IVIException("Environment Id not set!", IVIErrorCode.ENVIRONMENT_ID_NOT_SET);
        }
        environmentId = IVIConfiguration.getEnvironmentId();

        if(StringUtils.isEmpty(IVIConfiguration.getApiKey())) {
            throw new IVIException("API Key not set!", IVIErrorCode.APIKEY_NOT_SET);
        }
        apiKey = IVIConfiguration.getApiKey();

        if(StringUtils.isEmpty(IVIConfiguration.getHost())) {
            throw new IVIException("Host not set!", IVIErrorCode.HOST_NOT_SET);
        }
        host = IVIConfiguration.getHost();

        if(IVIConfiguration.getPort() == null) {
            throw new IVIException("Port not set!", IVIErrorCode.PORT_NOT_SET);
        }
        port = IVIConfiguration.getPort();
        keepAlive = IVIConfiguration.getKeepAlive();
    }

    abstract void initStub();

    public CallCredentials addAuthentication() {
        return new CallCredentials() {
            @Override
            public void applyRequestMetadata(RequestInfo requestInfo, Executor appExecutor, MetadataApplier applier) {
                var metadata = new Metadata();
                metadata.put(Metadata.Key.of("API-KEY", Metadata.ASCII_STRING_MARSHALLER), IVIConfiguration.getApiKey());
                applier.apply(metadata);
            }

            @Override
            public void thisUsesUnstableApi() { }
        };
    }
}
