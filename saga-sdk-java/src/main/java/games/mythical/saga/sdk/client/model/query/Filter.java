package games.mythical.saga.sdk.client.model.query;

import games.mythical.sga.sdk.proto.common.FilterConditional;
import games.mythical.sga.sdk.proto.common.FilterOperation;
import lombok.Data;

import java.util.LinkedList;
import java.util.Queue;

@Data
public class Filter {
    private Queue<FilterValue> filter = new LinkedList<>();

    public Filter and() {
        return addFilterValue(new Operation(FilterOperation.AND));
    }

    public Filter equal(String attribute, Object value) {
        return simpleOperation(attribute, value, FilterConditional.EQUALS);
    }

    public Filter notEqual(String attribute, Object value) {
        return simpleOperation(attribute, value, FilterConditional.NOT_EQUALS);
    }

    public Filter simpleOperation(String attribute, Object value, FilterConditional conditional) {
        addFilterValue(new Expression(attribute, conditional, value));
        return this;
    }

    private Filter addFilterValue(FilterValue value) {
        filter.add(value);
        return this;
    }

    public String getFilterAsString() {
        StringBuilder builder = new StringBuilder();
        filter.forEach(value -> builder.append(value.toString()).append(" "));

        return builder.toString();
    }

    public boolean filterContainsExpression(String attribute) {
        for (var filterValue : filter) {
            if (filterValue instanceof Expression && ((Expression) filterValue).getAttributeName().equals(attribute)) {
                return true;
            }
        }
        return false;
    }

    public void overrideExpression(String attribute, Object value, FilterConditional conditional) {
        for (var filterValue : filter) {
            if (filterValue instanceof Expression) {
                var expression = (Expression) filterValue;
                if (expression.getAttributeName().equals(attribute)) {
                    expression.setValue(value);
                    expression.setConditional(conditional);
                }
            }
        }
    }

    public boolean isEmpty() {
        return filter.isEmpty();
    }
}
