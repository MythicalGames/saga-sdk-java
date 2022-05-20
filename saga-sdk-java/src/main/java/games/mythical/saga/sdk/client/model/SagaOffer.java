package games.mythical.saga.sdk.client.model;

import games.mythical.proto_util.ProtoUtil;
import games.mythical.proto_util.dto.DtoExclude;
import games.mythical.saga.sdk.proto.api.offer.OfferProto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaOffer {
    private String oauthId;
    private String titleId;
    private String gameInventoryId;
    private String currency;
    private String total;
    @DtoExclude
    private Instant createdTimestamp;

    public static SagaOffer fromProto(OfferProto proto) {
        var offer = ProtoUtil.toDto(proto, SagaOffer.class);

        var createdTimestamp = Instant.ofEpochMilli(proto.getCreatedAt());
        offer.setCreatedTimestamp(createdTimestamp);

        return offer;
    }

}
