package games.mythical.saga.sdk.client.model.query;

import games.mythical.sga.sdk.proto.common.FilterValueProto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class FilterValue {
    public static FilterValueProto toProto(FilterValue filterValue) {
        var filterValueBuilder = FilterValueProto.newBuilder();

        if (filterValue instanceof Operation) {
            filterValueBuilder.setOperation(((Operation) filterValue).getOperation());
        } else if (filterValue instanceof Expression) {
            filterValueBuilder.setExpression(Expression.toProto((Expression) filterValue));
        } else {
            log.error("Subclass of FilterValue not supported: {}", filterValue);
        }

        return filterValueBuilder.build();
    }

}
