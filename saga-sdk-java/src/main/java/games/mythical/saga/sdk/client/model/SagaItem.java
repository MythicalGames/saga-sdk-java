package games.mythical.saga.sdk.client.model;

import games.mythical.saga.sdk.proto.api.item.ItemProto;
import games.mythical.saga.sdk.proto.common.item.ItemState;
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
public class SagaItem {
    private String traceId;
    private String gameInventoryId;
    private String gameItemTypeId;
    private String oauthId;
    private int serialNumber;
    private String metadataUri;
    @DtoExclude
    private SagaMetadata metadata;
    private ItemState itemState;
    @DtoExclude
    private Instant createdTimestamp;
    @DtoExclude
    private Instant updatedTimestamp;

    public static SagaItem fromProto(ItemProto proto) {
        var user = ProtoUtil.toDto(proto, SagaItem.class);

        if (proto.hasMetadata()) {
            user.setMetadata(SagaMetadata.fromProto(proto.getMetadata()));
        }

        var createdTimestamp = Instant.ofEpochMilli(proto.getCreatedTimestamp());
        user.setCreatedTimestamp(createdTimestamp);

        var updatedTimestamp = Instant.ofEpochMilli(proto.getUpdatedTimestamp());
        user.setUpdatedTimestamp(updatedTimestamp);

        return user;
    }
}
