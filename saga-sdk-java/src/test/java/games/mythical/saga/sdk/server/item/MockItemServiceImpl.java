package games.mythical.saga.sdk.server.item;

import games.mythical.saga.sdk.client.model.SagaObject;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.api.item.*;
import games.mythical.saga.sdk.proto.common.Finalized;
import games.mythical.saga.sdk.proto.common.item.ItemState;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class MockItemServiceImpl extends ItemServiceGrpc.ItemServiceImplBase {
    private final Map<String, SagaObject> items = new ConcurrentHashMap<>();

    @Override
    public void issueItem(IssueItemRequest request, StreamObserver<IssueItemStartedResponse> responseObserver) {
        var response = IssueItemStartedResponse.newBuilder()
                .setTrackingId(RandomStringUtils.randomAlphanumeric(30))
                .setItemState(ItemState.PENDING_ISSUED)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getItem(GetItemRequest request, StreamObserver<Item> responseObserver) {
        if (items.containsKey(request.getGameInventoryId())) {
            try {
                var item = items.get(request.getGameInventoryId());
                responseObserver.onNext(toProto(item));
                responseObserver.onCompleted();
            } catch (SagaException e) {
                log.error("Couldn't convert item!", e);
                responseObserver.onError(e);
            }
        } else {
            responseObserver.onError(Status.NOT_FOUND.asException());
        }
    }

    @Override
    public void getItems(GetItemsRequest request, StreamObserver<Items> responseObserver) {
        try {
            var result = new ArrayList<Item>();

            ItemState state;
            if (request.getFinalized().equals(Finalized.YES) || request.getFinalized().equals(Finalized.ALL)) {
                state = ItemState.ISSUED;
            } else {
                state = ItemState.PENDING_ISSUED;
            }

            for (var item : items.values()) {
//                if (item.getItemState().equals(state)) {
                result.add(toProto(item));
//                }
            }

            responseObserver.onNext(Items.newBuilder().addAllItems(result).build());
            responseObserver.onCompleted();
        } catch (SagaException e) {
            log.error("Couldn't convert item!", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void getItemsForPlayer(GetItemsForPlayerRequest request, StreamObserver<Items> responseObserver) {
        try {
            var result = new ArrayList<Item>();
            for (var item : items.values()) {
//                if (item.getPlayerId().equals(request.getPlayerId())) {
                result.add(toProto(item));
//                }
            }

            responseObserver.onNext(Items.newBuilder().addAllItems(result).build());
            responseObserver.onCompleted();
        } catch (SagaException e) {
            log.error("Couldn't convert item!", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void transferItem(TransferItemRequest request, StreamObserver<TransferItemStartedResponse> responseObserver) {
        if (!items.containsKey(request.getGameItemInventoryId())) {
            responseObserver.onError(Status.NOT_FOUND.asException());
            return;
        }

        try {
            var newItemBuilder = toProto(items.get(request.getGameItemInventoryId())).toBuilder();
            newItemBuilder.setPlayerId(request.getDestinationPlayerId());
            items.put(request.getGameItemInventoryId(), SagaObject.fromProto(newItemBuilder.build()));

            var response = TransferItemStartedResponse.newBuilder()
                    .setTrackingId(RandomStringUtils.randomAlphanumeric(30))
                    .setItemState(ItemState.PENDING_TRANSFERRED)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (SagaException e) {
            log.error("Couldn't convert item!", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void burnItem(BurnItemRequest request, StreamObserver<BurnItemStartedResponse> responseObserver) {
        try {
            var newItemBuilder = toProto(items.get(request.getGameItemInventoryId())).toBuilder();
            newItemBuilder.setItemState(ItemState.PENDING_BURNED);
            items.put(request.getGameItemInventoryId(), SagaObject.fromProto(newItemBuilder.build()));

            var response = BurnItemStartedResponse.newBuilder()
                    .setTrackingId(RandomStringUtils.randomAlphanumeric(30))
                    .setItemState(ItemState.PENDING_BURNED)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (SagaException e) {
            log.error("Couldn't convert item!", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void updateItemMetadata(UpdateItemMetadataRequest request, StreamObserver<UpdateItemMetadataResponse> responseObserver) {
        var updateMap = new HashMap<String, UpdateItemMetadata>();
        for (var updateItem : request.getUpdateItemsList()) {
            var gameInventoryId = updateItem.getGameInventoryId();
            if (!items.containsKey(gameInventoryId)) {
                responseObserver.onError(Status.NOT_FOUND.asException());
                return;
            }

            updateMap.put(updateItem.getGameInventoryId(), updateItem);
        }

//        try {
//            for (var gameInventoryId : updateMap.keySet()) {
//                var item = items.get(gameInventoryId);
//                item.setMetadata(IVIMetadata.fromProto(updateMap.get(gameInventoryId).getMetadata()));
//            }
//
//            responseObserver.onNext(UpdateItemMetadataResponse.newBuilder().build());
//            responseObserver.onCompleted();
//        } catch (SagaException e) {
//            log.error("Couldn't convert item!", e);
//            responseObserver.onError(e);
//        }
    }

    public void setItems(Collection<SagaObject> items) {
        for (var item : items) {
            this.items.putIfAbsent("temp", item);
        }
    }

    public void reset() {
        items.clear();
    }

    private Item toProto(SagaObject item) throws SagaException {
        return Item.newBuilder()
                .setGameInventoryId("temp")
                .build();
    }
}
