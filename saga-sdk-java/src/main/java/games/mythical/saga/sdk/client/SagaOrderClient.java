package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.SagaOrderExecutor;
import games.mythical.saga.sdk.client.model.SagaQuote;
import games.mythical.saga.sdk.client.observer.SagaOrderObserver;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.exception.SagaErrorCode;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.api.order.ConfirmQuoteRequest;
import games.mythical.saga.sdk.proto.api.order.CreateQuoteRequest;
import games.mythical.saga.sdk.proto.api.order.OrderServiceGrpc;
import games.mythical.saga.sdk.proto.api.order.PaymentProviderData;
import games.mythical.saga.sdk.proto.streams.Subscribe;
import games.mythical.saga.sdk.proto.streams.order.OrderStreamGrpc;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
public class SagaOrderClient extends AbstractSagaClient {
    private final SagaOrderExecutor executor;
    private OrderServiceGrpc.OrderServiceBlockingStub serviceBlockingStub;

    SagaOrderClient(SagaSdkConfig config, SagaOrderExecutor executor) throws SagaException {
        super(config);
        this.executor = executor;
        initStub();
    }

    @Override
    void initStub() {
        serviceBlockingStub = OrderServiceGrpc.newBlockingStub(channel).withCallCredentials(addAuthentication());
        var streamBlockingStub = OrderStreamGrpc.newBlockingStub(channel)
                .withCallCredentials(addAuthentication());
        subscribeToStream(new SagaOrderObserver(config, executor, streamBlockingStub, this::subscribeToStream));
    }

    void subscribeToStream(SagaOrderObserver observer) {
        // set up server stream
        var streamStub = OrderStreamGrpc.newStub(channel).withCallCredentials(addAuthentication());
        var subscribe = Subscribe.newBuilder()
                .setTitleId(config.getTitleId())
                .build();

        streamStub.orderStatusStream(subscribe, observer);
    }

    public Optional<SagaQuote> createQuote(String oauthId,
                                           BigDecimal subtotal,
                                           PaymentProviderData paymentProviderData,
                                           String itemTypeAddress,
                                           String listingAddress,
                                           boolean buyMythToken,
                                           boolean withdrawMythToken,
                                           boolean convertMythToUsd,
                                           String withdrawItemAddress) throws SagaException {
        var builder = CreateQuoteRequest.newBuilder()
                .setTitleId(config.getTitleId())
                .setOauthId(oauthId)
                .setSubtotal(subtotal.toPlainString())
                .setPaymentProviderData(paymentProviderData);

        if (StringUtils.isNotBlank(itemTypeAddress)) {
            builder.setItemTypeAddress(itemTypeAddress);
        }

        if (StringUtils.isNotBlank(listingAddress)) {
            builder.setListingAddress(listingAddress);
        }

        if (StringUtils.isNotBlank(withdrawItemAddress)) {
            builder.setWithdrawItemAddress(withdrawItemAddress);
        }

        if (buyMythToken) {
            builder.setBuyMythToken(true);
        }

        if (withdrawMythToken) {
            builder.setWithdrawMythToken(true);
        }

        if (convertMythToUsd) {
            builder.setMythToUsd(true);
        }

        try {
            var quote = serviceBlockingStub.createQuote(builder.build());
            return Optional.of(SagaQuote.fromProto(quote));
        } catch (StatusRuntimeException e) {
            if (e.getStatus() == Status.NOT_FOUND) {
                return Optional.empty();
            }

            throw SagaException.fromGrpcException(e);
        }
    }

    public void confirmQuote(String oauthId,
                             String quoteId,
                             PaymentProviderData paymentProviderData,
                             String fraudSessionId) throws SagaException {
        var request = ConfirmQuoteRequest.newBuilder()
                .setTitleId(config.getTitleId())
                .setOauthId(oauthId)
                .setPaymentProviderData(paymentProviderData)
                .setFraudSessionId(fraudSessionId)
                .build();

        try {
            var receivedResponse = serviceBlockingStub.confirmQuote(request);
            executor.emitReceived(quoteId, receivedResponse.getTraceId());
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on confirmQuote, order may be lost!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }
}
