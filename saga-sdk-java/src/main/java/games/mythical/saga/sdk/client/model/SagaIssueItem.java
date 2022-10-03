package games.mythical.saga.sdk.client.model;

import games.mythical.saga.sdk.proto.api.item.IssueItemProto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SagaIssueItem {
    private String inventoryId;
    private SagaMetadata metadata;

    public static IssueItemProto toProto(SagaIssueItem issueItem) {
        return IssueItemProto.newBuilder()
                .setInventoryId(issueItem.getInventoryId())
                .setMetadata(SagaMetadata.toProto(issueItem.getMetadata()))
                .build();
    }
}
