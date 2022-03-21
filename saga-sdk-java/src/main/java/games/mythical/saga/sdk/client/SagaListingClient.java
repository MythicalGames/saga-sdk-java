package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.SagaListingExecutor;
import games.mythical.saga.sdk.client.model.SagaListing;
import games.mythical.saga.sdk.client.model.SagaListingQuote;
import games.mythical.saga.sdk.client.model.query.QueryOptions;
import games.mythical.saga.sdk.client.observer.SagaStatusUpdateObserver;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.exception.SagaErrorCode;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.api.listing.*;
import games.mythical.saga.sdk.proto.streams.StatusStreamGrpc;
import games.mythical.saga.sdk.proto.streams.Subscribe;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        var streamBlockingStub = StatusStreamGrpc.newBlockingStub(channel)
                .withCallCredentials(addAuthentication());

        if (SagaStatusUpdateObserver.getInstance() == null) {
            subscribeToStream(SagaStatusUpdateObserver.initialize(streamBlockingStub, this::subscribeToStream));
        }
        SagaStatusUpdateObserver.getInstance().with(executor);
    }

    void subscribeToStream(SagaStatusUpdateObserver observer) {
        // set up server stream
        var streamStub = StatusStreamGrpc.newStub(channel).withCallCredentials(addAuthentication());
        var subscribe = Subscribe.newBuilder()
                .setTitleId(config.getTitleId())
                .build();

        streamStub.statusStream(subscribe, observer);
    }

    public Optional<SagaListingQuote> createListingQuote(String oauthId,
                                                         String gameInventoryId,
                                                         BigDecimal subtotal,
                                                         String currency) throws SagaException {
        var request = CreateListingQuoteRequest.newBuilder()
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

    public String confirmListing(String oauthId, String quoteId) throws SagaException {
        var request = ConfirmListingRequest.newBuilder()
                .setOauthId(oauthId)
                .build();

        try {
            var receivedResponse = serviceBlockingStub.confirmListing(request);
            executor.emitReceived(quoteId, receivedResponse.getTraceId());
            return receivedResponse.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on confirmListing, listing may be lost!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }

    public String cancelListing(String oauthId, String listingId) throws SagaException {
        var request = CancelListingRequest.newBuilder()
                .setOauthId(oauthId)
                .build();

        try {
            var receivedResponse = serviceBlockingStub.cancelListing(request);
            executor.emitReceived(listingId, receivedResponse.getTraceId());
            return receivedResponse.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on cancelListing, listing may be lost!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }

    public List<SagaListing> getListings(String itemTypeId,
                                         String token,
                                         String oauthId,
                                         QueryOptions queryOptions) throws SagaException {
        if (queryOptions == null) {
            queryOptions = new QueryOptions();
        }
        var request = GetListingsRequest.newBuilder()
                .setItemTypeId(itemTypeId)
                .setToken(token)
                .setOauthId(oauthId)
                .setQueryOptions(QueryOptions.toProto(queryOptions))
                .build();

        try {
            var receivedResponse = serviceBlockingStub.getListings(request);
            return receivedResponse.getListingsList().stream().map(SagaListing::fromProto).collect(Collectors.toList());
        } catch (StatusRuntimeException e) {
            if (e.getStatus() == Status.NOT_FOUND) {
                return List.of();
            }

            throw SagaException.fromGrpcException(e);
        }
    }
}
