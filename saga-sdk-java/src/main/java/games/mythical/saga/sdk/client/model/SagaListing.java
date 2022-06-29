package games.mythical.saga.sdk.client.model;

import games.mythical.proto_util.ProtoUtil;
import games.mythical.proto_util.dto.DtoExclude;
import games.mythical.saga.sdk.proto.api.listing.ListingProto;
import games.mythical.saga.sdk.util.ConversionUtils;
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
    private Instant createdAt;

    public static SagaListing fromProto(ListingProto proto) {
        var listing = ProtoUtil.toDto(proto, SagaListing.class);

        var createdAt = ConversionUtils.protoTimestampToInstant(proto.getCreatedAt());
        listing.setCreatedAt(createdAt);


        return listing;
    }
}
