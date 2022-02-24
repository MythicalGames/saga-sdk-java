package games.mythical.saga.sdk.client.model;

import games.mythical.saga.sdk.exception.IVIException;
import games.mythical.ivi.sdk.proto.api.order.ItemTypeOrder;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Data
@Builder
public class IVIPurchasedItems {
    private List<String> gameInventoryIds;
    private String itemName;
    private String gameItemTypeId;
    private BigDecimal amountPaid;
    private String currency;
    private IVIMetadata metadata;

    public ItemTypeOrder toProto() throws IVIException {
        return ItemTypeOrder.newBuilder()
                .addAllGameInventoryIds(gameInventoryIds)
                .setItemName(itemName)
                .setGameItemTypeId(gameItemTypeId)
                .setAmountPaid(amountPaid.toString())
                .setCurrency(currency)
                .setMetadata(IVIMetadata.toProto(metadata))
                .build();
    }

    public static IVIPurchasedItems fromProto(ItemTypeOrder purchasedItems) throws IVIException {
        return IVIPurchasedItems.builder()
                .gameInventoryIds(purchasedItems.getGameInventoryIdsList())
                .itemName(purchasedItems.getItemName())
                .gameItemTypeId(purchasedItems.getGameItemTypeId())
                .amountPaid(new BigDecimal(purchasedItems.getAmountPaid()))
                .currency(purchasedItems.getCurrency())
                .metadata(IVIMetadata.fromProto(purchasedItems.getMetadata()))
                .build();
    }
}
