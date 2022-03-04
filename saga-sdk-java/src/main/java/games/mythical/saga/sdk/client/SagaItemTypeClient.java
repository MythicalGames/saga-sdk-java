package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.SagaItemTypeExecutor;
import games.mythical.saga.sdk.client.model.SagaItemType;
import games.mythical.saga.sdk.client.model.SagaMetadata;
import games.mythical.saga.sdk.client.observer.SagaItemTypeObserver;
import games.mythical.saga.sdk.exception.SagaErrorCode;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.api.itemtype.*;
import games.mythical.saga.sdk.proto.streams.Subscribe;
import games.mythical.saga.sdk.proto.streams.itemtype.ItemTypeStreamGrpc;
import io.grpc.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SagaItemTypeClient extends AbstractSagaClient {
    private final SagaItemTypeExecutor executor;
    private ItemTypeServiceGrpc.ItemTypeServiceBlockingStub serviceBlockingStub;

    public SagaItemTypeClient(SagaItemTypeExecutor executor) throws SagaException {
        super();

        this.executor = executor;
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .keepAliveTime(keepAlive, TimeUnit.SECONDS)
                .build();
        initStub();
    }

    SagaItemTypeClient(SagaItemTypeExecutor executor, ManagedChannel channel) throws SagaException {
        this.executor = executor;
        this.channel = channel;
        initStub();
    }

    @Override
    void initStub() {
        serviceBlockingStub = ItemTypeServiceGrpc.newBlockingStub(channel).withCallCredentials(addAuthentication());
        var streamBlockingStub = ItemTypeStreamGrpc.newBlockingStub(channel)
                .withCallCredentials(addAuthentication());
        subscribeToStream(new SagaItemTypeObserver(executor, streamBlockingStub, this::subscribeToStream));
    }

    void subscribeToStream(SagaItemTypeObserver observer) {
        // set up server stream
        var streamStub = ItemTypeStreamGrpc.newStub(channel).withCallCredentials(addAuthentication());
        var subscribe = Subscribe.newBuilder()
                .setEnvironmentId(environmentId)
                .build();

        streamStub.itemTypeStatusStream(subscribe, observer);
    }

    public Optional<SagaItemType> getItemType(String gameItemTypeId) throws SagaException {
        var request = GetItemTypeRequest.newBuilder()
                .setEnvironmentId(environmentId)
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

    public List<SagaItemType> getItemTypes() throws SagaException {
        return getItemTypes(List.of());
    }

    public List<SagaItemType> getItemTypes(Collection<String> gameItemTypeIds) throws SagaException {
        var builder = GetItemTypesRequest.newBuilder()
                .setEnvironmentId(environmentId);

        if (!gameItemTypeIds.isEmpty()) {
            builder.addAllGameItemTypeIds(gameItemTypeIds);
        }

        try {
            var result = serviceBlockingStub.getItemTypes(builder.build());
            return SagaItemType.fromProto(result.getItemTypesList());
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        }
    }

    public void createItemType(String gameItemTypeId,
                               boolean withdrawable,
                               SagaMetadata metadata) throws SagaException {
        try {
            log.trace("ItemTypeClient.createItemType called for game item type id: {}", gameItemTypeId);
            var request = CreateItemTypeRequest.newBuilder()
                    .setTitleId(environmentId)
                    .setGameItemTypeId(gameItemTypeId)
                    .setWithdrawable(withdrawable)
                    .setMetadata(SagaMetadata.toProto(metadata))
                    .build();
            var result = serviceBlockingStub.createItemType(request);
            executor.emitReceived(gameItemTypeId, result.getTraceId());
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
                    .setEnvironmentId(environmentId)
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
                    .setEnvironmentId(environmentId)
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
