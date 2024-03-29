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
import games.mythical.saga.sdk.util.ConversionUtils;
import games.mythical.saga.sdk.util.ValidateUtil;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.util.List;

@Slf4j
public class SagaItemTypeClient extends AbstractSagaStreamClient {
    private final SagaItemTypeExecutor executor;
    private ItemTypeServiceGrpc.ItemTypeServiceBlockingStub serviceBlockingStub;

    SagaItemTypeClient(SagaSdkConfig config) throws SagaException {
        this(config, null);
    }

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

    public SagaItemType getItemType(String itemTypeId) throws SagaException {
        var request = GetItemTypeRequest.newBuilder()
                .setItemTypeId(itemTypeId)
                .build();

        try {
            var item = serviceBlockingStub.getItemType(request);
            ValidateUtil.checkFound(item, String.format("Unable to find item %s", request.getItemTypeId()));
            return SagaItemType.fromProto(item);
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        }
    }

    public List<SagaItemType> getItemTypes(String gameTitleId,
                                           String publisherAddress,
                                           int pageSize,
                                           SortOrder sortOrder,
                                           Instant createdAtCursor) throws SagaException {
        var request = GetItemTypesRequest.newBuilder()
                .setGameTitleId(StringUtils.defaultString(gameTitleId))
                .setPublisherAddress(StringUtils.defaultString(publisherAddress))
                .setQueryOptions(CommonFactory.toProto(pageSize, sortOrder, createdAtCursor))
                .build();

        try {
            var result = serviceBlockingStub.getItemTypes(request);
            return SagaItemType.fromProto(result.getItemTypesList());
        } catch (StatusRuntimeException e) {
            if (e.getStatus() == Status.NOT_FOUND) {
                return List.of();
            }
            throw SagaException.fromGrpcException(e);
        }
    }

    public String createItemType(String itemTypeId,
                                 String name,
                                 String symbol,
                                 int maxSupply,
                                 Instant dateFinished,
                                 MintMode mintMode) throws SagaException {
        try {
            if(itemTypeId.isBlank()) {
                var msg = "itemTypeId cannot be empty";
                log.error(msg);
                throw new SagaException(SagaErrorCode.PARSING_DATA_EXCEPTION, msg);
            }
            log.trace("ItemTypeClient.createItemType called for game item type id: {}", itemTypeId);
            var builder = CreateItemTypeRequest.newBuilder()
                    .setItemTypeId(itemTypeId)
                    .setName(name)
                    .setSymbol(symbol)
                    .setMaxSupply(maxSupply)
                     .setMintMode(mintMode);

            if (dateFinished != null) {
                builder.setDateFinished(ConversionUtils.instantToProtoTimestamp(dateFinished));
            }

            var result = serviceBlockingStub.createItemType(builder.build());
            return result.getTraceId();
        } catch (SagaException e) {
            throw e;
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on createItemType, item type may be lost!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }

    public String startMint(String itemTypeId) throws SagaException {
        try  {
            log.trace("ItemTypeClient.startMint called for {}", itemTypeId);
            var request = StartMintRequest.newBuilder().setItemTypeId(itemTypeId).build();
            var result = serviceBlockingStub.startMint(request);
            return result.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling startMint", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }

    public String endMint(String itemTypeId) throws SagaException {
        try  {
            log.trace("ItemTypeClient.endMint called for {}", itemTypeId);
            var request = EndMintRequest.newBuilder().setItemTypeId(itemTypeId).build();
            var result = serviceBlockingStub.endMint(request);
            return result.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling endMint", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }
}
