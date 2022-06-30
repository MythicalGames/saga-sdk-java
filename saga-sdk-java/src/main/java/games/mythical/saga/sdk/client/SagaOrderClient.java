package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.SagaOrderExecutor;
import games.mythical.saga.sdk.client.model.SagaCreditCardData;
import games.mythical.saga.sdk.client.observer.SagaStatusUpdateObserver;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.exception.SagaErrorCode;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.factory.PmtProviderDataFactory;
import games.mythical.saga.sdk.proto.api.order.ConfirmOrderRequest;
import games.mythical.saga.sdk.proto.api.order.CreateOrderQuoteRequest;
import games.mythical.saga.sdk.proto.api.order.OrderServiceGrpc;
import games.mythical.saga.sdk.proto.api.order.PaymentProviderData;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

@Slf4j
public class SagaOrderClient extends AbstractSagaStreamClient {
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
        initStreamStub();
        SagaStatusUpdateObserver.getInstance().with(executor);
    }

    public String createQuote(String oauthId,
                               BigDecimal subtotal,
                               SagaCreditCardData creditCardData,
                               String itemTypeId,
                               String listingAddress,
                               boolean buyMythToken,
                               boolean withdrawMythToken,
                               boolean convertMythToUsd,
                               String withdrawItemAddress) throws SagaException {
        return createQuote(oauthId,
                           subtotal,
                           PmtProviderDataFactory.fromCreditCard(creditCardData),
                           itemTypeId,
                           listingAddress,
                           buyMythToken,
                           withdrawMythToken,
                           convertMythToUsd,
                           withdrawItemAddress);
    }

    public String createQuote(String oauthId,
                              BigDecimal subtotal,
                              String upholdCardId,
                              String itemTypeId,
                              String listingAddress,
                              boolean buyMythToken,
                              boolean withdrawMythToken,
                              boolean convertMythToUsd,
                              String withdrawItemAddress) throws SagaException {
        return createQuote(oauthId,
                           subtotal,
                           PmtProviderDataFactory.fromUpholdCard(upholdCardId),
                           itemTypeId,
                           listingAddress,
                           buyMythToken,
                           withdrawMythToken,
                           convertMythToUsd,
                           withdrawItemAddress);
    }

    public String confirmOrder(String oauthId,
                                String quoteId,
                                SagaCreditCardData creditCardData,
                                String fraudSessionId) throws SagaException {
        return confirmOrder(oauthId,
                            quoteId,
                            PmtProviderDataFactory.fromCreditCard(creditCardData),
                            fraudSessionId);
    }

    public String confirmOrder(String oauthId,
                                String quoteId,
                                String upholdCardId,
                                String fraudSessionId) throws SagaException {
        return confirmOrder(oauthId,
                            quoteId,
                            PmtProviderDataFactory.fromUpholdCard(upholdCardId),
                            fraudSessionId);
    }

    private String createQuote(String oauthId,
                              BigDecimal subtotal,
                              PaymentProviderData paymentProviderData,
                              String itemTypeId,
                              String listingAddress,
                              boolean buyMythToken,
                              boolean withdrawMythToken,
                              boolean convertMythToUsd,
                              String withdrawItemAddress) throws SagaException {
        var builder = CreateOrderQuoteRequest.newBuilder()
                .setTitleId(config.getTitleId())
                .setOauthId(oauthId)
                .setSubtotal(subtotal.toPlainString())
                .setPaymentProviderData(paymentProviderData);

        if (StringUtils.isNotBlank(itemTypeId)) {
            builder.setItemTypeId(itemTypeId);
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
            var receivedResponse = serviceBlockingStub.createOrderQuote(builder.build());
            return receivedResponse.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on createOrder, order may be lost!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }

    private String confirmOrder(String oauthId,
                               String quoteId,
                               PaymentProviderData paymentProviderData,
                               String fraudSessionId) throws SagaException {
        var request = ConfirmOrderRequest.newBuilder()
                .setTitleId(config.getTitleId())
                .setOauthId(oauthId)
                .setPaymentProviderData(paymentProviderData)
                .setFraudSessionId(fraudSessionId)
                .build();

        try {
            var receivedResponse = serviceBlockingStub.confirmOrder(request);
            return receivedResponse.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on confirmOrder, order may be lost!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }
}
