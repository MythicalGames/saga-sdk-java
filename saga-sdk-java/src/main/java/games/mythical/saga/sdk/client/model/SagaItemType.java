package games.mythical.saga.sdk.client.model;

import games.mythical.saga.sdk.proto.api.itemtype.ItemTypeProto;
import games.mythical.saga.sdk.proto.api.itemtype.PriRevShareSettings;
import games.mythical.saga.sdk.proto.api.itemtype.PriceMap;
import games.mythical.saga.sdk.proto.api.itemtype.SecRevShareSettings;
import games.mythical.saga.sdk.proto.common.itemtype.ItemTypeState;
import games.mythical.shared.util.ProtoUtil;
import games.mythical.shared.util.dto.DtoExclude;
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
    private String gameItemTypeId;
    private String name;
    private String address;
    private String titleId;
    // TODO: reminder these are proto objects
    private PriRevShareSettings priRevShareSettings;
    private SecRevShareSettings secRevShareSettings;
    private boolean withdrawable;
    private PriceMap priceMap;
    private ItemTypeState itemTypeState;
    @DtoExclude
    private Instant createdTimestamp;
    @DtoExclude
    private Instant updatedTimestamp;
    @DtoExclude
    private SagaMetadata metadata;

    public static SagaItemType fromProto(ItemTypeProto proto) {
        var user = ProtoUtil.toDto(proto, SagaItemType.class);

        if (proto.hasMetadata()) {
            user.setMetadata(SagaMetadata.fromProto(proto.getMetadata()));
        }

        var createdTimestamp = Instant.ofEpochMilli(proto.getCreatedTimestamp());
        user.setCreatedTimestamp(createdTimestamp);

        var updatedTimestamp = Instant.ofEpochMilli(proto.getUpdatedTimestamp());
        user.setUpdatedTimestamp(updatedTimestamp);

        return user;
    }

    public static List<SagaItemType> fromProto(List<ItemTypeProto> protos) {
        return protos.stream()
                .map(SagaItemType::fromProto)
                .collect(Collectors.toList());
    }
}
