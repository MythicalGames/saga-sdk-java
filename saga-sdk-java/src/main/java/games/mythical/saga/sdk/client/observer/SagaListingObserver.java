package games.mythical.saga.sdk.client.observer;

import games.mythical.saga.sdk.client.executor.SagaListingExecutor;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.proto.common.listing.ListingState;
import games.mythical.saga.sdk.proto.streams.listing.ListingStatusConfirmRequest;
import games.mythical.saga.sdk.proto.streams.listing.ListingStatusUpdate;
import games.mythical.saga.sdk.proto.streams.listing.ListingStreamGrpc;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.function.Consumer;

@Slf4j
public class SagaListingObserver extends AbstractObserver<ListingStatusUpdate> {
    private final SagaSdkConfig config;
    private final SagaListingExecutor sagaListingExecutor;
    private final ListingStreamGrpc.ListingStreamBlockingStub streamBlockingStub;
    private final Consumer<SagaListingObserver> resubscribe;

    public SagaListingObserver(SagaSdkConfig config,
                               SagaListingExecutor sagaListingExecutor,
                               ListingStreamGrpc.ListingStreamBlockingStub streamBlockingStub,
                               Consumer<SagaListingObserver> resubscribe) {
        this.config = config;
        this.sagaListingExecutor = sagaListingExecutor;
        this.streamBlockingStub = streamBlockingStub;
        this.resubscribe = resubscribe;
    }

    @Override
    public void onNext(ListingStatusUpdate message) {
        log.trace("ListingObserver.onNext for user: {}", message.getOauthId());
        resetConnectionRetry();
        try {
            sagaListingExecutor.updateListing(
                    message.getOauthId(),
                    message.getTraceId(),
                    message.getQuoteId(),
                    message.getListingId(),
                    new BigDecimal(message.getTotal()),
                    message.getListingState()
            );
            updateListingConfirmation(message.getOauthId(), message.getTraceId(), message.getListingState());
        } catch (Exception e) {
            log.error("Exception calling updateListing for {}. {}", message.getOauthId(), e);
        }
    }

    @Override
    public void onError(Throwable t) {
        log.error("ListingObserver.onError", t);
        sleepBetweenReconnects();
        resubscribe.accept(this);
    }

    @Override
    public void onCompleted() {
        log.info("ListingObserver stream closed");
        sleepBetweenReconnects();
        resubscribe.accept(this);
    }

    private void updateListingConfirmation(String oauthId, String traceId, ListingState listingState) {
        var request = ListingStatusConfirmRequest.newBuilder()
                .setOauthId(oauthId)
                .setTraceId(traceId)
                .setListingState(listingState)
                .build();
        streamBlockingStub.listingStatusConfirmation(request);
    }
}
