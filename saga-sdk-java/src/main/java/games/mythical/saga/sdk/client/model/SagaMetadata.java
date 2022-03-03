package games.mythical.saga.sdk.client.model;

import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.common.Metadata;
import games.mythical.saga.sdk.util.ConversionUtils;
import games.mythical.shared.util.ProtoUtil;
import games.mythical.shared.util.dto.DtoExclude;
import games.mythical.shared.util.proto.ProtoExclude;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class SagaMetadata {
    private String name;
    private String description;
    private String image;
    @ProtoExclude
    @DtoExclude
    private Map<String, Object> properties;

    public static SagaMetadata fromProto(Metadata proto) {
        return ProtoUtil.toDto(proto, SagaMetadata.class);
    }

    public static Metadata toProto(SagaMetadata metadata) throws SagaException {
        var builder = ProtoUtil.toProtoBuilder(metadata, Metadata.Builder.class);

        if (metadata.getProperties() != null) {
            builder.setProperties(ConversionUtils.convertProperties(metadata.getProperties()));
        }

        return builder.build();
    }
}