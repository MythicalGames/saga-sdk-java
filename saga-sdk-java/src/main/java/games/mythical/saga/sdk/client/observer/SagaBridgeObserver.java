package games.mythical.saga.sdk.client.observer;

import games.mythical.saga.sdk.client.executor.SagaBridgeExecutor;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.proto.streams.bridge.BridgeStatusConfirmRequest;
import games.mythical.saga.sdk.proto.streams.bridge.BridgeStatusUpdate;
import games.mythical.saga.sdk.proto.streams.bridge.BridgeStreamGrpc;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
public class SagaBridgeObserver extends AbstractObserver<BridgeStatusUpdate> {
    private final SagaSdkConfig config;
    private final SagaBridgeExecutor sagaBridgeExecutor;
    private final BridgeStreamGrpc.BridgeStreamBlockingStub streamBlockingStub;
    private final Consumer<SagaBridgeObserver> resubscribe;

    public SagaBridgeObserver(SagaSdkConfig config,
                              SagaBridgeExecutor sagaBridgeExecutor,
                              BridgeStreamGrpc.BridgeStreamBlockingStub streamBlockingStub,
                              Consumer<SagaBridgeObserver> resubscribe) {
        this.config = config;
        this.sagaBridgeExecutor = sagaBridgeExecutor;
        this.streamBlockingStub = streamBlockingStub;
        this.resubscribe = resubscribe;
    }

    @Override
    public void onNext(BridgeStatusUpdate message) {
        log.trace("BridgeObserver.onNext for user {} with item {}", message.getOauthId(), message.getGameInventoryId());
        resetConnectionRetry();
        try {
            sagaBridgeExecutor.updateItem(
                    message.getOauthId(),
                    message.getGameInventoryId(),
                    message.getGameItemTypeId(),
                    message.getDestinationAddress(),
                    message.getDestinationChain(),
                    message.getOriginAddress(),
                    message.getMythicalTransactionId(),
                    message.getMainnetTransactionId(),
                    message.getTraceId()
            );
            updateBridgeConfirmation(message.getOauthId(), message.getTraceId(), message.getMythicalTransactionId());
        } catch (Exception e) {
            log.error("Exception calling updateBridgeConfirmation for {}:{}. {}", message.getOauthId(), message.getGameInventoryId(), e);
        }
    }

    @Override
    public void onError(Throwable t) {
        log.error("BridgeObserver.onError", t);
        sleepBetweenReconnects();
        resubscribe.accept(this);
    }

    @Override
    public void onCompleted() {
        log.info("BridgeObserver stream closed");
        sleepBetweenReconnects();
        resubscribe.accept(this);
    }

    private void updateBridgeConfirmation(String oauthId, String traceId, String transactionId) {
        var request = BridgeStatusConfirmRequest.newBuilder()
                .setOauthId(oauthId)
                .setTraceId(traceId)
                .setMythicalTransactionId(transactionId)
                .build();
        streamBlockingStub.bridgeStatusConfirmation(request);
    }
}
