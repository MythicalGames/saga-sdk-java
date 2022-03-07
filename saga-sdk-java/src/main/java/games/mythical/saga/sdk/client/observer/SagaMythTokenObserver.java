package games.mythical.saga.sdk.client.observer;

import games.mythical.saga.sdk.client.executor.SagaMythTokenExecutor;
import games.mythical.saga.sdk.proto.common.myth.MythTokenState;
import games.mythical.saga.sdk.proto.streams.myth.MythTokenStatusConfirmRequest;
import games.mythical.saga.sdk.proto.streams.myth.MythTokenStatusUpdate;
import games.mythical.saga.sdk.proto.streams.myth.MythTokenStreamGrpc;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
public class SagaMythTokenObserver extends AbstractObserver<MythTokenStatusUpdate> {
    private final SagaMythTokenExecutor sagaMythTokenExecutor;
    private final MythTokenStreamGrpc.MythTokenStreamBlockingStub streamBlockingStub;
    private final Consumer<SagaMythTokenObserver> resubscribe;

    public SagaMythTokenObserver(SagaMythTokenExecutor sagaMythTokenExecutor,
                                 MythTokenStreamGrpc.MythTokenStreamBlockingStub streamBlockingStub,
                                 Consumer<SagaMythTokenObserver> resubscribe) {
        this.sagaMythTokenExecutor = sagaMythTokenExecutor;
        this.streamBlockingStub = streamBlockingStub;
        this.resubscribe = resubscribe;
    }

    @Override
    public void onNext(MythTokenStatusUpdate message) {
        // different identification instead of trace id?
        log.trace("SagaMythTokenObserver.onNext for token: {}", message.getTraceId());
        resetConnectionRetry();
        try {
            sagaMythTokenExecutor.updateMythToken(
                    message.getTraceId(),
                    message.getTokenState()
            );
            updateTokenConfirmation(message.getTraceId(), message.getTokenState());
        } catch (Exception e) {
            log.error("Exception calling updateMythToken for {}. {}", message.getTraceId(), e);
        }
    }

    @Override
    public void onError(Throwable t) {
        log.error("MythTokenObserver.onError", t);
        sleepBetweenReconnects();
        resubscribe.accept(this);
    }

    @Override
    public void onCompleted() {
        log.info("MythTokenObserver stream closed");
        sleepBetweenReconnects();
        resubscribe.accept(this);
    }

    private void updateTokenConfirmation(String traceId, MythTokenState tokenState) {
        var request = MythTokenStatusConfirmRequest.newBuilder()
                .setTraceId(traceId)
                .setTokenState(tokenState)
                .build();
        streamBlockingStub.mythTokenStatusConfirmation(request);
    }
}
