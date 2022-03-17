package games.mythical.saga.sdk.client.observer;

import games.mythical.saga.sdk.client.executor.*;
import games.mythical.saga.sdk.exception.SagaErrorCode;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.streams.StatusConfirmRequest;
import games.mythical.saga.sdk.proto.streams.StatusStreamGrpc;
import games.mythical.saga.sdk.proto.streams.StatusUpdate;
import games.mythical.saga.sdk.proto.streams.bridge.BridgeStatusUpdate;
import games.mythical.saga.sdk.proto.streams.gamecoin.GameCoinStatusUpdate;
import games.mythical.saga.sdk.proto.streams.item.ItemStatusUpdate;
import games.mythical.saga.sdk.proto.streams.itemtype.ItemTypeStatusUpdate;
import games.mythical.saga.sdk.proto.streams.listing.ListingStatusUpdate;
import games.mythical.saga.sdk.proto.streams.myth.MythTokenStatusUpdate;
import games.mythical.saga.sdk.proto.streams.offer.OfferStatusUpdate;
import games.mythical.saga.sdk.proto.streams.order.OrderStatusUpdate;
import games.mythical.saga.sdk.proto.streams.user.UserStatusUpdate;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.function.Consumer;

@Slf4j
public final class SagaStatusUpdateObserver extends AbstractObserver<StatusUpdate> {
    private static SagaStatusUpdateObserver instance;
    private final StatusStreamGrpc.StatusStreamBlockingStub streamBlockingStub;
    private final Consumer<SagaStatusUpdateObserver> resubscribe;

    private SagaBridgeExecutor sagaBridgeExecutor;
    private SagaGameCoinExecutor sagaGameCoinExecutor;
    private SagaItemExecutor sagaItemExecutor;
    private SagaItemTypeExecutor sagaItemTypeExecutor;
    private SagaListingExecutor sagaListingExecutor;
    private SagaMythTokenExecutor sagaMythTokenExecutor;
    private SagaOfferExecutor sagaOfferExecutor;
    private SagaOrderExecutor sagaOrderExecutor;
    private SagaUserExecutor sagaUserExecutor;

    public SagaStatusUpdateObserver(StatusStreamGrpc.StatusStreamBlockingStub streamBlockingStub,
                                    Consumer<SagaStatusUpdateObserver> resubscribe) {
        this.streamBlockingStub = streamBlockingStub;
        this.resubscribe = resubscribe;
    }

    public static SagaStatusUpdateObserver getInstance() {
        return instance;
    }

    public static SagaStatusUpdateObserver initialize(StatusStreamGrpc.StatusStreamBlockingStub streamBlockingStub,
                                                      Consumer<SagaStatusUpdateObserver> resubscribe) {
        instance = new SagaStatusUpdateObserver(streamBlockingStub, resubscribe);
        return instance;
    }

    public static void clear() {
        instance = null;
    }

    public SagaStatusUpdateObserver with(SagaBridgeExecutor sagaBridgeExecutor) {
        this.sagaBridgeExecutor = sagaBridgeExecutor;
        return this;
    }

    public SagaStatusUpdateObserver with(SagaGameCoinExecutor sagaGameCoinExecutor) {
        this.sagaGameCoinExecutor = sagaGameCoinExecutor;
        return this;
    }

    public SagaStatusUpdateObserver with(SagaItemExecutor sagaItemExecutor) {
        this.sagaItemExecutor = sagaItemExecutor;
        return this;
    }

    public SagaStatusUpdateObserver with(SagaItemTypeExecutor sagaItemTypeExecutor) {
        this.sagaItemTypeExecutor = sagaItemTypeExecutor;
        return this;
    }

    public SagaStatusUpdateObserver with(SagaListingExecutor sagaListingExecutor) {
        this.sagaListingExecutor = sagaListingExecutor;
        return this;
    }

    public SagaStatusUpdateObserver with(SagaMythTokenExecutor sagaMythTokenExecutor) {
        this.sagaMythTokenExecutor = sagaMythTokenExecutor;
        return this;
    }

    public SagaStatusUpdateObserver with(SagaOfferExecutor sagaOfferExecutor) {
        this.sagaOfferExecutor = sagaOfferExecutor;
        return this;
    }

    public SagaStatusUpdateObserver with(SagaOrderExecutor sagaOrderExecutor) {
        this.sagaOrderExecutor = sagaOrderExecutor;
        return this;
    }

    public SagaStatusUpdateObserver with(SagaUserExecutor sagaUserExecutor) {
        this.sagaUserExecutor = sagaUserExecutor;
        return this;
    }

