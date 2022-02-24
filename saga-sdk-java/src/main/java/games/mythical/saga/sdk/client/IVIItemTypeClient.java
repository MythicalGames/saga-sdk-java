package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.IVIItemTypeExecutor;
import games.mythical.saga.sdk.client.model.IVIItemType;
import games.mythical.saga.sdk.client.model.IVIMetadata;
import games.mythical.saga.sdk.client.observer.IVIItemTypeObserver;
import games.mythical.saga.sdk.exception.IVIErrorCode;
import games.mythical.saga.sdk.exception.IVIException;
import games.mythical.ivi.sdk.proto.api.itemtype.*;
import games.mythical.ivi.sdk.proto.streams.Subscribe;
import games.mythical.ivi.sdk.proto.streams.itemtype.ItemTypeStatusStreamGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusException;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
public class IVIItemTypeClient extends AbstractIVIClient {
    private ItemTypeServiceGrpc.ItemTypeServiceBlockingStub serviceBlockingStub;
    private final IVIItemTypeExecutor itemTypeExecutor;

    @SuppressWarnings("unused")
    public IVIItemTypeClient(IVIItemTypeExecutor itemTypeExecutor) throws IVIException {
        super();

        this.itemTypeExecutor = itemTypeExecutor;
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .keepAliveTime(keepAlive, TimeUnit.SECONDS)
                .build();
        initStub();
    }

    IVIItemTypeClient(IVIItemTypeExecutor itemTypeExecutor, ManagedChannel channel) throws IVIException {
        this.itemTypeExecutor = itemTypeExecutor;
        this.channel = channel;
        initStub();
    }

    @Override
    protected void initStub() {
        serviceBlockingStub = ItemTypeServiceGrpc.newBlockingStub(channel).withCallCredentials(addAuthentication());
        var streamBlockingStub = ItemTypeStatusStreamGrpc.newBlockingStub(channel)
                .withCallCredentials(addAuthentication());
        subscribeToStream(new IVIItemTypeObserver(itemTypeExecutor, streamBlockingStub, this::subscribeToStream));
    }

    void subscribeToStream(IVIItemTypeObserver observer) {
        // set up server stream
        var streamStub = ItemTypeStatusStreamGrpc.newStub(channel)
                .withCallCredentials(addAuthentication());
        var subscribe = Subscribe.newBuilder()
                .setEnvironmentId(environmentId)
                .build();
        streamStub.itemTypeStatusStream(subscribe, observer);
    }

    public Optional<IVIItemType> getItemType(String gameItemTypeId) throws IVIException {
        var result = getItemTypes(List.of(gameItemTypeId));
        if(result.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(result.get(0));
        }
    }

    public List<IVIItemType> getItemTypes() throws IVIException {
        return getItemTypes(Collections.emptyList());
    }

    public List<IVIItemType> getItemTypes(Collection<String> gameItemTypeIds) throws IVIException {
        var requestBuilder = GetItemTypesRequest.newBuilder()
                .setEnvironmentId(environmentId);

        if(!gameItemTypeIds.isEmpty()) {
            requestBuilder.addAllGameItemTypeIds(gameItemTypeIds);
        }

        var result = serviceBlockingStub.getItemTypes(requestBuilder.build());
        return IVIItemType.fromProto(result.getItemTypesList());
    }

    public void createItemType(String gameItemTypeId,
                               String tokenName,
                               String category,
                               int maxSupply,
                               int issueTimeSpan,
                               boolean burnable,
                               boolean transferable,
                               boolean sellable,
                               Collection<UUID> agreementIds,
                               IVIMetadata metadata) throws IVIException {
        try {
            log.trace("ItemTypeClient.createItemType called for game item type id: {} {}:{}", gameItemTypeId, tokenName, category);
            var request = CreateItemTypeRequest.newBuilder()
                    .setEnvironmentId(environmentId)
                    .setGameItemTypeId(gameItemTypeId)
                    .setTokenName(tokenName)
                    .setCategory(category)
                    .setMaxSupply(maxSupply)
                    .setIssueTimeSpan(issueTimeSpan)
                    .setBurnable(burnable)
                    .setTransferable(transferable)
                    .setSellable(sellable)
                    .addAllAgreementIds(agreementIds.stream().map(UUID::toString).collect(Collectors.toList()))
                    .setMetadata(IVIMetadata.toProto(metadata))
                    .build();
            var result = serviceBlockingStub.createItemType(request);
            itemTypeExecutor.updateItemTypeStatus(result.getGameItemTypeId(),
                    result.getTrackingId(),
                    result.getItemTypeState());
        } catch (IVIException e) {
            log.error("Error parsing metadata!", e);
            throw new IVIException(IVIErrorCode.PARSING_DATA_EXCEPTION);
        } catch (StatusRuntimeException e) {
            throw IVIException.fromGrpcException(e);
        } catch (StatusException e) {
            throw IVIException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling updateItemType on createItemType, item type will be in an invalid state!", e);
            throw new IVIException(IVIErrorCode.LOCAL_EXCEPTION);
        }
    }

    public void freezeItemType(String gameItemTypeId) throws IVIException {
        try {
            log.trace("ItemTypeClient.freezeItemType called for {}", gameItemTypeId);
            var request = FreezeItemTypeRequest.newBuilder()
                    .setEnvironmentId(environmentId)
                    .setGameItemTypeId(gameItemTypeId)
                    .build();
            var result = serviceBlockingStub.freezeItemType(request);
            itemTypeExecutor.updateItemTypeStatus(gameItemTypeId,
                    result.getTrackingId(),
                    result.getItemTypeState());
        } catch (StatusRuntimeException e) {
            throw IVIException.fromGrpcException(e);
        } catch (StatusException e) {
            throw IVIException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling updateItemType on createItemType, item type will be in an invalid state!", e);
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void updateItemTypeMetadata(String gameItemTypeId, IVIMetadata metadata) throws IVIException {
        log.trace("ItemTypeClient.updateItemTypeMetadata called for {}", gameItemTypeId);
        try {
            var request = UpdateItemTypeMetadataPayload.newBuilder()
                    .setEnvironmentId(environmentId)
                    .setGameItemTypeId(gameItemTypeId)
                    .setMetadata(IVIMetadata.toProto(metadata))
                    .build();
            serviceBlockingStub.updateItemTypeMetadata(request);
        } catch (StatusRuntimeException e) {
            throw IVIException.fromGrpcException(e);
        } catch (IVIException e) {
            log.error("Error parsing metadata!", e);
            throw new IVIException(IVIErrorCode.PARSING_DATA_EXCEPTION);
        }
    }
}

