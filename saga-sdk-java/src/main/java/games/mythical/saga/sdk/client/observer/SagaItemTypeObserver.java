package games.mythical.saga.sdk.client.observer;

import games.mythical.saga.sdk.client.executor.SagaItemTypeExecutor;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.proto.common.itemtype.ItemTypeState;
import games.mythical.saga.sdk.proto.streams.itemtype.ItemTypeStatusConfirmRequest;
import games.mythical.saga.sdk.proto.streams.itemtype.ItemTypeStatusUpdate;
import games.mythical.saga.sdk.proto.streams.itemtype.ItemTypeStreamGrpc;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
public class SagaItemTypeObserver extends AbstractObserver<ItemTypeStatusUpdate> {
    private final SagaSdkConfig config;
    private final SagaItemTypeExecutor sagaItemTypeExecutor;
    private final ItemTypeStreamGrpc.ItemTypeStreamBlockingStub streamBlockingStub;
    private final Consumer<SagaItemTypeObserver> resubscribe;

    public SagaItemTypeObserver(SagaSdkConfig config,
                                SagaItemTypeExecutor sagaItemTypeExecutor,
                                ItemTypeStreamGrpc.ItemTypeStreamBlockingStub streamBlockingStub,
                                Consumer<SagaItemTypeObserver> resubscribe) {
        this.config = config;
        this.sagaItemTypeExecutor = sagaItemTypeExecutor;
        this.streamBlockingStub = streamBlockingStub;
        this.resubscribe = resubscribe;
    }

    @Override
    public void onNext(ItemTypeStatusUpdate message) {
        log.trace("ItemTypeObserver.onNext for item type: {}", message.getGameItemTypeId());
        resetConnectionRetry();
        try {
            sagaItemTypeExecutor.updateItemType(
                    message.getGameItemTypeId(),
                    message.getTraceId(),
                    message.getItemTypeState()
            );
            updateItemTypeConfirmation(message.getGameItemTypeId(), message.getTraceId(), message.getItemTypeState());
        } catch (Exception e) {
            log.error("Exception calling updateItemType for {}. {}", message.getGameItemTypeId(), e);
        }
    }

    @Override
    public void onError(Throwable t) {
        log.error("ItemTypeObserver.onError", t);
        sleepBetweenReconnects();
        resubscribe.accept(this);
    }

    @Override
    public void onCompleted() {
        log.info("ItemTypeObserver stream closed");
        sleepBetweenReconnects();
        resubscribe.accept(this);
    }

    private void updateItemTypeConfirmation(String gameItemTypeId, String traceId, ItemTypeState itemTypeState) {
        var request = ItemTypeStatusConfirmRequest.newBuilder()
                .setEnvironmentId(config.getTitleId())
                .setGameItemTypeId(gameItemTypeId)
                .setTraceId(traceId)
                .setItemTypeState(itemTypeState)
                .build();
        streamBlockingStub.itemTypeStatusConfirmation(request);
    }
}
