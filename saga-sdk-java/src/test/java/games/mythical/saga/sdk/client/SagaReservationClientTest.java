package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.MockReservationExecutor;
import games.mythical.saga.sdk.client.model.SagaMetadata;
import games.mythical.saga.sdk.client.model.SagaRedeemItem;
import games.mythical.saga.sdk.proto.api.reservation.ReservationServiceGrpc;
import games.mythical.saga.sdk.proto.common.ReceivedResponse;
import games.mythical.saga.sdk.proto.streams.StatusUpdate;
import games.mythical.saga.sdk.proto.streams.reservation.ReservationRedeemedProto;
import games.mythical.saga.sdk.proto.streams.reservation.ReservationReleasedProto;
import games.mythical.saga.sdk.proto.streams.reservation.ReservationUpdate;
import games.mythical.saga.sdk.server.MockServer;
import games.mythical.saga.sdk.server.stream.MockStatusStreamingImpl;
import games.mythical.saga.sdk.util.ConcurrentFinisher;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SagaReservationClientTest  extends AbstractClientTest {

    private final MockReservationExecutor executor = new MockReservationExecutor();
    private MockServer server;
    private SagaReservationClient client;

    @Mock
    private ReservationServiceGrpc.ReservationServiceBlockingStub blockingStub;

    @BeforeEach
    void setUp() throws Exception {
        server = new MockServer(new MockStatusStreamingImpl());
        server.start();
        port = server.getPort();

        client = setUpFactory().createSagaReservationClient(executor);
        FieldUtils.writeField(client, "serviceBlockingStub", blockingStub, true);
    }

    @AfterEach
    void tearDown() throws Exception {
        client.stop();
        Thread.sleep(500);
        server.stop();
    }

    @Test
    public void redeemReservation() throws Exception {
        final var expectedResponse = ReceivedResponse.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(blockingStub.redeemReservation(any())).thenReturn(expectedResponse);

        final var reservationId = RandomStringUtils.randomAlphanumeric(10);
        final var oauthId = RandomStringUtils.randomAlphanumeric(10);
        final var item = SagaRedeemItem.builder()
                .itemTypeId(RandomStringUtils.randomAlphanumeric(10))
                .inventoryId(UUID.randomUUID().toString())
                .metadata(SagaMetadata.builder()
                        .name(RandomStringUtils.randomAlphanumeric(10))
                        .description(RandomStringUtils.randomAlphanumeric(10))
                        .image(RandomStringUtils.randomAlphanumeric(10))
                        .build())
                .build();

        final var traceId = client.redeemReservation(reservationId, oauthId, Collections.singletonList(item));

        checkTraceAndStart(expectedResponse, traceId);

        final var update = ReservationUpdate.newBuilder()
                .setReservationRedeemed(ReservationRedeemedProto.newBuilder().setReservationId(reservationId).build()).build();
        server.getStatusStream().sendStatus(titleId, StatusUpdate.newBuilder()
                .setTraceId(traceId)
                .setReservationUpdate(update)
                .build());

        ConcurrentFinisher.wait(traceId);

        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertEquals(reservationId, executor.getReservationId());

        server.verifyCalls("StatusStream", 1);
        server.verifyCalls("StatusConfirmation", 1);
    }

    @Test
    public void releaseReservation() throws Exception {
        final var expectedResponse = ReceivedResponse.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(blockingStub.releaseReservation(any())).thenReturn(expectedResponse);

        final var reservationId = RandomStringUtils.randomAlphanumeric(10);
        final var itemTypeId = RandomStringUtils.randomAlphanumeric(10);
        final var traceId = client.releaseReservation(reservationId, itemTypeId);

        checkTraceAndStart(expectedResponse, traceId);

        final var update = ReservationUpdate.newBuilder()
                .setReservationReleased(ReservationReleasedProto.newBuilder().setReservationId(reservationId).build()).build();
        server.getStatusStream().sendStatus(titleId, StatusUpdate.newBuilder()
                .setTraceId(traceId)
                .setReservationUpdate(update)
                .build());

        ConcurrentFinisher.wait(traceId);

        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertEquals(reservationId, executor.getReservationId());

        server.verifyCalls("StatusStream", 1);
        server.verifyCalls("StatusConfirmation", 1);
    }
}
