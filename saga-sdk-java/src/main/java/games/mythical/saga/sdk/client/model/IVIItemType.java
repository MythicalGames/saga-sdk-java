package games.mythical.saga.sdk.client.model;

import games.mythical.saga.sdk.exception.IVIException;
import games.mythical.ivi.sdk.proto.api.itemtype.ItemType;
import games.mythical.ivi.sdk.proto.common.itemtype.ItemTypeState;
import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class IVIItemType {
    private String gameItemTypeId;
    private int maxSupply;
    private int currentSupply;
    private int issuedSupply;
    private String issuer;
    private int issueTimeSpan;
    private String category;
    private String tokenName;
    private boolean fungible;
    private boolean burnable;
    private boolean transferable;
    private boolean finalized;
    private boolean sellable;
    private String baseUri;
    private List<UUID> agreementIds;
    private String trackingId;
    private IVIMetadata metadata;
    private Instant createdTimestamp;
    private Instant updatedTimestamp;
    private ItemTypeState itemTypeState;

    IVIItemType(String gameItemTypeId,
                int maxSupply,
                int currentSupply,
                int issuedSupply,
                String issuer,
                int issueTimeSpan,
                String category,
                String tokenName,
                boolean fungible,
                boolean burnable,
                boolean transferable,
                boolean finalized,
                boolean sellable,
                String baseUri,
                List<UUID> agreementIds,
                String trackingId,
                IVIMetadata metadata,
                Instant createdTimestamp,
                Instant updatedTimestamp,
                ItemTypeState itemTypeState) {
        this.gameItemTypeId = gameItemTypeId;
        this.maxSupply = maxSupply;
        this.currentSupply = currentSupply;
        this.issuedSupply = issuedSupply;
        this.issuer = issuer;
        this.issueTimeSpan = issueTimeSpan;
        this.category = category;
        this.tokenName = tokenName;
        this.fungible = fungible;
        this.burnable = burnable;
        this.transferable = transferable;
        this.finalized = finalized;
        this.sellable = sellable;
        this.baseUri = baseUri;
        this.agreementIds = agreementIds;
        this.trackingId = trackingId;
        this.metadata = metadata;
        this.createdTimestamp = createdTimestamp;
        this.updatedTimestamp = updatedTimestamp;
        this.itemTypeState = itemTypeState;
    }

    public static IVIItemType fromProto(ItemType itemType) throws IVIException {
        return new IVIItemType(itemType.getGameItemTypeId(),
                itemType.getMaxSupply(),
                itemType.getCurrentSupply(),
                itemType.getIssuedSupply(),
                itemType.getIssuer(),
                itemType.getIssueTimeSpan(),
                itemType.getCategory(),
                itemType.getTokenName(),
                itemType.getFungible(),
                itemType.getBurnable(),
                itemType.getTransferable(),
                itemType.getFinalized(),
                itemType.getSellable(),
                itemType.getBaseUri(),
                itemType.getAgreementIdsList().stream().map(UUID::fromString).collect(Collectors.toList()),
                itemType.getTrackingId(),
                IVIMetadata.fromProto(itemType.getMetadata()),
                Instant.ofEpochSecond(itemType.getCreatedTimestamp()),
                Instant.ofEpochSecond(itemType.getUpdatedTimestamp()),
                itemType.getItemTypeState());
    }

    public static List<IVIItemType> fromProto(List<ItemType> itemTypes) throws IVIException {
        var result = new ArrayList<IVIItemType>();
        for (var itemType : itemTypes) {
            result.add(IVIItemType.fromProto(itemType));
        }

        return result;
    }
}
