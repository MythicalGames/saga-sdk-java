# Protocol Documentation
<a name="top"></a>

## Table of Contents

- [api/bridge/definition.proto](#api_bridge_definition-proto)
    - [BridgeProto](#saga-api-bridge-BridgeProto)
    - [GetBridgeRequest](#saga-api-bridge-GetBridgeRequest)
    - [WithdrawItemRequest](#saga-api-bridge-WithdrawItemRequest)
  
- [api/bridge/rpc.proto](#api_bridge_rpc-proto)
    - [BridgeService](#saga-api-bridge-BridgeService)
  
- [api/gamecoin/definition.proto](#api_gamecoin_definition-proto)
    - [BurnGameCoinRequest](#saga-api-gamecoin-BurnGameCoinRequest)
    - [GameCoinProto](#saga-api-gamecoin-GameCoinProto)
    - [GameCoinsProto](#saga-api-gamecoin-GameCoinsProto)
    - [GetGameCoinRequest](#saga-api-gamecoin-GetGameCoinRequest)
    - [GetGameCoinsRequest](#saga-api-gamecoin-GetGameCoinsRequest)
    - [IssueGameCoinRequest](#saga-api-gamecoin-IssueGameCoinRequest)
    - [TransferGameCoinRequest](#saga-api-gamecoin-TransferGameCoinRequest)
  
- [api/gamecoin/rpc.proto](#api_gamecoin_rpc-proto)
    - [GameCoinService](#saga-api-gamecoin-GameCoinService)
  
- [api/item/definition.proto](#api_item_definition-proto)
    - [BurnItemRequest](#saga-api-item-BurnItemRequest)
    - [GetItemRequest](#saga-api-item-GetItemRequest)
    - [GetItemsForPlayerRequest](#saga-api-item-GetItemsForPlayerRequest)
    - [GetItemsRequest](#saga-api-item-GetItemsRequest)
    - [IssueItemRequest](#saga-api-item-IssueItemRequest)
    - [ItemProto](#saga-api-item-ItemProto)
    - [ItemsProto](#saga-api-item-ItemsProto)
    - [TransferItemRequest](#saga-api-item-TransferItemRequest)
    - [UpdateItemMetadata](#saga-api-item-UpdateItemMetadata)
    - [UpdateItemsMetadataRequest](#saga-api-item-UpdateItemsMetadataRequest)
  
- [api/item/rpc.proto](#api_item_rpc-proto)
    - [ItemService](#saga-api-item-ItemService)
  
- [api/itemtype/definition.proto](#api_itemtype_definition-proto)
    - [CreateItemTypeRequest](#saga-api-itemtype-CreateItemTypeRequest)
    - [GetItemTypeRequest](#saga-api-itemtype-GetItemTypeRequest)
    - [GetItemTypesRequest](#saga-api-itemtype-GetItemTypesRequest)
    - [ItemTypeProto](#saga-api-itemtype-ItemTypeProto)
    - [ItemTypesProto](#saga-api-itemtype-ItemTypesProto)
    - [PriRevShareSettings](#saga-api-itemtype-PriRevShareSettings)
    - [PriceMap](#saga-api-itemtype-PriceMap)
    - [SecRevShareSettings](#saga-api-itemtype-SecRevShareSettings)
    - [UpdateItemTypeMetadataPayload](#saga-api-itemtype-UpdateItemTypeMetadataPayload)
    - [UpdateItemTypePayload](#saga-api-itemtype-UpdateItemTypePayload)
  
- [api/itemtype/rpc.proto](#api_itemtype_rpc-proto)
    - [ItemTypeService](#saga-api-itemtype-ItemTypeService)
  
- [api/listing/definition.proto](#api_listing_definition-proto)
    - [CancelListingRequest](#saga-api-listing-CancelListingRequest)
    - [ConfirmListingRequest](#saga-api-listing-ConfirmListingRequest)
    - [CreateListingQuoteRequest](#saga-api-listing-CreateListingQuoteRequest)
    - [GetListingsRequest](#saga-api-listing-GetListingsRequest)
    - [ListingProto](#saga-api-listing-ListingProto)
    - [ListingQuoteProto](#saga-api-listing-ListingQuoteProto)
    - [ListingsProto](#saga-api-listing-ListingsProto)
  
- [api/listing/rpc.proto](#api_listing_rpc-proto)
    - [ListingService](#saga-api-listing-ListingService)
  
- [api/myth/definition.proto](#api_myth_definition-proto)
    - [ConfirmBuyingMythTokenRequest](#saga-api-myth-ConfirmBuyingMythTokenRequest)
    - [ConfirmMythTokenWithdrawalRequest](#saga-api-myth-ConfirmMythTokenWithdrawalRequest)
    - [CurrencyExchangeProto](#saga-api-myth-CurrencyExchangeProto)
    - [GasFeeProto](#saga-api-myth-GasFeeProto)
    - [QuoteBuyingMythTokenRequest](#saga-api-myth-QuoteBuyingMythTokenRequest)
    - [QuoteBuyingMythTokenResponse](#saga-api-myth-QuoteBuyingMythTokenResponse)
    - [QuoteMythTokenWithdrawalRequest](#saga-api-myth-QuoteMythTokenWithdrawalRequest)
    - [QuoteMythTokenWithdrawalResponse](#saga-api-myth-QuoteMythTokenWithdrawalResponse)
  
- [api/myth/rpc.proto](#api_myth_rpc-proto)
    - [MythService](#saga-api-myth-MythService)
  
- [api/offer/definition.proto](#api_offer_definition-proto)
    - [CancelOfferRequest](#saga-api-offer-CancelOfferRequest)
    - [ConfirmOfferRequest](#saga-api-offer-ConfirmOfferRequest)
    - [CreateOfferQuoteRequest](#saga-api-offer-CreateOfferQuoteRequest)
    - [GetOffersRequest](#saga-api-offer-GetOffersRequest)
    - [OfferProto](#saga-api-offer-OfferProto)
    - [OfferQuoteProto](#saga-api-offer-OfferQuoteProto)
    - [OffersProto](#saga-api-offer-OffersProto)
  
- [api/offer/rpc.proto](#api_offer_rpc-proto)
    - [OfferService](#saga-api-offer-OfferService)
  
- [api/order/definition.proto](#api_order_definition-proto)
    - [ConfirmOrderRequest](#saga-api-order-ConfirmOrderRequest)
    - [CreateOrderQuoteRequest](#saga-api-order-CreateOrderQuoteRequest)
    - [CreditCardData](#saga-api-order-CreditCardData)
    - [PaymentProviderData](#saga-api-order-PaymentProviderData)
    - [QuoteProto](#saga-api-order-QuoteProto)
  
    - [PaymentProviderId](#saga-api-order-PaymentProviderId)
  
- [api/order/rpc.proto](#api_order_rpc-proto)
    - [OrderService](#saga-api-order-OrderService)
  
- [api/payment/definition.proto](#api_payment_definition-proto)
    - [Address](#saga-api-payment-Address)
    - [CardPaymentData](#saga-api-payment-CardPaymentData)
    - [CreatePaymentMethodRequest](#saga-api-payment-CreatePaymentMethodRequest)
    - [CybersourcePaymentData](#saga-api-payment-CybersourcePaymentData)
    - [DeletePaymentMethodRequest](#saga-api-payment-DeletePaymentMethodRequest)
    - [GetPaymentMethodRequest](#saga-api-payment-GetPaymentMethodRequest)
    - [PaymentMethodProto](#saga-api-payment-PaymentMethodProto)
    - [UpdatePaymentMethodRequest](#saga-api-payment-UpdatePaymentMethodRequest)
  
- [api/payment/rpc.proto](#api_payment_rpc-proto)
    - [PaymentService](#saga-api-payment-PaymentService)
  
- [api/title/definition.proto](#api_title_definition-proto)
    - [GetTitlesRequest](#saga-api-title-GetTitlesRequest)
    - [TitleProto](#saga-api-title-TitleProto)
    - [TitlesProto](#saga-api-title-TitlesProto)
  
- [api/title/rpc.proto](#api_title_rpc-proto)
    - [TitleService](#saga-api-title-TitleService)
  
- [api/transaction/definition.proto](#api_transaction_definition-proto)
    - [GetTransactionsForItemTypeRequest](#saga-api-transaction-GetTransactionsForItemTypeRequest)
    - [GetTransactionsForPlayerRequest](#saga-api-transaction-GetTransactionsForPlayerRequest)
    - [TransactionProto](#saga-api-transaction-TransactionProto)
    - [TransactionsProto](#saga-api-transaction-TransactionsProto)
  
- [api/transaction/rpc.proto](#api_transaction_rpc-proto)
    - [TransactionService](#saga-api-transaction-TransactionService)
  
- [api/user/definition.proto](#api_user_definition-proto)
    - [CreateUserRequest](#saga-api-user-CreateUserRequest)
    - [FungibleToken](#saga-api-user-FungibleToken)
    - [GetUserRequest](#saga-api-user-GetUserRequest)
    - [GetUsersRequest](#saga-api-user-GetUsersRequest)
    - [GetWalletAssetsRequest](#saga-api-user-GetWalletAssetsRequest)
    - [NftItem](#saga-api-user-NftItem)
    - [UpdateUserRequest](#saga-api-user-UpdateUserRequest)
    - [UserProto](#saga-api-user-UserProto)
    - [UsersProto](#saga-api-user-UsersProto)
    - [WalletAsset](#saga-api-user-WalletAsset)
  
- [api/user/rpc.proto](#api_user_rpc-proto)
    - [UserService](#saga-api-user-UserService)
  
- [common/common.proto](#common_common-proto)
    - [ErrorResponse](#saga-common-ErrorResponse)
    - [Metadata](#saga-common-Metadata)
    - [ReceivedResponse](#saga-common-ReceivedResponse)
  
    - [ErrorCode](#saga-common-ErrorCode)
  
- [common/finalization.proto](#common_finalization-proto)
    - [Finalized](#saga-common-Finalized)
  
- [common/gamecoin/definition.proto](#common_gamecoin_definition-proto)
    - [GameCoinState](#saga-proto-common-gamecoin-GameCoinState)
  
- [common/item/definition.proto](#common_item_definition-proto)
    - [ItemState](#saga-proto-common-item-ItemState)
  
- [common/itemtype/definition.proto](#common_itemtype_definition-proto)
    - [ItemTypeState](#saga-proto-common-itemtype-ItemTypeState)
  
- [common/listing/definition.proto](#common_listing_definition-proto)
    - [ListingState](#saga-proto-common-listing-ListingState)
  
- [common/myth/definition.proto](#common_myth_definition-proto)
    - [MythTokenState](#saga-proto-common-myth-MythTokenState)
  
- [common/offer/definition.proto](#common_offer_definition-proto)
    - [OfferState](#saga-proto-common-offer-OfferState)
  
- [common/order/definition.proto](#common_order_definition-proto)
    - [OrderState](#saga-proto-common-order-OrderState)
  
- [common/payment/definition.proto](#common_payment_definition-proto)
    - [PaymentMethodUpdateStatus](#saga-proto-common-payment-PaymentMethodUpdateStatus)
    - [PaymentProviderId](#saga-proto-common-payment-PaymentProviderId)
  
- [common/query.proto](#common_query-proto)
    - [ExpressionProto](#saga-common-ExpressionProto)
    - [FilterValueProto](#saga-common-FilterValueProto)
    - [QueryOptionsProto](#saga-common-QueryOptionsProto)
  
    - [FilterConditional](#saga-common-FilterConditional)
    - [FilterOperation](#saga-common-FilterOperation)
  
- [common/sort.proto](#common_sort-proto)
    - [SortOrder](#saga-common-SortOrder)
  
- [common/user/definition.proto](#common_user_definition-proto)
    - [UserState](#saga-proto-common-user-UserState)
  
- [streams/bridge/definition.proto](#streams_bridge_definition-proto)
    - [BridgeStatusUpdate](#saga-rpc-streams-bridge-BridgeStatusUpdate)
    - [BridgeUpdate](#saga-rpc-streams-bridge-BridgeUpdate)
  
- [streams/common.proto](#streams_common-proto)
    - [Subscribe](#saga-rpc-streams-Subscribe)
  
- [streams/gamecoin/definition.proto](#streams_gamecoin_definition-proto)
    - [GameCoinStatusConfirmRequest](#saga-rpc-streams-gamecoin-GameCoinStatusConfirmRequest)
    - [GameCoinStatusUpdate](#saga-rpc-streams-gamecoin-GameCoinStatusUpdate)
    - [GameCoinUpdate](#saga-rpc-streams-gamecoin-GameCoinUpdate)
  
- [streams/item/definition.proto](#streams_item_definition-proto)
    - [ItemStatusUpdate](#saga-rpc-streams-item-ItemStatusUpdate)
    - [ItemUpdate](#saga-rpc-streams-item-ItemUpdate)
  
- [streams/itemtype/definition.proto](#streams_itemtype_definition-proto)
    - [ItemTypeStatusUpdate](#saga-rpc-streams-itemtype-ItemTypeStatusUpdate)
    - [ItemTypeUpdate](#saga-rpc-streams-itemtype-ItemTypeUpdate)
  
- [streams/listing/definition.proto](#streams_listing_definition-proto)
    - [ListingStatusUpdate](#saga-rpc-streams-listing-ListingStatusUpdate)
    - [ListingUpdate](#saga-rpc-streams-listing-ListingUpdate)
  
- [streams/myth/definition.proto](#streams_myth_definition-proto)
    - [MythTokenStatusUpdate](#saga-rpc-streams-myth-MythTokenStatusUpdate)
    - [MythTokenUpdate](#saga-rpc-streams-myth-MythTokenUpdate)
  
- [streams/offer/definition.proto](#streams_offer_definition-proto)
    - [OfferStatusUpdate](#saga-rpc-streams-offer-OfferStatusUpdate)
    - [OfferUpdate](#saga-rpc-streams-offer-OfferUpdate)
  
- [streams/order/definition.proto](#streams_order_definition-proto)
    - [OrderStatusUpdate](#saga-rpc-streams-order-OrderStatusUpdate)
    - [OrderUpdate](#saga-rpc-streams-order-OrderUpdate)
  
- [streams/payment/definition.proto](#streams_payment_definition-proto)
    - [PaymentMethodStatusUpdate](#saga-rpc-streams-payment-PaymentMethodStatusUpdate)
    - [PaymentUpdate](#saga-rpc-streams-payment-PaymentUpdate)
  
- [streams/stream.proto](#streams_stream-proto)
    - [StatusConfirmRequest](#saga-rpc-streams-StatusConfirmRequest)
    - [StatusUpdate](#saga-rpc-streams-StatusUpdate)
  
    - [StatusStream](#saga-rpc-streams-StatusStream)
  
- [streams/user/definition.proto](#streams_user_definition-proto)
    - [UserStatusUpdate](#saga-rpc-streams-user-UserStatusUpdate)
    - [UserUpdate](#saga-rpc-streams-user-UserUpdate)
  
- [Scalar Value Types](#scalar-value-types)



<a name="api_bridge_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/bridge/definition.proto



<a name="saga-api-bridge-BridgeProto"></a>

### BridgeProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |
| mythical_address | [string](#string) |  |  |
| mainnet_address | [string](#string) |  | Address on Mainnet |
| chain_name | [string](#string) |  | Name of the chain |






<a name="saga-api-bridge-GetBridgeRequest"></a>

### GetBridgeRequest
Get Bridge Call






<a name="saga-api-bridge-WithdrawItemRequest"></a>

### WithdrawItemRequest
Withdraw Call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  |  |
| game_item_type_id | [string](#string) |  | Id of the ItemType this Item belongs to |
| game_inventory_id | [string](#string) |  | Id of the GameInventory this Item belongs to |
| destination_address | [string](#string) |  | Address of where this Item is going to |
| destination_chain | [string](#string) |  | Chain of where this Item is going to |
| origin_address | [string](#string) |  | Address that this Item is being withdrawn from |





 

 

 

 



<a name="api_bridge_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/bridge/rpc.proto


 

 

 


<a name="saga-api-bridge-BridgeService"></a>

### BridgeService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| WithdrawItem | [WithdrawItemRequest](#saga-api-bridge-WithdrawItemRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Withdraw an Item |
| GetBridge | [GetBridgeRequest](#saga-api-bridge-GetBridgeRequest) | [BridgeProto](#saga-api-bridge-BridgeProto) | Get Bridge |

 



<a name="api_gamecoin_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/gamecoin/definition.proto



<a name="saga-api-gamecoin-BurnGameCoinRequest"></a>

### BurnGameCoinRequest
Burn coins call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  | User to burn coins from |
| currency_id | [string](#string) |  |  |
| coin_count | [int32](#int32) |  | Amount of coins to burn |






<a name="saga-api-gamecoin-GameCoinProto"></a>

### GameCoinProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |
| coin_count | [int32](#int32) |  | Amount of coins |
| currency_id | [string](#string) |  |  |
| name | [string](#string) |  |  |
| oauth_id | [string](#string) |  | User the coins belong to |
| created_timestamp | [int64](#int64) |  |  |
| updated_timestamp | [int64](#int64) |  |  |






<a name="saga-api-gamecoin-GameCoinsProto"></a>

### GameCoinsProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| game_coins | [GameCoinProto](#saga-api-gamecoin-GameCoinProto) | repeated |  |






<a name="saga-api-gamecoin-GetGameCoinRequest"></a>

### GetGameCoinRequest
Get coin call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  | User to get coins for |
| currency_id | [string](#string) |  |  |






<a name="saga-api-gamecoin-GetGameCoinsRequest"></a>

### GetGameCoinsRequest
Get coins call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  | User to get coins for |
| created_after_timestamp | [uint64](#uint64) |  |  |
| page_size | [int32](#int32) |  |  |
| sort_order | [saga.common.SortOrder](#saga-common-SortOrder) |  |  |






<a name="saga-api-gamecoin-IssueGameCoinRequest"></a>

### IssueGameCoinRequest
Issue coins call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| currency_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  | User to issue coins to |
| coin_count | [int32](#int32) |  | Amount of coins to issue |






<a name="saga-api-gamecoin-TransferGameCoinRequest"></a>

### TransferGameCoinRequest
Transfer coins call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| source_oauth_id | [string](#string) |  | User to transfer coins from |
| destination_oauth_id | [string](#string) |  | User to transfer coins to |
| currency_id | [string](#string) |  |  |
| coin_count | [int32](#int32) |  | Amount of coins to transfer |





 

 

 

 



<a name="api_gamecoin_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/gamecoin/rpc.proto


 

 

 


<a name="saga-api-gamecoin-GameCoinService"></a>

### GameCoinService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| GetGameCoin | [GetGameCoinRequest](#saga-api-gamecoin-GetGameCoinRequest) | [GameCoinProto](#saga-api-gamecoin-GameCoinProto) | Get a GameCoin for a user |
| GetGameCoins | [GetGameCoinsRequest](#saga-api-gamecoin-GetGameCoinsRequest) | [GameCoinsProto](#saga-api-gamecoin-GameCoinsProto) |  |
| IssueGameCoin | [IssueGameCoinRequest](#saga-api-gamecoin-IssueGameCoinRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Issue game coins to a user |
| TransferGameCoin | [TransferGameCoinRequest](#saga-api-gamecoin-TransferGameCoinRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Transfer can coins between users |
| BurnGameCoin | [BurnGameCoinRequest](#saga-api-gamecoin-BurnGameCoinRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Burn game coins for a user |

 



<a name="api_item_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/item/definition.proto



<a name="saga-api-item-BurnItemRequest"></a>

### BurnItemRequest
Burn item call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| game_inventory_id | [string](#string) |  | Game&#39;s id for the Item to burn |






<a name="saga-api-item-GetItemRequest"></a>

### GetItemRequest
Get Item call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| game_inventory_id | [string](#string) |  | Game&#39;s id for the Item to retrieve |
| history | [bool](#bool) |  | Include history of the Item? |






<a name="saga-api-item-GetItemsForPlayerRequest"></a>

### GetItemsForPlayerRequest
Get Items for Player call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  | Player to get Items for |






<a name="saga-api-item-GetItemsRequest"></a>

### GetItemsRequest
Get Items call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| query_options | [saga.common.QueryOptionsProto](#saga-common-QueryOptionsProto) |  | Sort/filter options |
| finalized | [saga.common.Finalized](#saga-common-Finalized) |  |  |
| token_name | [string](#string) |  |  |






<a name="saga-api-item-IssueItemRequest"></a>

### IssueItemRequest
Issue item call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| game_inventory_id | [string](#string) |  | GameInventory Id of Item being issued |
| oauth_id | [string](#string) |  | User that is issuing Item |
| game_item_type_id | [string](#string) |  | Unique id set for your game of the Item being issued |
| metadata | [saga.common.Metadata](#saga-common-Metadata) |  | Metadata associated to Item being issued |
| store_id | [string](#string) |  | (optional) Id of store |
| order_id | [string](#string) |  |  |
| request_ip | [string](#string) |  |  |






<a name="saga-api-item-ItemProto"></a>

### ItemProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |
| game_inventory_id | [string](#string) |  | The game&#39;s unique id for this Item |
| game_item_type_id | [string](#string) |  | The game&#39;s ItemType id associated to this Item |
| oauth_id | [string](#string) |  | User for this Item |
| serial_number | [int32](#int32) |  |  |
| metadata_uri | [string](#string) |  | Metadata accessible address |
| metadata | [saga.common.Metadata](#saga-common-Metadata) |  | Metadata for this Item |
| item_state | [saga.proto.common.item.ItemState](#saga-proto-common-item-ItemState) |  | State that the Item is in. See ItemState for more information |
| created_timestamp | [int64](#int64) |  | When was this Item created |
| updated_timestamp | [int64](#int64) |  | When was this Item last updated |






<a name="saga-api-item-ItemsProto"></a>

### ItemsProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| items | [ItemProto](#saga-api-item-ItemProto) | repeated |  |






<a name="saga-api-item-TransferItemRequest"></a>

### TransferItemRequest
Transfer item call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| game_inventory_id | [string](#string) |  | Game&#39;s id for the Item to transfer |
| source_oauth_id | [string](#string) |  | User of Item to transfer from |
| destination_oauth_id | [string](#string) |  | User of Item to transfer to |
| store_id | [string](#string) |  |  |






<a name="saga-api-item-UpdateItemMetadata"></a>

### UpdateItemMetadata
Update Metadata call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| game_inventory_id | [string](#string) |  | Update Metadata for the Item with this id |
| metadata | [saga.common.Metadata](#saga-common-Metadata) |  | Metadata to update with |






<a name="saga-api-item-UpdateItemsMetadataRequest"></a>

### UpdateItemsMetadataRequest
Update Metadata on Item call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| update_items | [UpdateItemMetadata](#saga-api-item-UpdateItemMetadata) | repeated |  |





 

 

 

 



<a name="api_item_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/item/rpc.proto


 

 

 


<a name="saga-api-item-ItemService"></a>

### ItemService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| GetItem | [GetItemRequest](#saga-api-item-GetItemRequest) | [ItemProto](#saga-api-item-ItemProto) | Get an item |
| GetItems | [GetItemsRequest](#saga-api-item-GetItemsRequest) | [ItemsProto](#saga-api-item-ItemsProto) | Get items based on filters |
| GetItemsForPlayer | [GetItemsForPlayerRequest](#saga-api-item-GetItemsForPlayerRequest) | [ItemsProto](#saga-api-item-ItemsProto) | Get all Items for a player |
| IssueItem | [IssueItemRequest](#saga-api-item-IssueItemRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Issue an Item |
| TransferItem | [TransferItemRequest](#saga-api-item-TransferItemRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Transfer Item between users |
| BurnItem | [BurnItemRequest](#saga-api-item-BurnItemRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Burn an Item |
| UpdateItemsMetadata | [UpdateItemsMetadataRequest](#saga-api-item-UpdateItemsMetadataRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Update the Metadata for an Item |

 



<a name="api_itemtype_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/itemtype/definition.proto



<a name="saga-api-itemtype-CreateItemTypeRequest"></a>

### CreateItemTypeRequest
Create item type call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| publisher_address | [string](#string) |  |  |
| game_item_type_id | [string](#string) |  | The game&#39;s unique id for this ItemType |
| name | [string](#string) |  |  |
| pri_rev_share_settings | [PriRevShareSettings](#saga-api-itemtype-PriRevShareSettings) |  | (optional) |
| sec_rev_share_settings | [SecRevShareSettings](#saga-api-itemtype-SecRevShareSettings) |  |  |
| metadata | [saga.common.Metadata](#saga-common-Metadata) |  |  |
| withdrawable | [bool](#bool) |  | Is this ItemType withdrawable? |






<a name="saga-api-itemtype-GetItemTypeRequest"></a>

### GetItemTypeRequest
Get ItemType call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| game_item_type_id | [string](#string) |  |  |






<a name="saga-api-itemtype-GetItemTypesRequest"></a>

### GetItemTypesRequest
Get ItemTypes call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| query_options | [saga.common.QueryOptionsProto](#saga-common-QueryOptionsProto) |  |  |






<a name="saga-api-itemtype-ItemTypeProto"></a>

### ItemTypeProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |
| game_item_type_id | [string](#string) |  | The game&#39;s unique id for this ItemType |
| name | [string](#string) |  | Name of this ItemType |
| title_id | [string](#string) |  | Title this ItemType is associated with |
| pri_rev_share_settings | [PriRevShareSettings](#saga-api-itemtype-PriRevShareSettings) |  |  |
| sec_rev_share_settings | [SecRevShareSettings](#saga-api-itemtype-SecRevShareSettings) |  |  |
| withdrawable | [bool](#bool) |  | Is this iten withdrawable? |
| price_map | [PriceMap](#saga-api-itemtype-PriceMap) |  |  |
| item_type_state | [saga.proto.common.itemtype.ItemTypeState](#saga-proto-common-itemtype-ItemTypeState) |  |  |
| created_timestamp | [int64](#int64) |  | When this ItemType was created |
| updated_timestamp | [int64](#int64) |  | When this ItemType was last updated |
| metadata | [saga.common.Metadata](#saga-common-Metadata) |  | Metadata associated w ith this ItemType |






<a name="saga-api-itemtype-ItemTypesProto"></a>

### ItemTypesProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| item_types | [ItemTypeProto](#saga-api-itemtype-ItemTypeProto) | repeated |  |






<a name="saga-api-itemtype-PriRevShareSettings"></a>

### PriRevShareSettings







<a name="saga-api-itemtype-PriceMap"></a>

### PriceMap







<a name="saga-api-itemtype-SecRevShareSettings"></a>

### SecRevShareSettings







<a name="saga-api-itemtype-UpdateItemTypeMetadataPayload"></a>

### UpdateItemTypeMetadataPayload
Update Metadata on ItemType call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| game_item_type_id | [string](#string) |  | Game&#39;s ItemTypeId for the Metadata to update |
| metadata | [saga.common.Metadata](#saga-common-Metadata) |  | Metadata to update the ItemType with |






<a name="saga-api-itemtype-UpdateItemTypePayload"></a>

### UpdateItemTypePayload
Update ItemType call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| game_item_type_id | [string](#string) |  | Game&#39;s ItemTypeId for the ItemType to update |
| withdrawable | [bool](#bool) |  | withdrawable state to udpate to |





 

 

 

 



<a name="api_itemtype_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/itemtype/rpc.proto


 

 

 


<a name="saga-api-itemtype-ItemTypeService"></a>

### ItemTypeService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| GetItemType | [GetItemTypeRequest](#saga-api-itemtype-GetItemTypeRequest) | [ItemTypeProto](#saga-api-itemtype-ItemTypeProto) | Get an ItemType by Id |
| GetItemTypes | [GetItemTypesRequest](#saga-api-itemtype-GetItemTypesRequest) | [ItemTypesProto](#saga-api-itemtype-ItemTypesProto) | Get ItemTypes based on filters |
| CreateItemType | [CreateItemTypeRequest](#saga-api-itemtype-CreateItemTypeRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Create an ItemType |
| UpdateItemType | [UpdateItemTypePayload](#saga-api-itemtype-UpdateItemTypePayload) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Update the ItemType |

 



<a name="api_listing_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/listing/definition.proto



<a name="saga-api-listing-CancelListingRequest"></a>

### CancelListingRequest
Cancel the Listing call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  | User of listing to cancel |
| listing_id | [string](#string) |  | Id of Listing to cancel |






<a name="saga-api-listing-ConfirmListingRequest"></a>

### ConfirmListingRequest
Confirm the Listing call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  | User of this Listing |
| quote_id | [string](#string) |  | Quote Id of this Listing |






<a name="saga-api-listing-CreateListingQuoteRequest"></a>

### CreateListingQuoteRequest
Create Quote for a Listing call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  | User of this Listing Quote |
| game_inventory_id | [string](#string) |  | Id of GameInventory for this Listing |
| total | [string](#string) |  | Total cost amount for the Listing |
| currency | [string](#string) |  | Currency that the total is in |






<a name="saga-api-listing-GetListingsRequest"></a>

### GetListingsRequest
Get Listings call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| query_options | [saga.common.QueryOptionsProto](#saga-common-QueryOptionsProto) |  | Filter/Sorting options for the call |
| item_type_id | [string](#string) |  | Id of ItemType to get Listings for |
| token | [string](#string) |  |  |
| oauth_id | [string](#string) |  | User to get Listings for |






<a name="saga-api-listing-ListingProto"></a>

### ListingProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  | User of this Listing |
| game_inventory_id | [string](#string) |  | Item associated with this Listing |
| currency | [string](#string) |  | Type of currency the total is in |
| total | [string](#string) |  | Total price of Listing |
| created_timestamp | [uint64](#uint64) |  | When the Listing was created |






<a name="saga-api-listing-ListingQuoteProto"></a>

### ListingQuoteProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  | User of this Listing |
| quote_id | [string](#string) |  | unique QuoteId for this Listing |
| game_inventory_id | [string](#string) |  | Item associated with this Listing |
| tax | [string](#string) |  |  |
| tax_currency | [string](#string) |  | Currency the tax is in |
| total | [string](#string) |  | Total price of Listing |
| currency | [string](#string) |  | Type of currency the total is in |
| created_timestamp | [uint64](#uint64) |  | When the Listing was created |






<a name="saga-api-listing-ListingsProto"></a>

### ListingsProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| listings | [ListingProto](#saga-api-listing-ListingProto) | repeated |  |





 

 

 

 



<a name="api_listing_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/listing/rpc.proto


 

 

 


<a name="saga-api-listing-ListingService"></a>

### ListingService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| CreateListingQuote | [CreateListingQuoteRequest](#saga-api-listing-CreateListingQuoteRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Get a quote for a Listing |
| ConfirmListing | [ConfirmListingRequest](#saga-api-listing-ConfirmListingRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Confirm the Listing |
| CancelListing | [CancelListingRequest](#saga-api-listing-CancelListingRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Cancel the Listing |
| GetListings | [GetListingsRequest](#saga-api-listing-GetListingsRequest) | [ListingsProto](#saga-api-listing-ListingsProto) | Get Listings based on filters |

 



<a name="api_myth_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/myth/definition.proto



<a name="saga-api-myth-ConfirmBuyingMythTokenRequest"></a>

### ConfirmBuyingMythTokenRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| quote_id | [string](#string) |  |  |
| user_id | [string](#string) |  |  |
| credit_card_info | [saga.api.payment.CardPaymentData](#saga-api-payment-CardPaymentData) |  |  |






<a name="saga-api-myth-ConfirmMythTokenWithdrawalRequest"></a>

### ConfirmMythTokenWithdrawalRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| quote_id | [string](#string) |  | Quote Id for this withdrawal |






<a name="saga-api-myth-CurrencyExchangeProto"></a>

### CurrencyExchangeProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| bid | [string](#string) |  | bid amount from Uphold |
| ask | [string](#string) |  | ask amount from Uphold |






<a name="saga-api-myth-GasFeeProto"></a>

### GasFeeProto
Proto of converted units from gwei/eth


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| gwei_amount | [string](#string) |  | GWEI amount to convert |
| eth_amount | [string](#string) |  | ETH amount to convert |
| converted_amount | [string](#string) |  | Amount after conversion |
| converted_currency | [string](#string) |  | Currency to convert GWEI/ETH to |






<a name="saga-api-myth-QuoteBuyingMythTokenRequest"></a>

### QuoteBuyingMythTokenRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| quantity | [string](#string) |  | Amount of MYTH Tokens to buy |
| credit_card_info | [saga.api.payment.CardPaymentData](#saga-api-payment-CardPaymentData) |  | Credit card payment |
| denomination_currency | [string](#string) |  |  |
| origin_sub_account | [string](#string) |  |  |
| user_id | [string](#string) |  | User that is buying MYTH Tokens |






<a name="saga-api-myth-QuoteBuyingMythTokenResponse"></a>

### QuoteBuyingMythTokenResponse



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| uphold_quote_id | [string](#string) |  |  |
| origin_sub_account | [string](#string) |  |  |






<a name="saga-api-myth-QuoteMythTokenWithdrawalRequest"></a>

### QuoteMythTokenWithdrawalRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| user_id | [string](#string) |  |  |
| quantity | [string](#string) |  |  |






<a name="saga-api-myth-QuoteMythTokenWithdrawalResponse"></a>

### QuoteMythTokenWithdrawalResponse



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| total_amount | [string](#string) |  | Amount of MYTH Tokens to withdraw |
| gas_fee | [string](#string) |  | Gas cost of withdrawal |





 

 

 

 



<a name="api_myth_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/myth/rpc.proto


 

 

 


<a name="saga-api-myth-MythService"></a>

### MythService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| GetGasFee | [.google.protobuf.Empty](#google-protobuf-Empty) | [GasFeeProto](#saga-api-myth-GasFeeProto) | Get the current MYTH price of 1 gas unit |
| GetCurrencyExchange | [.google.protobuf.Empty](#google-protobuf-Empty) | [CurrencyExchangeProto](#saga-api-myth-CurrencyExchangeProto) | Get the exchange rate of MYTH Token |
| QuoteBuyingMythToken | [QuoteBuyingMythTokenRequest](#saga-api-myth-QuoteBuyingMythTokenRequest) | [QuoteBuyingMythTokenResponse](#saga-api-myth-QuoteBuyingMythTokenResponse) | Quote buying MYTH Tokens |
| ConfirmBuyingMythToken | [ConfirmBuyingMythTokenRequest](#saga-api-myth-ConfirmBuyingMythTokenRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Confirm buying of MYTH Tokens |
| QuoteMythTokenWithdrawal | [QuoteMythTokenWithdrawalRequest](#saga-api-myth-QuoteMythTokenWithdrawalRequest) | [QuoteMythTokenWithdrawalResponse](#saga-api-myth-QuoteMythTokenWithdrawalResponse) | Quote withdrawing MYTH Tokens |
| ConfirmMythTokenWithdrawal | [ConfirmMythTokenWithdrawalRequest](#saga-api-myth-ConfirmMythTokenWithdrawalRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Confirm withdrawing MYTH Tokens |

 



<a name="api_offer_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/offer/definition.proto



<a name="saga-api-offer-CancelOfferRequest"></a>

### CancelOfferRequest
Cancel Offer call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  | User that the Offer belongs to |
| offer_id | [string](#string) |  | Id of the Offer to Cancel |






<a name="saga-api-offer-ConfirmOfferRequest"></a>

### ConfirmOfferRequest
Confirm Offer call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  |  |
| quote_id | [string](#string) |  | Quote Id of the Offer

@exclude TODO: where&#39;s the payment data? Also are offers fronted payment or we somehow storing it (securely) then charging when needed |






<a name="saga-api-offer-CreateOfferQuoteRequest"></a>

### CreateOfferQuoteRequest
Create Offer Quote call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  |  |
| game_inventory_id | [string](#string) |  | GameInventory Id of the Offer |
| total | [string](#string) |  | Total quoted for the Offer |
| currency | [string](#string) |  | Currency the total is in |






<a name="saga-api-offer-GetOffersRequest"></a>

### GetOffersRequest
Get Offers call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| query_options | [saga.common.QueryOptionsProto](#saga-common-QueryOptionsProto) |  | Filter/Sort options for the call |
| item_type_id | [string](#string) |  | Id of ItemType to get Offers for |
| token | [string](#string) |  | Token to get Offers for |
| oauth_id | [string](#string) |  | User to get Offers for |






<a name="saga-api-offer-OfferProto"></a>

### OfferProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  | User of the Offer |
| game_inventory_id | [string](#string) |  | GameInventory Id of the Offer |
| currency | [string](#string) |  | Currency of the total |
| total | [string](#string) |  | Total cost of the offer |
| created_timestamp | [uint64](#uint64) |  | When the offer was created |






<a name="saga-api-offer-OfferQuoteProto"></a>

### OfferQuoteProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  | User of this Offer Quote |
| quote_id | [string](#string) |  | Id of this Offer Quote |
| game_inventory_id | [string](#string) |  | GameInventory Id for this Offer Quote |
| tax | [string](#string) |  | Amount of tax

@exclude and fees? |
| tax_currency | [string](#string) |  | Currency that Tax is in |
| total | [string](#string) |  | Total cost of the offer |
| currency | [string](#string) |  | Currency the total is in |
| created_timestamp | [uint64](#uint64) |  | When the Offer was created |






<a name="saga-api-offer-OffersProto"></a>

### OffersProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| offers | [OfferProto](#saga-api-offer-OfferProto) | repeated |  |





 

 

 

 



<a name="api_offer_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/offer/rpc.proto


 

 

 


<a name="saga-api-offer-OfferService"></a>

### OfferService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| CreateOfferQuote | [CreateOfferQuoteRequest](#saga-api-offer-CreateOfferQuoteRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Create an Offer quote |
| ConfirmOffer | [ConfirmOfferRequest](#saga-api-offer-ConfirmOfferRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Confirm the Offer |
| CancelOffer | [CancelOfferRequest](#saga-api-offer-CancelOfferRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Cancel the Offer |
| GetOffers | [GetOffersRequest](#saga-api-offer-GetOffersRequest) | [OffersProto](#saga-api-offer-OffersProto) | Get Offers based on filters |

 



<a name="api_order_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/order/definition.proto



<a name="saga-api-order-ConfirmOrderRequest"></a>

### ConfirmOrderRequest
Confirm Order call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| title_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |
| quote_id | [string](#string) |  |  |
| payment_provider_data | [PaymentProviderData](#saga-api-order-PaymentProviderData) |  |  |
| fraud_session_id | [string](#string) |  |  |






<a name="saga-api-order-CreateOrderQuoteRequest"></a>

### CreateOrderQuoteRequest
Create Order Quote call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| title_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |
| subtotal | [string](#string) |  |  |
| payment_provider_data | [PaymentProviderData](#saga-api-order-PaymentProviderData) |  |  |
| game_item_type_id | [string](#string) |  |  |
| listing_address | [string](#string) |  |  |
| buy_myth_token | [bool](#bool) |  |  |
| withdraw_myth_token | [bool](#bool) |  |  |
| myth_to_usd | [bool](#bool) |  |  |
| withdraw_item_address | [string](#string) |  |  |
| no_op | [bool](#bool) |  |  |






<a name="saga-api-order-CreditCardData"></a>

### CreditCardData



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| first_name | [string](#string) |  |  |
| last_name | [string](#string) |  |  |
| address_line_1 | [string](#string) |  |  |
| address_line_2 | [string](#string) |  |  |
| city | [string](#string) |  |  |
| state | [string](#string) |  |  |
| postal_code | [string](#string) |  |  |
| country_name | [string](#string) |  |  |
| country_iso_alpha_2 | [string](#string) |  |  |
| expiration_month | [string](#string) |  |  |
| expiration_year | [string](#string) |  |  |
| card_type | [string](#string) |  |  |
| instrument_id | [string](#string) |  |  |
| payment_method_token_id | [string](#string) |  |  |






<a name="saga-api-order-PaymentProviderData"></a>

### PaymentProviderData



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| credit_card_data | [CreditCardData](#saga-api-order-CreditCardData) |  |  |
| uphold_card_id | [string](#string) |  |  |






<a name="saga-api-order-QuoteProto"></a>

### QuoteProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |
| quote_id | [string](#string) |  |  |
| tax | [string](#string) |  |  |
| tax_currency | [string](#string) |  |  |
| total | [string](#string) |  |  |
| currency | [string](#string) |  |  |
| payment_provider_id | [PaymentProviderId](#saga-api-order-PaymentProviderId) |  |  |
| buyer_oauth_id | [string](#string) |  |  |
| seller_oauth_id | [string](#string) |  |  |
| conversion_rate | [string](#string) |  |  |
| created_timestamp | [uint64](#uint64) |  |  |





 


<a name="saga-api-order-PaymentProviderId"></a>

### PaymentProviderId


| Name | Number | Description |
| ---- | ------ | ----------- |
| CREDIT_CARD | 0 |  |
| UPHOLD | 1 |  |
| MYTHICAL | 2 |  |


 

 

 



<a name="api_order_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/order/rpc.proto


 

 

 


<a name="saga-api-order-OrderService"></a>

### OrderService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| CreateOrderQuote | [CreateOrderQuoteRequest](#saga-api-order-CreateOrderQuoteRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Create an Order |
| ConfirmOrder | [ConfirmOrderRequest](#saga-api-order-ConfirmOrderRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Confirm the Order |

 



<a name="api_payment_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/payment/definition.proto



<a name="saga-api-payment-Address"></a>

### Address



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| first_name | [string](#string) |  |  |
| last_name | [string](#string) |  |  |
| address_line_1 | [string](#string) |  |  |
| address_line_2 | [string](#string) |  |  |
| city | [string](#string) |  |  |
| state | [string](#string) |  |  |
| postal_code | [string](#string) |  |  |
| country_name | [string](#string) |  |  |
| country_iso_alpha_2 | [string](#string) |  |  |






<a name="saga-api-payment-CardPaymentData"></a>

### CardPaymentData



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| make_default | [bool](#bool) |  | Make this the default payment |
| cybersource | [CybersourcePaymentData](#saga-api-payment-CybersourcePaymentData) |  |  |






<a name="saga-api-payment-CreatePaymentMethodRequest"></a>

### CreatePaymentMethodRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  |  |
| card_payment_data | [CardPaymentData](#saga-api-payment-CardPaymentData) |  |  |
| address | [Address](#saga-api-payment-Address) |  |  |






<a name="saga-api-payment-CybersourcePaymentData"></a>

### CybersourcePaymentData



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| expiration_month | [string](#string) |  |  |
| expiration_year | [string](#string) |  |  |
| card_type | [string](#string) |  |  |
| instrument_id | [string](#string) |  |  |






<a name="saga-api-payment-DeletePaymentMethodRequest"></a>

### DeletePaymentMethodRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  |  |
| card_payment_data | [CardPaymentData](#saga-api-payment-CardPaymentData) |  |  |






<a name="saga-api-payment-GetPaymentMethodRequest"></a>

### GetPaymentMethodRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  |  |






<a name="saga-api-payment-PaymentMethodProto"></a>

### PaymentMethodProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  | User of this Payment Method |
| card_payment_data | [CardPaymentData](#saga-api-payment-CardPaymentData) |  | Card data |
| address | [Address](#saga-api-payment-Address) |  | Address of this Payment Method |






<a name="saga-api-payment-UpdatePaymentMethodRequest"></a>

### UpdatePaymentMethodRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  |  |
| card_payment_data | [CardPaymentData](#saga-api-payment-CardPaymentData) |  |  |
| address | [Address](#saga-api-payment-Address) |  |  |





 

 

 

 



<a name="api_payment_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/payment/rpc.proto


 

 

 


<a name="saga-api-payment-PaymentService"></a>

### PaymentService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| CreatePaymentMethod | [CreatePaymentMethodRequest](#saga-api-payment-CreatePaymentMethodRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Create a Payment Method |
| GetPaymentMethod | [GetPaymentMethodRequest](#saga-api-payment-GetPaymentMethodRequest) | [PaymentMethodProto](#saga-api-payment-PaymentMethodProto) | Get Payment Method for a user |
| UpdatePaymentMethod | [UpdatePaymentMethodRequest](#saga-api-payment-UpdatePaymentMethodRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Update the Payment Method for a User |
| DeletePaymentMethod | [DeletePaymentMethodRequest](#saga-api-payment-DeletePaymentMethodRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Delete a Payment Method for a User |

 



<a name="api_title_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/title/definition.proto



<a name="saga-api-title-GetTitlesRequest"></a>

### GetTitlesRequest
Get Titles call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| query_options | [saga.common.QueryOptionsProto](#saga-common-QueryOptionsProto) |  | Sort/Filter options |






<a name="saga-api-title-TitleProto"></a>

### TitleProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| title_id | [string](#string) |  | Unique id |
| name | [string](#string) |  | Name for this Title |
| created_timestamp | [int64](#int64) |  | When this Title was created |






<a name="saga-api-title-TitlesProto"></a>

### TitlesProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| titles | [TitleProto](#saga-api-title-TitleProto) | repeated |  |





 

 

 

 



<a name="api_title_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/title/rpc.proto


 

 

 


<a name="saga-api-title-TitleService"></a>

### TitleService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| GetTitles | [GetTitlesRequest](#saga-api-title-GetTitlesRequest) | [TitlesProto](#saga-api-title-TitlesProto) | Get all titles from a filter |

 



<a name="api_transaction_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/transaction/definition.proto



<a name="saga-api-transaction-GetTransactionsForItemTypeRequest"></a>

### GetTransactionsForItemTypeRequest
Get Transactions for an ItemType


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| item_type_id | [string](#string) |  | Id of ItemType to query on |
| token_id | [string](#string) |  | Token Id |
| query_options | [saga.common.QueryOptionsProto](#saga-common-QueryOptionsProto) |  | Sort/Filter options |






<a name="saga-api-transaction-GetTransactionsForPlayerRequest"></a>

### GetTransactionsForPlayerRequest
Get Transactions for a Player call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  | Player to get Transactions for |
| query_options | [saga.common.QueryOptionsProto](#saga-common-QueryOptionsProto) |  | Sort/Filter options |






<a name="saga-api-transaction-TransactionProto"></a>

### TransactionProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| transaction_id | [string](#string) |  | Unique Id |
| title_id | [string](#string) |  | title that this transaction is from |
| created_timestamp | [int64](#int64) |  | When this Transaction was created |






<a name="saga-api-transaction-TransactionsProto"></a>

### TransactionsProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| transactions | [TransactionProto](#saga-api-transaction-TransactionProto) | repeated |  |





 

 

 

 



<a name="api_transaction_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/transaction/rpc.proto


 

 

 


<a name="saga-api-transaction-TransactionService"></a>

### TransactionService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| GetTransactionsForPlayer | [GetTransactionsForPlayerRequest](#saga-api-transaction-GetTransactionsForPlayerRequest) | [TransactionsProto](#saga-api-transaction-TransactionsProto) | Get all Transactions for a Player |
| GetTransactionsForItemType | [GetTransactionsForItemTypeRequest](#saga-api-transaction-GetTransactionsForItemTypeRequest) | [TransactionsProto](#saga-api-transaction-TransactionsProto) | Get all Transactions for an ItemType |

 



<a name="api_user_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/user/definition.proto



<a name="saga-api-user-CreateUserRequest"></a>

### CreateUserRequest
Create User call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| title_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |






<a name="saga-api-user-FungibleToken"></a>

### FungibleToken



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| name | [string](#string) |  | Name for this Fungible Token |
| game_id | [string](#string) |  | Id of game this is part of |
| quantity | [string](#string) |  | Amount of token available |
| contract_address | [string](#string) |  | Contract address of this Fungible Token |






<a name="saga-api-user-GetUserRequest"></a>

### GetUserRequest
Get User call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  | Get the user of this Id |






<a name="saga-api-user-GetUsersRequest"></a>

### GetUsersRequest
Get Users call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| query_options | [saga.common.QueryOptionsProto](#saga-common-QueryOptionsProto) |  | Sort/Filter options |






<a name="saga-api-user-GetWalletAssetsRequest"></a>

### GetWalletAssetsRequest
Get wallet assets call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  | (optional) User to get Wallet for |
| publisher_id | [string](#string) |  | (optional) Get related Mythical chain wallet for this Publisher Id |
| partner_id | [string](#string) |  | (optional) Get related Mythical chain wallet for this Partner Id |






<a name="saga-api-user-NftItem"></a>

### NftItem



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| item_type_name | [string](#string) |  | Name of the ItemType |
| item_type_id | [string](#string) |  | Id of the ItemType |
| contract_address | [string](#string) |  | Contract address of the NFT |
| token_id | [string](#string) |  |  |






<a name="saga-api-user-UpdateUserRequest"></a>

### UpdateUserRequest
Update User call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| title_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |






<a name="saga-api-user-UserProto"></a>

### UserProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  | Unique Id (oauth) for this User |
| chain_address | [string](#string) |  | Address on the chain |
| user_state | [saga.proto.common.user.UserState](#saga-proto-common-user-UserState) |  | What state the User is in |
| created_timestamp | [uint64](#uint64) |  | When this User was created |






<a name="saga-api-user-UsersProto"></a>

### UsersProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| saga_users | [UserProto](#saga-api-user-UserProto) | repeated |  |






<a name="saga-api-user-WalletAsset"></a>

### WalletAsset



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| nmyth_token | [string](#string) |  |  |
| nmyth_token_quantity | [string](#string) |  | Quantity of nMYTH tokens |
| ausd_token | [string](#string) |  |  |
| ausd_token_quantity | [string](#string) |  | Quantity of aUSD tokens |
| nft_items | [NftItem](#saga-api-user-NftItem) | repeated | List of NFT Items |
| fungible_tokens | [FungibleToken](#saga-api-user-FungibleToken) | repeated | List of Fungible Tokens |





 

 

 

 



<a name="api_user_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/user/rpc.proto


 

 

 


<a name="saga-api-user-UserService"></a>

### UserService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| GetUser | [GetUserRequest](#saga-api-user-GetUserRequest) | [UserProto](#saga-api-user-UserProto) | Get a User given their oauth Id |
| GetUsers | [GetUsersRequest](#saga-api-user-GetUsersRequest) | [UsersProto](#saga-api-user-UsersProto) | Get a list of Users based on query parameters |
| CreateUser | [CreateUserRequest](#saga-api-user-CreateUserRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Create a User |
| UpdateUser | [UpdateUserRequest](#saga-api-user-UpdateUserRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Update a User |
| GetWalletAssets | [GetWalletAssetsRequest](#saga-api-user-GetWalletAssetsRequest) | [WalletAsset](#saga-api-user-WalletAsset) | Get assets for a User/publisher/partner |

 



<a name="common_common-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## common/common.proto



<a name="saga-common-ErrorResponse"></a>

### ErrorResponse



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| error_code | [ErrorCode](#saga-common-ErrorCode) |  | The type of error that has occurred |
| msg | [string](#string) |  | Description of error |






<a name="saga-common-Metadata"></a>

### Metadata
Metadata properties of Item


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| name | [string](#string) |  | Name of item |
| description | [string](#string) |  | Description of the item |
| image | [string](#string) |  | URL to the image of the item |
| properties | [google.protobuf.Struct](#google-protobuf-Struct) |  | Additional properties about the Item in a &lt;Key,Value&gt; structure |






<a name="saga-common-ReceivedResponse"></a>

### ReceivedResponse



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |





 


<a name="saga-common-ErrorCode"></a>

### ErrorCode


| Name | Number | Description |
| ---- | ------ | ----------- |
| UNKNOWN | 0 | Unknown Error |
| INTERNAL | 1 | Internal System Error |
| GENERAL | 2 | Generic Error Code |
| TIMEOUT | 3 | Operation Timed Out |
| ITEM_NOT_FOUND | 4 | The specified item could not be found |
| ITEM_TYPE_NOT_FOUND | 5 | The specified itme type could not be found |
| USER_NOT_FOUND | 6 | The specified user could not be found |


 

 

 



<a name="common_finalization-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## common/finalization.proto


 


<a name="saga-common-Finalized"></a>

### Finalized


| Name | Number | Description |
| ---- | ------ | ----------- |
| ALL | 0 |  |
| YES | 1 |  |
| NO | 2 |  |


 

 

 



<a name="common_gamecoin_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## common/gamecoin/definition.proto


 


<a name="saga-proto-common-gamecoin-GameCoinState"></a>

### GameCoinState


| Name | Number | Description |
| ---- | ------ | ----------- |
| FAILED | 0 | Failed to issue/transfer/burn Game Coin |
| ISSUED | 1 | Game Coin issued successfully |
| TRANSFERRED | 2 | Game Coin transferred successfully |
| BURNED | 3 | Game Coin burned successfully |


 

 

 



<a name="common_item_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## common/item/definition.proto


 


<a name="saga-proto-common-item-ItemState"></a>

### ItemState


| Name | Number | Description |
| ---- | ------ | ----------- |
| FAILED | 0 | Failed to perform current Item action |
| ISSUED | 1 | Item issued successfully |
| LISTED | 2 | Item listed successfully |
| TRANSFERRED | 3 | Item transferred successfully |
| BURNED | 4 | item no longer exists on the blockchain so the API will have to keep track through transaction history |
| LISTING_CLOSED | 5 | Listing for Item is closed |
| WITHDRAWN | 6 | Item withdrawn successfully |
| DEPOSITED | 7 | Item deposited successfully |


 

 

 



<a name="common_itemtype_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## common/itemtype/definition.proto


 


<a name="saga-proto-common-itemtype-ItemTypeState"></a>

### ItemTypeState


| Name | Number | Description |
| ---- | ------ | ----------- |
| FAILED | 0 | ItemType failed to perform current action |
| CREATED | 1 | ItemType created successfully |
| FROZEN | 2 | ItemType is Frozen |
| SOLD_OUT | 3 | ItemType is Sold Out |
| EXPIRED | 4 | ItemType has been expired |


 

 

 



<a name="common_listing_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## common/listing/definition.proto


 


<a name="saga-proto-common-listing-ListingState"></a>

### ListingState


| Name | Number | Description |
| ---- | ------ | ----------- |
| FAILED | 0 | Listing has failed current action |
| CREATED | 1 | Listing created successfully |
| SOLD | 2 | Listing sold successfully |
| CANCELLED | 3 | Listing cancelled successfully |


 

 

 



<a name="common_myth_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## common/myth/definition.proto


 


<a name="saga-proto-common-myth-MythTokenState"></a>

### MythTokenState


| Name | Number | Description |
| ---- | ------ | ----------- |
| FAILED | 0 | MYTH Token failed to be transferred/withdrawn |
| TRANSFERRED | 1 | MYTH Token transferred successfully |
| WITHDRAWN | 2 | MYTH Token withdrawn successfully |


 

 

 



<a name="common_offer_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## common/offer/definition.proto


 


<a name="saga-proto-common-offer-OfferState"></a>

### OfferState


| Name | Number | Description |
| ---- | ------ | ----------- |
| FAILED | 0 | Offer failed current action |
| CREATED | 1 | Offer created successfully |
| CONFIRMED | 2 | Offer confirmed successfully |
| CANCELLED | 3 | Offer canceled |


 

 

 



<a name="common_order_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## common/order/definition.proto


 


<a name="saga-proto-common-order-OrderState"></a>

### OrderState


| Name | Number | Description |
| ---- | ------ | ----------- |
| FAILED | 0 | Order failed current action |
| STARTED | 1 | Order started |
| COMPLETE | 2 | Order completed successfully |
| PAID | 3 | Order paid successfully |
| EXPIRED | 4 | Order has expired |
| REFUNDED | 5 | Order refunded successfully |


 

 

 



<a name="common_payment_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## common/payment/definition.proto


 


<a name="saga-proto-common-payment-PaymentMethodUpdateStatus"></a>

### PaymentMethodUpdateStatus
Payment Update Status

| Name | Number | Description |
| ---- | ------ | ----------- |
| UPDATED | 0 |  |
| CREATED | 1 |  |
| DELETED | 2 |  |



<a name="saga-proto-common-payment-PaymentProviderId"></a>

### PaymentProviderId
Types of Payments

| Name | Number | Description |
| ---- | ------ | ----------- |
| BRAINTREE | 0 |  |
| BITPAY | 1 |  |
| CYBERSOURCE | 2 |  |
| UPHOLD | 3 |  |


 

 

 



<a name="common_query-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## common/query.proto



<a name="saga-common-ExpressionProto"></a>

### ExpressionProto
Allowed expression in a query. Expression is a combination of [attribute_name | conditional | value]


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| attribute_name | [string](#string) |  | name of the attribute to filter on |
| conditional | [FilterConditional](#saga-common-FilterConditional) |  | how to filter on the attribute (See FilterConditional) |
| double_value | [double](#double) |  |  |
| string_value | [string](#string) |  |  |
| bool_value | [bool](#bool) |  |  |






<a name="saga-common-FilterValueProto"></a>

### FilterValueProto
container to allow building out a full filter of Expressions and FilterOperations


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| expression | [ExpressionProto](#saga-common-ExpressionProto) |  |  |
| operation | [FilterOperation](#saga-common-FilterOperation) |  |  |






<a name="saga-common-QueryOptionsProto"></a>

### QueryOptionsProto
Options allowed when querying


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| filter_options | [FilterValueProto](#saga-common-FilterValueProto) | repeated | Complete filter chain of attributes to filter on |
| page_size | [int32](#int32) |  | Size of the page of results |
| sort_order | [SortOrder](#saga-common-SortOrder) |  | Which order to sort |
| sort_attribute | [string](#string) |  | Which attribute to sort on |





 


<a name="saga-common-FilterConditional"></a>

### FilterConditional
Allowed compare operations when filtering

| Name | Number | Description |
| ---- | ------ | ----------- |
| EQUALS | 0 | ObjectA = ObjectB |
| EQUALS_LESS_THAN | 1 | ObjectA &lt;= ObjectB |
| LESS_THAN | 2 | ObjectA &lt; ObjectB |
| EQUALS_GREATER_THAN | 3 | ObjectA &gt;= ObjectB |
| GREATER_THAN | 4 | ObjectA &gt; ObjectB |
| NOT_EQUALS | 5 | ObjectA != ObjectB |
| CONTAINS | 6 | ObjectA is in ListB |
| NOT_CONTAINS | 7 | ObjectA is not in ListB |



<a name="saga-common-FilterOperation"></a>

### FilterOperation
Alloed operations when chaining filters

| Name | Number | Description |
| ---- | ------ | ----------- |
| AND | 0 | FilterA &amp;&amp; FilterB |
| OR | 1 | FilterA || FilterB |


 

 

 



<a name="common_sort-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## common/sort.proto


 


<a name="saga-common-SortOrder"></a>

### SortOrder


| Name | Number | Description |
| ---- | ------ | ----------- |
| ASC | 0 | Sort ascending |
| DESC | 1 | Sort descending |


 

 

 



<a name="common_user_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## common/user/definition.proto


 


<a name="saga-proto-common-user-UserState"></a>

### UserState


| Name | Number | Description |
| ---- | ------ | ----------- |
| FAILED | 0 | User failed current action |
| LINKED | 1 | User linked successfully |


 

 

 



<a name="streams_bridge_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## streams/bridge/definition.proto



<a name="saga-rpc-streams-bridge-BridgeStatusUpdate"></a>

### BridgeStatusUpdate
Results from a Bridge status update gRPC stream call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  |  |
| game_item_type_id | [string](#string) |  |  |
| game_inventory_id | [string](#string) |  |  |
| destination_address | [string](#string) |  |  |
| destination_chain | [string](#string) |  |  |
| origin_address | [string](#string) |  |  |
| mythical_transaction_id | [string](#string) |  |  |
| mainnet_transaction_id | [string](#string) |  |  |






<a name="saga-rpc-streams-bridge-BridgeUpdate"></a>

### BridgeUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| error | [saga.common.ErrorResponse](#saga-common-ErrorResponse) |  |  |
| status_update | [BridgeStatusUpdate](#saga-rpc-streams-bridge-BridgeStatusUpdate) |  |  |





 

 

 

 



<a name="streams_common-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## streams/common.proto



<a name="saga-rpc-streams-Subscribe"></a>

### Subscribe



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| title_id | [string](#string) |  |  |





 

 

 

 



<a name="streams_gamecoin_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## streams/gamecoin/definition.proto



<a name="saga-rpc-streams-gamecoin-GameCoinStatusConfirmRequest"></a>

### GameCoinStatusConfirmRequest
GameCoin information sent on a confirm request


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |
| title_id | [string](#string) |  |  |
| currency_id | [string](#string) |  |  |
| game_coin_state | [saga.proto.common.gamecoin.GameCoinState](#saga-proto-common-gamecoin-GameCoinState) |  |  |






<a name="saga-rpc-streams-gamecoin-GameCoinStatusUpdate"></a>

### GameCoinStatusUpdate
Results from a GameCoin status update gRPC stream call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  | User of GameCoin |
| currency_id | [string](#string) |  |  |
| coin_count | [int32](#int32) |  | Amount of coins |
| game_coin_state | [saga.proto.common.gamecoin.GameCoinState](#saga-proto-common-gamecoin-GameCoinState) |  | State of the GameCoin, see GameCoinState |






<a name="saga-rpc-streams-gamecoin-GameCoinUpdate"></a>

### GameCoinUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| error | [saga.common.ErrorResponse](#saga-common-ErrorResponse) |  |  |
| status_update | [GameCoinStatusUpdate](#saga-rpc-streams-gamecoin-GameCoinStatusUpdate) |  |  |





 

 

 

 



<a name="streams_item_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## streams/item/definition.proto



<a name="saga-rpc-streams-item-ItemStatusUpdate"></a>

### ItemStatusUpdate
Results from an Item status update gRPC stream call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| game_inventory_id | [string](#string) |  | Game&#39;s unique Id for the Item |
| game_item_type_id | [string](#string) |  | Game&#39;s ItemTypeId for the ItemType for this Item |
| oauth_id | [string](#string) |  | User for this Item |
| serial_number | [int32](#int32) |  |  |
| metadata_uri | [string](#string) |  | Metadata address |
| item_state | [saga.proto.common.item.ItemState](#saga-proto-common-item-ItemState) |  | State of the Item, see ItemState |






<a name="saga-rpc-streams-item-ItemUpdate"></a>

### ItemUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| error | [saga.common.ErrorResponse](#saga-common-ErrorResponse) |  |  |
| status_update | [ItemStatusUpdate](#saga-rpc-streams-item-ItemStatusUpdate) |  |  |





 

 

 

 



<a name="streams_itemtype_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## streams/itemtype/definition.proto



<a name="saga-rpc-streams-itemtype-ItemTypeStatusUpdate"></a>

### ItemTypeStatusUpdate
Results from a ItemType status update gRPC stream call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| game_item_type_id | [string](#string) |  | Game&#39;s unique id for this ItemType |
| item_type_state | [saga.proto.common.itemtype.ItemTypeState](#saga-proto-common-itemtype-ItemTypeState) |  | State of the ItemType, see ItemTypeState |






<a name="saga-rpc-streams-itemtype-ItemTypeUpdate"></a>

### ItemTypeUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| error | [saga.common.ErrorResponse](#saga-common-ErrorResponse) |  |  |
| status_update | [ItemTypeStatusUpdate](#saga-rpc-streams-itemtype-ItemTypeStatusUpdate) |  |  |





 

 

 

 



<a name="streams_listing_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## streams/listing/definition.proto



<a name="saga-rpc-streams-listing-ListingStatusUpdate"></a>

### ListingStatusUpdate
Results from a Listing status update gRPC stream call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  | User of this Listing |
| quote_id | [string](#string) |  | Quote id associated to this Listing |
| listing_id | [string](#string) |  | Unique id for this listing |
| total | [string](#string) |  | total cose on listing |
| listing_state | [saga.proto.common.listing.ListingState](#saga-proto-common-listing-ListingState) |  | State of the Listing, see ListingState |






<a name="saga-rpc-streams-listing-ListingUpdate"></a>

### ListingUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| error | [saga.common.ErrorResponse](#saga-common-ErrorResponse) |  |  |
| status_update | [ListingStatusUpdate](#saga-rpc-streams-listing-ListingStatusUpdate) |  |  |





 

 

 

 



<a name="streams_myth_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## streams/myth/definition.proto



<a name="saga-rpc-streams-myth-MythTokenStatusUpdate"></a>

### MythTokenStatusUpdate
Results from a MYTH Token status update gRPC stream call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| token_state | [saga.proto.common.myth.MythTokenState](#saga-proto-common-myth-MythTokenState) |  | State of the MYTH Token, see MythTokenState |






<a name="saga-rpc-streams-myth-MythTokenUpdate"></a>

### MythTokenUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| error | [saga.common.ErrorResponse](#saga-common-ErrorResponse) |  |  |
| status_update | [MythTokenStatusUpdate](#saga-rpc-streams-myth-MythTokenStatusUpdate) |  |  |





 

 

 

 



<a name="streams_offer_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## streams/offer/definition.proto



<a name="saga-rpc-streams-offer-OfferStatusUpdate"></a>

### OfferStatusUpdate
Results from a Offer status update gRPC stream call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  | User of this Offer |
| quote_id | [string](#string) |  | Quote associated to this Offer |
| offer_id | [string](#string) |  | Unique id for this Offer |
| game_inventory_id | [string](#string) |  | Game&#39;s id for the Item associated with this Offer |
| total | [string](#string) |  | Total price for the offer |
| offer_state | [saga.proto.common.offer.OfferState](#saga-proto-common-offer-OfferState) |  | State of the Offer, see OfferState |






<a name="saga-rpc-streams-offer-OfferUpdate"></a>

### OfferUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| error | [saga.common.ErrorResponse](#saga-common-ErrorResponse) |  |  |
| status_update | [OfferStatusUpdate](#saga-rpc-streams-offer-OfferStatusUpdate) |  |  |





 

 

 

 



<a name="streams_order_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## streams/order/definition.proto



<a name="saga-rpc-streams-order-OrderStatusUpdate"></a>

### OrderStatusUpdate
Results from an Order status update gRPC stream call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  | User of the Order |
| quote_id | [string](#string) |  |  |
| order_id | [string](#string) |  |  |
| total | [string](#string) |  | Total price of the Order |
| order_state | [saga.proto.common.order.OrderState](#saga-proto-common-order-OrderState) |  | State of the Order, see OrderState |






<a name="saga-rpc-streams-order-OrderUpdate"></a>

### OrderUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| error | [saga.common.ErrorResponse](#saga-common-ErrorResponse) |  |  |
| status_update | [OrderStatusUpdate](#saga-rpc-streams-order-OrderStatusUpdate) |  |  |





 

 

 

 



<a name="streams_payment_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## streams/payment/definition.proto



<a name="saga-rpc-streams-payment-PaymentMethodStatusUpdate"></a>

### PaymentMethodStatusUpdate
Result of payment method creation, update, or deletion


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  |  |
| default | [bool](#bool) |  |  |
| payment_method_status | [saga.proto.common.payment.PaymentMethodUpdateStatus](#saga-proto-common-payment-PaymentMethodUpdateStatus) |  |  |






<a name="saga-rpc-streams-payment-PaymentUpdate"></a>

### PaymentUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| error | [saga.common.ErrorResponse](#saga-common-ErrorResponse) |  |  |
| status_update | [PaymentMethodStatusUpdate](#saga-rpc-streams-payment-PaymentMethodStatusUpdate) |  |  |





 

 

 

 



<a name="streams_stream-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## streams/stream.proto



<a name="saga-rpc-streams-StatusConfirmRequest"></a>

### StatusConfirmRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |






<a name="saga-rpc-streams-StatusUpdate"></a>

### StatusUpdate
Returned results on sending a Status stream call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |
| bridge_update | [bridge.BridgeUpdate](#saga-rpc-streams-bridge-BridgeUpdate) |  |  |
| game_coin_update | [gamecoin.GameCoinUpdate](#saga-rpc-streams-gamecoin-GameCoinUpdate) |  |  |
| item_update | [item.ItemUpdate](#saga-rpc-streams-item-ItemUpdate) |  |  |
| item_type_update | [itemtype.ItemTypeUpdate](#saga-rpc-streams-itemtype-ItemTypeUpdate) |  |  |
| listing_update | [listing.ListingUpdate](#saga-rpc-streams-listing-ListingUpdate) |  |  |
| myth_token_update | [myth.MythTokenUpdate](#saga-rpc-streams-myth-MythTokenUpdate) |  |  |
| offer_update | [offer.OfferUpdate](#saga-rpc-streams-offer-OfferUpdate) |  |  |
| order_update | [order.OrderUpdate](#saga-rpc-streams-order-OrderUpdate) |  |  |
| payment_update | [payment.PaymentUpdate](#saga-rpc-streams-payment-PaymentUpdate) |  |  |
| user_update | [user.UserUpdate](#saga-rpc-streams-user-UserUpdate) |  |  |
| created_timestamp | [int64](#int64) |  |  |





 

 

 


<a name="saga-rpc-streams-StatusStream"></a>

### StatusStream


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| StatusStream | [Subscribe](#saga-rpc-streams-Subscribe) | [StatusUpdate](#saga-rpc-streams-StatusUpdate) stream | Send a call to update the status |
| StatusConfirmation | [StatusConfirmRequest](#saga-rpc-streams-StatusConfirmRequest) | [.google.protobuf.Empty](#google-protobuf-Empty) | Get verification of status |

 



<a name="streams_user_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## streams/user/definition.proto



<a name="saga-rpc-streams-user-UserStatusUpdate"></a>

### UserStatusUpdate
Results from a User status update gRPC stream call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  | Unique id for the user |
| user_state | [saga.proto.common.user.UserState](#saga-proto-common-user-UserState) |  | State of the User, see UserState |






<a name="saga-rpc-streams-user-UserUpdate"></a>

### UserUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| error | [saga.common.ErrorResponse](#saga-common-ErrorResponse) |  |  |
| status_update | [UserStatusUpdate](#saga-rpc-streams-user-UserStatusUpdate) |  |  |





 

 

 

 



## Scalar Value Types

| .proto Type | Notes | C++ | Java | Python | Go | C# | PHP | Ruby |
| ----------- | ----- | --- | ---- | ------ | -- | -- | --- | ---- |
| <a name="double" /> double |  | double | double | float | float64 | double | float | Float |
| <a name="float" /> float |  | float | float | float | float32 | float | float | Float |
| <a name="int32" /> int32 | Uses variable-length encoding. Inefficient for encoding negative numbers – if your field is likely to have negative values, use sint32 instead. | int32 | int | int | int32 | int | integer | Bignum or Fixnum (as required) |
| <a name="int64" /> int64 | Uses variable-length encoding. Inefficient for encoding negative numbers – if your field is likely to have negative values, use sint64 instead. | int64 | long | int/long | int64 | long | integer/string | Bignum |
| <a name="uint32" /> uint32 | Uses variable-length encoding. | uint32 | int | int/long | uint32 | uint | integer | Bignum or Fixnum (as required) |
| <a name="uint64" /> uint64 | Uses variable-length encoding. | uint64 | long | int/long | uint64 | ulong | integer/string | Bignum or Fixnum (as required) |
| <a name="sint32" /> sint32 | Uses variable-length encoding. Signed int value. These more efficiently encode negative numbers than regular int32s. | int32 | int | int | int32 | int | integer | Bignum or Fixnum (as required) |
| <a name="sint64" /> sint64 | Uses variable-length encoding. Signed int value. These more efficiently encode negative numbers than regular int64s. | int64 | long | int/long | int64 | long | integer/string | Bignum |
| <a name="fixed32" /> fixed32 | Always four bytes. More efficient than uint32 if values are often greater than 2^28. | uint32 | int | int | uint32 | uint | integer | Bignum or Fixnum (as required) |
| <a name="fixed64" /> fixed64 | Always eight bytes. More efficient than uint64 if values are often greater than 2^56. | uint64 | long | int/long | uint64 | ulong | integer/string | Bignum |
| <a name="sfixed32" /> sfixed32 | Always four bytes. | int32 | int | int | int32 | int | integer | Bignum or Fixnum (as required) |
| <a name="sfixed64" /> sfixed64 | Always eight bytes. | int64 | long | int/long | int64 | long | integer/string | Bignum |
| <a name="bool" /> bool |  | bool | boolean | boolean | bool | bool | boolean | TrueClass/FalseClass |
| <a name="string" /> string | A string must always contain UTF-8 encoded or 7-bit ASCII text. | string | String | str/unicode | string | string | string | String (UTF-8) |
| <a name="bytes" /> bytes | May contain any arbitrary sequence of bytes. | string | ByteString | str | []byte | ByteString | string | String (ASCII-8BIT) |

