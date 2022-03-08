package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.SagaItemExecutor;
import games.mythical.saga.sdk.client.model.SagaItem;
import games.mythical.saga.sdk.client.model.SagaMetadata;
import games.mythical.saga.sdk.client.observer.SagaItemObserver;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.exception.SagaErrorCode;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.api.item.*;
import games.mythical.saga.sdk.proto.streams.Subscribe;
import games.mythical.saga.sdk.proto.streams.item.ItemStreamGrpc;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;

@Slf4j
public class SagaItemClient extends AbstractSagaClient {
    private final SagaItemExecutor executor;
    private ItemServiceGrpc.ItemServiceBlockingStub serviceBlockingStub;

    SagaItemClient(SagaSdkConfig config, SagaItemExecutor executor) throws SagaException {
        super(config);
        this.executor = executor;
        initStub();
    }

    @Override
    void initStub() {
        serviceBlockingStub = ItemServiceGrpc.newBlockingStub(channel).withCallCredentials(addAuthentication());
        var streamBlockingStub = ItemStreamGrpc.newBlockingStub(channel)
                .withCallCredentials(addAuthentication());
        subscribeToStream(new SagaItemObserver(config, executor, streamBlockingStub, this::subscribeToStream));
    }

    void subscribeToStream(SagaItemObserver observer) {
        // set up server stream
        var streamStub = ItemStreamGrpc.newStub(channel).withCallCredentials(addAuthentication());
        var subscribe = Subscribe.newBuilder()
                .setTitleId(config.getTitleId())
                .build();

        streamStub.itemStatusStream(subscribe, observer);
    }

    public Optional<SagaItem> getItem(String gameInventoryId, boolean history) throws SagaException {
        var request = GetItemRequest.newBuilder()
                .setTitleId(config.getTitleId())
                .setGameInventoryId(gameInventoryId)
                .setHistory(history)
                .build();

        try {
            var item = serviceBlockingStub.getItem(request);
            return Optional.of(SagaItem.fromProto(item));
        } catch (StatusRuntimeException e) {
            if (e.getStatus() == Status.NOT_FOUND) {
                return Optional.empty();
            }

            throw SagaException.fromGrpcException(e);
        }
    }

    public void issueItem(String gameInventoryId,
                          String oauthId,
                          String gameItemTypeId,
                          SagaMetadata metadata,
                          String storeId,
                          String orderId,
                          String requestIp) throws SagaException {
        var builder = IssueItemRequest.newBuilder()
                .setTitleId(config.getTitleId())
                .setGameInventoryId(gameInventoryId)
                .setOauthId(oauthId)
                .setGameItemTypeId(gameItemTypeId)
                .setMetadata(SagaMetadata.toProto(metadata));

        if (StringUtils.isNotBlank(storeId)) {
            builder.setStoreId(storeId);
        }

        if (StringUtils.isNotBlank(orderId)) {
            builder.setOrderId(orderId);
        }

        if (StringUtils.isNotBlank(requestIp)) {
            builder.setRequestIp(requestIp);
        }

        try {
            var receivedResponse = serviceBlockingStub.issueItem(builder.build());
            executor.emitReceived(gameInventoryId, receivedResponse.getTraceId());
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on issueItem, item may be lost!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }

    public void transferItem(String gameInventoryId,
                             String sourceOauthId,
                             String destOauthId,
                             String storeId) throws SagaException {
        var builder = TransferItemRequest.newBuilder()
                .setTitleId(config.getTitleId())
                .setGameInventoryId(gameInventoryId)
                .setSourceOauthId(sourceOauthId)
                .setDestinationOauthId(destOauthId);

        if (StringUtils.isNotBlank(storeId)) {
            builder.setStoreId(storeId);
        }

        try {
            var receivedResponse = serviceBlockingStub.transferItem(builder.build());
            executor.emitReceived(gameInventoryId, receivedResponse.getTraceId());
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on transferItem, item may be out of sync!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }

    public void burnItem(String gameInventoryId) throws SagaException {
        var request = BurnItemRequest.newBuilder()
                .setTitleId(config.getTitleId())
                .setGameInventoryId(gameInventoryId)
                .build();

        try {
            var receivedResponse = serviceBlockingStub.burnItem(request);
            executor.emitReceived(gameInventoryId, receivedResponse.getTraceId());
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on burnItem, item may be out of sync!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }

    public void updateItemMetadata(String gameInventoryId, SagaMetadata metadata) throws SagaException {
        var updateItem = UpdateItemMetadata.newBuilder()
                .setGameInventoryId(gameInventoryId)
                .setMetadata(SagaMetadata.toProto(metadata))
                .build();

        _updateItemMetadata(List.of(updateItem));
    }

    private void _updateItemMetadata(List<UpdateItemMetadata> updateItems) throws SagaException {
        try {
            var request = UpdateItemsMetadataRequest.newBuilder()
                    .setTitleId(config.getTitleId())
                    .addAllUpdateItems(updateItems)
                    .build();

            serviceBlockingStub.updateItemsMetadata(request);
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        }
    }
}
