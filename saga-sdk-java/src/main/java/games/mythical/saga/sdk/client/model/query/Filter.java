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
        return addFilterValue(new FilterValue(FilterOperation.AND));
    }

    public Filter equal(String attribute, Object value) {
        return simpleOperation(attribute, value, FilterConditional.EQUALS);
    }

    public Filter notEqual(String attribute, Object value) {
        return simpleOperation(attribute, value, FilterConditional.NOT_EQUALS);
    }

    public Filter simpleOperation(String attribute, Object value, FilterConditional conditional) {
        filter.add(new FilterValue(attribute));
        filter.add(new FilterValue(conditional));
        filter.add(new FilterValue(value));
        return this;
    }

    public Filter addFilterValue(FilterValue value) {
        filter.add(value);
        return this;
    }

    public String getFilterAsString() {
        StringBuilder builder = new StringBuilder();
        filter.forEach(value -> builder.append(value.getValue().toString()).append(" "));

        return builder.toString();
    }
}
