package games.mythical.saga.sdk.client.model;

import games.mythical.proto_util.ProtoUtil;
import games.mythical.proto_util.dto.DtoExclude;
import games.mythical.proto_util.proto.ProtoExclude;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.common.Metadata;
import games.mythical.saga.sdk.util.ConversionUtils;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Builder
public class SagaMetadata {
    private String name;
    private String description;
    private String image;
    @ProtoExclude
    @DtoExclude
    private Map<String, Object> properties;
    private String externalUrl;
    private String backgroundColor;
    private String animationUrl;
    private String youtubeUrl;
    @ProtoExclude
    @DtoExclude
    private List<SagaMetadataAttribute> attributes;

    public static SagaMetadata fromProto(Metadata proto) {
        return ProtoUtil.toDto(proto, SagaMetadata.class);
    }

    public static Metadata toProto(SagaMetadata metadata) throws SagaException {
        var builder = ProtoUtil.toProtoBuilder(metadata, Metadata.Builder.class);

        if (metadata.getProperties() != null) {
            builder.setProperties(ConversionUtils.convertProperties(metadata.getProperties()));
        }

        if (metadata.getAttributes() != null) {
            builder.addAllAttributes(metadata.getAttributes().stream().map(SagaMetadataAttribute::toProto).collect(Collectors.toList()));
        }

        return builder.build();
    }
}