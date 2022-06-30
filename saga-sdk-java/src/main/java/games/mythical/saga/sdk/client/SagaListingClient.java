package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.SagaListingExecutor;
import games.mythical.saga.sdk.client.model.SagaListing;
import games.mythical.saga.sdk.client.observer.SagaStatusUpdateObserver;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.exception.SagaErrorCode;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.factory.CommonFactory;
import games.mythical.saga.sdk.proto.api.listing.*;
import games.mythical.saga.sdk.proto.common.SortOrder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class SagaListingClient extends AbstractSagaStreamClient {
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
        initStreamStub();
        SagaStatusUpdateObserver.getInstance().with(executor);
    }

    public String createListingQuote(String oauthId,
                                     String inventoryId,
                                     BigDecimal subtotal,
                                     String currency) throws SagaException {
        var request = CreateListingQuoteRequest.newBuilder()
                .setOauthId(oauthId)
                .setInventoryId(inventoryId)
                .setTotal(subtotal.toPlainString())
                .setCurrency(currency)
                .build();

        try {
            var receivedResponse = serviceBlockingStub.createListingQuote(request);
            return receivedResponse.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on cancelListing, listing may be lost!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }

    public String confirmListing(String oauthId, String quoteId) throws SagaException {
        var request = ConfirmListingRequest.newBuilder()
                .setOauthId(oauthId)
                .build();

        try {
            var receivedResponse = serviceBlockingStub.confirmListing(request);
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
                                         int pageSize,
                                         SortOrder sortOrder,
                                         Instant createdAtCursor) throws SagaException {
        var request = GetListingsRequest.newBuilder()
                .setItemTypeId(itemTypeId)
                .setToken(token)
                .setOauthId(oauthId)
                .setQueryOptions(CommonFactory.toProto(pageSize, sortOrder, createdAtCursor))
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
