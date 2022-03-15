package games.mythical.saga.sdk.client.model;

import games.mythical.saga.sdk.proto.api.listing.ListingProto;
import games.mythical.shared.util.ProtoUtil;
import games.mythical.shared.util.dto.DtoExclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaListing {
    private String oauthId;
    private String titleId;
    private String gameInventoryId;
    private String currency;
    private String total;
    @DtoExclude
    private Instant createdTimestamp;

    public static SagaListing fromProto(ListingProto proto) {
        var listing = ProtoUtil.toDto(proto, SagaListing.class);

        var createdTimestamp = Instant.ofEpochMilli(proto.getCreatedTimestamp());
        listing.setCreatedTimestamp(createdTimestamp);


        return listing;
    }
}
