package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.SagaItemExecutor;
import games.mythical.saga.sdk.client.model.SagaIssueItem;
import games.mythical.saga.sdk.client.model.SagaItem;
import games.mythical.saga.sdk.client.observer.SagaStatusUpdateObserver;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.exception.SagaErrorCode;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.factory.CommonFactory;
import games.mythical.saga.sdk.proto.api.item.*;
import games.mythical.saga.sdk.proto.common.Finalized;
import games.mythical.saga.sdk.proto.common.SortOrder;
import games.mythical.saga.sdk.proto.common.item.BlockChains;
import games.mythical.saga.sdk.util.ValidateUtil;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class SagaItemClient extends AbstractSagaStreamClient {
    private final SagaItemExecutor executor;
    private ItemServiceGrpc.ItemServiceBlockingStub serviceBlockingStub;

    SagaItemClient(SagaSdkConfig config) throws SagaException {
        this(config, null);
    }

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

    public SagaItem getItem(String inventoryId) throws SagaException {
        var request = GetItemRequest.newBuilder()
                .setInventoryId(inventoryId)
                .build();

        try {
            var item = serviceBlockingStub.getItem(request);
            ValidateUtil.checkFound(item, String.format("Item %s not found", request.getInventoryId()));
            return SagaItem.fromProto(item);
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        }
    }

    public List<SagaItem> getItems(Finalized finalized,
                                   String tokenName,
                                   int pageSize,
                                   SortOrder sortOrder,
                                   Instant createdAtCursor) throws SagaException {
        var request = GetItemsRequest.newBuilder()
                .setQueryOptions(CommonFactory.toProto(pageSize, sortOrder, createdAtCursor))
                .setFinalized(finalized);

        if (StringUtils.isNotBlank(tokenName)) {
            request.setTokenName(tokenName);
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

    public List<SagaItem> getItemsForPlayer(String oauthId,
                                            int pageSize,
                                            SortOrder sortOrder,
                                            Instant createdAtCursor) throws SagaException {
        ValidateUtil.notBlank(oauthId, "oauthId is a required value");
        var request = GetItemsForPlayerRequest.newBuilder()
                .setQueryOptions(CommonFactory.toProto(pageSize, sortOrder, createdAtCursor))
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

    public String issueItem(List<SagaIssueItem> issueItems,
                            String recipientOauthId,
                            String itemTypeId) throws SagaException {
        var items = issueItems.stream().map(SagaIssueItem::toProto).collect(Collectors.toList());
        var builder = IssueItemRequest.newBuilder()
                .addAllItems(items)
                .setItemTypeId(itemTypeId);

        if (StringUtils.isNotBlank(recipientOauthId)) {
            builder.setRecipientOauthId(recipientOauthId);
        }

        try {
            var receivedResponse = serviceBlockingStub.issueItem(builder.build());
            return receivedResponse.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception on issueItem, item may be lost!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }

    public String transferItem(String inventoryId, String destOauthId, Boolean prefundGas) throws SagaException {
        var request = TransferItemRequest.newBuilder()
                .setInventoryId(inventoryId)
                .setDestinationOauthId(destOauthId)
                .setPrefundGas(prefundGas)
                .build();

        try {
            var receivedResponse = serviceBlockingStub.transferItem(request);
            return receivedResponse.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on transferItem, item may be out of sync!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }

    public String transferItemBulk(String idempotencyId, String destOauthId, List<String> inventoryIds) throws SagaException {
        var request = TransferItemBulkRequest.newBuilder()
                .setIdempotencyId(idempotencyId)
                .setDestinationOauthId(destOauthId)
                .addAllInventoryIds(inventoryIds)
                .build();

        try {
            var receivedResponse = serviceBlockingStub.transferItemBulk(request);
            return receivedResponse.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on transferBulkItem, items may be out of sync!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }

    public String burnItem(String inventoryId, Boolean prefundGas) throws SagaException {
        var request = BurnItemRequest.newBuilder()
                .setInventoryId(inventoryId)
                .setPrefundGas(prefundGas)
                .build();

        try {
            var receivedResponse = serviceBlockingStub.burnItem(request);
            return receivedResponse.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on burnItem, item may be out of sync!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }

    public String depositItem(String inventoryId,
                              String createdBy,
                              String fromAddress,
                              String toAddress,
                              BlockChains fromChain,
                              String transactionId) throws SagaException {
        var request = DepositItemRequest.newBuilder()
                .setInventoryId(inventoryId)
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
}
