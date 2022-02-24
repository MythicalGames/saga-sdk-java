package games.mythical.saga.sdk.client;

import games.mythical.ivi.sdk.client.model.*;
import games.mythical.saga.sdk.client.model.*;
import games.mythical.saga.sdk.config.IVIConfiguration;
import games.mythical.saga.sdk.exception.IVIException;
import games.mythical.ivi.sdk.proto.api.item.Item;
import games.mythical.ivi.sdk.proto.api.itemtype.ItemType;
import games.mythical.ivi.sdk.proto.api.order.*;
import games.mythical.ivi.sdk.proto.api.player.IVIPlayer;
import games.mythical.ivi.sdk.proto.common.item.ItemState;
import games.mythical.ivi.sdk.proto.common.itemtype.ItemTypeState;
import games.mythical.ivi.sdk.proto.common.order.OrderState;
import games.mythical.ivi.sdk.proto.common.player.PlayerState;
import io.grpc.ManagedChannel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@Slf4j
public abstract class AbstractClientTest {
    protected static final String host = "localhost";
    protected static final String apiKey = "MOCK_API_KEY";
    protected static final String environmentId = "MOCK_ENV_ID";
    protected static final String currency = "BB";

    protected int port;
    protected ManagedChannel channel;

    protected void setUpConfig() {
        IVIConfiguration.setHost(host);
        IVIConfiguration.setPort(port);
        IVIConfiguration.setApiKey(apiKey);
        IVIConfiguration.setEnvironmentId(environmentId);
    }

    protected IVIItemType generateNewItemType() throws Exception {
        var maxSupply = RandomUtils.nextInt(10, 100);
        return generateItemType("", maxSupply, maxSupply, 0, ItemTypeState.PENDING_CREATE);
    }

    protected IVIItemType generateItemType(String trackingId,
                                           int maxSupply,
                                           int currentSupply,
                                           int issuedSupply,
                                           ItemTypeState itemTypeState) throws Exception {
        var tokenName = RandomStringUtils.randomAlphanumeric(30);
        var itemType = ItemType.newBuilder()
                .setGameItemTypeId(RandomStringUtils.randomAlphanumeric(30))
                .setMaxSupply(maxSupply)
                .setCurrentSupply(currentSupply)
                .setIssuedSupply(issuedSupply)
                .setIssuer(RandomStringUtils.randomAlphanumeric(30))
                .setIssueTimeSpan(RandomUtils.nextInt())
                .setCategory(RandomStringUtils.randomAlphanumeric(30))
                .setTokenName(tokenName)
                .setFungible(RandomUtils.nextBoolean())
                .setBurnable(RandomUtils.nextBoolean())
                .setTransferable(RandomUtils.nextBoolean())
                .setSellable(RandomUtils.nextBoolean())
                .setFinalized(true)
                .setBaseUri(RandomStringUtils.randomAlphanumeric(30))
                .setTrackingId(trackingId)
                .setItemTypeState(itemTypeState)
                .setMetadata(IVIMetadata.toProto(generateItemMetadata()))
                .build();

        return IVIItemType.fromProto(itemType);
    }

    @SuppressWarnings("SameParameterValue")
    protected Map<String, IVIItemType> generateItemTypes(int count) throws Exception {
        var result = new HashMap<String, IVIItemType>();

        for (var i = 0; i < count; i++) {
            var trackingId = RandomStringUtils.randomAlphanumeric(30);
            var maxSupply = RandomUtils.nextInt(10, 100);
            var currentSupply = RandomUtils.nextInt(0, maxSupply);
            var issuedSupply = maxSupply - currentSupply;

            var itemType = generateItemType(trackingId, maxSupply, currentSupply, issuedSupply, ItemTypeState.CREATED);
            result.put(itemType.getGameItemTypeId(), itemType);
        }

        return result;
    }

