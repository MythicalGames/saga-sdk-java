package games.mythical.saga.sdk.client.observer;

import com.google.protobuf.Struct;
import com.google.protobuf.Value;
import games.mythical.saga.sdk.client.executor.*;
import games.mythical.saga.sdk.client.model.SagaItem;
import games.mythical.saga.sdk.client.model.SagaItemUpdate;
import games.mythical.saga.sdk.exception.ErrorData;
import games.mythical.saga.sdk.exception.SagaErrorCode;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.exception.SubError;
import games.mythical.saga.sdk.proto.streams.StatusUpdate;
import games.mythical.saga.sdk.proto.streams.currency.CurrencyUpdate;
import games.mythical.saga.sdk.proto.streams.item.ItemUpdate;
import games.mythical.saga.sdk.proto.streams.itemtype.ItemTypeUpdate;
import games.mythical.saga.sdk.proto.streams.metadata.MetadataUpdate;
import games.mythical.saga.sdk.proto.streams.playerwallet.PlayerWalletUpdate;
import games.mythical.saga.sdk.proto.streams.reservation.ReservationUpdate;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Slf4j
public final class SagaStatusUpdateObserver extends AbstractObserver<StatusUpdate> {
    private static final String PING_TRACE = "ping";
    private static SagaStatusUpdateObserver instance;
    private final Consumer<SagaStatusUpdateObserver> resubscribe;
    private SagaCurrencyExecutor sagaCurrencyExecutor;
    private SagaItemExecutor sagaItemExecutor;
    private SagaItemTypeExecutor sagaItemTypeExecutor;
    private SagaPlayerWalletExecutor sagaPlayerWalletExecutor;
    private SagaReservationExecutor sagaReservationExecutor;
    private SagaMetadataExecutor sagaMetadataExecutor;

    public SagaStatusUpdateObserver(Consumer<SagaStatusUpdateObserver> resubscribe) {
        this.resubscribe = resubscribe;
    }

    public static SagaStatusUpdateObserver getInstance() {
        return instance;
    }

    public static SagaStatusUpdateObserver initialize(Consumer<SagaStatusUpdateObserver> resubscribe) {
        instance = new SagaStatusUpdateObserver(resubscribe);
        return instance;
    }

