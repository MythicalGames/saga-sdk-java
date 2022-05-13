package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.SagaCurrencyExecutor;
import games.mythical.saga.sdk.client.model.SagaCurrency;
import games.mythical.saga.sdk.client.observer.SagaStatusUpdateObserver;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.exception.SagaErrorCode;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.api.currency.*;
import games.mythical.saga.sdk.proto.common.SortOrder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class SagaCurrencyClient extends AbstractSagaStreamClient {
    private final SagaCurrencyExecutor executor;
    private CurrencyServiceGrpc.CurrencyServiceBlockingStub serviceBlockingStub;

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

    public Optional<SagaCurrency> getCurrency(String currencyId, String playerWalletId) throws SagaException {
        var request = GetCurrencyByPlayerRequest.newBuilder()
                .setGameCurrencyTypeId(currencyId)
                .setPlayerWalletId(playerWalletId)
                .build();

        try {
            var currency = serviceBlockingStub.getCurrencyByPlayer(request);
            return Optional.of(SagaCurrency.fromProto(currency));
        } catch (StatusRuntimeException e) {
            if (e.getStatus() == Status.NOT_FOUND) {
                return Optional.empty();
            }

            throw SagaException.fromGrpcException(e);
        }
    }

    public String issueCurrency(String currencyId, String oauthId, String quantity) throws SagaException {
        var request = IssueCurrencyRequest.newBuilder()
                .setCurrencyId(currencyId)
                .setOauthId(oauthId)
                .setQuantity(quantity)
                .build();

        try {
            var receivedResponse = serviceBlockingStub.issueCurrency(request);
            executor.emitReceived(currencyId, oauthId, receivedResponse.getTraceId());
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
                                   String quantity) throws SagaException {
        var request = TransferCurrencyRequest.newBuilder()
                .setCurrencyId(currencyId)
                .setSourceOauthId(sourceOauthId)
                .setDestinationOauthId(destOauthId)
                .setQuantity(quantity)
                .build();

        try {
            var receivedResponse = serviceBlockingStub.transferCurrency(request);
            // TODO: destOauthId correct here?
            executor.emitReceived(currencyId, destOauthId, receivedResponse.getTraceId());
            return receivedResponse.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on transferCurrency, coin may be out of sync!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }

    public String burnCurrency(String currencyId, String oauthId, String quantity) throws SagaException {
        var request = BurnCurrencyRequest.newBuilder()
                .setCurrencyId(currencyId)
                .setOauthId(oauthId)
                .setQuantity(quantity)
                .build();

        try {
            var receivedResponse = serviceBlockingStub.burnCurrency(request);
            executor.emitReceived(currencyId, oauthId, receivedResponse.getTraceId());
            return receivedResponse.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on burnCurrency, coin may be out of sync!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }

}