    protected IVIPlayer generateIVIPlayer() {
        return IVIPlayer.newBuilder()
                .setPlayerId(RandomStringUtils.randomAlphanumeric(30))
                .setEmail("test@game.com")
                .setSidechainAccountName(RandomStringUtils.randomAlphanumeric(30))
                .setTrackingId(RandomStringUtils.randomAlphanumeric(30))
                .setPlayerState(PlayerState.LINKED)
                .build();
    }

    @SuppressWarnings("SameParameterValue")
    protected Map<String, IVIPlayer> generateIVIPlayers(int count) {
        var result = new HashMap<String, IVIPlayer>();
        for(var i = 0; i < count; i++) {
            var player = generateIVIPlayer();
            result.put(player.getPlayerId(), player);
        }
        return result;
    }

    protected IVIItem generateItem(long dGoodsId,
                                   String sideChainAccount,
                                   int serialNumber,
                                   String metadataUri,
                                   String trackingId,
                                   ItemState state) throws IVIException {
        var itemMetadata = generateItemMetadata();

        var item = Item.newBuilder()
                .setGameInventoryId(RandomStringUtils.randomAlphanumeric(30))
                .setGameItemTypeId(RandomStringUtils.randomAlphanumeric(30))
                .setDgoodsId(dGoodsId)
                .setItemName(RandomStringUtils.randomAlphanumeric(30))
                .setPlayerId(RandomStringUtils.randomAlphanumeric(30))
                .setOwnerSidechainAccount(sideChainAccount)
                .setSerialNumber(serialNumber)
                .setCurrencyBase(RandomStringUtils.randomAlphanumeric(30))
                .setMetadataUri(metadataUri)
                .setTrackingId(trackingId)
                .setMetadata(IVIMetadata.toProto(generateItemMetadata()))
                .setItemState(state)
                .setCreatedTimestamp(Instant.now().getEpochSecond() - 86400)
                .setUpdatedTimestamp(Instant.now().getEpochSecond())
                .build();

        return IVIItem.fromProto(item);
    }

    protected IVIItem generateNewItem() throws IVIException {
        return generateItem(0, "", 0, "", "", ItemState.PENDING_ISSUED);
    }

    @SuppressWarnings("SameParameterValue")
    protected Map<String, IVIItem> generateItems(int count) throws IVIException {
        var result = new HashMap<String, IVIItem>();
        for(var i = 0; i < count; i++) {
            var dGoodsId = RandomUtils.nextInt(100, 1000);
            var sidechain = RandomStringUtils.randomAlphanumeric(30);
            var serialNumber = RandomUtils.nextInt(10, 100);
            var metadataUri = RandomStringUtils.randomAlphanumeric(30);
            var trackingId = RandomStringUtils.randomAlphanumeric(30);

            ItemState state;
            if(RandomUtils.nextBoolean()) {
                state = ItemState.ISSUED;

            } else {
                state = ItemState.PENDING_ISSUED;
            }

            var item = generateItem(dGoodsId, sidechain, serialNumber, metadataUri, trackingId, state);
            result.put(item.getGameInventoryId(), item);
        }
        return result;
    }

    @SuppressWarnings("SameParameterValue")
    protected Map<String, IVIOrder> generateOrders(int count) throws IVIException {
        var orders = new HashMap<String, IVIOrder>();
        for(var i = 0; i < count; i++) {
            var orderBuilder = Order.newBuilder()
                    .setOrderId(RandomStringUtils.randomAlphanumeric(30))
                    .setStoreId(RandomStringUtils.randomAlphanumeric(30))
                    .setBuyerPlayerId(RandomStringUtils.randomAlphanumeric(30))
                    .setTax(String.valueOf(RandomUtils.nextDouble(0, 10)))
                    .setTotal(String.valueOf(RandomUtils.nextDouble(0, 200)))
                    .setAddress(generateAddress().toProto())
                    .setPaymentProviderId(PaymentProviderId.BRAINTREE)
                    .setOrderStatus(OrderState.COMPLETE)
                    .setPurchasedItems(ItemTypeOrders.getDefaultInstance());

            var order = orderBuilder.build();
            orders.put(order.getOrderId(), IVIOrder.fromProto(order));
        }

        return orders;
    }

