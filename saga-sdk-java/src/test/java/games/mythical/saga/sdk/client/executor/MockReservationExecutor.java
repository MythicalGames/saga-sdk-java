package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.client.model.SagaItem;
import lombok.Getter;

import java.util.List;

@Getter
public class MockReservationExecutor extends MockBaseExecutor implements SagaReservationExecutor {
    private String traceId;
    private String reservationId;
    private List<SagaItem> items;

    @Override
    public void onReservationCreated(String reservationId, String traceId) {
        this.reservationId = reservationId;
        this.traceId = traceId;
    }

    @Override
    public void onReservationRedeemed(String reservationId, List<SagaItem> items, String traceId) {
        this.reservationId = reservationId;
        this.items = items;
        this.traceId = traceId;
    }

    @Override
    public void onReservationReleased(String reservationId, String traceId) {
        this.reservationId = reservationId;
        this.traceId = traceId;
    }
}
