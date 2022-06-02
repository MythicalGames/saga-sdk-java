package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.SagaOfferExecutor;
import games.mythical.saga.sdk.client.model.SagaOffer;
import games.mythical.saga.sdk.client.model.query.QueryOptions;
import games.mythical.saga.sdk.client.observer.SagaStatusUpdateObserver;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.exception.SagaErrorCode;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.api.offer.*;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class SagaOfferClient extends AbstractSagaStreamClient {
    private final SagaOfferExecutor executor;
    private OfferServiceGrpc.OfferServiceBlockingStub serviceBlockingStub;

    SagaOfferClient(SagaSdkConfig config, SagaOfferExecutor executor) throws SagaException {
        super(config);
        this.executor = executor;
        initStub();
    }

    @Override
    void initStub() {
        serviceBlockingStub = OfferServiceGrpc.newBlockingStub(channel).withCallCredentials(addAuthentication());
        initStreamStub();
        SagaStatusUpdateObserver.getInstance().with(executor);
    }

    public String createOfferQuote(String oauthId,
                                   String gameInventoryId,
                                   BigDecimal subtotal,
                                   String currency) throws SagaException {
        var request = CreateOfferQuoteRequest.newBuilder()
                .setOauthId(oauthId)
                .setGameInventoryId(gameInventoryId)
                .setTotal(subtotal.toPlainString())
                .setCurrency(currency)
                .build();

        try {
            var receivedResponse = serviceBlockingStub.createOfferQuote(request);
            return receivedResponse.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on createOffer, offer may be lost!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }

    public String confirmOffer(String oauthId, String quoteId) throws SagaException {
        var request = ConfirmOfferRequest.newBuilder()
                .setOauthId(oauthId)
                .build();

        try {
            var receivedResponse = serviceBlockingStub.confirmOffer(request);
            return receivedResponse.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on confirmOffer, offer may be lost!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }

    public String cancelOffer(String oauthId, String quoteId) throws SagaException {
        var request = CancelOfferRequest.newBuilder()
                .setOauthId(oauthId)
                .build();

        try {
            var receivedResponse = serviceBlockingStub.cancelOffer(request);
            return receivedResponse.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on cancelOffer, offer may be lost!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }

    public List<SagaOffer> getOffers(String itemTypeId,
                                     String token,
                                     String oauthId,
                                     QueryOptions queryOptions) throws SagaException {
        if (queryOptions == null) {
            queryOptions = new QueryOptions();
        }
        var request = GetOffersRequest.newBuilder()
                .setItemTypeId(itemTypeId)
                .setToken(token)
                .setOauthId(oauthId)
                .setQueryOptions(QueryOptions.toProto(queryOptions))
                .build();

        try {
            var receivedResponse = serviceBlockingStub.getOffers(request);
            return receivedResponse.getOffersList().stream().map(SagaOffer::fromProto).collect(Collectors.toList());
        } catch (StatusRuntimeException e) {
            if (e.getStatus() == Status.NOT_FOUND) {
                return List.of();
            }

            throw SagaException.fromGrpcException(e);
        }
    }
}
