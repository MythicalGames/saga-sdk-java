package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.SagaItemTypeExecutor;
import games.mythical.saga.sdk.client.model.SagaItemType;
import games.mythical.saga.sdk.client.observer.SagaStatusUpdateObserver;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.exception.SagaErrorCode;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.api.itemtype.*;
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
            throw SagaException.fromGrpcException2(e);
        }
    }

    public List<SagaItemType> getItemTypes(String gameTitleId, String publisherAddress) throws SagaException {
        var request = GetItemTypesRequest.newBuilder()
                .setGameTitleId(StringUtils.defaultString(gameTitleId))
                .setPublisherAddress(StringUtils.defaultString(publisherAddress))
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
                                 boolean randomize,
                                 Instant dateFinished,
                                 boolean withdrawable) throws SagaException {
        try {
            log.trace("ItemTypeClient.createItemType called for game item type id: {}", itemTypeId);
            var builder = CreateItemTypeRequest.newBuilder()
                    .setItemTypeId(itemTypeId)
                    .setName(name)
                    .setSymbol(symbol)
                    .setMaxSupply(maxSupply)
                    .setRandomize(randomize)
                    .setWithdrawable(withdrawable);

            if (dateFinished != null) {
                builder.setDateFinished(ConversionUtils.instantToProtoTimestamp(dateFinished));
            }

            var result = serviceBlockingStub.createItemType(builder.build());
            return result.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on createItemType, item type may be lost!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }

    public String updateItemType(String itemTypeId, boolean withdrawable) throws SagaException {
        try {
            log.trace("ItemTypeClient.updateItemType called for {}", itemTypeId);
            var request = UpdateItemTypePayload.newBuilder()
                    .setItemTypeId(itemTypeId)
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

    public String freezeItemType(String itemTypeId) throws SagaException {
        try {
            log.trace("ItemTypeClient.freezeItemType called for {}", itemTypeId);
            var request = FreezeItemTypePayload.newBuilder()
                    .setItemTypeId(itemTypeId)
                    .build();
            var result = serviceBlockingStub.freezeItemType(request);
            return result.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling freezeItemType!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }
}
