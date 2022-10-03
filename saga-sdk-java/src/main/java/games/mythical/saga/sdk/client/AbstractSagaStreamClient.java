package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.observer.SagaStatusUpdateObserver;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.streams.StatusStreamGrpc;
import games.mythical.saga.sdk.proto.streams.Subscribe;
import games.mythical.saga.sdk.util.ConversionUtils;
import org.apache.commons.lang3.StringUtils;

public abstract class AbstractSagaStreamClient extends AbstractSagaClient {

    protected AbstractSagaStreamClient(SagaSdkConfig config) throws SagaException {
        super(config);
    }

    protected void initStreamStub() {
        var streamStub = StatusStreamGrpc.newStub(channel)
                .withCallCredentials(addAuthentication());

        if (SagaStatusUpdateObserver.getInstance() == null) {
            subscribeToStream(SagaStatusUpdateObserver.initialize(streamStub, this::subscribeToStream));
        }
    }

    protected void subscribeToStream(SagaStatusUpdateObserver observer) {
        // set up server stream
        var streamStub = StatusStreamGrpc.newStub(channel).withCallCredentials(addAuthentication());
        var subscribeBuilder = Subscribe.newBuilder()
                .setTitleId(config.getTitleId())
                .setStreamId(StringUtils.defaultIfBlank(config.getStreamId(), ""));

        if (config.getStreamReplaySince() != null) {
            subscribeBuilder.setReplaySince(ConversionUtils.instantToProtoTimestamp(config.getStreamReplaySince()));
        }

        streamStub.statusStream(subscribeBuilder.build(), observer);
    }
}
