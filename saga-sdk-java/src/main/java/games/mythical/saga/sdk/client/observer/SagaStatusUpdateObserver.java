package games.mythical.saga.sdk.client.observer;

import com.google.protobuf.Struct;
import games.mythical.proto_util.ProtoUtil;
import games.mythical.saga.sdk.client.executor.*;
import games.mythical.saga.sdk.client.model.SagaPaymentMethod;
import games.mythical.saga.sdk.exception.ErrorData;
import games.mythical.saga.sdk.exception.SagaErrorCode;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.exception.SubError;
import games.mythical.saga.sdk.proto.streams.StatusConfirmRequest;
import games.mythical.saga.sdk.proto.streams.StatusStreamGrpc;
import games.mythical.saga.sdk.proto.streams.StatusUpdate;
import games.mythical.saga.sdk.proto.streams.bridge.BridgeUpdate;
import games.mythical.saga.sdk.proto.streams.currency.CurrencyUpdate;
import games.mythical.saga.sdk.proto.streams.item.ItemUpdate;
import games.mythical.saga.sdk.proto.streams.itemtype.ItemTypeUpdate;
import games.mythical.saga.sdk.proto.streams.listing.ListingUpdate;
import games.mythical.saga.sdk.proto.streams.myth.MythTokenUpdate;
import games.mythical.saga.sdk.proto.streams.offer.OfferUpdate;
import games.mythical.saga.sdk.proto.streams.order.OrderUpdate;
import games.mythical.saga.sdk.proto.streams.payment.PaymentUpdate;
import games.mythical.saga.sdk.proto.streams.playerwallet.PlayerWalletUpdate;
import games.mythical.saga.sdk.proto.streams.user.UserUpdate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.function.Consumer;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public final class SagaStatusUpdateObserver extends AbstractObserver<StatusUpdate> {
    private static SagaStatusUpdateObserver instance;
    private final StatusStreamGrpc.StatusStreamBlockingStub streamBlockingStub;
    private final Consumer<SagaStatusUpdateObserver> resubscribe;

    private SagaBridgeExecutor sagaBridgeExecutor;
    private SagaCurrencyExecutor sagaCurrencyExecutor;
    private SagaItemExecutor sagaItemExecutor;
    private SagaItemTypeExecutor sagaItemTypeExecutor;
    private SagaListingExecutor sagaListingExecutor;
    private SagaMythTokenExecutor sagaMythTokenExecutor;
    private SagaOfferExecutor sagaOfferExecutor;
    private SagaOrderExecutor sagaOrderExecutor;
    private SagaPaymentExecutor sagaPaymentExecutor;
    private SagaPlayerWalletExecutor sagaPlayerWalletExecutor;
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

    public SagaStatusUpdateObserver with(SagaCurrencyExecutor sagaCurrencyExecutor) {
        this.sagaCurrencyExecutor = sagaCurrencyExecutor;
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

    public SagaStatusUpdateObserver with(SagaPaymentExecutor sagaPaymentExecutor) {
        this.sagaPaymentExecutor = sagaPaymentExecutor;
        return this;
    }

    public SagaStatusUpdateObserver with(SagaPlayerWalletExecutor sagaPlayerWalletExecutor) {
        this.sagaPlayerWalletExecutor = sagaPlayerWalletExecutor;
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
                case BRIDGE_UPDATE:
                    handleBridgeUpdate(message.getBridgeUpdate(), message.getTraceId());
                    break;
                case CURRENCY_UPDATE:
                    handleCurrencyUpdate(message.getCurrencyUpdate(), message.getTraceId());
                    break;
                case ITEM_UPDATE:
                    handleItemUpdate(message.getItemUpdate(), message.getTraceId());
                    break;
                case ITEM_TYPE_UPDATE:
                    handleItemTypeUpdate(message.getItemTypeUpdate(), message.getTraceId());
                    break;
                case LISTING_UPDATE:
                    handleListingUpdate(message.getListingUpdate(), message.getTraceId());
                    break;
                case MYTH_TOKEN_UPDATE:
                    handleMythTokenUpdate(message.getMythTokenUpdate(), message.getTraceId());
                    break;
                case OFFER_UPDATE:
                    handleOfferUpdate(message.getOfferUpdate(), message.getTraceId());
                    break;
                case ORDER_UPDATE:
                    handleOrderUpdate(message.getOrderUpdate(), message.getTraceId());
                    break;
                case PAYMENT_UPDATE:
                    handlePaymentUpdate(message.getPaymentUpdate(), message.getTraceId());
                    break;
                case PLAYER_WALLET_UPDATE:
                    handlePlayerWalletUpdate(message.getPlayerWalletUpdate(), message.getTraceId());
                    break;
                case USER_UPDATE:
                    handleUserUpdate(message.getUserUpdate(), message.getTraceId());
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

    private void handleBridgeUpdate(BridgeUpdate update, String traceId) throws Exception {
        if (update.hasError()) {
            final var error = update.getError();
            sagaBridgeExecutor.onError(toErrData(error));
        } else {
            final var message = update.getStatusUpdate();
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
    }

    private void handleCurrencyUpdate(CurrencyUpdate update, String traceId) throws Exception {
        if (update.hasError()) {
            final var error = update.getError();
            sagaCurrencyExecutor.onError(toErrData(error));
        } else {
            final var message = update.getStatusUpdate();
            sagaCurrencyExecutor.updateCurrency(
                    message.getGameCurrencyTypeId(),
                    message.getQuantity(),
                    message.getOwnerAddress(),
                    traceId,
                    message.getCurrencyState()
            );
        }
    }

    private void handleItemUpdate(ItemUpdate update, String traceId) throws Exception {
        if (update.hasError()) {
            final var error = update.getError();
            sagaItemExecutor.onError(toErrData(error));
        } else {
            final var message = update.getStatusUpdate();
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
    }

    private void handleItemTypeUpdate(ItemTypeUpdate update, String traceId) throws Exception {
        if (update.hasError()) {
            final var error = update.getError();
            sagaItemTypeExecutor.onError(toErrData(error));
        } else {
            final var message = update.getStatusUpdate();
            sagaItemTypeExecutor.updateItemType(
                    message.getGameItemTypeId(),
                    traceId,
                    message.getItemTypeState()
            );
        }
    }

    private void handleListingUpdate(ListingUpdate update, String traceId) throws Exception {
        if (update.hasError()) {
            final var error = update.getError();
            sagaListingExecutor.onError(toErrData(error));
        } else {
            final var message = update.getStatusUpdate();
            sagaListingExecutor.updateListing(
                    message.getOauthId(),
                    traceId,
                    message.getQuoteId(),
                    message.getListingId(),
                    new BigDecimal(message.getTotal()),
                    message.getListingState()
            );
        }
    }

    private void handleMythTokenUpdate(MythTokenUpdate update, String traceId) throws Exception {
        if (update.hasError()) {
            final var error = update.getError();
            sagaMythTokenExecutor.onError(toErrData(error));
        } else {
            final var message = update.getStatusUpdate();
            sagaMythTokenExecutor.updateMythToken(
                    traceId,
                    message.getTokenState()
            );
        }
    }

    private void handleOfferUpdate(OfferUpdate update, String traceId) throws Exception {
        if (update.hasError()) {
            final var error = update.getError();
            sagaOfferExecutor.onError(toErrData(error));
        } else {
            final var message = update.getStatusUpdate();
            sagaOfferExecutor.updateOffer(
                    message.getOauthId(),
                    traceId,
                    message.getQuoteId(),
                    message.getOfferId(),
                    new BigDecimal(message.getTotal()),
                    message.getOfferState()
            );
        }
    }

    private void handleOrderUpdate(OrderUpdate update, String traceId) throws Exception {
        if (update.hasError()) {
            final var error = update.getError();
            sagaOrderExecutor.onError(toErrData(error));
        } else {
            final var message = update.getStatusUpdate();
            sagaOrderExecutor.updateOrder(
                    message.getOauthId(),
                    traceId,
                    message.getQuoteId(),
                    message.getOrderId(),
                    new BigDecimal(message.getTotal()),
                    message.getOrderState()
            );
        }
    }

    private void handlePaymentUpdate(PaymentUpdate update, String traceId) throws Exception {
        if (update.hasError()) {
            final var error = update.getError();
            sagaPaymentExecutor.onError(toErrData(error));
        } else {
            final var message = update.getStatusUpdate();
            sagaPaymentExecutor.updatePaymentMethod(
                    traceId,
                    SagaPaymentMethod.fromProto(message.getPaymentMethod()),
                    message.getPaymentMethodStatus()
            );
        }
    }

    private void handlePlayerWalletUpdate(PlayerWalletUpdate update, String traceId) throws Exception {
        if (update.hasError()) {
            final var error = update.getError();
            sagaPlayerWalletExecutor.onError(toErrData(error));
        } else {
            final var statusUpdate = update.getStatusUpdate();
            sagaPlayerWalletExecutor.updatePlayerWallet(
                    traceId,
                    statusUpdate.getOauthId(),
                    statusUpdate.getAddress(),
                    statusUpdate.getState()
            );
        }
    }

    private void handleUserUpdate(UserUpdate update, String traceId) throws Exception {
        if (update.hasError()) {
            final var error = update.getError();
            sagaUserExecutor.onError(toErrData(error));
        } else {
            sagaUserExecutor.updateUser(update.getStatusUpdate().getOauthId(), traceId);
        }
    }

    private void updateStatusConfirmation(String traceId) {
        var request = StatusConfirmRequest.newBuilder()
                .setTraceId(traceId)
                .build();
        //noinspection ResultOfMethodCallIgnored
        streamBlockingStub.statusConfirmation(request);
    }

    private Map<String, String> toMetadata(Struct protoMetadata) {
        final var metadataMap = new HashMap<String, String>();
        protoMetadata.getFieldsMap()
            .forEach((key, value) -> metadataMap.put(key, value.toString()));
        return metadataMap;
    }
    private SubError toSubError(games.mythical.saga.sdk.proto.common.SubError error) {
        return SubError.builder()
            .code(error.getErrorCode())
            .message(error.getMessage())
            .source(error.getSource())
            .metadata(toMetadata(error.getMetadata()))
            .build();
    }

    private ErrorData toErrData(games.mythical.saga.sdk.proto.common.ErrorData error) {
        final var subErrors = error.getSuberrorsList().stream()
            .map(this::toSubError)
            .collect(Collectors.toList());

        return ErrorData.builder()
            .code(error.getErrorCode())
            .trace(error.getTrace())
            .source(error.getSource())
            .message(error.getMessage())
            .metadata(toMetadata(error.getMetadata()))
            .suberrors(subErrors)
            .build();
    }
}