    @SuppressWarnings("SameParameterValue")
    protected List<IVIPurchasedItems> generatePurchasedItems(int count) {
        var items = new ArrayList<IVIPurchasedItems>();
        for(var i = 0; i < count; i++) {
            var iviPurchasedItemBuilder = IVIPurchasedItems.builder()
                    .gameInventoryIds(List.of(RandomStringUtils.randomAlphanumeric(30)))
                    .itemName(RandomStringUtils.randomAlphanumeric(30))
                    .gameItemTypeId(RandomStringUtils.randomAlphanumeric(30))
                    .amountPaid(BigDecimal.valueOf(RandomUtils.nextDouble()))
                    .currency(RandomStringUtils.randomAlphanumeric(3))
                    .metadata(generateItemMetadata());

            items.add(iviPurchasedItemBuilder.build());
        }

        return items;
    }

    protected IVIOrderAddress generateAddress() {
        return IVIOrderAddress.builder()
                .firstName(RandomStringUtils.randomAlphanumeric(30))
                .lastName(RandomStringUtils.randomAlphanumeric(30))
                .addressLine1(RandomStringUtils.randomAlphanumeric(30))
                .addressLine2(RandomStringUtils.randomAlphanumeric(30))
                .city(RandomStringUtils.randomAlphanumeric(30))
                .state(RandomStringUtils.randomAlphanumeric(30))
                .postalCode(RandomStringUtils.randomNumeric(10))
                .countryName(RandomStringUtils.randomAlphanumeric(30))
                .countryIsoAlpha2(RandomStringUtils.randomAlphanumeric(5))
                .build();
    }

    enum PropertyType {
        STRING,
        NUMBER,
        ABILITY;

        static PropertyType nextPropertyType() {
            var choice = RandomUtils.nextInt(0, PropertyType.values().length);
            return PropertyType.values()[choice];
        }
    }

    protected Map<String, Object> generateProperties(int count) {
        var properties = new HashMap<String, Object>();
        for(var i = 0; i < count; i++) {
            var key = String.format("property%s", i);

            switch (PropertyType.nextPropertyType()) {
                case STRING:
                    properties.put(key, RandomStringUtils.randomAlphanumeric(30));
                    break;
                case NUMBER:
                    properties.put(key, RandomUtils.nextInt(1, 1000));
                    break;
                case ABILITY:
                    properties.put(key, generateAbility());
                    break;
            }
        }

        return properties;
    }

    protected MockAbility generateAbility() {
        return MockAbility.builder()
                .ability1(RandomStringUtils.randomAlphanumeric(30))
                .ability2(RandomUtils.nextInt())
                .ability3(BigDecimal.valueOf(RandomUtils.nextDouble(1, 250)))
                .build();
    }

    @SuppressWarnings("SameParameterValue")
    protected IVIMetadata generateItemMetadata() {
        return IVIMetadata.builder()
                .name(RandomStringUtils.randomAlphanumeric(30))
                .description(RandomStringUtils.randomAlphanumeric(30))
                .image(RandomStringUtils.randomAlphanumeric(30))
                .properties(generateProperties(3))
                .build();
    }

    protected void verifyProperties(Map<String, Object> expectedProperties, Map<String, Object> actualProperties) {
        for (var key : expectedProperties.keySet()) {
            if(actualProperties.containsKey(key)) {
                assertEquals(expectedProperties.get(key), actualProperties.get(key));
            } else {
                fail("Property key " + key + " is missing!");
            }
        }
    }

    protected void verifyMetadata(IVIMetadata expectedMetadata, IVIMetadata actualMetadata) {
        assertEquals(expectedMetadata.getDescription(), actualMetadata.getDescription());
        assertEquals(expectedMetadata.getName(), actualMetadata.getName());
        assertEquals(expectedMetadata.getImage(), actualMetadata.getImage());

        verifyProperties(expectedMetadata.getProperties(), actualMetadata.getProperties());
    }
}
