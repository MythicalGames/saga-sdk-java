package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.SagaItemTypeExecutor;
import games.mythical.saga.sdk.client.model.SagaItemType;
import games.mythical.saga.sdk.client.model.SagaMetadata;
import games.mythical.saga.sdk.client.model.query.QueryOptions;
import games.mythical.saga.sdk.client.observer.SagaStatusUpdateObserver;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.exception.SagaErrorCode;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.api.itemtype.*;
import games.mythical.saga.sdk.proto.common.FilterConditional;
import games.mythical.saga.sdk.proto.streams.StatusStreamGrpc;
import games.mythical.saga.sdk.proto.streams.Subscribe;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
public class SagaItemTypeClient extends AbstractSagaClient {
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
        var streamBlockingStub = StatusStreamGrpc.newBlockingStub(channel)
                .withCallCredentials(addAuthentication());

        if (SagaStatusUpdateObserver.getInstance() == null) {
            subscribeToStream(SagaStatusUpdateObserver.initialize(streamBlockingStub, this::subscribeToStream));
        }
        SagaStatusUpdateObserver.getInstance().with(executor);
    }

    void subscribeToStream(SagaStatusUpdateObserver observer) {
        // set up server stream
        var streamStub = StatusStreamGrpc.newStub(channel).withCallCredentials(addAuthentication());
        var subscribe = Subscribe.newBuilder()
                .setTitleId(config.getTitleId())
                .build();

        streamStub.statusStream(subscribe, observer);
    }

    public Optional<SagaItemType> getItemType(String gameItemTypeId) throws SagaException {
        var request = GetItemTypeRequest.newBuilder()
                .setTitleId(config.getTitleId())
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

    public List<SagaItemType> getItemTypes(QueryOptions queryOptions) throws SagaException {
        if (queryOptions == null) {
            queryOptions = new QueryOptions();
        }

        // TODO set titleId to constant when defined
        queryOptions.addExpression("titleId", config.getTitleId(), FilterConditional.EQUALS, true);

        var builder = GetItemTypesRequest.newBuilder()
                .setQueryOptions(QueryOptions.toProto(queryOptions));

        try {
            var result = serviceBlockingStub.getItemTypes(builder.build());
            return SagaItemType.fromProto(result.getItemTypesList());
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        }
    }

    public String createItemType(String gameItemTypeId,
                               boolean withdrawable,
                               SagaMetadata metadata) throws SagaException {
        try {
            log.trace("ItemTypeClient.createItemType called for game item type id: {}", gameItemTypeId);
            var request = CreateItemTypeRequest.newBuilder()
                    .setTitleId(config.getTitleId())
                    .setGameItemTypeId(gameItemTypeId)
                    .setWithdrawable(withdrawable)
                    .setMetadata(SagaMetadata.toProto(metadata))
                    .build();
            var result = serviceBlockingStub.createItemType(request);
            executor.emitReceived(gameItemTypeId, result.getTraceId());
            return result.getTraceId();
        } catch (SagaException e) {
            log.error("Error parsing metadata!", e);
            throw new SagaException(SagaErrorCode.PARSING_DATA_EXCEPTION);
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (StatusException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on createItemType, item type may be lost!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }

    public void updateItemType(String gameItemTypeId, boolean withdrawable) throws SagaException {
        try {
            log.trace("ItemTypeClient.updateItemType called for {}", gameItemTypeId);
            var request = UpdateItemTypePayload.newBuilder()
                    .setTitleId(config.getTitleId())
                    .setGameItemTypeId(gameItemTypeId)
                    .setWithdrawable(withdrawable)
                    .build();
            serviceBlockingStub.updateItemType(request);
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling updateItemType ", e);
        }
    }

    public void updateItemTypeMetadata(String gameItemTypeId, SagaMetadata metadata) throws SagaException {
        log.trace("ItemTypeClient.updateItemTypeMetadata called for {}", gameItemTypeId);
        try {
            var request = UpdateItemTypeMetadataPayload.newBuilder()
                    .setTitleId(config.getTitleId())
                    .setGameItemTypeId(gameItemTypeId)
                    .setMetadata(SagaMetadata.toProto(metadata))
                    .build();
            serviceBlockingStub.updateItemTypeMetadata(request);
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (SagaException e) {
            log.error("Error parsing metadata!", e);
            throw new SagaException(SagaErrorCode.PARSING_DATA_EXCEPTION);
        }
    }
}
