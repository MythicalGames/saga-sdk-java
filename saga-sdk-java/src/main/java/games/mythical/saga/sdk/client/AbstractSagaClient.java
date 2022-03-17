package games.mythical.saga.sdk.client;

import com.google.common.net.HttpHeaders;
import games.mythical.saga.sdk.client.observer.SagaStatusUpdateObserver;
import games.mythical.saga.sdk.config.Constants;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.exception.SagaException;
import io.grpc.CallCredentials;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

@Slf4j
public abstract class AbstractSagaClient {
    protected final SagaSdkConfig config;
    protected final SagaCredentialsFactory sagaCredentialsFactory;
    protected ManagedChannel channel;

    protected AbstractSagaClient(SagaSdkConfig config) throws SagaException {
        this.config = config;
        this.channel = buildChannel(config);
        this.sagaCredentialsFactory = SagaCredentialsFactory.getInstance();
    }

    abstract void initStub();

    public CallCredentials addAuthentication() {
        return new CallCredentials() {
            @Override
            public void applyRequestMetadata(RequestInfo requestInfo, Executor appExecutor, MetadataApplier applier) {
                var metadata = new Metadata();
                metadata.put(Metadata.Key.of(HttpHeaders.AUTHORIZATION, Metadata.ASCII_STRING_MARSHALLER),
                        Constants.AUTH_TYPE + " " + sagaCredentialsFactory.getToken());
                applier.apply(metadata);
            }

            @Override
            public void thisUsesUnstableApi() {
            }
        };
    }

    public void stop() {
        log.info("Stopping client: {}", this.getClass().getName());
        SagaStatusUpdateObserver.clear();
        channel.shutdownNow();
    }

    private static ManagedChannel buildChannel(SagaSdkConfig config) {
        final var builder = ManagedChannelBuilder.forAddress(config.getHost(), config.getPort())
                .keepAliveTime(config.getKeepAlive(), TimeUnit.SECONDS);
        if (config.isPlainText() && "localhost".equals(config.getHost())) {
            builder.usePlaintext();
        }
        return builder.build();
    }
}
