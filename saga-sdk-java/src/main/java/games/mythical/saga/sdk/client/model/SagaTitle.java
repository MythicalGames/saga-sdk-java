package games.mythical.saga.sdk.client.model;

import games.mythical.saga.sdk.proto.api.title.TitleProto;
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
public class SagaTitle {
    private String titleId;
    private String name;
    @DtoExclude
    private Instant createdTimestamp;

    public static SagaTitle fromProto(TitleProto proto) {
        var title = ProtoUtil.toDto(proto, SagaTitle.class);

        var createdTimestamp = Instant.ofEpochMilli(proto.getCreatedTimestamp());
        title.setCreatedTimestamp(createdTimestamp);

        return title;
    }
}
