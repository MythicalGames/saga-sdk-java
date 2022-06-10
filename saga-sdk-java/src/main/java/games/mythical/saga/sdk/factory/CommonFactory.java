package games.mythical.saga.sdk.factory;

import games.mythical.saga.sdk.proto.common.QueryOptionsProto;
import games.mythical.saga.sdk.proto.common.SortOrder;

import java.time.Instant;

public class CommonFactory {
    public static QueryOptionsProto toProto(int pageSize, SortOrder sortOrder, Instant createdAtCursor) {
        return QueryOptionsProto.newBuilder()
                .setPageSize(pageSize)
                .setSortOrder(sortOrder)
                .setCreatedAtCursor(createdAtCursor == null ? -1 : createdAtCursor.toEpochMilli())
                .build();
    }
}
