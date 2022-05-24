package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.SagaItemExecutor;
import games.mythical.saga.sdk.client.model.SagaItem;
import games.mythical.saga.sdk.client.model.SagaMetadata;
import games.mythical.saga.sdk.client.model.query.QueryOptions;
import games.mythical.saga.sdk.client.observer.SagaStatusUpdateObserver;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.exception.SagaErrorCode;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.api.item.*;
import games.mythical.saga.sdk.proto.common.Finalized;
import games.mythical.saga.sdk.proto.common.item.BlockChains;
import games.mythical.saga.sdk.util.ValidateUtil;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class SagaItemClient extends AbstractSagaStreamClient {
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
        initStreamStub();
        SagaStatusUpdateObserver.getInstance().with(executor);
    }

    public Optional<SagaItem> getItem(String gameInventoryId, boolean history) throws SagaException {
        var request = GetItemRequest.newBuilder()
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

    public List<SagaItem> getItems(Finalized finalized, String token_name, QueryOptions queryOptions) throws SagaException {
        if (queryOptions == null) {
            queryOptions = new QueryOptions();
        }

        var request = GetItemsRequest.newBuilder()
                .setQueryOptions(QueryOptions.toProto(queryOptions))
                .setFinalized(finalized);

        if (StringUtils.isNotBlank(token_name)) {
            request.setTokenName(token_name);
        }

        try {
            var items = serviceBlockingStub.getItems(request.build());
            return items.getItemsList().stream().map(SagaItem::fromProto).collect(Collectors.toList());
        } catch (StatusRuntimeException e) {
            if (e.getStatus() == Status.NOT_FOUND) {
                return List.of();
            }

            throw SagaException.fromGrpcException(e);
        }
    }

    public List<SagaItem> getItemsForPlayer(String oauthId) throws SagaException {
        ValidateUtil.notBlank(oauthId, "oauthId is a required value");
        var request = GetItemsForPlayerRequest.newBuilder()
                .setOauthId(oauthId)
                .build();
        try {
            var items = serviceBlockingStub.getItemsForPlayer(request);
            return items.getItemsList().stream().map(SagaItem::fromProto).collect(Collectors.toList());
        } catch (StatusRuntimeException e) {
            if (e.getStatus() == Status.NOT_FOUND) {
                return List.of();
            }
            throw SagaException.fromGrpcException(e);
        }
    }

    public String issueItem(String gameInventoryId,
                            String oauthId,
                            String gameItemTypeId,
                            SagaMetadata metadata,
                            String orderId,
                            String requestIp,
                            String buyerWallet) throws SagaException {
        var builder = IssueItemRequest.newBuilder()
                .setGameInventoryId(gameInventoryId)
                .setGameItemTypeId(gameItemTypeId)
                .setMetadata(SagaMetadata.toProto(metadata));

        if (StringUtils.isNotBlank(oauthId)) {
            builder.setOauthId(oauthId);
        } else if (StringUtils.isNotBlank(buyerWallet)) {
            builder.setBuyerWallet(buyerWallet);
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
            return receivedResponse.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on issueItem, item may be lost!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }

    public String transferItem(String gameInventoryId,
                               String sourceOauthId,
                               String destOauthId,
                               String storeId) throws SagaException {
        var builder = TransferItemRequest.newBuilder()
                .setGameInventoryId(gameInventoryId)
                .setSourceOauthId(sourceOauthId)
                .setDestinationOauthId(destOauthId);

        if (StringUtils.isNotBlank(storeId)) {
            builder.setStoreId(storeId);
        }

        try {
            var receivedResponse = serviceBlockingStub.transferItem(builder.build());
            executor.emitReceived(gameInventoryId, receivedResponse.getTraceId());
            return receivedResponse.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on transferItem, item may be out of sync!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }

    public String burnItem(String gameInventoryId) throws SagaException {
        var request = BurnItemRequest.newBuilder()
                .setGameInventoryId(gameInventoryId)
                .build();

        try {
            var receivedResponse = serviceBlockingStub.burnItem(request);
            executor.emitReceived(gameInventoryId, receivedResponse.getTraceId());
            return receivedResponse.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on burnItem, item may be out of sync!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }

    public String depositItem(String gameItemInventoryId,
                            String createdBy,
                            String fromAddress,
                            String toAddress,
                            BlockChains fromChain,
                            String transactionId) throws SagaException {
        var request = DepositItemRequest.newBuilder()
                .setGameItemInventoryId(gameItemInventoryId)
                .setCreatedBy(createdBy)
                .setFromAddress(fromAddress)
                .setToAddress(toAddress)
                .setFromChain(fromChain)
                .setTransactionId(transactionId)
                .build();
        try {
            var receivedResponse = serviceBlockingStub.depositItem(request);
            return receivedResponse.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
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
                    .addAllUpdateItems(updateItems)
                    .build();

            serviceBlockingStub.updateItemsMetadata(request);
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        }
    }
}
