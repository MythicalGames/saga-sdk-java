package games.mythical.saga.sdk.client.model.query;

import games.mythical.sga.sdk.proto.common.ExpressionProto;
import games.mythical.sga.sdk.proto.common.FilterConditional;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class Expression extends FilterValue {
    private String attributeName;
    private FilterConditional conditional;
    private Object value;

    public static ExpressionProto toProto(Expression expression) {
        var protoBuilder = ExpressionProto.newBuilder();
        protoBuilder.setAttributeName(expression.getAttributeName())
                .setConditional(expression.getConditional());

        var value = expression.getValue();
        if (value instanceof Double) {
            protoBuilder.setDoubleValue((Double) value);
        } else if (value instanceof String) {
            protoBuilder.setStringValue((String) value);
        } else if (value instanceof Boolean) {
            protoBuilder.setBoolValue((Boolean) value);
        } else {
            log.error("Unknown value tupe in Expression {}", value);
        }

        return protoBuilder.build();
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", attributeName, conditional, value);
    }
}
