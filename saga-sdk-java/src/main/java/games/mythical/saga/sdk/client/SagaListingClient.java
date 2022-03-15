package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.SagaListingExecutor;
import games.mythical.saga.sdk.client.model.SagaListingQuote;
import games.mythical.saga.sdk.client.observer.SagaListingObserver;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.exception.SagaErrorCode;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.api.listing.CancelListingRequest;
import games.mythical.saga.sdk.proto.api.listing.ConfirmListingRequest;
import games.mythical.saga.sdk.proto.api.listing.CreateListingQuoteRequest;
import games.mythical.saga.sdk.proto.api.listing.ListingServiceGrpc;
import games.mythical.saga.sdk.proto.streams.Subscribe;
import games.mythical.saga.sdk.proto.streams.listing.ListingStreamGrpc;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
public class SagaListingClient extends AbstractSagaClient {
    private final SagaListingExecutor executor;
    private ListingServiceGrpc.ListingServiceBlockingStub serviceBlockingStub;

    SagaListingClient(SagaSdkConfig config, SagaListingExecutor executor) throws SagaException {
        super(config);
        this.executor = executor;
        initStub();
    }

    @Override
    void initStub() {
        serviceBlockingStub = ListingServiceGrpc.newBlockingStub(channel).withCallCredentials(addAuthentication());
        var streamBlockingStub = ListingStreamGrpc.newBlockingStub(channel)
                .withCallCredentials(addAuthentication());
        subscribeToStream(new SagaListingObserver(config, executor, streamBlockingStub, this::subscribeToStream));
    }

    void subscribeToStream(SagaListingObserver observer) {
        // set up server stream
        var streamStub = ListingStreamGrpc.newStub(channel).withCallCredentials(addAuthentication());
        var subscribe = Subscribe.newBuilder()
                .setTitleId(config.getTitleId())
                .build();

        streamStub.listingStatusStream(subscribe, observer);
    }

    public Optional<SagaListingQuote> createListingQuote(String oauthId,
                                                         String gameInventoryId,
                                                         BigDecimal subtotal,
                                                         String currency) throws SagaException {
        var request = CreateListingQuoteRequest.newBuilder()
                .setTitleId(config.getTitleId())
                .setOauthId(oauthId)
                .setGameInventoryId(gameInventoryId)
                .setTotal(subtotal.toPlainString())
                .setCurrency(currency)
                .build();

        try {
            var quote = serviceBlockingStub.createListingQuote(request);
            return Optional.of(SagaListingQuote.fromProto(quote));
        } catch (StatusRuntimeException e) {
            if (e.getStatus() == Status.NOT_FOUND) {
                return Optional.empty();
            }

            throw SagaException.fromGrpcException(e);
        }
    }

    public void confirmListing(String oauthId, String quoteId) throws SagaException {
        var request = ConfirmListingRequest.newBuilder()
                .setTitleId(config.getTitleId())
                .setOauthId(oauthId)
                .build();

        try {
            var receivedResponse = serviceBlockingStub.confirmListing(request);
            executor.emitReceived(quoteId, receivedResponse.getTraceId());
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on confirmListing, listing may be lost!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }

    public void cancelListing(String oauthId, String listingId) throws SagaException {
        var request = CancelListingRequest.newBuilder()
                .setTitleId(config.getTitleId())
                .setOauthId(oauthId)
                .build();

        try {
            var receivedResponse = serviceBlockingStub.cancelListing(request);
            executor.emitReceived(listingId, receivedResponse.getTraceId());
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on cancelListing, listing may be lost!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }
}