    public static void clear() {
        instance = null;
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

    public SagaStatusUpdateObserver with(SagaPlayerWalletExecutor sagaPlayerWalletExecutor) {
        this.sagaPlayerWalletExecutor = sagaPlayerWalletExecutor;
        return this;
    }

    public SagaStatusUpdateObserver with(SagaReservationExecutor sagaReservationExecutor) {
        this.sagaReservationExecutor = sagaReservationExecutor;
        return this;
    }

    public SagaStatusUpdateObserver with(SagaMetadataExecutor sagaMetadataExecutor) {
        this.sagaMetadataExecutor = sagaMetadataExecutor;
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
                case CURRENCY_UPDATE:
                    handleCurrencyUpdate(message.getCurrencyUpdate(), message.getTraceId());
                    break;
                case ITEM_UPDATE:
                    handleItemUpdate(message.getItemUpdate(), message.getTraceId());
                    break;
                case METADATA_UPDATE:
                    handleMetadataUpdate(message.getMetadataUpdate(), message.getTraceId());
                    break;
                case ITEM_TYPE_UPDATE:
                    handleItemTypeUpdate(message.getItemTypeUpdate(), message.getTraceId());
                    break;
                case PLAYER_WALLET_UPDATE:
                    handlePlayerWalletUpdate(message.getPlayerWalletUpdate(), message.getTraceId());
                    break;
                case RESERVATION_UPDATE:
                    handleReservationUpdate(message.getReservationUpdate(), message.getTraceId());
                    break;
                default: {
                    log.error("Unrecognized event {}", message.getStatusUpdateCase());
                    throw new SagaException(SagaErrorCode.UNRECOGNIZED);
                }
            }
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

    private void handleCurrencyUpdate(CurrencyUpdate update, String traceId) throws Exception {
        if (sagaCurrencyExecutor == null) {
            log.debug("Currency update received, but no currency executor registered {}", update);
        }
        else {
            if (update.hasError()) {
                final var error = update.getError();
                sagaCurrencyExecutor.onError(toErrData(error));
            } else {
                final var message = update.getStatusUpdate();
                sagaCurrencyExecutor.updateCurrency(
                    message.getCurrencyTypeId(),
                    message.getAmount(),
                    message.getOauthId(),
                    traceId,
                    message.getCurrencyState()
                );
            }
        }
    }

    private void handleItemUpdate(ItemUpdate update, String traceId) throws Exception {
        if (sagaItemExecutor == null) {
            log.debug("Item update received, but no item executor registered {}", update);
        }
        else {
            if (update.hasError()) {
                final var error = update.getError();
                sagaItemExecutor.onError(toErrData(error));
            } else if (update.hasStatusUpdate()) {
                final var message = update.getStatusUpdate();
                sagaItemExecutor.updateItem(
                    message.getInventoryId(),
                    message.getItemTypeId(),
                    message.getOauthId(),
                    message.getTokenId(),
                    message.getMetadataUrl(),
                    traceId,
                    message.getItemState()
                );
            } else {
                final var updates = update.getStatusUpdates().getStatusUpdatesList().stream()
                        .map(SagaItemUpdate::fromProto).collect(Collectors.toList());
                sagaItemExecutor.updateItems(updates, traceId);
            }
        }
    }

    private void handleMetadataUpdate(MetadataUpdate update, String traceId) throws Exception {
        if (sagaMetadataExecutor == null) {
            log.debug("Metadata update received, but no metadata executor registered {}", update);
        }
        else {
            if (update.hasError()) {
                final var error = update.getError();
                sagaItemExecutor.onError(toErrData(error));
            } else {
                final var message = update.getMetadataUpdated();
                sagaMetadataExecutor.updateMetadata(
                        message.getInventoryId(),
                        message.getMetadataUrl(),
                        traceId
                );
            }
        }
    }

    private void handleItemTypeUpdate(ItemTypeUpdate update, String traceId) throws Exception {
        if (sagaItemTypeExecutor == null) {
            log.debug("Item type update received, but no item type executor registered {}", update);
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

    private void handlePlayerWalletUpdate(PlayerWalletUpdate update, String traceId) throws Exception {
        if (sagaPlayerWalletExecutor == null) {
            log.debug("Player wallet update received, but no player wallet executor registered {}", update);
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

    private void handleReservationUpdate(ReservationUpdate update, String traceId) {
        if (sagaReservationExecutor == null) {
            log.debug("Reservation update received, but no reservation executor registered {}", update);
        } else {
            switch (update.getUpdateCase()) {
                case ERROR:
                    sagaReservationExecutor.onError(toErrData(update.getError()));
                    break;
                case RESERVATION_CREATED:
                    sagaReservationExecutor.onReservationCreated(update.getReservationCreated().getReservationId(), traceId);
                    break;
                case RESERVATION_RELEASED:
                    sagaReservationExecutor.onReservationReleased(update.getReservationReleased().getReservationId(), traceId);
                    break;
                case RESERVATION_REDEEMED:
                    final var items = update.getReservationRedeemed().getItemsList().stream()
                            .map(SagaItem::fromProto).collect(Collectors.toList());
                    sagaReservationExecutor.onReservationRedeemed(update.getReservationRedeemed().getReservationId(), items, traceId);
                    break;
                default:
                    log.error("Unknown reservation update: {}", update.getUpdateCase());
                    break;
            }
        }
    }

    private Map<String, String> toMetadata(Struct protoMetadata) {
        final var metadataMap = new HashMap<String, String>();
        protoMetadata.getFieldsMap()
            .forEach((key, value) -> metadataMap.put(key, metadataValueToString(value)));
        return metadataMap;
    }

    private String metadataValueToString(Value value) {
        if (value.hasNullValue()) {
            return null;
        } else if (value.hasStringValue()) {
            return value.getStringValue();
        }
        return value.toString();
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
