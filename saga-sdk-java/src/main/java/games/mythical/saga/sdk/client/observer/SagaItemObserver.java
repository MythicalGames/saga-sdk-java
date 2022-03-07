package games.mythical.saga.sdk.client.observer;

import games.mythical.saga.sdk.client.executor.SagaItemExecutor;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.proto.common.item.ItemState;
import games.mythical.saga.sdk.proto.streams.item.ItemStatusConfirmRequest;
import games.mythical.saga.sdk.proto.streams.item.ItemStatusUpdate;
import games.mythical.saga.sdk.proto.streams.item.ItemStreamGrpc;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
public class SagaItemObserver extends AbstractObserver<ItemStatusUpdate> {
    private final SagaSdkConfig config;
    private final SagaItemExecutor sagaItemExecutor;
    private final ItemStreamGrpc.ItemStreamBlockingStub streamBlockingStub;
    private final Consumer<SagaItemObserver> resubscribe;

    public SagaItemObserver(SagaSdkConfig config,
                            SagaItemExecutor SagaItemExecutor,
                            ItemStreamGrpc.ItemStreamBlockingStub streamBlockingStub,
                            Consumer<SagaItemObserver> resubscribe) {
        this.config = config;
        this.sagaItemExecutor = SagaItemExecutor;
        this.streamBlockingStub = streamBlockingStub;
        this.resubscribe = resubscribe;
    }

    @Override
    public void onNext(ItemStatusUpdate message) {
        log.trace("ItemObserver.onNext for item: {}", message.getGameInventoryId());
        resetConnectionRetry();
        try {
            sagaItemExecutor.updateItem(
                    message.getGameInventoryId(),
                    message.getGameItemTypeId(),
                    message.getOauthId(),
                    message.getSerialNumber(),
                    message.getMetadataUri(),
                    message.getTraceId(),
                    message.getItemState()
            );
            updateItemConfirmation(message.getGameInventoryId(), message.getTraceId(), message.getItemState());
        } catch (Exception e) {
            log.error("Exception calling updateItem for {}. {}", message.getGameInventoryId(), e);
        }
    }

    @Override
    public void onError(Throwable t) {
        log.error("ItemObserver.onError", t);
        sleepBetweenReconnects();
        resubscribe.accept(this);
    }

    @Override
    public void onCompleted() {
        log.info("ItemObserver stream closed");
        sleepBetweenReconnects();
        resubscribe.accept(this);
    }

    private void updateItemConfirmation(String gameInventoryId, String traceId, ItemState itemState) {
        var request = ItemStatusConfirmRequest.newBuilder()
                .setEnvironmentId(config.getTitleId())
                .setGameInventoryId(gameInventoryId)
                .setTraceId(traceId)
                .setItemState(itemState)
                .build();
        streamBlockingStub.itemStatusConfirmation(request);
    }
}
