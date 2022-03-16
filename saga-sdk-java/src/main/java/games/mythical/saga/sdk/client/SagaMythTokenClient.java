package games.mythical.saga.sdk.client;

import com.google.protobuf.Empty;
import games.mythical.saga.sdk.client.executor.SagaMythTokenExecutor;
import games.mythical.saga.sdk.client.model.SagaCurrencyExchange;
import games.mythical.saga.sdk.client.model.SagaGasFee;
import games.mythical.saga.sdk.client.model.SagaMythToken;
import games.mythical.saga.sdk.client.observer.SagaStatusUpdateObserver;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.exception.SagaErrorCode;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.api.myth.*;
import games.mythical.saga.sdk.proto.api.payments.CardPaymentData;
import games.mythical.saga.sdk.proto.streams.StatusStreamGrpc;
import games.mythical.saga.sdk.proto.streams.Subscribe;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
public class SagaMythTokenClient extends AbstractSagaClient {
    private final SagaMythTokenExecutor executor;
    private MythServiceGrpc.MythServiceBlockingStub serviceBlockingStub;

    SagaMythTokenClient(SagaSdkConfig config, SagaMythTokenExecutor executor) throws SagaException {
        super(config);
        this.executor = executor;
        initStub();
    }

    @Override
    void initStub() throws SagaException {
        serviceBlockingStub = MythServiceGrpc.newBlockingStub(channel).withCallCredentials(addAuthentication());
        var streamBlockingStub = StatusStreamGrpc.newBlockingStub(channel)
                .withCallCredentials(addAuthentication());
        subscribeToStream(new SagaStatusUpdateObserver(streamBlockingStub, this::subscribeToStream)
                .with(executor));
    }

    void subscribeToStream(SagaStatusUpdateObserver observer) {
        // set up server stream
        var streamStub = StatusStreamGrpc.newStub(channel).withCallCredentials(addAuthentication());
        var subscribe = Subscribe.newBuilder()
                .setTitleId(config.getTitleId())
                .build();

        streamStub.statusStream(subscribe, observer);
    }

    public Optional<SagaGasFee> getGasFee() {
        var gasFee = serviceBlockingStub.getGasFee(Empty.getDefaultInstance());
        return Optional.of(SagaGasFee.fromProto(gasFee));
    }

    public Optional<SagaCurrencyExchange> getExchangeRate() {
        var currencyExchange = serviceBlockingStub.getCurrencyExchange(Empty.getDefaultInstance());
        return Optional.of(SagaCurrencyExchange.fromProto(currencyExchange));
    }

    public Optional<SagaMythToken> quoteBuyingMythToken(BigDecimal quantity,
                                                        CardPaymentData creditCardInfo,
                                                        String denominationCurrency,
                                                        String originSubAccount,
                                                        String userId) {
        var request = QuoteBuyingMythTokenRequest.newBuilder()
                .setQuantity(quantity.toString())
                .setCreditCardInfo(creditCardInfo)
                .setDenominationCurrency(denominationCurrency)
                .setOriginSubAccount(originSubAccount)
                .setUserId(userId)
                .build();
        var mythToken = serviceBlockingStub.quoteBuyingMythToken(request);
        return Optional.of(SagaMythToken.builder()
                .quoteId(mythToken.getUpholdQuoteId())
                .originSubAccount(mythToken.getOriginSubAccount())
                .build());
    }

    public void confirmBuyingMythToken(String quoteId,
                                       String userId,
                                       CardPaymentData creditCardInfo) throws SagaException {
        var request = ConfirmBuyingMythTokenRequest.newBuilder()
                .setQuoteId(quoteId)
                .setUserId(userId)
                .setCreditCardInfo(creditCardInfo)
                .build();

        try {
            var receivedResponse = serviceBlockingStub.confirmBuyingMythToken(request);
            executor.emitReceived(receivedResponse.getTraceId());
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on confirmBuyingMythToken.", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }

    public Optional<SagaMythToken> quoteMythTokenWithdrawal(String userId,
                                                            BigDecimal quantity) {
        var request = QuoteMythTokenWithdrawalRequest.newBuilder()
                .setUserId(userId)
                .setQuantity(quantity.toString())
                .build();
        var mythToken = serviceBlockingStub.quoteMythTokenWithdrawal(request);
        return Optional.of(SagaMythToken.builder()
                .totalAmount(new BigDecimal(mythToken.getTotalAmount()))
                .gasFee(new BigDecimal(mythToken.getGasFee()))
                .build());
    }

    public void confirmMythTokenWithdrawal(String quoteId) throws SagaException {
        var request = ConfirmMythTokenWithdrawalRequest.newBuilder()
                .setQuoteId(quoteId)
                .build();

        try {
            var receivedResponse = serviceBlockingStub.confirmMythTokenWithdrawal(request);
            executor.emitReceived(receivedResponse.getTraceId());
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on confirmMythTokenWithdrawal.", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }

    }


}
