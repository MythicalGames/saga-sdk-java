package games.mythical.saga.sdk.server.itemtype;

import games.mythical.saga.sdk.client.model.IVIItemType;
import games.mythical.saga.sdk.client.model.IVIMetadata;
import games.mythical.saga.sdk.exception.IVIException;
import games.mythical.ivi.sdk.proto.api.itemtype.*;
import games.mythical.ivi.sdk.proto.common.itemtype.ItemTypeState;
import io.grpc.stub.StreamObserver;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class MockItemTypeServiceImpl extends ItemTypeServiceGrpc.ItemTypeServiceImplBase {
    private final Map<String, ItemType> itemTypes = new ConcurrentHashMap<>();

    @Override
    public void createItemType(CreateItemTypeRequest request, StreamObserver<CreateItemAsyncResponse> responseObserver) {
        var response = CreateItemAsyncResponse.newBuilder()
                .setGameItemTypeId(RandomStringUtils.randomAlphanumeric(20, 50))
                .setTrackingId(RandomStringUtils.randomAlphanumeric(30))
                .setItemTypeState(ItemTypeState.PENDING_CREATE)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getItemTypes(GetItemTypesRequest request, StreamObserver<ItemTypes> responseObserver) {
        var result = new ArrayList<ItemType>();
        if(request.getGameItemTypeIdsList().isEmpty()) {
            result = new ArrayList<>(itemTypes.values());
        } else {
            for (var itemTypeId : request.getGameItemTypeIdsList()) {
                if(itemTypes.containsKey(itemTypeId)) {
                    result.add(itemTypes.get(itemTypeId));
                }
            }
        }

        responseObserver.onNext(ItemTypes.newBuilder().addAllItemTypes(result).build());
        responseObserver.onCompleted();
    }

    @Override
    public void freezeItemType(FreezeItemTypeRequest request, StreamObserver<FreezeItemTypeAsyncResponse> responseObserver) {
        var response = FreezeItemTypeAsyncResponse.newBuilder()
                .setTrackingId(RandomStringUtils.randomAlphanumeric(30))
                .setItemTypeState(ItemTypeState.PENDING_FREEZE)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    public void setItemTypes(Collection<IVIItemType> itemTypes) throws IVIException {
        for (var itemType : itemTypes) {
            this.itemTypes.putIfAbsent(itemType.getGameItemTypeId(), fromIVI(itemType));
        }
    }

    public void reset() {
        itemTypes.clear();
    }

    private ItemType fromIVI(IVIItemType itemType) throws IVIException {
        return ItemType.newBuilder()
                .setGameItemTypeId(itemType.getGameItemTypeId())
                .setMaxSupply(itemType.getMaxSupply())
                .setCurrentSupply(itemType.getCurrentSupply())
                .setIssuedSupply(itemType.getIssuedSupply())
                .setIssueTimeSpan(itemType.getIssueTimeSpan())
                .setCategory(itemType.getCategory())
                .setTokenName(itemType.getTokenName())
                .setFungible(itemType.isFungible())
                .setBurnable(itemType.isBurnable())
                .setTransferable(itemType.isTransferable())
                .setFinalized(itemType.isFinalized())
                .setSellable(itemType.isSellable())
                .setBaseUri(itemType.getBaseUri())
                .addAllAgreementIds(itemType.getAgreementIds().stream().map(UUID::toString).collect(Collectors.toList()))
                .setTrackingId(itemType.getTrackingId())
                .setMetadata(IVIMetadata.toProto(itemType.getMetadata()))
                .setCreatedTimestamp(itemType.getCreatedTimestamp().getEpochSecond())
                .setUpdatedTimestamp(itemType.getUpdatedTimestamp().getEpochSecond())
                .setItemTypeState(itemType.getItemTypeState())
                .build();
    }
}
