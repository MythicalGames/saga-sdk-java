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
    private int pageSize;
    private SortOrder sortOrder;
    private String sortAttribute;

    public static QueryOptionsProto toProto(QueryOptions queryOptions) {
        var queryOptionsBuilder = QueryOptionsProto.newBuilder()
                .setPageSize(queryOptions.getPageSize())
                .setSortAttribute(queryOptions.getSortAttribute())
                .setSortOrder(queryOptions.getSortOrder());

        for (var filter : queryOptions.getFilterOptions().getFilter()) {
            queryOptionsBuilder.addFilterOptions(FilterValue.toProto(filter));
        }

        return queryOptionsBuilder.build();
    }

}
