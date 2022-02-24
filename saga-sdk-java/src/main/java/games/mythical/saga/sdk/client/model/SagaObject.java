package games.mythical.saga.sdk.client.model;

import games.mythical.saga.sdk.exception.SagaException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaObject {
    private String data;

    public static SagaObject fromProto(Object someProtoObject) throws SagaException {
        return SagaObject.builder()
                .data("map things here")
                .build();
    }
}
