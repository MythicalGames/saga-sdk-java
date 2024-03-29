# Protocol Documentation
<a name="top"></a>

## Table of Contents

- [api/common/error.proto](#api_common_error-proto)
    - [ErrorProto](#saga-api-common-ErrorProto)
  
- [api/currency/definition.proto](#api_currency_definition-proto)
    - [BalanceOfPlayerProto](#saga-api-currency-BalanceOfPlayerProto)
    - [BalanceProto](#saga-api-currency-BalanceProto)
    - [BalancesOfPlayerProto](#saga-api-currency-BalancesOfPlayerProto)
    - [BurnCurrencyRequest](#saga-api-currency-BurnCurrencyRequest)
    - [GetBalanceOfPlayerRequest](#saga-api-currency-GetBalanceOfPlayerRequest)
    - [GetBalancesOfPlayerRequest](#saga-api-currency-GetBalancesOfPlayerRequest)
    - [GetCurrencyForPlayerRequest](#saga-api-currency-GetCurrencyForPlayerRequest)
    - [IssueCurrencyRequest](#saga-api-currency-IssueCurrencyRequest)
    - [TransferCurrencyRequest](#saga-api-currency-TransferCurrencyRequest)
    - [UserAmountProto](#saga-api-currency-UserAmountProto)
  
- [api/currency/rpc.proto](#api_currency_rpc-proto)
    - [CurrencyService](#saga-api-currency-CurrencyService)
  
- [api/currencytype/definition.proto](#api_currencytype_definition-proto)
    - [CurrencyTypeProto](#saga-api-currencytype-CurrencyTypeProto)
    - [CurrencyTypesProto](#saga-api-currencytype-CurrencyTypesProto)
    - [GetCurrencyTypeRequest](#saga-api-currencytype-GetCurrencyTypeRequest)
    - [GetCurrencyTypesRequest](#saga-api-currencytype-GetCurrencyTypesRequest)
    - [PublisherBalanceProto](#saga-api-currencytype-PublisherBalanceProto)
  
- [api/currencytype/rpc.proto](#api_currencytype_rpc-proto)
    - [CurrencyTypeService](#saga-api-currencytype-CurrencyTypeService)
  
- [api/item/definition.proto](#api_item_definition-proto)
    - [BurnItemRequest](#saga-api-item-BurnItemRequest)
    - [DepositItemRequest](#saga-api-item-DepositItemRequest)
    - [GetItemRequest](#saga-api-item-GetItemRequest)
    - [GetItemsForPlayerRequest](#saga-api-item-GetItemsForPlayerRequest)
    - [GetItemsRequest](#saga-api-item-GetItemsRequest)
    - [IssueItemProto](#saga-api-item-IssueItemProto)
    - [IssueItemRequest](#saga-api-item-IssueItemRequest)
    - [ItemProto](#saga-api-item-ItemProto)
    - [ItemsProto](#saga-api-item-ItemsProto)
    - [TransferItemBulkRequest](#saga-api-item-TransferItemBulkRequest)
    - [TransferItemRequest](#saga-api-item-TransferItemRequest)
  
- [api/item/rpc.proto](#api_item_rpc-proto)
    - [ItemService](#saga-api-item-ItemService)
  
- [api/itemtype/definition.proto](#api_itemtype_definition-proto)
    - [CreateItemTypeRequest](#saga-api-itemtype-CreateItemTypeRequest)
    - [EndMintRequest](#saga-api-itemtype-EndMintRequest)
    - [FailedItemTypeBatch](#saga-api-itemtype-FailedItemTypeBatch)
    - [GetItemTypeRequest](#saga-api-itemtype-GetItemTypeRequest)
    - [GetItemTypesRequest](#saga-api-itemtype-GetItemTypesRequest)
    - [ItemTypeProto](#saga-api-itemtype-ItemTypeProto)
    - [ItemTypesProto](#saga-api-itemtype-ItemTypesProto)
    - [StartMintRequest](#saga-api-itemtype-StartMintRequest)
  
    - [MintMode](#saga-api-itemtype-MintMode)
  
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
  
- [api/metadata/definition.proto](#api_metadata_definition-proto)
    - [UpdateItemMetadataRequest](#saga-api-metadata-UpdateItemMetadataRequest)
  
- [api/metadata/rpc.proto](#api_metadata_rpc-proto)
    - [MetadataService](#saga-api-metadata-MetadataService)
  
- [api/nftbridge/definition.proto](#api_nftbridge_definition-proto)
    - [GetNftBridgeRequest](#saga-api-nftbridge-GetNftBridgeRequest)
    - [NftBridgeProto](#saga-api-nftbridge-NftBridgeProto)
    - [QuoteBridgeNFTRequest](#saga-api-nftbridge-QuoteBridgeNFTRequest)
    - [QuoteBridgeNFTResponse](#saga-api-nftbridge-QuoteBridgeNFTResponse)
    - [WithdrawItemRequest](#saga-api-nftbridge-WithdrawItemRequest)
  
- [api/nftbridge/rpc.proto](#api_nftbridge_rpc-proto)
    - [NftBridgeService](#saga-api-nftbridge-NftBridgeService)
  
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
  
- [api/playerwallet/definition.proto](#api_playerwallet_definition-proto)
    - [CreatePlayerWalletRequest](#saga-api-playerwallet-CreatePlayerWalletRequest)
    - [GetPlayerWalletRequest](#saga-api-playerwallet-GetPlayerWalletRequest)
    - [PlayerWalletProto](#saga-api-playerwallet-PlayerWalletProto)
  
- [api/playerwallet/rpc.proto](#api_playerwallet_rpc-proto)
    - [PlayerWalletService](#saga-api-playerwallet-PlayerWalletService)
  
- [api/reservation/definition.proto](#api_reservation_definition-proto)
    - [CreateReservationRequest](#saga-api-reservation-CreateReservationRequest)
    - [ItemReservationProto](#saga-api-reservation-ItemReservationProto)
    - [RedeemItemProto](#saga-api-reservation-RedeemItemProto)
    - [RedeemReservationRequest](#saga-api-reservation-RedeemReservationRequest)
    - [ReleaseReservationRequest](#saga-api-reservation-ReleaseReservationRequest)
  
- [api/reservation/rpc.proto](#api_reservation_rpc-proto)
    - [ReservationService](#saga-api-reservation-ReservationService)
  
- [common/common.proto](#common_common-proto)
    - [ErrorData](#saga-common-ErrorData)
    - [Metadata](#saga-common-Metadata)
    - [MetadataAttribute](#saga-common-MetadataAttribute)
    - [ReceivedResponse](#saga-common-ReceivedResponse)
    - [SubError](#saga-common-SubError)
  
- [common/currency/definition.proto](#common_currency_definition-proto)
    - [CurrencyState](#saga-proto-common-currency-CurrencyState)
  
- [common/currencytype/definition.proto](#common_currencytype_definition-proto)
    - [CurrencyTypeState](#saga-proto-common-currencytype-CurrencyTypeState)
  
- [common/finalization.proto](#common_finalization-proto)
    - [Finalized](#saga-common-Finalized)
  
- [common/item/definition.proto](#common_item_definition-proto)
    - [BlockChains](#saga-proto-common-item-BlockChains)
    - [ItemState](#saga-proto-common-item-ItemState)
  
- [common/itemtype/definition.proto](#common_itemtype_definition-proto)
    - [ItemTypeState](#saga-proto-common-itemtype-ItemTypeState)
  
- [common/listing/definition.proto](#common_listing_definition-proto)
    - [ListingState](#saga-proto-common-listing-ListingState)
  
- [common/offer/definition.proto](#common_offer_definition-proto)
    - [OfferState](#saga-proto-common-offer-OfferState)
  
- [common/query.proto](#common_query-proto)
    - [QueryOptionsProto](#saga-common-QueryOptionsProto)
  
- [common/sort.proto](#common_sort-proto)
    - [SortOrder](#saga-common-SortOrder)
  
- [streams/common.proto](#streams_common-proto)
    - [Subscribe](#saga-rpc-streams-Subscribe)
  
- [streams/currency/definition.proto](#streams_currency_definition-proto)
    - [BalanceProto](#saga-rpc-streams-currency-BalanceProto)
    - [CurrencyStatusUpdate](#saga-rpc-streams-currency-CurrencyStatusUpdate)
    - [CurrencyUpdate](#saga-rpc-streams-currency-CurrencyUpdate)
  
- [streams/currencytype/definition.proto](#streams_currencytype_definition-proto)
    - [CurrencyTypeStatusUpdate](#saga-rpc-streams-currencytype-CurrencyTypeStatusUpdate)
    - [CurrencyTypeUpdate](#saga-rpc-streams-currencytype-CurrencyTypeUpdate)
  
- [streams/item/definition.proto](#streams_item_definition-proto)
    - [ItemStatusUpdate](#saga-rpc-streams-item-ItemStatusUpdate)
    - [ItemStatusUpdates](#saga-rpc-streams-item-ItemStatusUpdates)
    - [ItemUpdate](#saga-rpc-streams-item-ItemUpdate)
  
- [streams/itemtype/definition.proto](#streams_itemtype_definition-proto)
    - [ItemTypeStatusUpdate](#saga-rpc-streams-itemtype-ItemTypeStatusUpdate)
    - [ItemTypeUpdate](#saga-rpc-streams-itemtype-ItemTypeUpdate)
  
- [streams/metadata/definition.proto](#streams_metadata_definition-proto)
    - [MetadataUpdate](#saga-rpc-streams-metadata-MetadataUpdate)
    - [MetadataUpdateProto](#saga-rpc-streams-metadata-MetadataUpdateProto)
  
- [streams/playerwallet/definition.proto](#streams_playerwallet_definition-proto)
    - [PlayerWalletStatusUpdate](#saga-rpc-streams-playerwallet-PlayerWalletStatusUpdate)
    - [PlayerWalletUpdate](#saga-rpc-streams-playerwallet-PlayerWalletUpdate)
  
- [streams/reservation/definition.proto](#streams_reservation_definition-proto)
    - [ReservationCreatedProto](#saga-rpc-streams-reservation-ReservationCreatedProto)
    - [ReservationRedeemedProto](#saga-rpc-streams-reservation-ReservationRedeemedProto)
    - [ReservationReleasedProto](#saga-rpc-streams-reservation-ReservationReleasedProto)
    - [ReservationUpdate](#saga-rpc-streams-reservation-ReservationUpdate)
  
- [streams/stream.proto](#streams_stream-proto)
    - [StatusConfirmRequest](#saga-rpc-streams-StatusConfirmRequest)
    - [StatusUpdate](#saga-rpc-streams-StatusUpdate)
  
    - [StatusStream](#saga-rpc-streams-StatusStream)
  
- [Scalar Value Types](#scalar-value-types)



<a name="api_common_error-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/common/error.proto



<a name="saga-api-common-ErrorProto"></a>

### ErrorProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| code | [int32](#int32) |  |  |
| message | [string](#string) |  |  |
| source | [string](#string) |  |  |
| trace_id | [string](#string) |  |  |





 

 

 

 



<a name="api_currency_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/currency/definition.proto



<a name="saga-api-currency-BalanceOfPlayerProto"></a>

### BalanceOfPlayerProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  |  |
| balance | [BalanceProto](#saga-api-currency-BalanceProto) |  |  |
| trace_id | [string](#string) |  |  |






<a name="saga-api-currency-BalanceProto"></a>

### BalanceProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| currency_type_id | [string](#string) |  |  |
| balance_in_wei | [string](#string) |  |  |






<a name="saga-api-currency-BalancesOfPlayerProto"></a>

### BalancesOfPlayerProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  |  |
| balances | [BalanceProto](#saga-api-currency-BalanceProto) | repeated |  |
| trace_id | [string](#string) |  |  |






<a name="saga-api-currency-BurnCurrencyRequest"></a>

### BurnCurrencyRequest
Burn currency call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| currency_type_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |
| amount_in_wei | [string](#string) |  |  |
| idempotency_id | [string](#string) |  |  |
| prefund_gas | [bool](#bool) |  |  |






<a name="saga-api-currency-GetBalanceOfPlayerRequest"></a>

### GetBalanceOfPlayerRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| currency_type_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  | User to get balance for |






<a name="saga-api-currency-GetBalancesOfPlayerRequest"></a>

### GetBalancesOfPlayerRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  | User to get balances for |
| query_options | [saga.common.QueryOptionsProto](#saga-common-QueryOptionsProto) |  |  |






<a name="saga-api-currency-GetCurrencyForPlayerRequest"></a>

### GetCurrencyForPlayerRequest
Get Currency call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  | User to get currency for |
| currency_type_id | [string](#string) |  |  |






<a name="saga-api-currency-IssueCurrencyRequest"></a>

### IssueCurrencyRequest
Issue currency call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| currency_type_id | [string](#string) |  | Currency Type the currency to issue belongs to |
| user_amounts | [UserAmountProto](#saga-api-currency-UserAmountProto) | repeated | Users to issue currency to |
| idempotency_id | [string](#string) |  | Unique id to ensure request is processed only once |






<a name="saga-api-currency-TransferCurrencyRequest"></a>

### TransferCurrencyRequest
Transfer currency call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| currency_type_id | [string](#string) |  |  |
| source_oauth_id | [string](#string) |  |  |
| dest_oauth_id | [string](#string) |  |  |
| amount_in_wei | [string](#string) |  |  |
| idempotency_id | [string](#string) |  |  |
| prefund_gas | [bool](#bool) |  |  |






<a name="saga-api-currency-UserAmountProto"></a>

### UserAmountProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  | User to issue currency to |
| amount_in_wei | [string](#string) |  | Amount to issue |





 

 

 

 



<a name="api_currency_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/currency/rpc.proto


 

 

 


<a name="saga-api-currency-CurrencyService"></a>

### CurrencyService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| IssueCurrency | [IssueCurrencyRequest](#saga-api-currency-IssueCurrencyRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Issue currency to a user |
| TransferCurrency | [TransferCurrencyRequest](#saga-api-currency-TransferCurrencyRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Transfer currency between users |
| BurnCurrency | [BurnCurrencyRequest](#saga-api-currency-BurnCurrencyRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Burn currency for a user |
| GetBalanceOfPlayer | [GetBalanceOfPlayerRequest](#saga-api-currency-GetBalanceOfPlayerRequest) | [BalanceOfPlayerProto](#saga-api-currency-BalanceOfPlayerProto) | Get Balance of a player |
| GetBalancesOfPlayer | [GetBalancesOfPlayerRequest](#saga-api-currency-GetBalancesOfPlayerRequest) | [BalancesOfPlayerProto](#saga-api-currency-BalancesOfPlayerProto) | Get Balances of a player |

 



<a name="api_currencytype_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/currencytype/definition.proto



<a name="saga-api-currencytype-CurrencyTypeProto"></a>

### CurrencyTypeProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |
| currency_type_id | [string](#string) |  |  |
| name | [string](#string) |  |  |
| symbol | [string](#string) |  |  |
| image_url | [string](#string) |  |  |
| contract_address | [string](#string) |  |  |
| transaction_id | [string](#string) |  |  |
| publisher_balance | [PublisherBalanceProto](#saga-api-currencytype-PublisherBalanceProto) |  |  |
| created_at | [google.protobuf.Timestamp](#google-protobuf-Timestamp) |  |  |
| updated_at | [google.protobuf.Timestamp](#google-protobuf-Timestamp) |  |  |






<a name="saga-api-currencytype-CurrencyTypesProto"></a>

### CurrencyTypesProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| currency_types | [CurrencyTypeProto](#saga-api-currencytype-CurrencyTypeProto) | repeated |  |






<a name="saga-api-currencytype-GetCurrencyTypeRequest"></a>

### GetCurrencyTypeRequest
Currency Type call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| currency_type_id | [string](#string) |  |  |






<a name="saga-api-currencytype-GetCurrencyTypesRequest"></a>

### GetCurrencyTypesRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| query_options | [saga.common.QueryOptionsProto](#saga-common-QueryOptionsProto) |  |  |






<a name="saga-api-currencytype-PublisherBalanceProto"></a>

### PublisherBalanceProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| publisher_address | [string](#string) |  |  |
| balance_in_wei | [string](#string) |  |  |





 

 

 

 



<a name="api_currencytype_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/currencytype/rpc.proto


 

 

 


<a name="saga-api-currencytype-CurrencyTypeService"></a>

### CurrencyTypeService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| GetCurrencyType | [GetCurrencyTypeRequest](#saga-api-currencytype-GetCurrencyTypeRequest) | [CurrencyTypeProto](#saga-api-currencytype-CurrencyTypeProto) |  |
| GetCurrencyTypes | [GetCurrencyTypesRequest](#saga-api-currencytype-GetCurrencyTypesRequest) | [CurrencyTypesProto](#saga-api-currencytype-CurrencyTypesProto) |  |

 



<a name="api_item_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/item/definition.proto



<a name="saga-api-item-BurnItemRequest"></a>

### BurnItemRequest
Burn item call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| inventory_id | [string](#string) |  | Game&#39;s id for the Item to burn |
| prefund_gas | [bool](#bool) |  |  |






<a name="saga-api-item-DepositItemRequest"></a>

### DepositItemRequest
Deposit item call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| inventory_id | [string](#string) |  |  |
| created_by | [string](#string) |  |  |
| from_address | [string](#string) |  |  |
| to_address | [string](#string) |  |  |
| from_chain | [saga.proto.common.item.BlockChains](#saga-proto-common-item-BlockChains) |  |  |
| transaction_id | [string](#string) |  |  |






<a name="saga-api-item-GetItemRequest"></a>

### GetItemRequest
Get Item call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| inventory_id | [string](#string) |  | Game&#39;s id for the Item to retrieve |






<a name="saga-api-item-GetItemsForPlayerRequest"></a>

### GetItemsForPlayerRequest
Get Items for Player call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  | Player to get Items for |
| query_options | [saga.common.QueryOptionsProto](#saga-common-QueryOptionsProto) |  | Sort/filter options |






<a name="saga-api-item-GetItemsRequest"></a>

### GetItemsRequest
Get Items call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| query_options | [saga.common.QueryOptionsProto](#saga-common-QueryOptionsProto) |  | Sort/filter options |
| finalized | [saga.common.Finalized](#saga-common-Finalized) |  |  |
| token_name | [string](#string) |  |  |






<a name="saga-api-item-IssueItemProto"></a>

### IssueItemProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| inventory_id | [string](#string) |  | GameInventory Ids of Items being issued |
| metadata | [saga.common.Metadata](#saga-common-Metadata) |  | Metadata associated to Item being issued |
| token_id | [uint64](#uint64) |  | TokenId associated to Item being issued |






<a name="saga-api-item-IssueItemRequest"></a>

### IssueItemRequest
Issue item call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| items | [IssueItemProto](#saga-api-item-IssueItemProto) | repeated | Repeated items so that metadata is unique per item |
| recipient_oauth_id | [string](#string) |  | Oauth id of wallet accepting items |
| item_type_id | [string](#string) |  | Unique id set for your game of the Item being issued |






<a name="saga-api-item-ItemProto"></a>

### ItemProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |
| inventory_id | [string](#string) |  | The game&#39;s unique id for this Item |
| oauth_id | [string](#string) |  |  |
| token_id | [int64](#int64) |  |  |
| finalized | [bool](#bool) |  |  |
| block_explorer_url | [string](#string) |  |  |
| metadata_url | [string](#string) |  |  |
| created_at | [google.protobuf.Timestamp](#google-protobuf-Timestamp) |  | When was this Item created |
| updated_at | [google.protobuf.Timestamp](#google-protobuf-Timestamp) |  | When was this Item last updated |
| state | [string](#string) |  |  |






<a name="saga-api-item-ItemsProto"></a>

### ItemsProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| items | [ItemProto](#saga-api-item-ItemProto) | repeated |  |






<a name="saga-api-item-TransferItemBulkRequest"></a>

### TransferItemBulkRequest
Transfer bulk item call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| game_title_id | [string](#string) |  |  |
| inventory_ids | [string](#string) | repeated | Game&#39;s ids for the Item to transfer |
| destination_oauth_id | [string](#string) |  | User of Item to transfer to |
| idempotency_id | [string](#string) |  |  |






<a name="saga-api-item-TransferItemRequest"></a>

### TransferItemRequest
Transfer item call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| inventory_id | [string](#string) |  | Game&#39;s id for the Item to transfer |
| destination_oauth_id | [string](#string) |  | User of Item to transfer to |
| idempotency_id | [string](#string) |  |  |
| prefund_gas | [bool](#bool) |  |  |





 

 

 

 



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
| TransferItemBulk | [TransferItemBulkRequest](#saga-api-item-TransferItemBulkRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Transfer Items between users |
| BurnItem | [BurnItemRequest](#saga-api-item-BurnItemRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Burn an Item |
| DepositItem | [DepositItemRequest](#saga-api-item-DepositItemRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Deposit an Item |

 



<a name="api_itemtype_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/itemtype/definition.proto



<a name="saga-api-itemtype-CreateItemTypeRequest"></a>

### CreateItemTypeRequest
Create item type call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| item_type_id | [string](#string) |  | The game&#39;s unique id for this ItemType |
| name | [string](#string) |  |  |
| symbol | [string](#string) |  |  |
| max_supply | [int64](#int64) |  |  |
| randomize | [bool](#bool) |  | **Deprecated.**  |
| date_finished | [google.protobuf.Timestamp](#google-protobuf-Timestamp) |  |  |
| withdrawable | [bool](#bool) |  | **Deprecated.**  |
| mint_mode | [MintMode](#saga-api-itemtype-MintMode) |  |  |






<a name="saga-api-itemtype-EndMintRequest"></a>

### EndMintRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| item_type_id | [string](#string) |  |  |






<a name="saga-api-itemtype-FailedItemTypeBatch"></a>

### FailedItemTypeBatch



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| item_type_id | [string](#string) |  |  |






<a name="saga-api-itemtype-GetItemTypeRequest"></a>

### GetItemTypeRequest
Get ItemType call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| item_type_id | [string](#string) |  |  |






<a name="saga-api-itemtype-GetItemTypesRequest"></a>

### GetItemTypesRequest
Get ItemTypes call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| game_title_id | [string](#string) |  |  |
| publisher_address | [string](#string) |  |  |
| query_options | [saga.common.QueryOptionsProto](#saga-common-QueryOptionsProto) |  |  |






<a name="saga-api-itemtype-ItemTypeProto"></a>

### ItemTypeProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |
| item_type_id | [string](#string) |  |  |
| name | [string](#string) |  |  |
| symbol | [string](#string) |  |  |
| max_supply | [int64](#int64) |  |  |
| contract_address | [string](#string) |  |  |
| block_explorer_url | [string](#string) |  |  |
| finalized | [bool](#bool) |  |  |
| withdrawable | [bool](#bool) |  | **Deprecated.**  |
| created_at | [google.protobuf.Timestamp](#google-protobuf-Timestamp) |  |  |
| updated_at | [google.protobuf.Timestamp](#google-protobuf-Timestamp) |  |  |
| issued_supply | [int64](#int64) |  |  |
| available_supply | [google.protobuf.Int64Value](#google-protobuf-Int64Value) |  |  |
| mintable | [bool](#bool) |  |  |
| mint_ended | [bool](#bool) |  |  |
| randomize | [bool](#bool) |  | **Deprecated.**  |
| total_supply | [int64](#int64) |  |  |
| mint_mode | [MintMode](#saga-api-itemtype-MintMode) |  |  |
| bridgeable | [bool](#bool) |  |  |






<a name="saga-api-itemtype-ItemTypesProto"></a>

### ItemTypesProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| item_types | [ItemTypeProto](#saga-api-itemtype-ItemTypeProto) | repeated |  |






<a name="saga-api-itemtype-StartMintRequest"></a>

### StartMintRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| item_type_id | [string](#string) |  |  |





 


<a name="saga-api-itemtype-MintMode"></a>

### MintMode


| Name | Number | Description |
| ---- | ------ | ----------- |
| MINT_MODE_UNSPECIFIED | 0 |  |
| MINT_MODE_SERIAL | 1 |  |
| MINT_MODE_RANDOM | 2 |  |
| MINT_MODE_SELECTED | 3 |  |


 

 

 



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
| StartMint | [StartMintRequest](#saga-api-itemtype-StartMintRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Update the ItemType |
| EndMint | [EndMintRequest](#saga-api-itemtype-EndMintRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) |  |

 



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
| inventory_id | [string](#string) |  | Id of GameInventory for this Listing |
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
| trace_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  | User of this Listing |
| inventory_id | [string](#string) |  | Item associated with this Listing |
| currency | [string](#string) |  | Type of currency the total is in |
| total | [string](#string) |  | Total price of Listing |
| created_at | [google.protobuf.Timestamp](#google-protobuf-Timestamp) |  | When the Listing was created |






<a name="saga-api-listing-ListingQuoteProto"></a>

### ListingQuoteProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  | User of this Listing |
| quote_id | [string](#string) |  | unique QuoteId for this Listing |
| inventory_id | [string](#string) |  | Item associated with this Listing |
| tax | [string](#string) |  |  |
| tax_currency | [string](#string) |  | Currency the tax is in |
| total | [string](#string) |  | Total price of Listing |
| currency | [string](#string) |  | Type of currency the total is in |
| created_at | [google.protobuf.Timestamp](#google-protobuf-Timestamp) |  | When the Listing was created |






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

 



<a name="api_metadata_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/metadata/definition.proto



<a name="saga-api-metadata-UpdateItemMetadataRequest"></a>

### UpdateItemMetadataRequest
Update Metadata call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| inventory_id | [string](#string) |  | Update Metadata for the Item with this id |
| metadata | [saga.common.Metadata](#saga-common-Metadata) |  | Metadata to update with |





 

 

 

 



<a name="api_metadata_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/metadata/rpc.proto


 

 

 


<a name="saga-api-metadata-MetadataService"></a>

### MetadataService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| UpdateItemMetadata | [UpdateItemMetadataRequest](#saga-api-metadata-UpdateItemMetadataRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Update an Item&#39;s metadata |

 



<a name="api_nftbridge_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/nftbridge/definition.proto



<a name="saga-api-nftbridge-GetNftBridgeRequest"></a>

### GetNftBridgeRequest
Get NftBridge Call






<a name="saga-api-nftbridge-NftBridgeProto"></a>

### NftBridgeProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |
| mythical_address | [string](#string) |  |  |
| mainnet_address | [string](#string) |  | Address on Mainnet |
| chain_name | [string](#string) |  | Name of the chain |






<a name="saga-api-nftbridge-QuoteBridgeNFTRequest"></a>

### QuoteBridgeNFTRequest
Get Bridge Quote Call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| origin_chain_id | [int32](#int32) |  |  |
| target_chain_id | [int32](#int32) |  |  |
| game_title_id | [string](#string) |  |  |
| inventory_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |






<a name="saga-api-nftbridge-QuoteBridgeNFTResponse"></a>

### QuoteBridgeNFTResponse



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |
| fee_in_originchain_native_token | [string](#string) |  |  |
| fee_in_originchain_native_token_unit | [string](#string) |  |  |
| fee_in_usd | [string](#string) |  |  |
| expires_at | [string](#string) |  |  |
| gas_price_originchain | [string](#string) |  |  |
| gas_price_originchain_unit | [string](#string) |  |  |
| gas_price_targetchain | [string](#string) |  |  |
| gas_price_targetchain_unit | [string](#string) |  |  |
| signature | [string](#string) |  |  |






<a name="saga-api-nftbridge-WithdrawItemRequest"></a>

### WithdrawItemRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| quote_request | [QuoteBridgeNFTRequest](#saga-api-nftbridge-QuoteBridgeNFTRequest) |  |  |
| fee_in_originchain_native_token | [string](#string) |  |  |
| expires_at | [string](#string) |  |  |
| signature | [string](#string) |  |  |
| item_type_id | [string](#string) |  |  |
| targetchain_wallet_address | [string](#string) |  |  |





 

 

 

 



<a name="api_nftbridge_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/nftbridge/rpc.proto


 

 

 


<a name="saga-api-nftbridge-NftBridgeService"></a>

### NftBridgeService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| WithdrawItem | [WithdrawItemRequest](#saga-api-nftbridge-WithdrawItemRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Withdraw an Item |
| GetBridge | [GetNftBridgeRequest](#saga-api-nftbridge-GetNftBridgeRequest) | [NftBridgeProto](#saga-api-nftbridge-NftBridgeProto) | Get Bridge |
| GetBridgeQuote | [QuoteBridgeNFTRequest](#saga-api-nftbridge-QuoteBridgeNFTRequest) | [QuoteBridgeNFTResponse](#saga-api-nftbridge-QuoteBridgeNFTResponse) | Get Bridge Quote |

 



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
| inventory_id | [string](#string) |  | GameInventory Id of the Offer |
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
| inventory_id | [string](#string) |  | GameInventory Id of the Offer |
| currency | [string](#string) |  | Currency of the total |
| total | [string](#string) |  | Total cost of the offer |
| created_at | [google.protobuf.Timestamp](#google-protobuf-Timestamp) |  | When the offer was created |






<a name="saga-api-offer-OfferQuoteProto"></a>

### OfferQuoteProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  | User of this Offer Quote |
| quote_id | [string](#string) |  | Id of this Offer Quote |
| inventory_id | [string](#string) |  | GameInventory Id for this Offer Quote |
| tax | [string](#string) |  | Amount of tax

@exclude and fees? |
| tax_currency | [string](#string) |  | Currency that Tax is in |
| total | [string](#string) |  | Total cost of the offer |
| currency | [string](#string) |  | Currency the total is in |
| created_at | [google.protobuf.Timestamp](#google-protobuf-Timestamp) |  | When the Offer was created |






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

 



<a name="api_playerwallet_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/playerwallet/definition.proto



<a name="saga-api-playerwallet-CreatePlayerWalletRequest"></a>

### CreatePlayerWalletRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  |  |






<a name="saga-api-playerwallet-GetPlayerWalletRequest"></a>

### GetPlayerWalletRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  |  |
| wallet_address | [string](#string) |  |  |






<a name="saga-api-playerwallet-PlayerWalletProto"></a>

### PlayerWalletProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |
| address | [string](#string) |  |  |
| balance_in_wei | [string](#string) |  |  |
| created_at | [google.protobuf.Timestamp](#google-protobuf-Timestamp) |  |  |
| updated_at | [google.protobuf.Timestamp](#google-protobuf-Timestamp) |  |  |
| withdrawable_limit_in_wei | [string](#string) |  |  |





 

 

 

 



<a name="api_playerwallet_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/playerwallet/rpc.proto


 

 

 


<a name="saga-api-playerwallet-PlayerWalletService"></a>

### PlayerWalletService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| CreatePlayerWallet | [CreatePlayerWalletRequest](#saga-api-playerwallet-CreatePlayerWalletRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Create a Player Wallet |
| GetPlayerWallet | [GetPlayerWalletRequest](#saga-api-playerwallet-GetPlayerWalletRequest) | [PlayerWalletProto](#saga-api-playerwallet-PlayerWalletProto) | Get a Player Wallet |

 



<a name="api_reservation_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/reservation/definition.proto



<a name="saga-api-reservation-CreateReservationRequest"></a>

### CreateReservationRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| reservation_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |
| item_reservations | [ItemReservationProto](#saga-api-reservation-ItemReservationProto) | repeated |  |
| ttl | [google.protobuf.Int64Value](#google-protobuf-Int64Value) |  |  |






<a name="saga-api-reservation-ItemReservationProto"></a>

### ItemReservationProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| item_type_id | [string](#string) |  |  |
| count | [int64](#int64) |  |  |






<a name="saga-api-reservation-RedeemItemProto"></a>

### RedeemItemProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| inventory_id | [string](#string) |  |  |
| item_type_id | [string](#string) |  |  |
| metadata | [saga.common.Metadata](#saga-common-Metadata) |  |  |
| token_id | [uint64](#uint64) |  |  |






<a name="saga-api-reservation-RedeemReservationRequest"></a>

### RedeemReservationRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| reservation_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |
| items | [RedeemItemProto](#saga-api-reservation-RedeemItemProto) | repeated |  |






<a name="saga-api-reservation-ReleaseReservationRequest"></a>

### ReleaseReservationRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| reservation_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |





 

 

 

 



<a name="api_reservation_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/reservation/rpc.proto


 

 

 


<a name="saga-api-reservation-ReservationService"></a>

### ReservationService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| CreateReservation | [CreateReservationRequest](#saga-api-reservation-CreateReservationRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) |  |
| RedeemReservation | [RedeemReservationRequest](#saga-api-reservation-RedeemReservationRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) |  |
| ReleaseReservation | [ReleaseReservationRequest](#saga-api-reservation-ReleaseReservationRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) |  |

 



<a name="common_common-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## common/common.proto



<a name="saga-common-ErrorData"></a>

### ErrorData



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| error_code | [string](#string) |  | The error code for this type of error |
| message | [string](#string) |  | Description of the error |
| source | [string](#string) |  | Indicator of which service the error occurred in |
| trace | [string](#string) |  | Trace id for this operation, if any |
| metadata | [google.protobuf.Struct](#google-protobuf-Struct) |  | Error metadata |
| suberrors | [SubError](#saga-common-SubError) | repeated | Sub-errors assocated with this incident |






<a name="saga-common-Metadata"></a>

### Metadata
Metadata properties of Item


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| name | [string](#string) |  | Name of item |
| description | [string](#string) |  | Description of the item |
| image | [string](#string) |  | URL to the image of the item |
| external_url | [string](#string) |  | URL to the item |
| background_color | [string](#string) |  | OpenSea background color |
| animation_url | [string](#string) |  | URL to a media/animation file |
| youtube_url | [string](#string) |  | URL to a YouTube video |
| attributes | [MetadataAttribute](#saga-common-MetadataAttribute) | repeated | Metadata attributes |






<a name="saga-common-MetadataAttribute"></a>

### MetadataAttribute



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trait_type | [string](#string) |  | Name of the trait/attribute |
| display_type | [string](#string) |  | Display type (number, date, etc.). Not needed for string traits |
| max_value | [google.protobuf.DoubleValue](#google-protobuf-DoubleValue) |  | For numeric traits, a maximum allowed value |
| str_value | [string](#string) |  |  |
| int_value | [int64](#int64) |  |  |
| double_value | [double](#double) |  |  |






<a name="saga-common-ReceivedResponse"></a>

### ReceivedResponse



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |






<a name="saga-common-SubError"></a>

### SubError



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| error_code | [string](#string) |  | The error code for this type of error |
| message | [string](#string) |  | Description of the error |
| source | [string](#string) |  | Indicator of which service the error occurred in |
| metadata | [google.protobuf.Struct](#google-protobuf-Struct) |  | Error metadata |





 

 

 

 



<a name="common_currency_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## common/currency/definition.proto


 


<a name="saga-proto-common-currency-CurrencyState"></a>

### CurrencyState


| Name | Number | Description |
| ---- | ------ | ----------- |
| FAILED | 0 | Failed to issue/transfer/burn Currency |
| ISSUED | 1 | Currency issued successfully |
| TRANSFERRED | 2 | Currency transferred successfully |
| BURNED | 3 | Currency burned successfully |


 

 

 



<a name="common_currencytype_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## common/currencytype/definition.proto


 


<a name="saga-proto-common-currencytype-CurrencyTypeState"></a>

### CurrencyTypeState


| Name | Number | Description |
| ---- | ------ | ----------- |
| FAILED | 0 | CurrencyType failed to perform current action |
| CREATED | 1 | CurrencyType issued successfully |


 

 

 



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


 

 

 



<a name="common_item_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## common/item/definition.proto


 


<a name="saga-proto-common-item-BlockChains"></a>

### BlockChains


| Name | Number | Description |
| ---- | ------ | ----------- |
| ETH | 0 |  |



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
| BRIDGED_FROM_MYTHICAL_CHAIN | 8 |  |
| BRIDGED_TO_MYTHICAL_CHAIN | 9 |  |


 

 

 



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
| MINTABLE | 5 | ItemType can be minted |
| MINT_ENDED | 6 | ItemType mint has permanently ended |
| BRIDGEABLE | 7 | ItemType is now bridgeable to an external chain(s) |


 

 

 



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


 

 

 



<a name="common_query-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## common/query.proto



<a name="saga-common-QueryOptionsProto"></a>

### QueryOptionsProto
Options allowed when querying


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| page_size | [int32](#int32) |  | Size of the page of results |
| sort_order | [SortOrder](#saga-common-SortOrder) |  | Which order to sort |
| created_at_cursor | [google.protobuf.Timestamp](#google-protobuf-Timestamp) | optional | Cursor-based pagination based on created_at |





 

 

 

 



<a name="common_sort-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## common/sort.proto


 


<a name="saga-common-SortOrder"></a>

### SortOrder


| Name | Number | Description |
| ---- | ------ | ----------- |
| ASC | 0 | Sort ascending |
| DESC | 1 | Sort descending |


 

 

 



<a name="streams_common-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## streams/common.proto



<a name="saga-rpc-streams-Subscribe"></a>

### Subscribe



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| title_id | [string](#string) |  |  |
| stream_id | [string](#string) |  |  |
| replay_since | [google.protobuf.Timestamp](#google-protobuf-Timestamp) | optional |  |





 

 

 

 



<a name="streams_currency_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## streams/currency/definition.proto



<a name="saga-rpc-streams-currency-BalanceProto"></a>

### BalanceProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  |  |
| currency_type_id | [string](#string) |  |  |
| balance_in_wei | [string](#string) |  |  |






<a name="saga-rpc-streams-currency-CurrencyStatusUpdate"></a>

### CurrencyStatusUpdate
Results from a Currency status update gRPC stream call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| transaction_id | [string](#string) |  | Id given from chain |
| balances | [BalanceProto](#saga-rpc-streams-currency-BalanceProto) | repeated |  |
| idempotency_id | [string](#string) |  |  |
| currency_state | [saga.proto.common.currency.CurrencyState](#saga-proto-common-currency-CurrencyState) |  | State of the Currency, see CurrencyState |






<a name="saga-rpc-streams-currency-CurrencyUpdate"></a>

### CurrencyUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| error | [saga.common.ErrorData](#saga-common-ErrorData) |  |  |
| status_update | [CurrencyStatusUpdate](#saga-rpc-streams-currency-CurrencyStatusUpdate) |  |  |





 

 

 

 



<a name="streams_currencytype_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## streams/currencytype/definition.proto



<a name="saga-rpc-streams-currencytype-CurrencyTypeStatusUpdate"></a>

### CurrencyTypeStatusUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| currency_type_id | [string](#string) |  |  |
| currency_type_state | [saga.proto.common.currencytype.CurrencyTypeState](#saga-proto-common-currencytype-CurrencyTypeState) |  |  |
| transaction_id | [string](#string) |  |  |
| contract_address | [string](#string) |  |  |
| idempotency_id | [string](#string) |  |  |






<a name="saga-rpc-streams-currencytype-CurrencyTypeUpdate"></a>

### CurrencyTypeUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| error | [saga.common.ErrorData](#saga-common-ErrorData) |  |  |
| status_update | [CurrencyTypeStatusUpdate](#saga-rpc-streams-currencytype-CurrencyTypeStatusUpdate) |  |  |





 

 

 

 



<a name="streams_item_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## streams/item/definition.proto



<a name="saga-rpc-streams-item-ItemStatusUpdate"></a>

### ItemStatusUpdate
Results from an Item status update gRPC stream call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| inventory_id | [string](#string) |  | Game&#39;s unique Id for the Item |
| item_type_id | [string](#string) |  | Game&#39;s ItemTypeId for the ItemType for this Item |
| oauth_id | [string](#string) |  | User for this Item |
| token_id | [int64](#int64) |  |  |
| metadata_url | [string](#string) |  | Metadata address |
| item_state | [saga.proto.common.item.ItemState](#saga-proto-common-item-ItemState) |  | State of the Item, see ItemState |






<a name="saga-rpc-streams-item-ItemStatusUpdates"></a>

### ItemStatusUpdates



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| status_updates | [ItemStatusUpdate](#saga-rpc-streams-item-ItemStatusUpdate) | repeated |  |






<a name="saga-rpc-streams-item-ItemUpdate"></a>

### ItemUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| error | [saga.common.ErrorData](#saga-common-ErrorData) |  |  |
| status_update | [ItemStatusUpdate](#saga-rpc-streams-item-ItemStatusUpdate) |  |  |
| status_updates | [ItemStatusUpdates](#saga-rpc-streams-item-ItemStatusUpdates) |  |  |





 

 

 

 



<a name="streams_itemtype_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## streams/itemtype/definition.proto



<a name="saga-rpc-streams-itemtype-ItemTypeStatusUpdate"></a>

### ItemTypeStatusUpdate
Results from a ItemType status update gRPC stream call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| item_type_id | [string](#string) |  | Game&#39;s unique id for this ItemType |
| item_type_state | [saga.proto.common.itemtype.ItemTypeState](#saga-proto-common-itemtype-ItemTypeState) |  | State of the ItemType, see ItemTypeState |






<a name="saga-rpc-streams-itemtype-ItemTypeUpdate"></a>

### ItemTypeUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| error | [saga.common.ErrorData](#saga-common-ErrorData) |  |  |
| status_update | [ItemTypeStatusUpdate](#saga-rpc-streams-itemtype-ItemTypeStatusUpdate) |  |  |





 

 

 

 



<a name="streams_metadata_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## streams/metadata/definition.proto



<a name="saga-rpc-streams-metadata-MetadataUpdate"></a>

### MetadataUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| error | [saga.common.ErrorData](#saga-common-ErrorData) |  |  |
| metadata_updated | [MetadataUpdateProto](#saga-rpc-streams-metadata-MetadataUpdateProto) |  |  |






<a name="saga-rpc-streams-metadata-MetadataUpdateProto"></a>

### MetadataUpdateProto
Results from an metadata status update gRPC stream call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| inventory_id | [string](#string) |  | Game&#39;s unique Id for the Item |
| metadata_url | [string](#string) |  | Metadata address |





 

 

 

 



<a name="streams_playerwallet_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## streams/playerwallet/definition.proto



<a name="saga-rpc-streams-playerwallet-PlayerWalletStatusUpdate"></a>

### PlayerWalletStatusUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  | Wallet owner OAuth ID |






<a name="saga-rpc-streams-playerwallet-PlayerWalletUpdate"></a>

### PlayerWalletUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| error | [saga.common.ErrorData](#saga-common-ErrorData) |  |  |
| status_update | [PlayerWalletStatusUpdate](#saga-rpc-streams-playerwallet-PlayerWalletStatusUpdate) |  |  |





 

 

 

 



<a name="streams_reservation_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## streams/reservation/definition.proto



<a name="saga-rpc-streams-reservation-ReservationCreatedProto"></a>

### ReservationCreatedProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| reservation_id | [string](#string) |  |  |






<a name="saga-rpc-streams-reservation-ReservationRedeemedProto"></a>

### ReservationRedeemedProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| reservation_id | [string](#string) |  |  |
| items | [saga.api.item.ItemProto](#saga-api-item-ItemProto) | repeated |  |
| failed_batches | [saga.api.itemtype.FailedItemTypeBatch](#saga-api-itemtype-FailedItemTypeBatch) | repeated |  |






<a name="saga-rpc-streams-reservation-ReservationReleasedProto"></a>

### ReservationReleasedProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| reservation_id | [string](#string) |  |  |






<a name="saga-rpc-streams-reservation-ReservationUpdate"></a>

### ReservationUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| error | [saga.common.ErrorData](#saga-common-ErrorData) |  |  |
| reservation_created | [ReservationCreatedProto](#saga-rpc-streams-reservation-ReservationCreatedProto) |  |  |
| reservation_released | [ReservationReleasedProto](#saga-rpc-streams-reservation-ReservationReleasedProto) |  |  |
| reservation_redeemed | [ReservationRedeemedProto](#saga-rpc-streams-reservation-ReservationRedeemedProto) |  |  |





 

 

 

 



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
| currency_update | [currency.CurrencyUpdate](#saga-rpc-streams-currency-CurrencyUpdate) |  |  |
| item_update | [item.ItemUpdate](#saga-rpc-streams-item-ItemUpdate) |  |  |
| item_type_update | [itemtype.ItemTypeUpdate](#saga-rpc-streams-itemtype-ItemTypeUpdate) |  |  |
| player_wallet_update | [playerwallet.PlayerWalletUpdate](#saga-rpc-streams-playerwallet-PlayerWalletUpdate) |  |  |
| reservation_update | [reservation.ReservationUpdate](#saga-rpc-streams-reservation-ReservationUpdate) |  |  |
| metadata_update | [metadata.MetadataUpdate](#saga-rpc-streams-metadata-MetadataUpdate) |  |  |
| currency_type_update | [currencytype.CurrencyTypeUpdate](#saga-rpc-streams-currencytype-CurrencyTypeUpdate) |  |  |
| created_at | [google.protobuf.Timestamp](#google-protobuf-Timestamp) |  |  |





 

 

 


<a name="saga-rpc-streams-StatusStream"></a>

### StatusStream


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| StatusStream | [Subscribe](#saga-rpc-streams-Subscribe) | [StatusUpdate](#saga-rpc-streams-StatusUpdate) stream | Send a call to update the status |
| StatusConfirmation | [StatusConfirmRequest](#saga-rpc-streams-StatusConfirmRequest) | [.google.protobuf.Empty](#google-protobuf-Empty) | Get verification of status |

 



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

