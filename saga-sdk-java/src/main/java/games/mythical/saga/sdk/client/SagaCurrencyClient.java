package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.SagaCurrencyExecutor;
import games.mythical.saga.sdk.client.model.SagaCurrency;
import games.mythical.saga.sdk.client.model.SagaUserAmount;
import games.mythical.saga.sdk.client.observer.SagaStatusUpdateObserver;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.exception.SagaErrorCode;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.api.currency.*;
import games.mythical.saga.sdk.util.ValidateUtil;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SagaCurrencyClient extends AbstractSagaStreamClient {
    private final SagaCurrencyExecutor executor;
    private CurrencyServiceGrpc.CurrencyServiceBlockingStub serviceBlockingStub;

    SagaCurrencyClient(SagaSdkConfig config) throws SagaException {
        this(config, null);
    }

    SagaCurrencyClient(SagaSdkConfig config, SagaCurrencyExecutor executor) throws SagaException {
        super(config);
        this.executor = executor;
        initStub();
    }

    @Override
    void initStub() {
        serviceBlockingStub = CurrencyServiceGrpc.newBlockingStub(channel).withCallCredentials(addAuthentication());
        initStreamStub();
        SagaStatusUpdateObserver.getInstance().with(executor);
    }

    public SagaCurrency getCurrency(String currencyId, String oauthId) throws SagaException {
        var request = GetCurrencyForPlayerRequest.newBuilder()
                .setCurrencyTypeId(currencyId)
                .setOauthId(oauthId)
                .build();

        try {
            var currency = serviceBlockingStub.getCurrencyForPlayer(request);
            ValidateUtil.checkFound(currency,
                    String.format("Unable to find currency %s for player %s",
                            currencyId, oauthId));
            return SagaCurrency.fromProto(currency);
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        }
    }

    public String issueCurrency(String currencyTypeId,
                                List<SagaUserAmount> userAmounts,
                                String idempotencyId) throws SagaException {

        List<UserAmountProto> amounts = new ArrayList<>();
        userAmounts.forEach(userAmount -> amounts.add(UserAmountProto.newBuilder()
                .setAmountInWei(userAmount.getAmount_in_wei())
                .setOauthId(userAmount.getOauth_id())
                .build()));
        var request = IssueCurrencyRequest.newBuilder()
                .setCurrencyTypeId(currencyTypeId)
                .addAllUserAmounts(amounts)
                .setIdempotencyId(idempotencyId)
                .build();

        try {
            var receivedResponse = serviceBlockingStub.issueCurrency(request);
            return receivedResponse.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on issueCurrency, coin may be lost!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }

    public String transferCurrency(String currencyId,
                                   String sourceOauthId,
                                   String destOauthId,
                                   long amount) throws SagaException {
        var request = TransferCurrencyRequest.newBuilder()
                .setCurrencyId(currencyId)
                .setSourceOauthId(sourceOauthId)
                .setDestinationOauthId(destOauthId)
                .setAmount(amount)
                .build();

        try {
            var receivedResponse = serviceBlockingStub.transferCurrency(request);
            return receivedResponse.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on transferCurrency, coin may be out of sync!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }

    public String burnCurrency(String currencyId, String oauthId, long amount) throws SagaException {
        var request = BurnCurrencyRequest.newBuilder()
                .setCurrencyId(currencyId)
                .setOauthId(oauthId)
                .setAmount(amount)
                .build();

        try {
            var receivedResponse = serviceBlockingStub.burnCurrency(request);
            return receivedResponse.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on burnCurrency, coin may be out of sync!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }

}
