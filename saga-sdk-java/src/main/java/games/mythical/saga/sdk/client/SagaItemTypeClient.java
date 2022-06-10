package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.SagaItemTypeExecutor;
import games.mythical.saga.sdk.client.model.SagaItemType;
import games.mythical.saga.sdk.client.observer.SagaStatusUpdateObserver;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.exception.SagaErrorCode;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.factory.CommonFactory;
import games.mythical.saga.sdk.proto.api.itemtype.*;
import games.mythical.saga.sdk.proto.common.SortOrder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Slf4j
public class SagaItemTypeClient extends AbstractSagaStreamClient {
    private final SagaItemTypeExecutor executor;
    private ItemTypeServiceGrpc.ItemTypeServiceBlockingStub serviceBlockingStub;

    SagaItemTypeClient(SagaSdkConfig config, SagaItemTypeExecutor executor) throws SagaException {
        super(config);
        this.executor = executor;
        initStub();
    }

    @Override
    void initStub() {
        serviceBlockingStub = ItemTypeServiceGrpc.newBlockingStub(channel).withCallCredentials(addAuthentication());
        initStreamStub();
        SagaStatusUpdateObserver.getInstance().with(executor);
    }

    public Optional<SagaItemType> getItemType(String gameItemTypeId) throws SagaException {
        var request = GetItemTypeRequest.newBuilder()
                .setGameItemTypeId(gameItemTypeId)
                .build();

        try {
            var item = serviceBlockingStub.getItemType(request);
            return Optional.of(SagaItemType.fromProto(item));
        } catch (StatusRuntimeException e) {
            if (e.getStatus() == Status.NOT_FOUND) {
                return Optional.empty();
            }

            throw SagaException.fromGrpcException(e);
        }
    }

    public List<SagaItemType> getItemTypes(String gameTitleId,
                                           String publisherAddress,
                                           int pageSize,
                                           SortOrder sortOrder,
                                           Instant createdAtCursor) throws SagaException {
        var request = GetItemTypesRequest.newBuilder()
                .setGameTitleId(gameTitleId)
                .setPublisherAddress(publisherAddress)
                .setQueryOptions(CommonFactory.toProto(pageSize, sortOrder, createdAtCursor))
                .build();

        try {
            var result = serviceBlockingStub.getItemTypes(request);
            return SagaItemType.fromProto(result.getItemTypesList());
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        }
    }

    public String createItemType(String gameItemTypeId,
                                 BigDecimal basePrice,
                                 String name,
                                 String symbol,
                                 int maxSupply) throws SagaException {
        try {
            log.trace("ItemTypeClient.createItemType called for game item type id: {}", gameItemTypeId);
            var request = CreateItemTypeRequest.newBuilder()
                    .setGameItemTypeId(gameItemTypeId)
                    .setBasePrice(basePrice.toString())
                    .setName(name)
                    .setSymbol(symbol)
                    .setMaxSupply(maxSupply)
                    .build();
            var result = serviceBlockingStub.createItemType(request);
            return result.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on createItemType, item type may be lost!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }

    public String updateItemType(String gameItemTypeId, boolean withdrawable) throws SagaException {
        try {
            log.trace("ItemTypeClient.updateItemType called for {}", gameItemTypeId);
            var request = UpdateItemTypePayload.newBuilder()
                    .setGameItemTypeId(gameItemTypeId)
                    .setWithdrawable(withdrawable)
                    .build();
            var result = serviceBlockingStub.updateItemType(request);
            return result.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on updateItemType, item type may be lost!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }
}
