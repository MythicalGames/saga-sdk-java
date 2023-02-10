package games.mythical.saga.sdk.client.model;

import games.mythical.proto_util.ProtoUtil;
import games.mythical.proto_util.dto.DtoExclude;
import games.mythical.saga.sdk.proto.api.itemtype.ItemTypeProto;
import games.mythical.saga.sdk.util.ConversionUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaItemType {
    private String traceId;
    private String itemTypeId;
    private String name;
    private String symbol;
    private long maxSupply;
    private long issuedSupply;
    private long totalSupply;
    @DtoExclude
    private Long availableSupply;
    private String contractAddress;
    private String blockExplorerUrl;
    private boolean finalized;
    private boolean withdrawable;
    @DtoExclude
    private Instant createdAt;
    @DtoExclude
    private Instant updatedAt;
    private boolean mintable;
    private boolean mintEnded;
    private boolean randomize;
    private String mintMode;

    public static SagaItemType fromProto(ItemTypeProto proto) {
        var itemType = ProtoUtil.toDto(proto, SagaItemType.class);

        var createdAt = ConversionUtils.protoTimestampToInstant(proto.getCreatedAt());
        itemType.setCreatedAt(createdAt);

        var updatedAt = ConversionUtils.protoTimestampToInstant(proto.getUpdatedAt());
        itemType.setUpdatedAt(updatedAt);

        if (proto.hasAvailableSupply()) {
            itemType.setAvailableSupply(proto.getAvailableSupply().getValue());
        }

        return itemType;
    }

    public static List<SagaItemType> fromProto(List<ItemTypeProto> protos) {
        return protos.stream()
                .map(SagaItemType::fromProto)
                .collect(Collectors.toList());
    }
}
