package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.client.model.IVIItemType;
import games.mythical.ivi.sdk.proto.common.itemtype.ItemTypeState;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MockItemTypeExecutor implements IVIItemTypeExecutor {
    private String gameItemTypeId;
    private int currentSupply;
    private int issuedSupply;
    private String baseUri;
    private int issueTimeSpan;
    private String trackingId;
    private ItemTypeState itemTypeState;

    @Override
    public void updateItemType(String gameItemTypeId,
                               int currentSupply,
                               int issuedSupply,
                               String baseUri,
                               int issueTimeSpan,
                               String trackingId,
                               ItemTypeState itemTypeState) throws Exception {
        this.gameItemTypeId = gameItemTypeId;
        this.currentSupply = currentSupply;
        this.issuedSupply = issuedSupply;
        this.baseUri = baseUri;
        this.issueTimeSpan = issueTimeSpan;
        this.trackingId = trackingId;
        this.itemTypeState = itemTypeState;
    }

    @Override
    public void updateItemTypeStatus(String gameItemTypeId,
                                     String trackingId,
                                     ItemTypeState itemTypeState) throws Exception {
        this.gameItemTypeId = gameItemTypeId;
        this.trackingId = trackingId;
        this.itemTypeState = itemTypeState;
    }

    public void setFromItemType(IVIItemType itemType) {
            this.gameItemTypeId = itemType.getGameItemTypeId();
            this.currentSupply = itemType.getCurrentSupply();
            this.issuedSupply = itemType.getIssuedSupply();
            this.baseUri = itemType.getBaseUri();
            this.issueTimeSpan = itemType.getIssueTimeSpan();
            this.trackingId = itemType.getTrackingId();
            this.itemTypeState = itemType.getItemTypeState();
    }
}
