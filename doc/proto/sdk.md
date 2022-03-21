# Protocol Documentation
<a name="top"></a>

## Table of Contents

- [api/bridge/definition.proto](#api_bridge_definition-proto)
    - [BridgeProto](#saga-proto-api-bridge-BridgeProto)
    - [GetBridgeRequest](#saga-proto-api-bridge-GetBridgeRequest)
    - [WithdrawItemRequest](#saga-proto-api-bridge-WithdrawItemRequest)
  
- [api/bridge/rpc.proto](#api_bridge_rpc-proto)
    - [BridgeService](#saga-rpc-api-bridge-BridgeService)
  
- [api/gamecoin/definition.proto](#api_gamecoin_definition-proto)
    - [BurnGameCoinRequest](#saga-proto-api-gamecoin-BurnGameCoinRequest)
    - [GameCoinProto](#saga-proto-api-gamecoin-GameCoinProto)
    - [GameCoinsProto](#saga-proto-api-gamecoin-GameCoinsProto)
    - [GetGameCoinRequest](#saga-proto-api-gamecoin-GetGameCoinRequest)
    - [GetGameCoinsRequest](#saga-proto-api-gamecoin-GetGameCoinsRequest)
    - [IssueGameCoinRequest](#saga-proto-api-gamecoin-IssueGameCoinRequest)
    - [TransferGameCoinRequest](#saga-proto-api-gamecoin-TransferGameCoinRequest)
  
- [api/gamecoin/rpc.proto](#api_gamecoin_rpc-proto)
    - [GameCoinService](#saga-rpc-api-gamecoin-GameCoinService)
  
- [api/item/definition.proto](#api_item_definition-proto)
    - [BurnItemRequest](#saga-proto-api-item-BurnItemRequest)
    - [GetItemRequest](#saga-proto-api-item-GetItemRequest)
    - [GetItemsForPlayerRequest](#saga-proto-api-item-GetItemsForPlayerRequest)
    - [GetItemsRequest](#saga-proto-api-item-GetItemsRequest)
    - [IssueItemRequest](#saga-proto-api-item-IssueItemRequest)
    - [ItemProto](#saga-proto-api-item-ItemProto)
    - [ItemsProto](#saga-proto-api-item-ItemsProto)
    - [TransferItemRequest](#saga-proto-api-item-TransferItemRequest)
    - [UpdateItemMetadata](#saga-proto-api-item-UpdateItemMetadata)
    - [UpdateItemsMetadataRequest](#saga-proto-api-item-UpdateItemsMetadataRequest)
    - [UpdateItemsMetadataResponse](#saga-proto-api-item-UpdateItemsMetadataResponse)
  
- [api/item/rpc.proto](#api_item_rpc-proto)
    - [ItemService](#saga-rpc-api-item-ItemService)
  
- [api/itemtype/definition.proto](#api_itemtype_definition-proto)
    - [CreateItemTypeRequest](#saga-proto-api-itemtype-CreateItemTypeRequest)
    - [GetItemTypeRequest](#saga-proto-api-itemtype-GetItemTypeRequest)
    - [GetItemTypesRequest](#saga-proto-api-itemtype-GetItemTypesRequest)
    - [ItemTypeProto](#saga-proto-api-itemtype-ItemTypeProto)
    - [ItemTypesProto](#saga-proto-api-itemtype-ItemTypesProto)
    - [PriRevShareSettings](#saga-proto-api-itemtype-PriRevShareSettings)
    - [PriceMap](#saga-proto-api-itemtype-PriceMap)
    - [SecRevShareSettings](#saga-proto-api-itemtype-SecRevShareSettings)
    - [UpdateItemTypeMetadataPayload](#saga-proto-api-itemtype-UpdateItemTypeMetadataPayload)
    - [UpdateItemTypePayload](#saga-proto-api-itemtype-UpdateItemTypePayload)
  
- [api/itemtype/rpc.proto](#api_itemtype_rpc-proto)
    - [ItemTypeService](#saga-rpc-api-itemtype-ItemTypeService)
  
- [api/listing/definition.proto](#api_listing_definition-proto)
    - [CancelListingRequest](#saga-proto-api-listing-CancelListingRequest)
    - [ConfirmListingRequest](#saga-proto-api-listing-ConfirmListingRequest)
    - [CreateListingQuoteRequest](#saga-proto-api-listing-CreateListingQuoteRequest)
    - [GetListingsRequest](#saga-proto-api-listing-GetListingsRequest)
    - [ListingProto](#saga-proto-api-listing-ListingProto)
    - [ListingQuoteProto](#saga-proto-api-listing-ListingQuoteProto)
    - [ListingsProto](#saga-proto-api-listing-ListingsProto)
  
- [api/listing/rpc.proto](#api_listing_rpc-proto)
    - [ListingService](#saga-rpc-api-listing-ListingService)
  
- [api/myth/definition.proto](#api_myth_definition-proto)
    - [ConfirmBuyingMythTokenRequest](#saga-proto-api-myth-ConfirmBuyingMythTokenRequest)
    - [ConfirmMythTokenWithdrawalRequest](#saga-proto-api-myth-ConfirmMythTokenWithdrawalRequest)
    - [CurrencyExchangeProto](#saga-proto-api-myth-CurrencyExchangeProto)
    - [GasFeeProto](#saga-proto-api-myth-GasFeeProto)
    - [QuoteBuyingMythTokenRequest](#saga-proto-api-myth-QuoteBuyingMythTokenRequest)
    - [QuoteBuyingMythTokenResponse](#saga-proto-api-myth-QuoteBuyingMythTokenResponse)
    - [QuoteMythTokenWithdrawalRequest](#saga-proto-api-myth-QuoteMythTokenWithdrawalRequest)
    - [QuoteMythTokenWithdrawalResponse](#saga-proto-api-myth-QuoteMythTokenWithdrawalResponse)
  
- [api/myth/rpc.proto](#api_myth_rpc-proto)
    - [MythService](#saga-rpc-api-myth-MythService)
  
- [api/offer/definition.proto](#api_offer_definition-proto)
    - [CancelOfferRequest](#saga-proto-api-offer-CancelOfferRequest)
    - [ConfirmOfferRequest](#saga-proto-api-offer-ConfirmOfferRequest)
    - [CreateOfferQuoteRequest](#saga-proto-api-offer-CreateOfferQuoteRequest)
    - [GetOffersRequest](#saga-proto-api-offer-GetOffersRequest)
    - [OfferProto](#saga-proto-api-offer-OfferProto)
    - [OfferQuoteProto](#saga-proto-api-offer-OfferQuoteProto)
    - [OffersProto](#saga-proto-api-offer-OffersProto)
  
- [api/offer/rpc.proto](#api_offer_rpc-proto)
    - [OfferService](#saga-rpc-api-offer-OfferService)
  
- [api/order/definition.proto](#api_order_definition-proto)
    - [ConfirmOrderRequest](#saga-proto-api-order-ConfirmOrderRequest)
    - [CreateOrderQuoteRequest](#saga-proto-api-order-CreateOrderQuoteRequest)
    - [CreditCardData](#saga-proto-api-order-CreditCardData)
    - [PaymentProviderData](#saga-proto-api-order-PaymentProviderData)
    - [QuoteProto](#saga-proto-api-order-QuoteProto)
  
    - [PaymentProviderId](#saga-proto-api-order-PaymentProviderId)
  
- [api/order/rpc.proto](#api_order_rpc-proto)
    - [OrderService](#saga-rpc-api-order-OrderService)
  
- [api/payment/definition.proto](#api_payment_definition-proto)
    - [Address](#saga-proto-api-payment-Address)
    - [CardPaymentData](#saga-proto-api-payment-CardPaymentData)
    - [CreatePaymentMethodRequest](#saga-proto-api-payment-CreatePaymentMethodRequest)
    - [CybersourcePaymentData](#saga-proto-api-payment-CybersourcePaymentData)
    - [DeletePaymentMethodRequest](#saga-proto-api-payment-DeletePaymentMethodRequest)
    - [GetPaymentMethodRequest](#saga-proto-api-payment-GetPaymentMethodRequest)
    - [PaymentMethodProto](#saga-proto-api-payment-PaymentMethodProto)
    - [UpdatePaymentMethodRequest](#saga-proto-api-payment-UpdatePaymentMethodRequest)
  
- [api/payment/rpc.proto](#api_payment_rpc-proto)
    - [PaymentService](#saga-rpc-api-payment-PaymentService)
  
- [api/title/definition.proto](#api_title_definition-proto)
    - [GetTitlesRequest](#saga-proto-api-title-GetTitlesRequest)
    - [TitleProto](#saga-proto-api-title-TitleProto)
    - [TitlesProto](#saga-proto-api-title-TitlesProto)
  
- [api/title/rpc.proto](#api_title_rpc-proto)
    - [TitleService](#saga-rpc-api-title-TitleService)
  
- [api/transaction/definition.proto](#api_transaction_definition-proto)
    - [GetTransactionsForItemTypeRequest](#saga-proto-api-transaction-GetTransactionsForItemTypeRequest)
    - [GetTransactionsForPlayerRequest](#saga-proto-api-transaction-GetTransactionsForPlayerRequest)
    - [TransactionProto](#saga-proto-api-transaction-TransactionProto)
    - [TransactionsProto](#saga-proto-api-transaction-TransactionsProto)
  
- [api/transaction/rpc.proto](#api_transaction_rpc-proto)
    - [TransactionService](#saga-rpc-api-transaction-TransactionService)
  
- [api/user/definition.proto](#api_user_definition-proto)
    - [CybersourceAccount](#saga-proto-api-user-CybersourceAccount)
    - [FungibleToken](#saga-proto-api-user-FungibleToken)
    - [GetUserRequest](#saga-proto-api-user-GetUserRequest)
    - [GetUsersRequest](#saga-proto-api-user-GetUsersRequest)
    - [GetWalletAssetsRequest](#saga-proto-api-user-GetWalletAssetsRequest)
    - [NftItem](#saga-proto-api-user-NftItem)
    - [UpdateUserRequest](#saga-proto-api-user-UpdateUserRequest)
    - [UpholdAccount](#saga-proto-api-user-UpholdAccount)
    - [UserProto](#saga-proto-api-user-UserProto)
    - [UsersProto](#saga-proto-api-user-UsersProto)
    - [WalletAsset](#saga-proto-api-user-WalletAsset)
  
- [api/user/rpc.proto](#api_user_rpc-proto)
    - [UserService](#saga-rpc-api-user-UserService)
  
- [common/common.proto](#common_common-proto)
    - [Metadata](#saga-proto-common-Metadata)
    - [ReceivedResponse](#saga-proto-common-ReceivedResponse)
  
- [common/finalization.proto](#common_finalization-proto)
    - [Finalized](#saga-proto-common-finalization-Finalized)
  
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
    - [PaymentProviderId](#saga-proto-common-payment-PaymentProviderId)
  
- [common/query.proto](#common_query-proto)
    - [ExpressionProto](#saga-proto-common-query-ExpressionProto)
    - [FilterValueProto](#saga-proto-common-query-FilterValueProto)
    - [QueryOptionsProto](#saga-proto-common-query-QueryOptionsProto)
  
    - [FilterConditional](#saga-proto-common-query-FilterConditional)
    - [FilterOperation](#saga-proto-common-query-FilterOperation)
  
- [common/sort.proto](#common_sort-proto)
    - [SortOrder](#saga-proto-common-sort-SortOrder)
  
- [common/user/definition.proto](#common_user_definition-proto)
    - [UserState](#saga-proto-common-user-UserState)
  
- [streams/bridge/definition.proto](#streams_bridge_definition-proto)
    - [BridgeStatusUpdate](#saga-rpc-streams-bridge-BridgeStatusUpdate)
  
- [streams/common.proto](#streams_common-proto)
    - [Subscribe](#saga-rpc-streams-Subscribe)
  
- [streams/gamecoin/definition.proto](#streams_gamecoin_definition-proto)
    - [GameCoinStatusConfirmRequest](#saga-rpc-streams-gamecoin-GameCoinStatusConfirmRequest)
    - [GameCoinStatusUpdate](#saga-rpc-streams-gamecoin-GameCoinStatusUpdate)
  
- [streams/item/definition.proto](#streams_item_definition-proto)
    - [ItemStatusUpdate](#saga-rpc-streams-item-ItemStatusUpdate)
  
- [streams/itemtype/definition.proto](#streams_itemtype_definition-proto)
    - [ItemTypeStatusUpdate](#saga-rpc-streams-itemtype-ItemTypeStatusUpdate)
  
- [streams/listing/definition.proto](#streams_listing_definition-proto)
    - [ListingStatusUpdate](#saga-rpc-streams-listing-ListingStatusUpdate)
  
- [streams/myth/definition.proto](#streams_myth_definition-proto)
    - [MythTokenStatusUpdate](#saga-rpc-streams-myth-MythTokenStatusUpdate)
  
- [streams/offer/definition.proto](#streams_offer_definition-proto)
    - [OfferStatusUpdate](#saga-rpc-streams-offer-OfferStatusUpdate)
  
- [streams/order/definition.proto](#streams_order_definition-proto)
    - [OrderStatusUpdate](#saga-rpc-streams-order-OrderStatusUpdate)
  
- [streams/stream.proto](#streams_stream-proto)
    - [StatusConfirmRequest](#saga-rpc-streams-StatusConfirmRequest)
    - [StatusUpdate](#saga-rpc-streams-StatusUpdate)
  
    - [StatusStream](#saga-rpc-streams-StatusStream)
  
- [streams/user/definition.proto](#streams_user_definition-proto)
    - [UserStatusUpdate](#saga-rpc-streams-user-UserStatusUpdate)
  
- [Scalar Value Types](#scalar-value-types)



<a name="api_bridge_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/bridge/definition.proto



<a name="saga-proto-api-bridge-BridgeProto"></a>

### BridgeProto
Bridge definitions


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |
| mythical_address | [string](#string) |  | ??? |
| mainnet_address | [string](#string) |  |  |
| chain_name | [string](#string) |  |  |






<a name="saga-proto-api-bridge-GetBridgeRequest"></a>

### GetBridgeRequest
get bridge call






<a name="saga-proto-api-bridge-WithdrawItemRequest"></a>

### WithdrawItemRequest
withdraw item call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| title_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |
| game_item_type_id | [string](#string) |  |  |
| game_inventory_id | [string](#string) |  |  |
| destination_address | [string](#string) |  |  |
| destination_chain | [string](#string) |  |  |
| origin_address | [string](#string) |  |  |





 

 

 

 



<a name="api_bridge_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/bridge/rpc.proto


 

 

 


<a name="saga-rpc-api-bridge-BridgeService"></a>

### BridgeService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| WithdrawItem | [.saga.proto.api.bridge.WithdrawItemRequest](#saga-proto-api-bridge-WithdrawItemRequest) | [.saga.proto.common.ReceivedResponse](#saga-proto-common-ReceivedResponse) |  |
| GetBridge | [.saga.proto.api.bridge.GetBridgeRequest](#saga-proto-api-bridge-GetBridgeRequest) | [.saga.proto.api.bridge.BridgeProto](#saga-proto-api-bridge-BridgeProto) |  |

 



<a name="api_gamecoin_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/gamecoin/definition.proto



<a name="saga-proto-api-gamecoin-BurnGameCoinRequest"></a>

### BurnGameCoinRequest
Burn coins call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| title_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |
| currency_id | [string](#string) |  |  |
| coin_count | [int32](#int32) |  |  |






<a name="saga-proto-api-gamecoin-GameCoinProto"></a>

### GameCoinProto
GameCoin definitions


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |
| coin_count | [int32](#int32) |  |  |
| currency_id | [string](#string) |  |  |
| name | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |
| created_timestamp | [int64](#int64) |  |  |
| updated_timestamp | [int64](#int64) |  |  |






<a name="saga-proto-api-gamecoin-GameCoinsProto"></a>

### GameCoinsProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| game_coins | [GameCoinProto](#saga-proto-api-gamecoin-GameCoinProto) | repeated |  |






<a name="saga-proto-api-gamecoin-GetGameCoinRequest"></a>

### GetGameCoinRequest
Get coins call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| title_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |
| currency_id | [string](#string) |  |  |






<a name="saga-proto-api-gamecoin-GetGameCoinsRequest"></a>

### GetGameCoinsRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| title_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |
| created_after_timestamp | [uint64](#uint64) |  |  |
| page_size | [int32](#int32) |  |  |
| sort_order | [saga.proto.common.sort.SortOrder](#saga-proto-common-sort-SortOrder) |  |  |






<a name="saga-proto-api-gamecoin-IssueGameCoinRequest"></a>

### IssueGameCoinRequest
Issue coins call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| title_id | [string](#string) |  |  |
| currency_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |
| coin_count | [int32](#int32) |  |  |






<a name="saga-proto-api-gamecoin-TransferGameCoinRequest"></a>

### TransferGameCoinRequest
Transfer coins call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| title_id | [string](#string) |  |  |
| source_oauth_id | [string](#string) |  |  |
| destination_oauth_id | [string](#string) |  |  |
| currency_id | [string](#string) |  |  |
| coin_count | [int32](#int32) |  |  |





 

 

 

 



<a name="api_gamecoin_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/gamecoin/rpc.proto


 

 

 


<a name="saga-rpc-api-gamecoin-GameCoinService"></a>

### GameCoinService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| GetGameCoin | [.saga.proto.api.gamecoin.GetGameCoinRequest](#saga-proto-api-gamecoin-GetGameCoinRequest) | [.saga.proto.api.gamecoin.GameCoinProto](#saga-proto-api-gamecoin-GameCoinProto) |  |
| GetGameCoins | [.saga.proto.api.gamecoin.GetGameCoinsRequest](#saga-proto-api-gamecoin-GetGameCoinsRequest) | [.saga.proto.api.gamecoin.GameCoinsProto](#saga-proto-api-gamecoin-GameCoinsProto) |  |
| IssueGameCoin | [.saga.proto.api.gamecoin.IssueGameCoinRequest](#saga-proto-api-gamecoin-IssueGameCoinRequest) | [.saga.proto.common.ReceivedResponse](#saga-proto-common-ReceivedResponse) |  |
| TransferGameCoin | [.saga.proto.api.gamecoin.TransferGameCoinRequest](#saga-proto-api-gamecoin-TransferGameCoinRequest) | [.saga.proto.common.ReceivedResponse](#saga-proto-common-ReceivedResponse) |  |
| BurnGameCoin | [.saga.proto.api.gamecoin.BurnGameCoinRequest](#saga-proto-api-gamecoin-BurnGameCoinRequest) | [.saga.proto.common.ReceivedResponse](#saga-proto-common-ReceivedResponse) |  |

 



<a name="api_item_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/item/definition.proto



<a name="saga-proto-api-item-BurnItemRequest"></a>

### BurnItemRequest
Burn item call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| title_id | [string](#string) |  |  |
| game_inventory_id | [string](#string) |  |  |






<a name="saga-proto-api-item-GetItemRequest"></a>

### GetItemRequest
Get item calls


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| title_id | [string](#string) |  |  |
| game_inventory_id | [string](#string) |  |  |
| history | [bool](#bool) |  |  |






<a name="saga-proto-api-item-GetItemsForPlayerRequest"></a>

### GetItemsForPlayerRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| title_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |






<a name="saga-proto-api-item-GetItemsRequest"></a>

### GetItemsRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| query_options | [saga.proto.common.query.QueryOptionsProto](#saga-proto-common-query-QueryOptionsProto) |  |  |
| finalized | [saga.proto.common.finalization.Finalized](#saga-proto-common-finalization-Finalized) |  |  |
| token_name | [string](#string) |  |  |






<a name="saga-proto-api-item-IssueItemRequest"></a>

### IssueItemRequest
Issue item call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| title_id | [string](#string) |  |  |
| game_inventory_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |
| game_item_type_id | [string](#string) |  |  |
| metadata | [saga.proto.common.Metadata](#saga-proto-common-Metadata) |  |  |
| store_id | [string](#string) |  | optional |
| order_id | [string](#string) |  |  |
| request_ip | [string](#string) |  |  |






<a name="saga-proto-api-item-ItemProto"></a>

### ItemProto
Item definitions


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |
| game_inventory_id | [string](#string) |  |  |
| game_item_type_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |
| serial_number | [int32](#int32) |  |  |
| metadata_uri | [string](#string) |  |  |
| metadata | [saga.proto.common.Metadata](#saga-proto-common-Metadata) |  |  |
| item_state | [saga.proto.common.item.ItemState](#saga-proto-common-item-ItemState) |  |  |
| created_timestamp | [int64](#int64) |  |  |
| updated_timestamp | [int64](#int64) |  |  |






<a name="saga-proto-api-item-ItemsProto"></a>

### ItemsProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| items | [ItemProto](#saga-proto-api-item-ItemProto) | repeated |  |






<a name="saga-proto-api-item-TransferItemRequest"></a>

### TransferItemRequest
Transfer item call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| title_id | [string](#string) |  |  |
| game_inventory_id | [string](#string) |  |  |
| source_oauth_id | [string](#string) |  |  |
| destination_oauth_id | [string](#string) |  |  |
| store_id | [string](#string) |  |  |






<a name="saga-proto-api-item-UpdateItemMetadata"></a>

### UpdateItemMetadata
Metadata calls


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| game_inventory_id | [string](#string) |  |  |
| metadata | [saga.proto.common.Metadata](#saga-proto-common-Metadata) |  |  |






<a name="saga-proto-api-item-UpdateItemsMetadataRequest"></a>

### UpdateItemsMetadataRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| title_id | [string](#string) |  |  |
| update_items | [UpdateItemMetadata](#saga-proto-api-item-UpdateItemMetadata) | repeated |  |






<a name="saga-proto-api-item-UpdateItemsMetadataResponse"></a>

### UpdateItemsMetadataResponse
TODO: maybe at the minimum return the number successful of updated?
int32 count = 1;





 

 

 

 



<a name="api_item_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/item/rpc.proto


 

 

 


<a name="saga-rpc-api-item-ItemService"></a>

### ItemService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| GetItem | [.saga.proto.api.item.GetItemRequest](#saga-proto-api-item-GetItemRequest) | [.saga.proto.api.item.ItemProto](#saga-proto-api-item-ItemProto) |  |
| GetItems | [.saga.proto.api.item.GetItemsRequest](#saga-proto-api-item-GetItemsRequest) | [.saga.proto.api.item.ItemsProto](#saga-proto-api-item-ItemsProto) |  |
| GetItemsForPlayer | [.saga.proto.api.item.GetItemsForPlayerRequest](#saga-proto-api-item-GetItemsForPlayerRequest) | [.saga.proto.api.item.ItemsProto](#saga-proto-api-item-ItemsProto) |  |
| IssueItem | [.saga.proto.api.item.IssueItemRequest](#saga-proto-api-item-IssueItemRequest) | [.saga.proto.common.ReceivedResponse](#saga-proto-common-ReceivedResponse) |  |
| TransferItem | [.saga.proto.api.item.TransferItemRequest](#saga-proto-api-item-TransferItemRequest) | [.saga.proto.common.ReceivedResponse](#saga-proto-common-ReceivedResponse) |  |
| BurnItem | [.saga.proto.api.item.BurnItemRequest](#saga-proto-api-item-BurnItemRequest) | [.saga.proto.common.ReceivedResponse](#saga-proto-common-ReceivedResponse) |  |
| UpdateItemsMetadata | [.saga.proto.api.item.UpdateItemsMetadataRequest](#saga-proto-api-item-UpdateItemsMetadataRequest) | [.saga.proto.api.item.UpdateItemsMetadataResponse](#saga-proto-api-item-UpdateItemsMetadataResponse) |  |

 



<a name="api_itemtype_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/itemtype/definition.proto



<a name="saga-proto-api-itemtype-CreateItemTypeRequest"></a>

### CreateItemTypeRequest
Create item type call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| title_id | [string](#string) |  |  |
| publisher_address | [string](#string) |  |  |
| game_item_type_id | [string](#string) |  |  |
| name | [string](#string) |  |  |
| pri_rev_share_settings | [PriRevShareSettings](#saga-proto-api-itemtype-PriRevShareSettings) |  | optional |
| sec_rev_share_settings | [SecRevShareSettings](#saga-proto-api-itemtype-SecRevShareSettings) |  |  |
| metadata | [saga.proto.common.Metadata](#saga-proto-common-Metadata) |  |  |
| withdrawable | [bool](#bool) |  |  |






<a name="saga-proto-api-itemtype-GetItemTypeRequest"></a>

### GetItemTypeRequest
Get item type calls


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| title_id | [string](#string) |  |  |
| game_item_type_id | [string](#string) |  |  |






<a name="saga-proto-api-itemtype-GetItemTypesRequest"></a>

### GetItemTypesRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| title_id | [string](#string) |  |  |
| query_options | [saga.proto.common.query.QueryOptionsProto](#saga-proto-common-query-QueryOptionsProto) |  |  |






<a name="saga-proto-api-itemtype-ItemTypeProto"></a>

### ItemTypeProto
Item type definitions


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |
| game_item_type_id | [string](#string) |  |  |
| name | [string](#string) |  |  |
| title_id | [string](#string) |  |  |
| pri_rev_share_settings | [PriRevShareSettings](#saga-proto-api-itemtype-PriRevShareSettings) |  |  |
| sec_rev_share_settings | [SecRevShareSettings](#saga-proto-api-itemtype-SecRevShareSettings) |  |  |
| withdrawable | [bool](#bool) |  |  |
| price_map | [PriceMap](#saga-proto-api-itemtype-PriceMap) |  |  |
| item_type_state | [saga.proto.common.itemtype.ItemTypeState](#saga-proto-common-itemtype-ItemTypeState) |  |  |
| created_timestamp | [int64](#int64) |  |  |
| updated_timestamp | [int64](#int64) |  |  |
| metadata | [saga.proto.common.Metadata](#saga-proto-common-Metadata) |  |  |






<a name="saga-proto-api-itemtype-ItemTypesProto"></a>

### ItemTypesProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| item_types | [ItemTypeProto](#saga-proto-api-itemtype-ItemTypeProto) | repeated |  |






<a name="saga-proto-api-itemtype-PriRevShareSettings"></a>

### PriRevShareSettings







<a name="saga-proto-api-itemtype-PriceMap"></a>

### PriceMap







<a name="saga-proto-api-itemtype-SecRevShareSettings"></a>

### SecRevShareSettings







<a name="saga-proto-api-itemtype-UpdateItemTypeMetadataPayload"></a>

### UpdateItemTypeMetadataPayload
Update item type metadata


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| title_id | [string](#string) |  |  |
| game_item_type_id | [string](#string) |  |  |
| metadata | [saga.proto.common.Metadata](#saga-proto-common-Metadata) |  |  |






<a name="saga-proto-api-itemtype-UpdateItemTypePayload"></a>

### UpdateItemTypePayload



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| title_id | [string](#string) |  |  |
| game_item_type_id | [string](#string) |  |  |
| withdrawable | [bool](#bool) |  |  |





 

 

 

 



<a name="api_itemtype_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/itemtype/rpc.proto


 

 

 


<a name="saga-rpc-api-itemtype-ItemTypeService"></a>

### ItemTypeService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| GetItemType | [.saga.proto.api.itemtype.GetItemTypeRequest](#saga-proto-api-itemtype-GetItemTypeRequest) | [.saga.proto.api.itemtype.ItemTypeProto](#saga-proto-api-itemtype-ItemTypeProto) |  |
| GetItemTypes | [.saga.proto.api.itemtype.GetItemTypesRequest](#saga-proto-api-itemtype-GetItemTypesRequest) | [.saga.proto.api.itemtype.ItemTypesProto](#saga-proto-api-itemtype-ItemTypesProto) |  |
| CreateItemType | [.saga.proto.api.itemtype.CreateItemTypeRequest](#saga-proto-api-itemtype-CreateItemTypeRequest) | [.saga.proto.common.ReceivedResponse](#saga-proto-common-ReceivedResponse) |  |
| UpdateItemTypeMetadata | [.saga.proto.api.itemtype.UpdateItemTypeMetadataPayload](#saga-proto-api-itemtype-UpdateItemTypeMetadataPayload) | [.google.protobuf.Empty](#google-protobuf-Empty) |  |
| UpdateItemType | [.saga.proto.api.itemtype.UpdateItemTypePayload](#saga-proto-api-itemtype-UpdateItemTypePayload) | [.google.protobuf.Empty](#google-protobuf-Empty) |  |

 



<a name="api_listing_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/listing/definition.proto



<a name="saga-proto-api-listing-CancelListingRequest"></a>

### CancelListingRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| title_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |
| listing_id | [string](#string) |  |  |






<a name="saga-proto-api-listing-ConfirmListingRequest"></a>

### ConfirmListingRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| title_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |
| quote_id | [string](#string) |  |  |






<a name="saga-proto-api-listing-CreateListingQuoteRequest"></a>

### CreateListingQuoteRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| title_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |
| game_inventory_id | [string](#string) |  |  |
| total | [string](#string) |  |  |
| currency | [string](#string) |  |  |






<a name="saga-proto-api-listing-GetListingsRequest"></a>

### GetListingsRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| query_options | [saga.proto.common.query.QueryOptionsProto](#saga-proto-common-query-QueryOptionsProto) |  |  |
| item_type_id | [string](#string) |  |  |
| token | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |






<a name="saga-proto-api-listing-ListingProto"></a>

### ListingProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  |  |
| title_id | [string](#string) |  |  |
| game_inventory_id | [string](#string) |  |  |
| currency | [string](#string) |  |  |
| total | [string](#string) |  |  |
| created_timestamp | [uint64](#uint64) |  |  |






<a name="saga-proto-api-listing-ListingQuoteProto"></a>

### ListingQuoteProto
Listing definitions


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |
| quote_id | [string](#string) |  |  |
| game_inventory_id | [string](#string) |  |  |
| tax | [string](#string) |  | and fees? |
| tax_currency | [string](#string) |  |  |
| total | [string](#string) |  |  |
| currency | [string](#string) |  |  |
| created_timestamp | [uint64](#uint64) |  |  |






<a name="saga-proto-api-listing-ListingsProto"></a>

### ListingsProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| listings | [ListingProto](#saga-proto-api-listing-ListingProto) | repeated |  |





 

 

 

 



<a name="api_listing_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/listing/rpc.proto


 

 

 


<a name="saga-rpc-api-listing-ListingService"></a>

### ListingService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| CreateListingQuote | [.saga.proto.api.listing.CreateListingQuoteRequest](#saga-proto-api-listing-CreateListingQuoteRequest) | [.saga.proto.api.listing.ListingQuoteProto](#saga-proto-api-listing-ListingQuoteProto) |  |
| ConfirmListing | [.saga.proto.api.listing.ConfirmListingRequest](#saga-proto-api-listing-ConfirmListingRequest) | [.saga.proto.common.ReceivedResponse](#saga-proto-common-ReceivedResponse) |  |
| CancelListing | [.saga.proto.api.listing.CancelListingRequest](#saga-proto-api-listing-CancelListingRequest) | [.saga.proto.common.ReceivedResponse](#saga-proto-common-ReceivedResponse) |  |
| GetListings | [.saga.proto.api.listing.GetListingsRequest](#saga-proto-api-listing-GetListingsRequest) | [.saga.proto.api.listing.ListingsProto](#saga-proto-api-listing-ListingsProto) |  |

 



<a name="api_myth_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/myth/definition.proto



<a name="saga-proto-api-myth-ConfirmBuyingMythTokenRequest"></a>

### ConfirmBuyingMythTokenRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| quote_id | [string](#string) |  |  |
| user_id | [string](#string) |  |  |
| credit_card_info | [saga.proto.api.payment.CardPaymentData](#saga-proto-api-payment-CardPaymentData) |  |  |






<a name="saga-proto-api-myth-ConfirmMythTokenWithdrawalRequest"></a>

### ConfirmMythTokenWithdrawalRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| quote_id | [string](#string) |  |  |






<a name="saga-proto-api-myth-CurrencyExchangeProto"></a>

### CurrencyExchangeProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| bid | [string](#string) |  |  |
| ask | [string](#string) |  |  |






<a name="saga-proto-api-myth-GasFeeProto"></a>

### GasFeeProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| gwei_amount | [string](#string) |  |  |
| eth_amount | [string](#string) |  |  |
| converted_amount | [string](#string) |  |  |
| converted_currency | [string](#string) |  |  |






<a name="saga-proto-api-myth-QuoteBuyingMythTokenRequest"></a>

### QuoteBuyingMythTokenRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| quantity | [string](#string) |  |  |
| credit_card_info | [saga.proto.api.payment.CardPaymentData](#saga-proto-api-payment-CardPaymentData) |  |  |
| denomination_currency | [string](#string) |  |  |
| origin_sub_account | [string](#string) |  |  |
| user_id | [string](#string) |  |  |






<a name="saga-proto-api-myth-QuoteBuyingMythTokenResponse"></a>

### QuoteBuyingMythTokenResponse



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| uphold_quote_id | [string](#string) |  |  |
| origin_sub_account | [string](#string) |  |  |






<a name="saga-proto-api-myth-QuoteMythTokenWithdrawalRequest"></a>

### QuoteMythTokenWithdrawalRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| user_id | [string](#string) |  |  |
| quantity | [string](#string) |  |  |






<a name="saga-proto-api-myth-QuoteMythTokenWithdrawalResponse"></a>

### QuoteMythTokenWithdrawalResponse



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| total_amount | [string](#string) |  |  |
| gas_fee | [string](#string) |  |  |





 

 

 

 



<a name="api_myth_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/myth/rpc.proto


 

 

 


<a name="saga-rpc-api-myth-MythService"></a>

### MythService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| GetGasFee | [.google.protobuf.Empty](#google-protobuf-Empty) | [.saga.proto.api.myth.GasFeeProto](#saga-proto-api-myth-GasFeeProto) |  |
| GetCurrencyExchange | [.google.protobuf.Empty](#google-protobuf-Empty) | [.saga.proto.api.myth.CurrencyExchangeProto](#saga-proto-api-myth-CurrencyExchangeProto) |  |
| QuoteBuyingMythToken | [.saga.proto.api.myth.QuoteBuyingMythTokenRequest](#saga-proto-api-myth-QuoteBuyingMythTokenRequest) | [.saga.proto.api.myth.QuoteBuyingMythTokenResponse](#saga-proto-api-myth-QuoteBuyingMythTokenResponse) |  |
| ConfirmBuyingMythToken | [.saga.proto.api.myth.ConfirmBuyingMythTokenRequest](#saga-proto-api-myth-ConfirmBuyingMythTokenRequest) | [.saga.proto.common.ReceivedResponse](#saga-proto-common-ReceivedResponse) |  |
| QuoteMythTokenWithdrawal | [.saga.proto.api.myth.QuoteMythTokenWithdrawalRequest](#saga-proto-api-myth-QuoteMythTokenWithdrawalRequest) | [.saga.proto.api.myth.QuoteMythTokenWithdrawalResponse](#saga-proto-api-myth-QuoteMythTokenWithdrawalResponse) |  |
| ConfirmMythTokenWithdrawal | [.saga.proto.api.myth.ConfirmMythTokenWithdrawalRequest](#saga-proto-api-myth-ConfirmMythTokenWithdrawalRequest) | [.saga.proto.common.ReceivedResponse](#saga-proto-common-ReceivedResponse) |  |

 



<a name="api_offer_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/offer/definition.proto



<a name="saga-proto-api-offer-CancelOfferRequest"></a>

### CancelOfferRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| title_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |
| offer_id | [string](#string) |  |  |






<a name="saga-proto-api-offer-ConfirmOfferRequest"></a>

### ConfirmOfferRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| title_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |
| quote_id | [string](#string) |  | TODO: where&#39;s the payment data? Also are offers fronted payment or we somehow storing it (securely) then charging when needed |






<a name="saga-proto-api-offer-CreateOfferQuoteRequest"></a>

### CreateOfferQuoteRequest
offer-related


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| title_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |
| game_inventory_id | [string](#string) |  |  |
| total | [string](#string) |  |  |
| currency | [string](#string) |  |  |






<a name="saga-proto-api-offer-GetOffersRequest"></a>

### GetOffersRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| query_options | [saga.proto.common.query.QueryOptionsProto](#saga-proto-common-query-QueryOptionsProto) |  |  |
| item_type_id | [string](#string) |  |  |
| token | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |






<a name="saga-proto-api-offer-OfferProto"></a>

### OfferProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  |  |
| title_id | [string](#string) |  |  |
| game_inventory_id | [string](#string) |  |  |
| currency | [string](#string) |  |  |
| total | [string](#string) |  |  |
| created_timestamp | [uint64](#uint64) |  |  |






<a name="saga-proto-api-offer-OfferQuoteProto"></a>

### OfferQuoteProto
Offer definitions


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |
| quote_id | [string](#string) |  |  |
| game_inventory_id | [string](#string) |  |  |
| tax | [string](#string) |  | and fees? |
| tax_currency | [string](#string) |  |  |
| total | [string](#string) |  |  |
| currency | [string](#string) |  |  |
| created_timestamp | [uint64](#uint64) |  |  |






<a name="saga-proto-api-offer-OffersProto"></a>

### OffersProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| offers | [OfferProto](#saga-proto-api-offer-OfferProto) | repeated |  |





 

 

 

 



<a name="api_offer_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/offer/rpc.proto


 

 

 


<a name="saga-rpc-api-offer-OfferService"></a>

### OfferService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| CreateOfferQuote | [.saga.proto.api.offer.CreateOfferQuoteRequest](#saga-proto-api-offer-CreateOfferQuoteRequest) | [.saga.proto.api.offer.OfferQuoteProto](#saga-proto-api-offer-OfferQuoteProto) |  |
| ConfirmOffer | [.saga.proto.api.offer.ConfirmOfferRequest](#saga-proto-api-offer-ConfirmOfferRequest) | [.saga.proto.common.ReceivedResponse](#saga-proto-common-ReceivedResponse) |  |
| CancelOffer | [.saga.proto.api.offer.CancelOfferRequest](#saga-proto-api-offer-CancelOfferRequest) | [.saga.proto.common.ReceivedResponse](#saga-proto-common-ReceivedResponse) |  |
| GetOffers | [.saga.proto.api.offer.GetOffersRequest](#saga-proto-api-offer-GetOffersRequest) | [.saga.proto.api.offer.OffersProto](#saga-proto-api-offer-OffersProto) |  |

 



<a name="api_order_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/order/definition.proto



<a name="saga-proto-api-order-ConfirmOrderRequest"></a>

### ConfirmOrderRequest
confirm order call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| title_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |
| quote_id | [string](#string) |  |  |
| payment_provider_data | [PaymentProviderData](#saga-proto-api-order-PaymentProviderData) |  |  |
| fraud_session_id | [string](#string) |  |  |






<a name="saga-proto-api-order-CreateOrderQuoteRequest"></a>

### CreateOrderQuoteRequest
create order quote call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| title_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |
| subtotal | [string](#string) |  | TODO: amoir, so we still thinking storing price so it isn&#39;t passed in? |
| payment_provider_data | [PaymentProviderData](#saga-proto-api-order-PaymentProviderData) |  |  |
| game_item_type_id | [string](#string) |  |  |
| listing_address | [string](#string) |  |  |
| buy_myth_token | [bool](#bool) |  |  |
| withdraw_myth_token | [bool](#bool) |  |  |
| myth_to_usd | [bool](#bool) |  |  |
| withdraw_item_address | [string](#string) |  |  |
| no_op | [bool](#bool) |  |  |






<a name="saga-proto-api-order-CreditCardData"></a>

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






<a name="saga-proto-api-order-PaymentProviderData"></a>

### PaymentProviderData



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| credit_card_data | [CreditCardData](#saga-proto-api-order-CreditCardData) |  |  |
| uphold_card_id | [string](#string) |  |  |






<a name="saga-proto-api-order-QuoteProto"></a>

### QuoteProto
Order definitions


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |
| quote_id | [string](#string) |  |  |
| tax | [string](#string) |  | maybe call it fee? fee for fees in crypto and tax in fiat |
| tax_currency | [string](#string) |  |  |
| total | [string](#string) |  |  |
| currency | [string](#string) |  |  |
| payment_provider_id | [PaymentProviderId](#saga-proto-api-order-PaymentProviderId) |  |  |
| buyer_oauth_id | [string](#string) |  |  |
| seller_oauth_id | [string](#string) |  |  |
| conversion_rate | [string](#string) |  |  |
| created_timestamp | [uint64](#uint64) |  |  |





 


<a name="saga-proto-api-order-PaymentProviderId"></a>

### PaymentProviderId
TODO: there are duplicate PaymentProviderId in the payments definition

| Name | Number | Description |
| ---- | ------ | ----------- |
| CREDIT_CARD | 0 |  |
| UPHOLD | 1 |  |
| MYTHICAL | 2 |  |


 

 

 



<a name="api_order_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/order/rpc.proto


 

 

 


<a name="saga-rpc-api-order-OrderService"></a>

### OrderService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| CreateOrderQuote | [.saga.proto.api.order.CreateOrderQuoteRequest](#saga-proto-api-order-CreateOrderQuoteRequest) | [.saga.proto.api.order.QuoteProto](#saga-proto-api-order-QuoteProto) |  |
| ConfirmOrder | [.saga.proto.api.order.ConfirmOrderRequest](#saga-proto-api-order-ConfirmOrderRequest) | [.saga.proto.common.ReceivedResponse](#saga-proto-common-ReceivedResponse) |  |

 



<a name="api_payment_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/payment/definition.proto



<a name="saga-proto-api-payment-Address"></a>

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






<a name="saga-proto-api-payment-CardPaymentData"></a>

### CardPaymentData



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| make_default | [bool](#bool) |  |  |
| cybersource | [CybersourcePaymentData](#saga-proto-api-payment-CybersourcePaymentData) |  |  |






<a name="saga-proto-api-payment-CreatePaymentMethodRequest"></a>

### CreatePaymentMethodRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  |  |
| card_payment_data | [CardPaymentData](#saga-proto-api-payment-CardPaymentData) |  |  |
| address | [Address](#saga-proto-api-payment-Address) |  | Uphold linking info? |






<a name="saga-proto-api-payment-CybersourcePaymentData"></a>

### CybersourcePaymentData



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| expiration_month | [string](#string) |  |  |
| expiration_year | [string](#string) |  |  |
| card_type | [string](#string) |  |  |
| instrument_id | [string](#string) |  |  |






<a name="saga-proto-api-payment-DeletePaymentMethodRequest"></a>

### DeletePaymentMethodRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  |  |
| card_payment_data | [CardPaymentData](#saga-proto-api-payment-CardPaymentData) |  |  |






<a name="saga-proto-api-payment-GetPaymentMethodRequest"></a>

### GetPaymentMethodRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  |  |






<a name="saga-proto-api-payment-PaymentMethodProto"></a>

### PaymentMethodProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |
| card_payment_data | [CardPaymentData](#saga-proto-api-payment-CardPaymentData) |  |  |
| address | [Address](#saga-proto-api-payment-Address) |  |  |






<a name="saga-proto-api-payment-UpdatePaymentMethodRequest"></a>

### UpdatePaymentMethodRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  |  |
| card_payment_data | [CardPaymentData](#saga-proto-api-payment-CardPaymentData) |  |  |
| address | [Address](#saga-proto-api-payment-Address) |  | Uphold linking info |





 

 

 

 



<a name="api_payment_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/payment/rpc.proto


 

 

 


<a name="saga-rpc-api-payment-PaymentService"></a>

### PaymentService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| CreatePaymentMethod | [.saga.proto.api.payment.CreatePaymentMethodRequest](#saga-proto-api-payment-CreatePaymentMethodRequest) | [.saga.proto.api.payment.PaymentMethodProto](#saga-proto-api-payment-PaymentMethodProto) |  |
| GetPaymentMethod | [.saga.proto.api.payment.GetPaymentMethodRequest](#saga-proto-api-payment-GetPaymentMethodRequest) | [.saga.proto.api.payment.PaymentMethodProto](#saga-proto-api-payment-PaymentMethodProto) |  |
| UpdatePaymentMethod | [.saga.proto.api.payment.UpdatePaymentMethodRequest](#saga-proto-api-payment-UpdatePaymentMethodRequest) | [.saga.proto.api.payment.PaymentMethodProto](#saga-proto-api-payment-PaymentMethodProto) |  |
| DeletePaymentMethod | [.saga.proto.api.payment.DeletePaymentMethodRequest](#saga-proto-api-payment-DeletePaymentMethodRequest) | [.saga.proto.api.payment.PaymentMethodProto](#saga-proto-api-payment-PaymentMethodProto) |  |

 



<a name="api_title_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/title/definition.proto



<a name="saga-proto-api-title-GetTitlesRequest"></a>

### GetTitlesRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| query_options | [saga.proto.common.query.QueryOptionsProto](#saga-proto-common-query-QueryOptionsProto) |  |  |






<a name="saga-proto-api-title-TitleProto"></a>

### TitleProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| title_id | [string](#string) |  |  |
| name | [string](#string) |  |  |
| created_timestamp | [int64](#int64) |  |  |






<a name="saga-proto-api-title-TitlesProto"></a>

### TitlesProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| titles | [TitleProto](#saga-proto-api-title-TitleProto) | repeated |  |





 

 

 

 



<a name="api_title_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/title/rpc.proto


 

 

 


<a name="saga-rpc-api-title-TitleService"></a>

### TitleService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| GetTitles | [.saga.proto.api.title.GetTitlesRequest](#saga-proto-api-title-GetTitlesRequest) | [.saga.proto.api.title.TitlesProto](#saga-proto-api-title-TitlesProto) |  |

 



<a name="api_transaction_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/transaction/definition.proto



<a name="saga-proto-api-transaction-GetTransactionsForItemTypeRequest"></a>

### GetTransactionsForItemTypeRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| item_type_id | [string](#string) |  |  |
| token_id | [string](#string) |  |  |
| title_id | [string](#string) |  |  |
| query_options | [saga.proto.common.query.QueryOptionsProto](#saga-proto-common-query-QueryOptionsProto) |  |  |






<a name="saga-proto-api-transaction-GetTransactionsForPlayerRequest"></a>

### GetTransactionsForPlayerRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  |  |
| query_options | [saga.proto.common.query.QueryOptionsProto](#saga-proto-common-query-QueryOptionsProto) |  |  |






<a name="saga-proto-api-transaction-TransactionProto"></a>

### TransactionProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| transaction_id | [string](#string) |  |  |
| title_id | [string](#string) |  |  |
| created_timestamp | [int64](#int64) |  |  |






<a name="saga-proto-api-transaction-TransactionsProto"></a>

### TransactionsProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| transactions | [TransactionProto](#saga-proto-api-transaction-TransactionProto) | repeated |  |





 

 

 

 



<a name="api_transaction_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/transaction/rpc.proto


 

 

 


<a name="saga-rpc-api-transaction-TransactionService"></a>

### TransactionService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| GetTransactionsForPlayer | [.saga.proto.api.transaction.GetTransactionsForPlayerRequest](#saga-proto-api-transaction-GetTransactionsForPlayerRequest) | [.saga.proto.api.transaction.TransactionsProto](#saga-proto-api-transaction-TransactionsProto) |  |
| GetTransactionsForItemType | [.saga.proto.api.transaction.GetTransactionsForItemTypeRequest](#saga-proto-api-transaction-GetTransactionsForItemTypeRequest) | [.saga.proto.api.transaction.TransactionsProto](#saga-proto-api-transaction-TransactionsProto) |  |

 



<a name="api_user_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/user/definition.proto



<a name="saga-proto-api-user-CybersourceAccount"></a>

### CybersourceAccount



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| id | [string](#string) |  | and more? |






<a name="saga-proto-api-user-FungibleToken"></a>

### FungibleToken



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| name | [string](#string) |  |  |
| game_id | [string](#string) |  |  |
| quantity | [string](#string) |  |  |
| contract_address | [string](#string) |  |  |






<a name="saga-proto-api-user-GetUserRequest"></a>

### GetUserRequest
Get user calls


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| title_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |






<a name="saga-proto-api-user-GetUsersRequest"></a>

### GetUsersRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| query_options | [saga.proto.common.query.QueryOptionsProto](#saga-proto-common-query-QueryOptionsProto) |  |  |






<a name="saga-proto-api-user-GetWalletAssetsRequest"></a>

### GetWalletAssetsRequest
Get wallet assets call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  |  |
| publisher_id | [string](#string) |  |  |
| partner_id | [string](#string) |  |  |
| title_id | [string](#string) |  |  |






<a name="saga-proto-api-user-NftItem"></a>

### NftItem



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| item_type_name | [string](#string) |  |  |
| item_type_id | [string](#string) |  |  |
| contract_address | [string](#string) |  |  |
| token_id | [string](#string) |  |  |






<a name="saga-proto-api-user-UpdateUserRequest"></a>

### UpdateUserRequest
Update user call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| title_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  | TODO: what is being updated here |






<a name="saga-proto-api-user-UpholdAccount"></a>

### UpholdAccount



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| id | [string](#string) |  | and more? |






<a name="saga-proto-api-user-UserProto"></a>

### UserProto
User definitions


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |
| chain_address | [string](#string) |  |  |
| user_state | [saga.proto.common.user.UserState](#saga-proto-common-user-UserState) |  |  |
| uphold_accounts | [UpholdAccount](#saga-proto-api-user-UpholdAccount) | repeated |  |
| cybersource_account | [CybersourceAccount](#saga-proto-api-user-CybersourceAccount) |  |  |
| created_timestamp | [uint64](#uint64) |  |  |






<a name="saga-proto-api-user-UsersProto"></a>

### UsersProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| saga_users | [UserProto](#saga-proto-api-user-UserProto) | repeated |  |






<a name="saga-proto-api-user-WalletAsset"></a>

### WalletAsset



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| nmyth_token | [string](#string) |  |  |
| nmyth_token_quantity | [string](#string) |  |  |
| ausd_token | [string](#string) |  |  |
| ausd_token_quantity | [string](#string) |  |  |
| nft_items | [NftItem](#saga-proto-api-user-NftItem) | repeated |  |
| fungible_tokens | [FungibleToken](#saga-proto-api-user-FungibleToken) | repeated |  |





 

 

 

 



<a name="api_user_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/user/rpc.proto


 

 

 


<a name="saga-rpc-api-user-UserService"></a>

### UserService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| GetUser | [.saga.proto.api.user.GetUserRequest](#saga-proto-api-user-GetUserRequest) | [.saga.proto.api.user.UserProto](#saga-proto-api-user-UserProto) |  |
| GetUsers | [.saga.proto.api.user.GetUsersRequest](#saga-proto-api-user-GetUsersRequest) | [.saga.proto.api.user.UsersProto](#saga-proto-api-user-UsersProto) |  |
| UpdateUser | [.saga.proto.api.user.UpdateUserRequest](#saga-proto-api-user-UpdateUserRequest) | [.saga.proto.api.user.UserProto](#saga-proto-api-user-UserProto) |  |
| GetWalletAssets | [.saga.proto.api.user.GetWalletAssetsRequest](#saga-proto-api-user-GetWalletAssetsRequest) | [.saga.proto.api.user.WalletAsset](#saga-proto-api-user-WalletAsset) |  |

 



<a name="common_common-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## common/common.proto



<a name="saga-proto-common-Metadata"></a>

### Metadata



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| name | [string](#string) |  |  |
| description | [string](#string) |  |  |
| image | [string](#string) |  |  |
| properties | [google.protobuf.Struct](#google-protobuf-Struct) |  |  |






<a name="saga-proto-common-ReceivedResponse"></a>

### ReceivedResponse



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |





 

 

 

 



<a name="common_finalization-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## common/finalization.proto


 


<a name="saga-proto-common-finalization-Finalized"></a>

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
| FAILED | 0 |  |
| ISSUED | 1 |  |
| TRANSFERRED | 2 |  |
| BURNED | 3 |  |


 

 

 



<a name="common_item_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## common/item/definition.proto


 


<a name="saga-proto-common-item-ItemState"></a>

### ItemState


| Name | Number | Description |
| ---- | ------ | ----------- |
| FAILED | 0 |  |
| ISSUED | 1 |  |
| LISTED | 2 |  |
| TRANSFERRED | 3 |  |
| BURNED | 4 | item no longer exists on the blockchain so the API will have to keep track through transaction history |
| LISTING_CLOSED | 5 |  |
| WITHDRAWN | 6 |  |
| DEPOSITED | 7 |  |


 

 

 



<a name="common_itemtype_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## common/itemtype/definition.proto


 


<a name="saga-proto-common-itemtype-ItemTypeState"></a>

### ItemTypeState


| Name | Number | Description |
| ---- | ------ | ----------- |
| FAILED | 0 |  |
| CREATED | 1 |  |
| FROZEN | 2 |  |
| SOLD_OUT | 3 |  |
| EXPIRED | 4 |  |


 

 

 



<a name="common_listing_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## common/listing/definition.proto


 


<a name="saga-proto-common-listing-ListingState"></a>

### ListingState


| Name | Number | Description |
| ---- | ------ | ----------- |
| FAILED | 0 |  |
| CREATED | 1 |  |
| SOLD | 2 |  |
| CANCELLED | 3 |  |


 

 

 



<a name="common_myth_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## common/myth/definition.proto


 


<a name="saga-proto-common-myth-MythTokenState"></a>

### MythTokenState


| Name | Number | Description |
| ---- | ------ | ----------- |
| FAILED | 0 |  |
| TRANSFERRED | 1 |  |
| WITHDRAWN | 2 |  |


 

 

 



<a name="common_offer_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## common/offer/definition.proto


 


<a name="saga-proto-common-offer-OfferState"></a>

### OfferState


| Name | Number | Description |
| ---- | ------ | ----------- |
| FAILED | 0 |  |
| CREATED | 1 |  |
| CONFIRMED | 2 |  |
| CANCELLED | 3 |  |


 

 

 



<a name="common_order_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## common/order/definition.proto


 


<a name="saga-proto-common-order-OrderState"></a>

### OrderState


| Name | Number | Description |
| ---- | ------ | ----------- |
| FAILED | 0 |  |
| STARTED | 1 |  |
| COMPLETE | 2 |  |
| PAID | 3 |  |
| EXPIRED | 4 |  |
| REFUNDED | 5 |  |


 

 

 



<a name="common_payment_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## common/payment/definition.proto


 


<a name="saga-proto-common-payment-PaymentProviderId"></a>

### PaymentProviderId
Payment definitions

| Name | Number | Description |
| ---- | ------ | ----------- |
| BRAINTREE | 0 |  |
| BITPAY | 1 |  |
| CYBERSOURCE | 2 |  |
| UPHOLD | 3 |  |


 

 

 



<a name="common_query-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## common/query.proto



<a name="saga-proto-common-query-ExpressionProto"></a>

### ExpressionProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| attribute_name | [string](#string) |  |  |
| conditional | [FilterConditional](#saga-proto-common-query-FilterConditional) |  |  |
| double_value | [double](#double) |  |  |
| string_value | [string](#string) |  |  |
| bool_value | [bool](#bool) |  |  |






<a name="saga-proto-common-query-FilterValueProto"></a>

### FilterValueProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| expression | [ExpressionProto](#saga-proto-common-query-ExpressionProto) |  |  |
| operation | [FilterOperation](#saga-proto-common-query-FilterOperation) |  |  |






<a name="saga-proto-common-query-QueryOptionsProto"></a>

### QueryOptionsProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| filter_options | [FilterValueProto](#saga-proto-common-query-FilterValueProto) | repeated |  |
| page_size | [int32](#int32) |  |  |
| sort_order | [saga.proto.common.sort.SortOrder](#saga-proto-common-sort-SortOrder) |  |  |
| sort_attribute | [string](#string) |  |  |





 


<a name="saga-proto-common-query-FilterConditional"></a>

### FilterConditional


| Name | Number | Description |
| ---- | ------ | ----------- |
| EQUALS | 0 |  |
| EQUALS_LESS_THAN | 1 |  |
| LESS_THAN | 2 |  |
| EQUALS_GREATER_THAN | 3 |  |
| GREATER_THAN | 4 |  |
| NOT_EQUALS | 5 |  |
| CONTAINS | 6 |  |
| NOT_CONTAINS | 7 |  |



<a name="saga-proto-common-query-FilterOperation"></a>

### FilterOperation


| Name | Number | Description |
| ---- | ------ | ----------- |
| AND | 0 |  |
| OR | 1 |  |


 

 

 



<a name="common_sort-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## common/sort.proto


 


<a name="saga-proto-common-sort-SortOrder"></a>

### SortOrder


| Name | Number | Description |
| ---- | ------ | ----------- |
| ASC | 0 |  |
| DESC | 1 |  |


 

 

 



<a name="common_user_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## common/user/definition.proto


 


<a name="saga-proto-common-user-UserState"></a>

### UserState


| Name | Number | Description |
| ---- | ------ | ----------- |
| FAILED | 0 |  |
| LINKED | 1 |  |


 

 

 



<a name="streams_bridge_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## streams/bridge/definition.proto



<a name="saga-rpc-streams-bridge-BridgeStatusUpdate"></a>

### BridgeStatusUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  |  |
| trace_id | [string](#string) |  |  |
| game_item_type_id | [string](#string) |  |  |
| game_inventory_id | [string](#string) |  |  |
| destination_address | [string](#string) |  |  |
| destination_chain | [string](#string) |  |  |
| origin_address | [string](#string) |  |  |
| mythical_transaction_id | [string](#string) |  |  |
| mainnet_transaction_id | [string](#string) |  |  |





 

 

 

 



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



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |
| title_id | [string](#string) |  |  |
| currency_id | [string](#string) |  |  |
| game_coin_state | [saga.proto.common.gamecoin.GameCoinState](#saga-proto-common-gamecoin-GameCoinState) |  |  |






<a name="saga-rpc-streams-gamecoin-GameCoinStatusUpdate"></a>

### GameCoinStatusUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |
| currency_id | [string](#string) |  |  |
| coin_count | [int32](#int32) |  |  |
| game_coin_state | [saga.proto.common.gamecoin.GameCoinState](#saga-proto-common-gamecoin-GameCoinState) |  |  |





 

 

 

 



<a name="streams_item_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## streams/item/definition.proto



<a name="saga-rpc-streams-item-ItemStatusUpdate"></a>

### ItemStatusUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |
| game_inventory_id | [string](#string) |  |  |
| game_item_type_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |
| serial_number | [int32](#int32) |  |  |
| metadata_uri | [string](#string) |  |  |
| item_state | [saga.proto.common.item.ItemState](#saga-proto-common-item-ItemState) |  |  |





 

 

 

 



<a name="streams_itemtype_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## streams/itemtype/definition.proto



<a name="saga-rpc-streams-itemtype-ItemTypeStatusUpdate"></a>

### ItemTypeStatusUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |
| game_item_type_id | [string](#string) |  |  |
| item_type_state | [saga.proto.common.itemtype.ItemTypeState](#saga-proto-common-itemtype-ItemTypeState) |  |  |





 

 

 

 



<a name="streams_listing_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## streams/listing/definition.proto



<a name="saga-rpc-streams-listing-ListingStatusUpdate"></a>

### ListingStatusUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  |  |
| trace_id | [string](#string) |  |  |
| quote_id | [string](#string) |  |  |
| listing_id | [string](#string) |  |  |
| total | [string](#string) |  |  |
| listing_state | [saga.proto.common.listing.ListingState](#saga-proto-common-listing-ListingState) |  |  |





 

 

 

 



<a name="streams_myth_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## streams/myth/definition.proto



<a name="saga-rpc-streams-myth-MythTokenStatusUpdate"></a>

### MythTokenStatusUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |
| token_state | [saga.proto.common.myth.MythTokenState](#saga-proto-common-myth-MythTokenState) |  |  |





 

 

 

 



<a name="streams_offer_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## streams/offer/definition.proto



<a name="saga-rpc-streams-offer-OfferStatusUpdate"></a>

### OfferStatusUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  |  |
| trace_id | [string](#string) |  |  |
| quote_id | [string](#string) |  |  |
| offer_id | [string](#string) |  |  |
| game_inventory_id | [string](#string) |  |  |
| total | [string](#string) |  |  |
| offer_state | [saga.proto.common.offer.OfferState](#saga-proto-common-offer-OfferState) |  |  |





 

 

 

 



<a name="streams_order_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## streams/order/definition.proto



<a name="saga-rpc-streams-order-OrderStatusUpdate"></a>

### OrderStatusUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  |  |
| trace_id | [string](#string) |  |  |
| quote_id | [string](#string) |  | TODO: quote == order? |
| order_id | [string](#string) |  |  |
| total | [string](#string) |  |  |
| order_state | [saga.proto.common.order.OrderState](#saga-proto-common-order-OrderState) |  |  |





 

 

 

 



<a name="streams_stream-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## streams/stream.proto



<a name="saga-rpc-streams-StatusConfirmRequest"></a>

### StatusConfirmRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  | TODO: what else to confirm with? |






<a name="saga-rpc-streams-StatusUpdate"></a>

### StatusUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |
| bridge_status | [bridge.BridgeStatusUpdate](#saga-rpc-streams-bridge-BridgeStatusUpdate) |  |  |
| game_coin_status | [gamecoin.GameCoinStatusUpdate](#saga-rpc-streams-gamecoin-GameCoinStatusUpdate) |  |  |
| item_status | [item.ItemStatusUpdate](#saga-rpc-streams-item-ItemStatusUpdate) |  |  |
| item_type_status | [itemtype.ItemTypeStatusUpdate](#saga-rpc-streams-itemtype-ItemTypeStatusUpdate) |  |  |
| listing_status | [listing.ListingStatusUpdate](#saga-rpc-streams-listing-ListingStatusUpdate) |  |  |
| myth_token_status | [myth.MythTokenStatusUpdate](#saga-rpc-streams-myth-MythTokenStatusUpdate) |  |  |
| offer_status | [offer.OfferStatusUpdate](#saga-rpc-streams-offer-OfferStatusUpdate) |  |  |
| order_status | [order.OrderStatusUpdate](#saga-rpc-streams-order-OrderStatusUpdate) |  |  |
| user_status | [user.UserStatusUpdate](#saga-rpc-streams-user-UserStatusUpdate) |  |  |
| created_timestamp | [int64](#int64) |  |  |





 

 

 


<a name="saga-rpc-streams-StatusStream"></a>

### StatusStream


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| StatusStream | [Subscribe](#saga-rpc-streams-Subscribe) | [StatusUpdate](#saga-rpc-streams-StatusUpdate) stream |  |
| StatusConfirmation | [StatusConfirmRequest](#saga-rpc-streams-StatusConfirmRequest) | [.google.protobuf.Empty](#google-protobuf-Empty) |  |

 



<a name="streams_user_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## streams/user/definition.proto



<a name="saga-rpc-streams-user-UserStatusUpdate"></a>

### UserStatusUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  |  |
| trace_id | [string](#string) |  |  |
| user_state | [saga.proto.common.user.UserState](#saga-proto-common-user-UserState) |  |  |





 

 

 

 



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

