package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.SagaGameCoinExecutor;
import games.mythical.saga.sdk.client.model.SagaGameCoin;
import games.mythical.saga.sdk.client.observer.SagaStatusUpdateObserver;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.exception.SagaErrorCode;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.api.gamecoin.*;
import games.mythical.saga.sdk.proto.common.SortOrder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class SagaGameCoinClient extends AbstractSagaStreamClient {
    private final SagaGameCoinExecutor executor;
    private GameCoinServiceGrpc.GameCoinServiceBlockingStub serviceBlockingStub;

    SagaGameCoinClient(SagaSdkConfig config, SagaGameCoinExecutor executor) throws SagaException {
        super(config);
        this.executor = executor;
        initStub();
    }

    @Override
    void initStub() {
        serviceBlockingStub = GameCoinServiceGrpc.newBlockingStub(channel).withCallCredentials(addAuthentication());
        initStreamStub();
        SagaStatusUpdateObserver.getInstance().with(executor);
    }

    public Optional<SagaGameCoin> getGameCoin(String currencyId, String oauthId) throws SagaException {
        var request = GetGameCoinRequest.newBuilder()
                .setCurrencyId(currencyId)
                .setOauthId(oauthId)
                .build();

        try {
            var gameCoin = serviceBlockingStub.getGameCoin(request);
            return Optional.of(SagaGameCoin.fromProto(gameCoin));
        } catch (StatusRuntimeException e) {
            if (e.getStatus() == Status.NOT_FOUND) {
                return Optional.empty();
            }

            throw SagaException.fromGrpcException(e);
        }
    }

    public List<SagaGameCoin> getGameCoins(String oauthId,
                                           Instant createdAfterTimestamp,
                                           int pageSize,
                                           SortOrder sortOrder) throws SagaException {
        var request = GetGameCoinsRequest.newBuilder()
                .setOauthId(oauthId)
                .setCreatedAfterTimestamp(createdAfterTimestamp == null ? -1 : createdAfterTimestamp.toEpochMilli())
                .setPageSize(pageSize)
                .setSortOrder(sortOrder)
                .build();

        try {
            var gameCoins = serviceBlockingStub.getGameCoins(request);
            return gameCoins.getGameCoinsList().stream()
                    .map(SagaGameCoin::fromProto)
                    .collect(Collectors.toList());
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        }
    }

    public String issueGameCoin(String currencyId, String oauthId, int coinCount) throws SagaException {
        var request = IssueGameCoinRequest.newBuilder()
                .setCurrencyId(currencyId)
                .setOauthId(oauthId)
                .setCoinCount(coinCount)
                .build();

        try {
            var receivedResponse = serviceBlockingStub.issueGameCoin(request);
            executor.emitReceived(currencyId, oauthId, receivedResponse.getTraceId());
            return receivedResponse.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on issueGameCoin, coin may be lost!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }

    public String transferGameCoin(String currencyId,
                                 String sourceOauthId,
                                 String destOauthId,
                                 int coinCount) throws SagaException {
        var request = TransferGameCoinRequest.newBuilder()
                .setCurrencyId(currencyId)
                .setSourceOauthId(sourceOauthId)
                .setDestinationOauthId(destOauthId)
                .setCoinCount(coinCount)
                .build();

        try {
            var receivedResponse = serviceBlockingStub.transferGameCoin(request);
            // TODO: destOauthId correct here?
            executor.emitReceived(currencyId, destOauthId, receivedResponse.getTraceId());
            return receivedResponse.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on transferGameCoin, coin may be out of sync!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }

    public String burnGameCoin(String currencyId, String oauthId, int coinCount) throws SagaException {
        var request = BurnGameCoinRequest.newBuilder()
                .setCurrencyId(currencyId)
                .setOauthId(oauthId)
                .setCoinCount(coinCount)
                .build();

        try {
            var receivedResponse = serviceBlockingStub.burnGameCoin(request);
            executor.emitReceived(currencyId, oauthId, receivedResponse.getTraceId());
            return receivedResponse.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on burnGameCoin, coin may be out of sync!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }
}
