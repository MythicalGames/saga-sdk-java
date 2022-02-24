package games.mythical.saga.sdk.client;

import com.google.protobuf.InvalidProtocolBufferException;
import games.mythical.saga.sdk.client.executor.IVIItemExecutor;
import games.mythical.saga.sdk.client.model.IVIItem;
import games.mythical.saga.sdk.client.model.IVIMetadata;
import games.mythical.saga.sdk.client.model.IVIMetadataUpdate;
import games.mythical.saga.sdk.client.observer.IVIItemObserver;
import games.mythical.saga.sdk.exception.IVIErrorCode;
import games.mythical.saga.sdk.exception.IVIException;
import games.mythical.ivi.sdk.proto.api.item.*;
import games.mythical.ivi.sdk.proto.common.Finalized;
import games.mythical.ivi.sdk.proto.common.SortOrder;
import games.mythical.ivi.sdk.proto.streams.Subscribe;
import games.mythical.ivi.sdk.proto.streams.item.ItemStreamGrpc;
import io.grpc.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
public class IVIItemClient extends AbstractIVIClient {
    private ItemServiceGrpc.ItemServiceBlockingStub serviceBlockingStub;
    private final IVIItemExecutor iviItemExecutor;

    @SuppressWarnings("unused")
    public IVIItemClient(IVIItemExecutor iviItemExecutor) throws IVIException {
        super();

        this.iviItemExecutor = iviItemExecutor;
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .keepAliveTime(keepAlive, TimeUnit.SECONDS)
                .build();
        initStub();
    }

    IVIItemClient(IVIItemExecutor iviItemExecutor, ManagedChannel channel) throws IVIException {
        this.iviItemExecutor = iviItemExecutor;
        this.channel = channel;
        initStub();
    }

    @Override
    void initStub() {
        serviceBlockingStub = ItemServiceGrpc.newBlockingStub(channel).withCallCredentials(addAuthentication());
        var streamBlockingStub = ItemStreamGrpc.newBlockingStub(channel)
                .withCallCredentials(addAuthentication());
        subscribeToStream(new IVIItemObserver(iviItemExecutor, streamBlockingStub, this::subscribeToStream));
    }

    void subscribeToStream(IVIItemObserver observer) {
        // set up server stream
        var streamStub = ItemStreamGrpc.newStub(channel).withCallCredentials(addAuthentication());
        var subscribe = Subscribe.newBuilder()
                .setEnvironmentId(environmentId)
                .build();

        streamStub.itemStatusStream(subscribe, observer);
    }

    public void issueItem(String gameInventoryId,
                          String playerId,
                          String itemName,
                          String gameItemTypeId,
                          BigDecimal amountPaid,
                          String currency,
                          IVIMetadata metadata,
                          String storeId,
                          String orderId,
                          String requestIp) throws IVIException {
        try {
            var requestBuilder = IssueItemRequest.newBuilder()
                    .setEnvironmentId(environmentId)
                    .setGameInventoryId(gameInventoryId)
                    .setPlayerId(playerId)
                    .setItemName(itemName)
                    .setGameItemTypeId(gameItemTypeId)
                    .setMetadata(IVIMetadata.toProto(metadata))
                    .setAmountPaid(amountPaid.toString())
                    .setCurrency(currency)
                    .setStoreId(storeId);

            if(StringUtils.isNotBlank(orderId)) {
                requestBuilder.setOrderId(orderId);
            }

            if(StringUtils.isNotBlank(requestIp)) {
                requestBuilder.setRequestIp(requestIp);
            }

            var result = serviceBlockingStub.issueItem(requestBuilder.build());
            iviItemExecutor.updateItemState(gameInventoryId, result.getTrackingId(), result.getItemState());
        } catch (StatusRuntimeException e) {
            throw IVIException.fromGrpcException(e);
        } catch (StatusException e) {
            throw IVIException.fromGrpcException(e);
        } catch (InvalidProtocolBufferException e) {
            log.error("issueItem: couldn't convert additionalMetadata!", e);
            throw new IVIException(IVIErrorCode.LOCAL_EXCEPTION);
        } catch (Exception e) {
            log.error("Exception calling updateItemState on issueItem, item will be in an invalid state!", e);
            throw new IVIException(IVIErrorCode.LOCAL_EXCEPTION);
        }
    }

