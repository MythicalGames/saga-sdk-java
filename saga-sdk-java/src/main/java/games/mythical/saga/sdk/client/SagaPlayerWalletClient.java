package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.SagaPlayerWalletExecutor;
import games.mythical.saga.sdk.client.observer.SagaStatusUpdateObserver;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.api.playerwallet.CreatePlayerWalletRequest;
import games.mythical.saga.sdk.proto.api.playerwallet.PlayerWalletServiceGrpc;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SagaPlayerWalletClient extends AbstractSagaStreamClient {
    private final SagaPlayerWalletExecutor executor;
    private PlayerWalletServiceGrpc.PlayerWalletServiceBlockingStub serviceBlockingStub;

    protected SagaPlayerWalletClient(SagaSdkConfig config, SagaPlayerWalletExecutor executor) throws SagaException {
        super(config);
        this.executor = executor;
        initStub();
    }

    @Override
    void initStub() {
        serviceBlockingStub = PlayerWalletServiceGrpc.newBlockingStub(channel).withCallCredentials(addAuthentication());
        initStreamStub();
        SagaStatusUpdateObserver.getInstance().with(executor);
    }

    public String createPlayerWallet(String oauthId) throws SagaException {
        try {
            log.trace("SagaPlayerWalletClient.createPlayerWallet - oauthId: {}", oauthId);
            var request = CreatePlayerWalletRequest.newBuilder()
                    .setOauthId(oauthId)
                    .build();
            var result = serviceBlockingStub.createPlayerWallet(request);
            return result.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        }
    }
}
