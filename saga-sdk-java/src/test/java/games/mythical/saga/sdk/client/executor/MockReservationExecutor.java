package games.mythical.saga.sdk.client.executor;

import lombok.Getter;

@Getter
public class MockReservationExecutor extends MockBaseExecutor implements SagaReservationExecutor {
    private String traceId;
    private String reservationId;

    @Override
    public void onReservationCreated(String reservationId, String traceId) {
        this.reservationId = reservationId;
        this.traceId = traceId;
    }

    @Override
    public void onReservationRedeemed(String reservationId, String traceId) {
        this.reservationId = reservationId;
        this.traceId = traceId;
    }

    @Override
    public void onReservationReleased(String reservationId, String traceId) {
        this.reservationId = reservationId;
        this.traceId = traceId;
    }
}