    public void transferItem(String gameInventoryId,
                             String sourcePlayerId,
                             String destPlayerId,
                             String storeId) throws IVIException {
        var request = TransferItemRequest.newBuilder()
                .setEnvironmentId(environmentId)
                .setGameItemInventoryId(gameInventoryId)
                .setSourcePlayerId(sourcePlayerId)
                .setDestinationPlayerId(destPlayerId)
                .setStoreId(storeId)
                .build();

        try {
            var result = serviceBlockingStub.transferItem(request);
            iviItemExecutor.updateItemState(gameInventoryId, result.getTrackingId(), result.getItemState());
        } catch (StatusRuntimeException e) {
            throw IVIException.fromGrpcException(e);
        } catch (StatusException e) {
            throw IVIException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling updateItemState on transferItem, item will be in an invalid state!", e);
            throw new IVIException(IVIErrorCode.LOCAL_EXCEPTION);
        }
    }

    public void burnItem(String gameInventoryId) throws IVIException {
        var request = BurnItemRequest.newBuilder()
                .setEnvironmentId(environmentId)
                .setGameItemInventoryId(gameInventoryId)
                .build();

        try {
            var result = serviceBlockingStub.burnItem(request);
            iviItemExecutor.updateItemState(gameInventoryId, result.getTrackingId(), result.getItemState());
        } catch (StatusRuntimeException e) {
            throw IVIException.fromGrpcException(e);
        } catch (StatusException e) {
            throw IVIException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling updateItemState on burnItem, item will be in an invalid state!", e);
            throw new IVIException(IVIErrorCode.LOCAL_EXCEPTION);
        }
    }

    public Optional<IVIItem> getItem(String gameInventoryId) throws IVIException {
        return getItem(gameInventoryId, false);
    }

    public Optional<IVIItem> getItem(String gameInventoryId, boolean history) throws IVIException {
        var request = GetItemRequest.newBuilder()
                .setEnvironmentId(environmentId)
                .setGameInventoryId(gameInventoryId)
                .setHistory(history)
                .build();

        try {
            var item = serviceBlockingStub.getItem(request);
            return Optional.of(IVIItem.fromProto(item));
        } catch (StatusRuntimeException e) {
            if (e.getStatus() == Status.NOT_FOUND) {
                return Optional.empty();
            }

            throw IVIException.fromGrpcException(e);
        }
    }

    public List<IVIItem> getItems(Instant createdTimestamp, int pageSize, SortOrder sortOrder, Finalized finalized) throws IVIException {
        var request = GetItemsRequest.newBuilder()
                .setEnvironmentId(environmentId)
                .setCreatedTimestamp(createdTimestamp == null ? -1 : createdTimestamp.getEpochSecond())
                .setPageSize(pageSize)
                .setSortOrder(sortOrder)
                .setFinalized(finalized)
                .build();

        var response = serviceBlockingStub.getItems(request);

        var result = new ArrayList<IVIItem>();
        for(var item : response.getItemsList()) {
            result.add(IVIItem.fromProto(item));
        }

        return result;
    }

    public void updateItemMetadata(String gameInventoryId, IVIMetadata metadata) throws IVIException {
        var updateItems = UpdateItemMetadata.newBuilder()
                .setGameInventoryId(gameInventoryId)
                .setMetadata(IVIMetadata.toProto(metadata))
                .build();

        _updateItemMetadata(List.of(updateItems));
    }

    public void updateItemMetadata(List<IVIMetadataUpdate> updates) throws IVIException {
        var updateList = new ArrayList<UpdateItemMetadata>();
        for(var update : updates) {
            updateList.add(UpdateItemMetadata.newBuilder()
                    .setGameInventoryId(update.getGameInventoryId())
                    .setMetadata(IVIMetadata.toProto(update.getMetadata()))
                    .build());
        }

        _updateItemMetadata(updateList);
    }

    private void _updateItemMetadata(List<UpdateItemMetadata> updateList) throws IVIException {
        try {
            var request = UpdateItemMetadataRequest.newBuilder()
                    .setEnvironmentId(environmentId)
                    .addAllUpdateItems(updateList)
                    .build();

            var response = serviceBlockingStub.updateItemMetadata(request);
        } catch (StatusRuntimeException e) {
            throw IVIException.fromGrpcException(e);
        }
    }
}
