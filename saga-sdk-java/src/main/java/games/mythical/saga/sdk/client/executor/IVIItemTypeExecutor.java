package games.mythical.saga.sdk.client.executor;

import games.mythical.ivi.sdk.proto.common.itemtype.ItemTypeState;

public interface IVIItemTypeExecutor {
    void updateItemType(String gameItemTypeId,
                        int currentSupply,
                        int issuedSupply,
                        String baseUri,
                        int issueTimeSpan,
                        String trackingId,
                        ItemTypeState itemTypeState) throws Exception;

    void updateItemTypeStatus(String gameItemTypeId,
                              String trackingId,
                              ItemTypeState itemTypeState) throws Exception;
}
