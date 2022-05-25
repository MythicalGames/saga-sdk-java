package games.mythical.saga.sdk.client.model;

import games.mythical.proto_util.ProtoUtil;
import games.mythical.proto_util.dto.DtoExclude;
import games.mythical.saga.sdk.proto.api.itemtype.ItemTypeProto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaItemType {
    private String id;
    private String traceId;
    private String gameItemTypeId;
    private String gameTitleId;
    private String publisherAddress;
    private BigDecimal basePrice;
    private String name;
    private String symbol;
    private long maxSupply;
    private String contractAddress;
    private boolean finalized;
    @DtoExclude
    private Instant createdAt;
    @DtoExclude
    private Instant updatedAt;

    public static SagaItemType fromProto(ItemTypeProto proto) {
        var user = ProtoUtil.toDto(proto, SagaItemType.class);

        var createdAt = Instant.ofEpochMilli(proto.getCreatedAt());
        user.setCreatedAt(createdAt);

        var updatedAt = Instant.ofEpochMilli(proto.getUpdatedAt());
        user.setUpdatedAt(updatedAt);

        return user;
    }

    public static List<SagaItemType> fromProto(List<ItemTypeProto> protos) {
        return protos.stream()
                .map(SagaItemType::fromProto)
                .collect(Collectors.toList());
    }
}
