package games.mythical.saga.sdk.client.model;

import com.google.protobuf.DoubleValue;
import games.mythical.proto_util.ProtoUtil;
import games.mythical.proto_util.dto.DtoExclude;
import games.mythical.proto_util.proto.ProtoExclude;
import games.mythical.saga.sdk.proto.common.MetadataAttribute;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SagaMetadataAttribute {
    @ProtoExclude
    @DtoExclude
    private Object value;
    private String traitType;
    private String displayType;
    @ProtoExclude
    @DtoExclude
    private Double maxValue;

    public static SagaMetadataAttribute fromProto(MetadataAttribute proto) {
        var builder = ProtoUtil.toDtoBuilder(proto, SagaMetadataAttribute.SagaMetadataAttributeBuilder.class);

        if (proto.hasStrValue()) {
            builder.value(proto.getStrValue());
        } else if (proto.hasIntValue()) {
            builder.value(proto.getIntValue());
        } else if (proto.hasDoubleValue()) {
            builder.value(proto.getDoubleValue());
        }

        if (proto.hasMaxValue()) {
            builder.maxValue(proto.getMaxValue().getValue());
        }

        return ProtoUtil.toDto(proto, SagaMetadataAttribute.class);
    }

    public static MetadataAttribute toProto(SagaMetadataAttribute attribute) {
        var builder = ProtoUtil.toProtoBuilder(attribute, MetadataAttribute.Builder.class);

        if (attribute.getValue() instanceof String) {
            builder.setStrValue((String) attribute.getValue());
        } else if (attribute.getValue() instanceof Integer || attribute.getValue() instanceof Long) {
            builder.setIntValue(((Number) attribute.getValue()).longValue());
        } else if (attribute.getValue() instanceof Float || attribute.getValue() instanceof Double) {
            builder.setDoubleValue(((Number) attribute.getValue()).doubleValue());
        }

        if (attribute.getMaxValue() != null) {
            builder.setMaxValue(DoubleValue.newBuilder().setValue(attribute.getMaxValue()).build());
        }

        return builder.build();
    }
}
