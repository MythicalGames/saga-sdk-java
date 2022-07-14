package games.mythical.saga.sdk.client.model;

import games.mythical.proto_util.ProtoUtil;
import games.mythical.saga.sdk.proto.api.reservation.ItemReservationProto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SagaItemReservation {
    private String itemTypeId;
    private long count;

    public static SagaItemReservation fromProto(ItemReservationProto proto) {
        return ProtoUtil.toDto(proto, SagaItemReservation.class);
    }

    public static ItemReservationProto toProto(SagaItemReservation itemReservation) {
        return ProtoUtil.toProto(itemReservation, ItemReservationProto.class);
    }
}
