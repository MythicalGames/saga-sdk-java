package games.mythical.saga.sdk.client.model.query;

import games.mythical.saga.sdk.proto.common.SortOrder;
import games.mythical.sga.sdk.proto.common.QueryOptionsProto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
                .setPageSize(queryOptions.getPageSize() != null ? queryOptions.getPageSize() : Integer.MAX_VALUE)
                .setSortAttribute(queryOptions.getSortAttribute() != null ? queryOptions.getSortAttribute() : null)
                .setSortOrder(queryOptions.getSortOrder() != null ? queryOptions.getSortOrder() : null);

        if (queryOptions.getFilterOptions() != null) {
            for (var filter : queryOptions.getFilterOptions().getFilter()) {
                queryOptionsBuilder.addFilterOptions(FilterValue.toProto(filter));
            }
        }

        return queryOptionsBuilder.build();
    }

}
