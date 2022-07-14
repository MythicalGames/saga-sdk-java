package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.SagaReservationExecutor;
import games.mythical.saga.sdk.client.model.SagaItemReservation;
import games.mythical.saga.sdk.client.observer.SagaStatusUpdateObserver;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.exception.SagaErrorCode;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.api.reservation.CreateReservationRequest;
import games.mythical.saga.sdk.proto.api.reservation.ReservationServiceGrpc;
import io.grpc.StatusRuntimeException;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public class SagaReservationClient extends AbstractSagaStreamClient {
    private final SagaReservationExecutor executor;
    private ReservationServiceGrpc.ReservationServiceBlockingStub serviceBlockingStub;

    protected SagaReservationClient(SagaSdkConfig config, SagaReservationExecutor executor) throws SagaException {
        super(config);
        this.executor = executor;
        initStub();
    }

    @Override
    void initStub() {
        serviceBlockingStub = ReservationServiceGrpc.newBlockingStub(channel).withCallCredentials(addAuthentication());
        initStreamStub();
        SagaStatusUpdateObserver.getInstance().with(executor);
    }

    public String createReservation(String reservationId, String oauthId, List<SagaItemReservation> itemReservations) throws SagaException {
        if (StringUtils.isBlank(reservationId)) {
            throw new SagaException(SagaErrorCode.BAD_REQUEST, "Reservation ID is required");
        }
        if (StringUtils.isBlank(oauthId)) {
            throw new SagaException(SagaErrorCode.BAD_REQUEST, "OAuth ID is required");
        }
        if (itemReservations == null || itemReservations.isEmpty()) {
            throw new SagaException(SagaErrorCode.BAD_REQUEST, "At least one item reservation is required");
        }

        var request = CreateReservationRequest.newBuilder()
                .setReservationId(reservationId)
                .setOauthId(oauthId)
                .addAllItemReservations(itemReservations.stream().map(SagaItemReservation::toProto).collect(Collectors.toList()))
                .build();

        try {
            var response = serviceBlockingStub.createReservation(request);
            return response.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }
}
