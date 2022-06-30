package games.mythical.saga.sdk.client.observer;

import com.google.protobuf.Empty;
import com.google.protobuf.Struct;
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
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.function.Consumer;

@Slf4j
public final class SagaStatusUpdateObserver extends AbstractObserver<StatusUpdate> {
    private static final String PING_TRACE = "ping";
    private static SagaStatusUpdateObserver instance;
    private final StatusStreamGrpc.StatusStreamStub statusStreamStub;
    private final Consumer<SagaStatusUpdateObserver> resubscribe;
    private final ConfirmationObserver confirmationObserver = new ConfirmationObserver();

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

    public SagaStatusUpdateObserver(StatusStreamGrpc.StatusStreamStub statusStreamStub,
                                    Consumer<SagaStatusUpdateObserver> resubscribe) {
        this.statusStreamStub = statusStreamStub;
        this.resubscribe = resubscribe;
    }

    public static SagaStatusUpdateObserver getInstance() {
        return instance;
    }

    public static SagaStatusUpdateObserver initialize(StatusStreamGrpc.StatusStreamStub statusStreamStub,
                                                      Consumer<SagaStatusUpdateObserver> resubscribe) {
        instance = new SagaStatusUpdateObserver(statusStreamStub, resubscribe);
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

    @Override
    public void onNext(StatusUpdate message) {
        log.trace("StatusUpdateObserver.onNext for event {} with message {}", message.getStatusUpdateCase(), message.getTraceId());
        resetConnectionRetry();
        if (message.getTraceId().equalsIgnoreCase(PING_TRACE)) {
            return;
        }
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
        if (sagaBridgeExecutor == null) {
            log.error("Bridge update received, but no bridge executor registered {}", update);
        }
        else {
            if (update.hasError()) {
                final var error = update.getError();
                sagaBridgeExecutor.onError(toErrData(error));
            } else {
                final var message = update.getStatusUpdate();
                sagaBridgeExecutor.updateItem(
                    message.getOauthId(),
                    message.getGameInventoryId(),
                    message.getItemTypeId(),
                    message.getDestinationAddress(),
                    message.getDestinationChain(),
                    message.getOriginAddress(),
                    message.getMythicalTransactionId(),
                    message.getMainnetTransactionId(),
                    traceId
                );
            }
        }
    }

    private void handleCurrencyUpdate(CurrencyUpdate update, String traceId) throws Exception {
        if (sagaCurrencyExecutor == null) {
            log.error("Currency update received, but no currency executor registered {}", update);
        }
        else {
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
    }

    private void handleItemUpdate(ItemUpdate update, String traceId) throws Exception {
        if (sagaItemExecutor == null) {
            log.error("Item update received, but no item executor registered {}", update);
        }
        else {
            if (update.hasError()) {
                final var error = update.getError();
                sagaItemExecutor.onError(toErrData(error));
            } else {
                final var message = update.getStatusUpdate();
                sagaItemExecutor.updateItem(
                    message.getGameInventoryId(),
                    message.getItemTypeId(),
                    message.getOauthId(),
                    message.getSerialNumber(),
                    message.getMetadataUri(),
                    traceId,
                    message.getItemState()
                );
            }
        }
    }

    private void handleItemTypeUpdate(ItemTypeUpdate update, String traceId) throws Exception {
        if (sagaItemTypeExecutor == null) {
            log.error("Item type update received, but no item type executor registered {}", update);
        }
        else {
            if (update.hasError()) {
                final var error = update.getError();
                sagaItemTypeExecutor.onError(toErrData(error));
            } else {
                final var message = update.getStatusUpdate();
                sagaItemTypeExecutor.updateItemType(
                    message.getItemTypeId(),
                    traceId,
                    message.getItemTypeState()
                );
            }
        }
    }

    private void handleListingUpdate(ListingUpdate update, String traceId) throws Exception {
        if (sagaListingExecutor == null) {
            log.error("Listing update received, but no listing executor registered {}", update);
        }
        else {
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
    }

    private void handleMythTokenUpdate(MythTokenUpdate update, String traceId) throws Exception {
        if (sagaMythTokenExecutor == null) {
            log.error("Myth token update received, but no myth token executor registered {}", update);
        }
        else {
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
    }

    private void handleOfferUpdate(OfferUpdate update, String traceId) throws Exception {
        if (sagaOfferExecutor == null) {
            log.error("Offer update received, but no offer executor registered {}", update);
        }
        else {
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
    }

    private void handleOrderUpdate(OrderUpdate update, String traceId) throws Exception {
        if (sagaOrderExecutor == null) {
            log.error("Order update received, but no order executor registered {}", update);
        }
        else {
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
    }

    private void handlePaymentUpdate(PaymentUpdate update, String traceId) throws Exception {
        if (sagaPaymentExecutor == null) {
            log.error("Payment update received, but no payment executor registered {}", update);
        }
        else {
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
    }

    private void handlePlayerWalletUpdate(PlayerWalletUpdate update, String traceId) throws Exception {
        if (sagaPlayerWalletExecutor == null) {
            log.error("Player wallet update received, but no player wallet executor registered {}", update);
        }
        else {
            if (update.hasError()) {
                final var error = update.getError();
                sagaPlayerWalletExecutor.onError(toErrData(error));
            } else {
                final var statusUpdate = update.getStatusUpdate();
                sagaPlayerWalletExecutor.updatePlayerWallet(traceId, statusUpdate.getOauthId());
            }
        }
    }

    private void updateStatusConfirmation(String traceId) {
        var request = StatusConfirmRequest.newBuilder()
                .setTraceId(traceId)
                .build();
        //noinspection ResultOfMethodCallIgnored
        statusStreamStub.statusConfirmation(request, confirmationObserver);
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

    // Doesn't need to do anything since confirmations are just for logging on the gateway.
    private static class ConfirmationObserver extends AbstractObserver<Empty> {

        @Override
        public void onNext(Empty value) {
            // no-op
        }

        @Override
        public void onError(Throwable t) {
            // no-op
        }

        @Override
        public void onCompleted() {
            // no-op
        }
    }
}
