package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.SagaCurrencyExecutor;
import games.mythical.saga.sdk.client.model.SagaBalanceOfPlayer;
import games.mythical.saga.sdk.client.model.SagaBalancesOfPlayer;
import games.mythical.saga.sdk.client.model.SagaUserAmount;
import games.mythical.saga.sdk.client.observer.SagaStatusUpdateObserver;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.exception.SagaErrorCode;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.factory.CommonFactory;
import games.mythical.saga.sdk.proto.api.currency.*;
import games.mythical.saga.sdk.proto.common.SortOrder;
import games.mythical.saga.sdk.util.ValidateUtil;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
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

    public String issueCurrency(String currencyTypeId,
                                List<SagaUserAmount> userAmounts,
                                String idempotencyId) throws SagaException {

        List<UserAmountProto> amounts = new ArrayList<>();
        userAmounts.forEach(userAmount -> amounts.add(UserAmountProto.newBuilder()
                .setAmountInWei(userAmount.getAmountInWei())
                .setOauthId(userAmount.getOauthId())
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

    public SagaBalanceOfPlayer getBalanceOfPlayer(String currencyTypeId,
                                                  String oauthId) throws SagaException {
        var request = GetBalanceOfPlayerRequest.newBuilder()
                .setCurrencyTypeId(currencyTypeId)
                .setOauthId(oauthId)
                .build();

        try {
            var balance = serviceBlockingStub.getBalanceOfPlayer(request);
            ValidateUtil.checkFound(balance,
                    String.format("Unable to get currency %s balance for player %s",
                            currencyTypeId, oauthId));
            return SagaBalanceOfPlayer.fromProto(balance);
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        }
    }

    public SagaBalancesOfPlayer getBalancesOfPlayer(String oauthId,
                                                   int pageSize,
                                                   SortOrder sortOrder,
                                                   Instant createdAtCursor) throws SagaException {
        var request = GetBalancesOfPlayerRequest.newBuilder()
                .setOauthId(oauthId)
                .setQueryOptions(CommonFactory.toProto(pageSize, sortOrder, createdAtCursor))
                .build();

        try {
            var balances = serviceBlockingStub.getBalancesOfPlayer(request);
            ValidateUtil.checkFound(balances,
                    String.format("Unable to get currency balances for player %s", oauthId));
            return SagaBalancesOfPlayer.fromProto(balances);
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        }
    }

}
