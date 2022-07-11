package games.mythical.saga.sdk.client.executor;

public interface SagaReservationExecutor extends BaseSagaExecutor {
    void onReservationCreated(String reservationId, String traceId);
}
