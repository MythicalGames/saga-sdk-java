package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.client.model.SagaItem;

import java.util.List;

public interface SagaReservationExecutor extends BaseSagaExecutor {
    void onReservationCreated(String reservationId, String traceId);
    void onReservationRedeemed(String reservationId, List<SagaItem> items, String traceId);
    void onReservationReleased(String reservationId, String traceId);
}
