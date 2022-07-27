package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.SagaItemExecutor;
import games.mythical.saga.sdk.client.model.SagaItem;
import games.mythical.saga.sdk.client.model.SagaMetadata;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public String issueItem(Map<String, SagaMetadata> inventoryIdToMetadata,
                            String recipientOauthId,
                            String itemTypeId) throws SagaException {
        var items = new ArrayList<IssueItemProto>();
        for (var entry : inventoryIdToMetadata.entrySet()) {
            items.add(IssueItemProto.newBuilder()
                    .setInventoryId(entry.getKey())
                    .setMetadata(SagaMetadata.toProto(entry.getValue()))
                    .build());
        }
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

    public String transferItem(String inventoryId, String destOauthId) throws SagaException {
        var request = TransferItemRequest.newBuilder()
                .setInventoryId(inventoryId)
                .setDestinationOauthId(destOauthId)
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

    public String burnItem(String inventoryId) throws SagaException {
        var request = BurnItemRequest.newBuilder()
                .setInventoryId(inventoryId)
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

    public String updateItemMetadata(String inventoryId, SagaMetadata metadata) throws SagaException {
        try {
            var request = UpdateItemMetadataRequest.newBuilder()
                    .setInventoryId(inventoryId)
                    .setMetadata(SagaMetadata.toProto(metadata))
                    .build();

            var response = serviceBlockingStub.updateItemMetadata(request);
            return response.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        }
    }
}
