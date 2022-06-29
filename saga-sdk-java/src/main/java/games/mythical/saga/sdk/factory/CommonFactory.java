package games.mythical.saga.sdk.factory;

import games.mythical.saga.sdk.proto.common.QueryOptionsProto;
import games.mythical.saga.sdk.proto.common.SortOrder;

import games.mythical.saga.sdk.util.ConversionUtils;
import java.time.Instant;

public class CommonFactory {
    public static QueryOptionsProto toProto(int pageSize, SortOrder sortOrder, Instant createdAtCursor) {
        final var queryOptionsBuilder = QueryOptionsProto.newBuilder()
                .setPageSize(pageSize)
                .setSortOrder(sortOrder);
        if (createdAtCursor != null) {
            queryOptionsBuilder.setCreatedAtCursor(
                ConversionUtils.instantToProtoTimestamp(createdAtCursor));
        }
        return queryOptionsBuilder.build();
    }
}
