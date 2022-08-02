package games.mythical.saga.sdk.client.executor;

public interface SagaReservationExecutor extends BaseSagaExecutor {
    void onReservationCreated(String reservationId, String traceId);
    void onReservationRedeemed(String reservationId, String traceId);
    void onReservationReleased(String reservationId, String traceId);
}
