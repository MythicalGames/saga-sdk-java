package games.mythical.saga.sdk.client.model;

import games.mythical.proto_util.ProtoUtil;
import games.mythical.proto_util.dto.DtoExclude;
import games.mythical.saga.sdk.proto.api.item.ItemProto;
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
public class SagaItem {
    private String traceId;
    private String inventoryId;
    private String oauthId;
    private long tokenId;
    private boolean finalized;
    private String blockExplorerUrl;
    private String metadataUrl;
    @DtoExclude
    private Instant createdAt;
    @DtoExclude
    private Instant updatedAt;

    public static SagaItem fromProto(ItemProto proto) {
        var user = ProtoUtil.toDto(proto, SagaItem.class);

        var createdAt = ConversionUtils.protoTimestampToInstant(proto.getCreatedAt());
        user.setCreatedAt(createdAt);

        var updatedAt = ConversionUtils.protoTimestampToInstant(proto.getUpdatedAt());
        user.setUpdatedAt(updatedAt);

        return user;
    }
}
