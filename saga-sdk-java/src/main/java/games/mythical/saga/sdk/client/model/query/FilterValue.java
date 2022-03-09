package games.mythical.saga.sdk.client.model.query;

import games.mythical.sga.sdk.proto.common.FilterConditional;
import games.mythical.sga.sdk.proto.common.FilterOperation;
import games.mythical.sga.sdk.proto.common.FilterValueProto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class FilterValue {
    private Object value;

    public static FilterValueProto toProto(FilterValue filterValue) {
        var filterValueBuilder = FilterValueProto.newBuilder();

        var value = filterValue.getValue();
        if (value instanceof FilterConditional) {
            filterValueBuilder.setConditional((FilterConditional) value);
        } else if (value instanceof FilterOperation) {
            filterValueBuilder.setOperation((FilterOperation) value);
        } else if (value instanceof Double) {
            filterValueBuilder.setDoubleValue((Double) value);
        } else if (value instanceof String) {
            filterValueBuilder.setStringValue((String) value);
        } else if (value instanceof Boolean) {
            filterValueBuilder.setBoolValue((Boolean) value);
        } else {
            log.error("Unknown value tupe {}", value);
        }

        return filterValueBuilder.build();
    }

}
