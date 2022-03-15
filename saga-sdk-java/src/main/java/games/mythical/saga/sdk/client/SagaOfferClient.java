package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.SagaOfferExecutor;
import games.mythical.saga.sdk.client.model.SagaOfferQuote;
import games.mythical.saga.sdk.client.observer.SagaOfferObserver;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.exception.SagaErrorCode;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.api.offer.CancelOfferRequest;
import games.mythical.saga.sdk.proto.api.offer.ConfirmOfferRequest;
import games.mythical.saga.sdk.proto.api.offer.CreateOfferQuoteRequest;
import games.mythical.saga.sdk.proto.api.offer.OfferServiceGrpc;
import games.mythical.saga.sdk.proto.streams.Subscribe;
import games.mythical.saga.sdk.proto.streams.offer.OfferStreamGrpc;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
public class SagaOfferClient extends AbstractSagaClient {
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
        var streamBlockingStub = OfferStreamGrpc.newBlockingStub(channel)
                .withCallCredentials(addAuthentication());
        subscribeToStream(new SagaOfferObserver(config, executor, streamBlockingStub, this::subscribeToStream));
    }

    void subscribeToStream(SagaOfferObserver observer) {
        // set up server stream
        var streamStub = OfferStreamGrpc.newStub(channel).withCallCredentials(addAuthentication());
        var subscribe = Subscribe.newBuilder()
                .setTitleId(config.getTitleId())
                .build();

        streamStub.offerStatusStream(subscribe, observer);
    }

    public Optional<SagaOfferQuote> createOfferQuote(String oauthId,
                                                     String gameInventoryId,
                                                     BigDecimal subtotal,
                                                     String currency) throws SagaException {
        var request = CreateOfferQuoteRequest.newBuilder()
                .setTitleId(config.getTitleId())
                .setOauthId(oauthId)
                .setGameInventoryId(gameInventoryId)
                .setTotal(subtotal.toPlainString())
                .setCurrency(currency)
                .build();

        try {
            var quote = serviceBlockingStub.createOfferQuote(request);
            return Optional.of(SagaOfferQuote.fromProto(quote));
        } catch (StatusRuntimeException e) {
            if (e.getStatus() == Status.NOT_FOUND) {
                return Optional.empty();
            }

            throw SagaException.fromGrpcException(e);
        }
    }

    public void confirmOffer(String oauthId, String quoteId) throws SagaException {
        var request = ConfirmOfferRequest.newBuilder()
                .setTitleId(config.getTitleId())
                .setOauthId(oauthId)
                .build();

        try {
            var receivedResponse = serviceBlockingStub.confirmOffer(request);
            executor.emitReceived(quoteId, receivedResponse.getTraceId());
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on confirmOffer, offer may be lost!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }

    public void cancelOffer(String oauthId, String quoteId) throws SagaException {
        var request = CancelOfferRequest.newBuilder()
                .setTitleId(config.getTitleId())
                .setOauthId(oauthId)
                .build();

        try {
            var receivedResponse = serviceBlockingStub.cancelOffer(request);
            executor.emitReceived(quoteId, receivedResponse.getTraceId());
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on cancelOffer, offer may be lost!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }
}
