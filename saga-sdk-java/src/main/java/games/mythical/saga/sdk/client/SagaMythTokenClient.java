package games.mythical.saga.sdk.client;

import com.google.protobuf.Empty;
import games.mythical.proto_util.ProtoUtil;
import games.mythical.saga.sdk.client.executor.SagaMythTokenExecutor;
import games.mythical.saga.sdk.client.model.SagaCreditCardData;
import games.mythical.saga.sdk.client.model.SagaCurrencyExchange;
import games.mythical.saga.sdk.client.model.SagaGasFee;
import games.mythical.saga.sdk.client.model.SagaMythTokenBuyingQuote;
import games.mythical.saga.sdk.client.model.SagaMythTokenWithdrawalQuote;
import games.mythical.saga.sdk.client.observer.SagaStatusUpdateObserver;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.exception.SagaErrorCode;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.factory.PmtProviderDataFactory;
import games.mythical.saga.sdk.proto.api.myth.*;
import games.mythical.saga.sdk.proto.api.order.PaymentProviderData;
import games.mythical.saga.sdk.util.ValidateUtil;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
public class SagaMythTokenClient extends AbstractSagaStreamClient {
    private final SagaMythTokenExecutor executor;
    private MythServiceGrpc.MythServiceBlockingStub serviceBlockingStub;

    SagaMythTokenClient(SagaSdkConfig config, SagaMythTokenExecutor executor) throws SagaException {
        super(config);
        this.executor = executor;
        initStub();
    }

    @Override
    void initStub() {
        serviceBlockingStub = MythServiceGrpc.newBlockingStub(channel).withCallCredentials(addAuthentication());
        initStreamStub();
        SagaStatusUpdateObserver.getInstance().with(executor);
    }

    public SagaGasFee getGasFee() throws SagaException {
        var gasFee = serviceBlockingStub.getGasFee(Empty.getDefaultInstance());
        ValidateUtil.checkFound(gasFee, "Gas fee not found");
        return SagaGasFee.fromProto(gasFee);
    }

    public SagaCurrencyExchange getExchangeRate() throws SagaException {
        var currencyExchange = serviceBlockingStub.getCurrencyExchange(Empty.getDefaultInstance());
        ValidateUtil.checkFound(currencyExchange, "Saga currency exchange not found");
        return SagaCurrencyExchange.fromProto(currencyExchange);
    }

    public SagaMythTokenBuyingQuote quoteBuyingMythToken(BigDecimal quantity,
                                                        SagaCreditCardData creditCardData,
                                                        String denominationCurrency,
                                                        String originSubAccount,
                                                        String oauthId) throws SagaException {
        return quoteBuyingMythToken(quantity,
                                    PmtProviderDataFactory.fromCreditCard(creditCardData),
                                    denominationCurrency,
                                    originSubAccount,
                                    oauthId);
    }

    public SagaMythTokenBuyingQuote quoteBuyingMythToken(BigDecimal quantity,
                                                        String upholdCardId,
                                                        String denominationCurrency,
                                                        String originSubAccount,
                                                        String oauthId) throws SagaException {
        return quoteBuyingMythToken(quantity,
                                    PmtProviderDataFactory.fromUpholdCard(upholdCardId),
                                    denominationCurrency,
                                    originSubAccount,
                                    oauthId);
    }

    public SagaMythTokenWithdrawalQuote quoteMythTokenWithdrawal(String oauthId,
                                                  BigDecimal quantity) throws SagaException {
        var request = QuoteMythTokenWithdrawalRequest.newBuilder()
                .setOauthId(oauthId)
                .setQuantity(quantity.toString())
                .build();
        var mythToken = serviceBlockingStub.quoteMythTokenWithdrawal(request);
        ValidateUtil.checkQuote(mythToken, "Failed to generate myth token withdrawal quote for %s", oauthId);
        return ProtoUtil.toDto(mythToken, SagaMythTokenWithdrawalQuote.class);
    }

    public String confirmMythTokenWithdrawal(String quoteId) throws SagaException {
        var request = ConfirmMythTokenWithdrawalRequest.newBuilder()
                .setQuoteId(quoteId)
                .build();

        try {
            var receivedResponse = serviceBlockingStub.confirmMythTokenWithdrawal(request);
            return receivedResponse.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on confirmMythTokenWithdrawal.", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }

    public String confirmBuyingMythToken(String quoteId,
                                          String userId,
                                          SagaCreditCardData creditCardData) throws SagaException {
        return confirmBuyingMythToken(quoteId, userId, PmtProviderDataFactory.fromCreditCard(creditCardData));
    }

    public String confirmBuyingMythToken(String quoteId,
                                          String userId,
                                          String upholdCardId) throws SagaException {
        return confirmBuyingMythToken(quoteId, userId, PmtProviderDataFactory.fromUpholdCard(upholdCardId));
    }

    private String confirmBuyingMythToken(String quoteId,
                                          String userId,
                                          PaymentProviderData paymentProviderData) throws SagaException {
        var request = ConfirmBuyingMythTokenRequest.newBuilder()
            .setQuoteId(quoteId)
            .setOauthId(userId)
            .setPaymentProviderData(paymentProviderData)
            .build();

        try {
            var receivedResponse = serviceBlockingStub.confirmBuyingMythToken(request);
            return receivedResponse.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on confirmBuyingMythToken.", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }

    private SagaMythTokenBuyingQuote quoteBuyingMythToken(BigDecimal quantity,
                                               PaymentProviderData paymentProviderData,
                                               String denominationCurrency,
                                               String originSubAccount,
                                               String oauthId) throws SagaException {
        try {
            final var request = QuoteBuyingMythTokenRequest.newBuilder()
                .setQuantity(quantity.toString())
                .setPaymentProviderData(paymentProviderData)
                .setDenominationCurrency(denominationCurrency)
                .setOriginSubAccount(originSubAccount)
                .setOauthId(oauthId)
                .build();
            final var mythToken = serviceBlockingStub.quoteBuyingMythToken(request);
            ValidateUtil.checkQuote(mythToken, "Failed to generate myth token buying quote for %s", oauthId);
            return ProtoUtil.toDto(mythToken, SagaMythTokenBuyingQuote.class);
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        }
    }
}
