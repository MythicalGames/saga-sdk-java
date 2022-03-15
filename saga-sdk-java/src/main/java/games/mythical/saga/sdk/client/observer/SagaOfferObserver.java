package games.mythical.saga.sdk.client.observer;

import games.mythical.saga.sdk.client.executor.SagaOfferExecutor;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.proto.common.offer.OfferState;
import games.mythical.saga.sdk.proto.streams.offer.OfferStatusConfirmRequest;
import games.mythical.saga.sdk.proto.streams.offer.OfferStatusUpdate;
import games.mythical.saga.sdk.proto.streams.offer.OfferStreamGrpc;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.function.Consumer;

@Slf4j
public class SagaOfferObserver extends AbstractObserver<OfferStatusUpdate> {
    private final SagaSdkConfig config;
    private final SagaOfferExecutor sagaOfferExecutor;
    private final OfferStreamGrpc.OfferStreamBlockingStub streamBlockingStub;
    private final Consumer<SagaOfferObserver> resubscribe;

    public SagaOfferObserver(SagaSdkConfig config,
                             SagaOfferExecutor sagaOfferExecutor,
                             OfferStreamGrpc.OfferStreamBlockingStub streamBlockingStub,
                             Consumer<SagaOfferObserver> resubscribe) {
        this.config = config;
        this.sagaOfferExecutor = sagaOfferExecutor;
        this.streamBlockingStub = streamBlockingStub;
        this.resubscribe = resubscribe;
    }

    @Override
    public void onNext(OfferStatusUpdate message) {
        log.trace("OfferObserver.onNext for user: {}", message.getOauthId());
        resetConnectionRetry();
        try {
            sagaOfferExecutor.updateOffer(
                    message.getOauthId(),
                    message.getTraceId(),
                    message.getQuoteId(),
                    message.getOfferId(),
                    new BigDecimal(message.getTotal()),
                    message.getOfferState()
            );
            updateOfferConfirmation(message.getOauthId(), message.getTraceId(), message.getOfferState());
        } catch (Exception e) {
            log.error("Exception calling updateOffer for {}. {}", message.getOauthId(), e);
        }
    }

    @Override
    public void onError(Throwable t) {
        log.error("OfferObserver.onError", t);
        sleepBetweenReconnects();
        resubscribe.accept(this);
    }

    @Override
    public void onCompleted() {
        log.info("OfferObserver stream closed");
        sleepBetweenReconnects();
        resubscribe.accept(this);
    }

    private void updateOfferConfirmation(String oauthId, String traceId, OfferState offerState) {
        var request = OfferStatusConfirmRequest.newBuilder()
                .setOauthId(oauthId)
                .setTraceId(traceId)
                .setOfferState(offerState)
                .build();
        streamBlockingStub.offerStatusConfirmation(request);
    }
}
