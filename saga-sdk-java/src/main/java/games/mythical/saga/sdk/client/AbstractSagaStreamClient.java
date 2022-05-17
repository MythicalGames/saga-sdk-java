package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.observer.SagaStatusUpdateObserver;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.streams.StatusStreamGrpc;
import games.mythical.saga.sdk.proto.streams.Subscribe;

public abstract class AbstractSagaStreamClient extends AbstractSagaClient {

    protected AbstractSagaStreamClient(SagaSdkConfig config) throws SagaException {
        super(config);
    }

    protected void initStreamStub() {
        var streamBlockingStub = StatusStreamGrpc.newBlockingStub(channel)
                .withCallCredentials(addAuthentication());

        if (SagaStatusUpdateObserver.getInstance() == null) {
            subscribeToStream(SagaStatusUpdateObserver.initialize(streamBlockingStub, this::subscribeToStream));
        }
    }

    protected void subscribeToStream(SagaStatusUpdateObserver observer) {
        // set up server stream
        var streamStub = StatusStreamGrpc.newStub(channel).withCallCredentials(addAuthentication());
        var subscribe = Subscribe.newBuilder()
                .setTitleId(config.getTitleId())
                .build();

        streamStub.statusStream(subscribe, observer);
    }
}