    @Override
    public void onNext(StatusUpdate message) {
        log.trace("StatusUpdateObserver.onNext for event {} with message {}", message.getStatusUpdateCase(), message.getTraceId());
        resetConnectionRetry();
        try {
            switch (message.getStatusUpdateCase()) {
                case BRIDGE_STATUS:
                    handleBridgeUpdate(message.getBridgeStatus(), message.getTraceId());
                    break;
                case GAME_COIN_STATUS:
                    handleGameCoinUpdate(message.getGameCoinStatus(), message.getTraceId());
                    break;
                case ITEM_STATUS:
                    handleItemUpdate(message.getItemStatus(), message.getTraceId());
                    break;
                case ITEM_TYPE_STATUS:
                    handleItemTypeUpdate(message.getItemTypeStatus(), message.getTraceId());
                    break;
                case LISTING_STATUS:
                    handleListingUpdate(message.getListingStatus(), message.getTraceId());
                    break;
                case MYTH_TOKEN_STATUS:
                    handleMythTokenUpdate(message.getMythTokenStatus(), message.getTraceId());
                    break;
                case OFFER_STATUS:
                    handleOfferUpdate(message.getOfferStatus(), message.getTraceId());
                    break;
                case ORDER_STATUS:
                    handleOrderUpdate(message.getOrderStatus(), message.getTraceId());
                    break;
                case USER_STATUS:
                    handleUserUpdate(message.getUserStatus(), message.getTraceId());
                    break;
                default: {
                    log.error("Unrecognized event {}", message.getStatusUpdateCase());
                    throw new SagaException(SagaErrorCode.UNRECOGNIZED);
                }
            }
            updateStatusConfirmation(message.getTraceId());
        } catch (Exception e) {
            log.error("Exception calling executor action for message:{}. {}", message.getTraceId(), e);
        }
    }

    @Override
    public void onError(Throwable t) {
        log.error("StatusUpdateObserver.onError", t);
        sleepBetweenReconnects();
        resubscribe.accept(this);
    }

    @Override
    public void onCompleted() {
        log.info("StatusUpdateObserver stream closed");
        sleepBetweenReconnects();
        resubscribe.accept(this);
    }

    private void handleBridgeUpdate(BridgeStatusUpdate message, String traceId) throws Exception {
        sagaBridgeExecutor.updateItem(
                message.getOauthId(),
                message.getGameInventoryId(),
                message.getGameItemTypeId(),
                message.getDestinationAddress(),
                message.getDestinationChain(),
                message.getOriginAddress(),
                message.getMythicalTransactionId(),
                message.getMainnetTransactionId(),
                traceId
        );
    }

    private void handleGameCoinUpdate(GameCoinStatusUpdate message, String traceId) throws Exception {
        sagaGameCoinExecutor.updateGameCoin(
                message.getCurrencyId(),
                message.getCoinCount(),
                message.getOauthId(),
                traceId,
                message.getGameCoinState()
        );
    }

    private void handleItemUpdate(ItemStatusUpdate message, String traceId) throws Exception {
        sagaItemExecutor.updateItem(
                message.getGameInventoryId(),
                message.getGameItemTypeId(),
                message.getOauthId(),
                message.getSerialNumber(),
                message.getMetadataUri(),
                traceId,
                message.getItemState()
        );
    }

    private void handleItemTypeUpdate(ItemTypeStatusUpdate message, String traceId) throws Exception {
        sagaItemTypeExecutor.updateItemType(
                message.getGameItemTypeId(),
                traceId,
                message.getItemTypeState()
        );
    }

    private void handleListingUpdate(ListingStatusUpdate message, String traceId) throws Exception {
        sagaListingExecutor.updateListing(
                message.getOauthId(),
                traceId,
                message.getQuoteId(),
                message.getListingId(),
                new BigDecimal(message.getTotal()),
                message.getListingState()
        );
    }

    private void handleMythTokenUpdate(MythTokenStatusUpdate message, String traceId) throws Exception {
        sagaMythTokenExecutor.updateMythToken(
                traceId,
                message.getTokenState()
        );
    }

    private void handleOfferUpdate(OfferStatusUpdate message, String traceId) throws Exception {
        sagaOfferExecutor.updateOffer(
                message.getOauthId(),
                traceId,
                message.getQuoteId(),
                message.getOfferId(),
                new BigDecimal(message.getTotal()),
                message.getOfferState()
        );
    }

    private void handleOrderUpdate(OrderStatusUpdate message, String traceId) throws Exception {
        sagaOrderExecutor.updateOrder(
                message.getOauthId(),
                traceId,
                message.getQuoteId(),
                message.getOrderId(),
                new BigDecimal(message.getTotal()),
                message.getOrderState()
        );
    }

    private void handleUserUpdate(UserStatusUpdate message, String traceId) throws Exception {
        sagaUserExecutor.updateUser(message.getOauthId(), traceId);
    }

    private void updateStatusConfirmation(String traceId) {
        var request = StatusConfirmRequest.newBuilder()
                .setTraceId(traceId)
                .build();
        //noinspection ResultOfMethodCallIgnored
        streamBlockingStub.statusConfirmation(request);
    }
}
