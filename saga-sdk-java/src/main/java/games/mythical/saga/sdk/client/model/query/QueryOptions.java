package games.mythical.saga.sdk.client.model.query;

import games.mythical.saga.sdk.proto.common.SortOrder;
import games.mythical.saga.sdk.proto.common.FilterConditional;
import games.mythical.saga.sdk.proto.common.QueryOptionsProto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryOptions {
    private Filter filterOptions;
    private Integer pageSize;
    private SortOrder sortOrder;
    private String sortAttribute;

    public static QueryOptionsProto toProto(QueryOptions queryOptions) {
        var queryOptionsBuilder = QueryOptionsProto.newBuilder()
                .setPageSize(queryOptions.getPageSize() != null ? queryOptions.getPageSize() : 0);

        if (StringUtils.isNotBlank(queryOptions.getSortAttribute())) {
            queryOptionsBuilder.setSortAttribute(queryOptions.getSortAttribute());
        }

        if (queryOptions.getSortOrder() != null) {
            queryOptionsBuilder.setSortOrder(queryOptions.getSortOrder());
        }

        if (queryOptions.getFilterOptions() != null) {
            for (var filter : queryOptions.getFilterOptions().getFilter()) {
                queryOptionsBuilder.addFilterOptions(FilterValue.toProto(filter));
            }
        }

        return queryOptionsBuilder.build();
    }

    public void addExpression(String attributeName, Object value, FilterConditional conditional, boolean override) {
        var containsFilter = filterOptions.filterContainsExpression(attributeName);

        if (filterOptions.isEmpty()) {
            // add the filter without and
            filterOptions.simpleOperation(attributeName, value, conditional);
        } else if (containsFilter && override) {
            filterOptions.overrideExpression(attributeName, value, conditional);
        } else {
            // add the filter with and
            filterOptions.and().simpleOperation(attributeName, value, conditional);
        }
    }

}
