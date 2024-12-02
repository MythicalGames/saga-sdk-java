# Protocol Documentation
<a name="top"></a>

## Table of Contents

- [sdk/api/common/error.proto](#sdk_api_common_error-proto)
    - [ErrorProto](#sdk-saga-api-common-ErrorProto)
  
- [sdk/api/currency/definition.proto](#sdk_api_currency_definition-proto)
    - [BalanceOfPlayerProto](#sdk-saga-api-currency-BalanceOfPlayerProto)
    - [BalanceProto](#sdk-saga-api-currency-BalanceProto)
    - [BalancesOfPlayerProto](#sdk-saga-api-currency-BalancesOfPlayerProto)
    - [BurnCurrencyRequest](#sdk-saga-api-currency-BurnCurrencyRequest)
    - [GetBalanceOfPlayerRequest](#sdk-saga-api-currency-GetBalanceOfPlayerRequest)
    - [GetBalancesOfPlayerRequest](#sdk-saga-api-currency-GetBalancesOfPlayerRequest)
    - [GetCurrencyForPlayerRequest](#sdk-saga-api-currency-GetCurrencyForPlayerRequest)
    - [IssueCurrencyRequest](#sdk-saga-api-currency-IssueCurrencyRequest)
    - [TransferCurrencyRequest](#sdk-saga-api-currency-TransferCurrencyRequest)
    - [UserAmountProto](#sdk-saga-api-currency-UserAmountProto)
  
- [sdk/api/currency/rpc.proto](#sdk_api_currency_rpc-proto)
    - [CurrencyService](#sdk-saga-api-currency-CurrencyService)
  
- [sdk/api/currencytype/definition.proto](#sdk_api_currencytype_definition-proto)
    - [CurrencyTypeProto](#sdk-saga-api-currencytype-CurrencyTypeProto)
    - [CurrencyTypesProto](#sdk-saga-api-currencytype-CurrencyTypesProto)
    - [GetCurrencyTypeRequest](#sdk-saga-api-currencytype-GetCurrencyTypeRequest)
    - [GetCurrencyTypesRequest](#sdk-saga-api-currencytype-GetCurrencyTypesRequest)
    - [PublisherBalanceProto](#sdk-saga-api-currencytype-PublisherBalanceProto)
  
- [sdk/api/currencytype/rpc.proto](#sdk_api_currencytype_rpc-proto)
    - [CurrencyTypeService](#sdk-saga-api-currencytype-CurrencyTypeService)
  
- [sdk/api/item/definition.proto](#sdk_api_item_definition-proto)
    - [BurnItemRequest](#sdk-saga-api-item-BurnItemRequest)
    - [DepositItemRequest](#sdk-saga-api-item-DepositItemRequest)
    - [GetItemRequest](#sdk-saga-api-item-GetItemRequest)
    - [GetItemsForPlayerRequest](#sdk-saga-api-item-GetItemsForPlayerRequest)
    - [GetItemsRequest](#sdk-saga-api-item-GetItemsRequest)
    - [IssueItemProto](#sdk-saga-api-item-IssueItemProto)
    - [IssueItemRequest](#sdk-saga-api-item-IssueItemRequest)
    - [ItemProto](#sdk-saga-api-item-ItemProto)
    - [ItemsProto](#sdk-saga-api-item-ItemsProto)
    - [TransferItemBulkRequest](#sdk-saga-api-item-TransferItemBulkRequest)
    - [TransferItemRequest](#sdk-saga-api-item-TransferItemRequest)
  
- [sdk/api/item/rpc.proto](#sdk_api_item_rpc-proto)
    - [ItemService](#sdk-saga-api-item-ItemService)
  
- [sdk/api/itemtype/definition.proto](#sdk_api_itemtype_definition-proto)
    - [CreateItemTypeRequest](#sdk-saga-api-itemtype-CreateItemTypeRequest)
    - [EndMintRequest](#sdk-saga-api-itemtype-EndMintRequest)
    - [FailedItemTypeBatch](#sdk-saga-api-itemtype-FailedItemTypeBatch)
    - [GetItemTypeRequest](#sdk-saga-api-itemtype-GetItemTypeRequest)
    - [GetItemTypesRequest](#sdk-saga-api-itemtype-GetItemTypesRequest)
    - [ItemTypeProto](#sdk-saga-api-itemtype-ItemTypeProto)
    - [ItemTypesProto](#sdk-saga-api-itemtype-ItemTypesProto)
    - [StartMintRequest](#sdk-saga-api-itemtype-StartMintRequest)
  
    - [MintMode](#sdk-saga-api-itemtype-MintMode)
  
- [sdk/api/itemtype/rpc.proto](#sdk_api_itemtype_rpc-proto)
    - [ItemTypeService](#sdk-saga-api-itemtype-ItemTypeService)
  
- [sdk/api/listing/definition.proto](#sdk_api_listing_definition-proto)
    - [CancelListingRequest](#sdk-saga-api-listing-CancelListingRequest)
    - [ConfirmListingRequest](#sdk-saga-api-listing-ConfirmListingRequest)
    - [CreateListingQuoteRequest](#sdk-saga-api-listing-CreateListingQuoteRequest)
    - [GetListingsRequest](#sdk-saga-api-listing-GetListingsRequest)
    - [ListingProto](#sdk-saga-api-listing-ListingProto)
    - [ListingQuoteProto](#sdk-saga-api-listing-ListingQuoteProto)
    - [ListingsProto](#sdk-saga-api-listing-ListingsProto)
  
- [sdk/api/listing/rpc.proto](#sdk_api_listing_rpc-proto)
    - [ListingService](#sdk-saga-api-listing-ListingService)
  
- [sdk/api/metadata/definition.proto](#sdk_api_metadata_definition-proto)
    - [UpdateItemMetadataRequest](#sdk-saga-api-metadata-UpdateItemMetadataRequest)
  
- [sdk/api/metadata/rpc.proto](#sdk_api_metadata_rpc-proto)
    - [MetadataService](#sdk-saga-api-metadata-MetadataService)
  
- [sdk/api/nftbridge/definition.proto](#sdk_api_nftbridge_definition-proto)
    - [GetNftBridgeRequest](#sdk-saga-api-nftbridge-GetNftBridgeRequest)
    - [NftBridgeProto](#sdk-saga-api-nftbridge-NftBridgeProto)
    - [QuoteBridgeNFTRequest](#sdk-saga-api-nftbridge-QuoteBridgeNFTRequest)
    - [QuoteBridgeNFTResponse](#sdk-saga-api-nftbridge-QuoteBridgeNFTResponse)
    - [WithdrawItemRequest](#sdk-saga-api-nftbridge-WithdrawItemRequest)
  
- [sdk/api/nftbridge/rpc.proto](#sdk_api_nftbridge_rpc-proto)
    - [NftBridgeService](#sdk-saga-api-nftbridge-NftBridgeService)
  
- [sdk/api/offer/definition.proto](#sdk_api_offer_definition-proto)
    - [CancelOfferRequest](#sdk-saga-api-offer-CancelOfferRequest)
    - [ConfirmOfferRequest](#sdk-saga-api-offer-ConfirmOfferRequest)
    - [CreateOfferQuoteRequest](#sdk-saga-api-offer-CreateOfferQuoteRequest)
    - [GetOffersRequest](#sdk-saga-api-offer-GetOffersRequest)
    - [OfferProto](#sdk-saga-api-offer-OfferProto)
    - [OfferQuoteProto](#sdk-saga-api-offer-OfferQuoteProto)
    - [OffersProto](#sdk-saga-api-offer-OffersProto)
  
- [sdk/api/offer/rpc.proto](#sdk_api_offer_rpc-proto)
    - [OfferService](#sdk-saga-api-offer-OfferService)
  
- [sdk/api/playerwallet/definition.proto](#sdk_api_playerwallet_definition-proto)
    - [CreatePlayerWalletRequest](#sdk-saga-api-playerwallet-CreatePlayerWalletRequest)
    - [GetPlayerWalletRequest](#sdk-saga-api-playerwallet-GetPlayerWalletRequest)
    - [PlayerWalletProto](#sdk-saga-api-playerwallet-PlayerWalletProto)
  
- [sdk/api/playerwallet/rpc.proto](#sdk_api_playerwallet_rpc-proto)
    - [PlayerWalletService](#sdk-saga-api-playerwallet-PlayerWalletService)
  
- [sdk/api/reservation/definition.proto](#sdk_api_reservation_definition-proto)
    - [CreateReservationRequest](#sdk-saga-api-reservation-CreateReservationRequest)
    - [ItemReservationProto](#sdk-saga-api-reservation-ItemReservationProto)
    - [RedeemItemProto](#sdk-saga-api-reservation-RedeemItemProto)
    - [RedeemReservationRequest](#sdk-saga-api-reservation-RedeemReservationRequest)
    - [ReleaseReservationRequest](#sdk-saga-api-reservation-ReleaseReservationRequest)
  
- [sdk/api/reservation/rpc.proto](#sdk_api_reservation_rpc-proto)
    - [ReservationService](#sdk-saga-api-reservation-ReservationService)
  
- [sdk/common/common.proto](#sdk_common_common-proto)
    - [ErrorData](#sdk-saga-common-ErrorData)
    - [Metadata](#sdk-saga-common-Metadata)
    - [MetadataAttribute](#sdk-saga-common-MetadataAttribute)
    - [ReceivedResponse](#sdk-saga-common-ReceivedResponse)
    - [SubError](#sdk-saga-common-SubError)
  
- [sdk/common/currency/definition.proto](#sdk_common_currency_definition-proto)
    - [CurrencyState](#sdk-saga-proto-common-currency-CurrencyState)
  
- [sdk/common/currencytype/definition.proto](#sdk_common_currencytype_definition-proto)
    - [CurrencyTypeState](#sdk-saga-proto-common-currencytype-CurrencyTypeState)
  
- [sdk/common/finalization.proto](#sdk_common_finalization-proto)
    - [Finalized](#sdk-saga-common-Finalized)
  
- [sdk/common/item/definition.proto](#sdk_common_item_definition-proto)
    - [BlockChains](#sdk-saga-proto-common-item-BlockChains)
    - [ItemState](#sdk-saga-proto-common-item-ItemState)
  
- [sdk/common/itemtype/definition.proto](#sdk_common_itemtype_definition-proto)
    - [ItemTypeState](#sdk-saga-proto-common-itemtype-ItemTypeState)
  
- [sdk/common/listing/definition.proto](#sdk_common_listing_definition-proto)
    - [ListingState](#sdk-saga-proto-common-listing-ListingState)
  
- [sdk/common/offer/definition.proto](#sdk_common_offer_definition-proto)
    - [OfferState](#sdk-saga-proto-common-offer-OfferState)
  
- [sdk/common/query.proto](#sdk_common_query-proto)
    - [QueryOptionsProto](#sdk-saga-common-QueryOptionsProto)
  
- [sdk/common/sort.proto](#sdk_common_sort-proto)
    - [SortOrder](#sdk-saga-common-SortOrder)
  
- [sdk/streams/common.proto](#sdk_streams_common-proto)
    - [Subscribe](#sdk-saga-rpc-streams-Subscribe)
  
- [sdk/streams/currency/definition.proto](#sdk_streams_currency_definition-proto)
    - [BalanceProto](#sdk-saga-rpc-streams-currency-BalanceProto)
    - [CurrencyStatusUpdate](#sdk-saga-rpc-streams-currency-CurrencyStatusUpdate)
    - [CurrencyUpdate](#sdk-saga-rpc-streams-currency-CurrencyUpdate)
  
- [sdk/streams/currencytype/definition.proto](#sdk_streams_currencytype_definition-proto)
    - [CurrencyTypeStatusUpdate](#sdk-saga-rpc-streams-currencytype-CurrencyTypeStatusUpdate)
    - [CurrencyTypeUpdate](#sdk-saga-rpc-streams-currencytype-CurrencyTypeUpdate)
  
- [sdk/streams/item/definition.proto](#sdk_streams_item_definition-proto)
    - [ItemStatusUpdate](#sdk-saga-rpc-streams-item-ItemStatusUpdate)
    - [ItemStatusUpdates](#sdk-saga-rpc-streams-item-ItemStatusUpdates)
    - [ItemUpdate](#sdk-saga-rpc-streams-item-ItemUpdate)
  
- [sdk/streams/itemtype/definition.proto](#sdk_streams_itemtype_definition-proto)
    - [ItemTypeStatusUpdate](#sdk-saga-rpc-streams-itemtype-ItemTypeStatusUpdate)
    - [ItemTypeUpdate](#sdk-saga-rpc-streams-itemtype-ItemTypeUpdate)
  
- [sdk/streams/metadata/definition.proto](#sdk_streams_metadata_definition-proto)
    - [MetadataUpdate](#sdk-saga-rpc-streams-metadata-MetadataUpdate)
    - [MetadataUpdateProto](#sdk-saga-rpc-streams-metadata-MetadataUpdateProto)
  
- [sdk/streams/playerwallet/definition.proto](#sdk_streams_playerwallet_definition-proto)
    - [PlayerWalletStatusUpdate](#sdk-saga-rpc-streams-playerwallet-PlayerWalletStatusUpdate)
    - [PlayerWalletUpdate](#sdk-saga-rpc-streams-playerwallet-PlayerWalletUpdate)
  
- [sdk/streams/reservation/definition.proto](#sdk_streams_reservation_definition-proto)
    - [ReservationCreatedProto](#sdk-saga-rpc-streams-reservation-ReservationCreatedProto)
    - [ReservationRedeemedProto](#sdk-saga-rpc-streams-reservation-ReservationRedeemedProto)
    - [ReservationReleasedProto](#sdk-saga-rpc-streams-reservation-ReservationReleasedProto)
    - [ReservationUpdate](#sdk-saga-rpc-streams-reservation-ReservationUpdate)
  
- [sdk/streams/stream.proto](#sdk_streams_stream-proto)
    - [StatusConfirmRequest](#sdk-saga-rpc-streams-StatusConfirmRequest)
    - [StatusUpdate](#sdk-saga-rpc-streams-StatusUpdate)
  
    - [StatusStream](#sdk-saga-rpc-streams-StatusStream)
  
- [Scalar Value Types](#scalar-value-types)



<a name="sdk_api_common_error-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## sdk/api/common/error.proto



<a name="sdk-saga-api-common-ErrorProto"></a>

### ErrorProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| code | [int32](#int32) |  |  |
| message | [string](#string) |  |  |
| source | [string](#string) |  |  |
| trace_id | [string](#string) |  |  |





 

 

 

 



<a name="sdk_api_currency_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## sdk/api/currency/definition.proto



<a name="sdk-saga-api-currency-BalanceOfPlayerProto"></a>

### BalanceOfPlayerProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  |  |
| balance | [BalanceProto](#sdk-saga-api-currency-BalanceProto) |  |  |
| trace_id | [string](#string) |  |  |






<a name="sdk-saga-api-currency-BalanceProto"></a>

### BalanceProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| currency_type_id | [string](#string) |  |  |
| balance_in_wei | [string](#string) |  |  |






<a name="sdk-saga-api-currency-BalancesOfPlayerProto"></a>

### BalancesOfPlayerProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  |  |
| balances | [BalanceProto](#sdk-saga-api-currency-BalanceProto) | repeated |  |
| trace_id | [string](#string) |  |  |






<a name="sdk-saga-api-currency-BurnCurrencyRequest"></a>

### BurnCurrencyRequest
Burn currency call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| currency_type_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |
| amount_in_wei | [string](#string) |  |  |
| idempotency_id | [string](#string) |  |  |
| prefund_gas | [bool](#bool) |  |  |






<a name="sdk-saga-api-currency-GetBalanceOfPlayerRequest"></a>

### GetBalanceOfPlayerRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| currency_type_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  | User to get balance for |






<a name="sdk-saga-api-currency-GetBalancesOfPlayerRequest"></a>

### GetBalancesOfPlayerRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  | User to get balances for |
| query_options | [sdk.saga.common.QueryOptionsProto](#sdk-saga-common-QueryOptionsProto) |  |  |






<a name="sdk-saga-api-currency-GetCurrencyForPlayerRequest"></a>

### GetCurrencyForPlayerRequest
Get Currency call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  | User to get currency for |
| currency_type_id | [string](#string) |  |  |






<a name="sdk-saga-api-currency-IssueCurrencyRequest"></a>

### IssueCurrencyRequest
Issue currency call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| currency_type_id | [string](#string) |  | Currency Type the currency to issue belongs to |
| user_amounts | [UserAmountProto](#sdk-saga-api-currency-UserAmountProto) | repeated | Users to issue currency to |
| idempotency_id | [string](#string) |  | Unique id to ensure request is processed only once |






<a name="sdk-saga-api-currency-TransferCurrencyRequest"></a>

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






<a name="sdk-saga-api-currency-UserAmountProto"></a>

### UserAmountProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  | User to issue currency to |
| amount_in_wei | [string](#string) |  | Amount to issue |





 

 

 

 



<a name="sdk_api_currency_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## sdk/api/currency/rpc.proto


 

 

 


<a name="sdk-saga-api-currency-CurrencyService"></a>

### CurrencyService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| IssueCurrency | [IssueCurrencyRequest](#sdk-saga-api-currency-IssueCurrencyRequest) | [.sdk.saga.common.ReceivedResponse](#sdk-saga-common-ReceivedResponse) | Issue currency to a user |
| TransferCurrency | [TransferCurrencyRequest](#sdk-saga-api-currency-TransferCurrencyRequest) | [.sdk.saga.common.ReceivedResponse](#sdk-saga-common-ReceivedResponse) | Transfer currency between users |
| BurnCurrency | [BurnCurrencyRequest](#sdk-saga-api-currency-BurnCurrencyRequest) | [.sdk.saga.common.ReceivedResponse](#sdk-saga-common-ReceivedResponse) | Burn currency for a user |
| GetBalanceOfPlayer | [GetBalanceOfPlayerRequest](#sdk-saga-api-currency-GetBalanceOfPlayerRequest) | [BalanceOfPlayerProto](#sdk-saga-api-currency-BalanceOfPlayerProto) | Get Balance of a player |
| GetBalancesOfPlayer | [GetBalancesOfPlayerRequest](#sdk-saga-api-currency-GetBalancesOfPlayerRequest) | [BalancesOfPlayerProto](#sdk-saga-api-currency-BalancesOfPlayerProto) | Get Balances of a player |

 



<a name="sdk_api_currencytype_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## sdk/api/currencytype/definition.proto



<a name="sdk-saga-api-currencytype-CurrencyTypeProto"></a>

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
| publisher_balance | [PublisherBalanceProto](#sdk-saga-api-currencytype-PublisherBalanceProto) |  |  |
| created_at | [google.protobuf.Timestamp](#google-protobuf-Timestamp) |  |  |
| updated_at | [google.protobuf.Timestamp](#google-protobuf-Timestamp) |  |  |






<a name="sdk-saga-api-currencytype-CurrencyTypesProto"></a>

### CurrencyTypesProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| currency_types | [CurrencyTypeProto](#sdk-saga-api-currencytype-CurrencyTypeProto) | repeated |  |






<a name="sdk-saga-api-currencytype-GetCurrencyTypeRequest"></a>

### GetCurrencyTypeRequest
Currency Type call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| currency_type_id | [string](#string) |  |  |






<a name="sdk-saga-api-currencytype-GetCurrencyTypesRequest"></a>

### GetCurrencyTypesRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| query_options | [sdk.saga.common.QueryOptionsProto](#sdk-saga-common-QueryOptionsProto) |  |  |






<a name="sdk-saga-api-currencytype-PublisherBalanceProto"></a>

### PublisherBalanceProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| publisher_address | [string](#string) |  |  |
| balance_in_wei | [string](#string) |  |  |





 

 

 

 



<a name="sdk_api_currencytype_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## sdk/api/currencytype/rpc.proto


 

 

 


<a name="sdk-saga-api-currencytype-CurrencyTypeService"></a>

### CurrencyTypeService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| GetCurrencyType | [GetCurrencyTypeRequest](#sdk-saga-api-currencytype-GetCurrencyTypeRequest) | [CurrencyTypeProto](#sdk-saga-api-currencytype-CurrencyTypeProto) |  |
| GetCurrencyTypes | [GetCurrencyTypesRequest](#sdk-saga-api-currencytype-GetCurrencyTypesRequest) | [CurrencyTypesProto](#sdk-saga-api-currencytype-CurrencyTypesProto) |  |

 



<a name="sdk_api_item_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## sdk/api/item/definition.proto



<a name="sdk-saga-api-item-BurnItemRequest"></a>

### BurnItemRequest
Burn item call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| inventory_id | [string](#string) |  | Game&#39;s id for the Item to burn |
| prefund_gas | [bool](#bool) |  |  |






<a name="sdk-saga-api-item-DepositItemRequest"></a>

### DepositItemRequest
Deposit item call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| inventory_id | [string](#string) |  |  |
| created_by | [string](#string) |  |  |
| from_address | [string](#string) |  |  |
| to_address | [string](#string) |  |  |
| from_chain | [sdk.saga.proto.common.item.BlockChains](#sdk-saga-proto-common-item-BlockChains) |  |  |
| transaction_id | [string](#string) |  |  |






<a name="sdk-saga-api-item-GetItemRequest"></a>

### GetItemRequest
Get Item call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| inventory_id | [string](#string) |  | Game&#39;s id for the Item to retrieve |






<a name="sdk-saga-api-item-GetItemsForPlayerRequest"></a>

### GetItemsForPlayerRequest
Get Items for Player call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  | Player to get Items for |
| query_options | [sdk.saga.common.QueryOptionsProto](#sdk-saga-common-QueryOptionsProto) |  | Sort/filter options |






<a name="sdk-saga-api-item-GetItemsRequest"></a>

### GetItemsRequest
Get Items call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| query_options | [sdk.saga.common.QueryOptionsProto](#sdk-saga-common-QueryOptionsProto) |  | Sort/filter options |
| finalized | [sdk.saga.common.Finalized](#sdk-saga-common-Finalized) |  |  |
| token_name | [string](#string) |  |  |






<a name="sdk-saga-api-item-IssueItemProto"></a>

### IssueItemProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| inventory_id | [string](#string) |  | GameInventory Ids of Items being issued |
| metadata | [sdk.saga.common.Metadata](#sdk-saga-common-Metadata) |  | Metadata associated to Item being issued |
| token_id | [uint64](#uint64) |  | TokenId associated to Item being issued |






<a name="sdk-saga-api-item-IssueItemRequest"></a>

### IssueItemRequest
Issue item call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| items | [IssueItemProto](#sdk-saga-api-item-IssueItemProto) | repeated | Repeated items so that metadata is unique per item |
| recipient_oauth_id | [string](#string) |  | Oauth id of wallet accepting items |
| item_type_id | [string](#string) |  | Unique id set for your game of the Item being issued |






<a name="sdk-saga-api-item-ItemProto"></a>

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






<a name="sdk-saga-api-item-ItemsProto"></a>

### ItemsProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| items | [ItemProto](#sdk-saga-api-item-ItemProto) | repeated |  |






<a name="sdk-saga-api-item-TransferItemBulkRequest"></a>

### TransferItemBulkRequest
Transfer bulk item call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| game_title_id | [string](#string) |  |  |
| inventory_ids | [string](#string) | repeated | Game&#39;s ids for the Item to transfer |
| destination_oauth_id | [string](#string) |  | User of Item to transfer to |
| idempotency_id | [string](#string) |  |  |






<a name="sdk-saga-api-item-TransferItemRequest"></a>

### TransferItemRequest
Transfer item call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| inventory_id | [string](#string) |  | Game&#39;s id for the Item to transfer |
| destination_oauth_id | [string](#string) |  | User of Item to transfer to |
| idempotency_id | [string](#string) |  |  |
| prefund_gas | [bool](#bool) |  |  |





 

 

 

 



<a name="sdk_api_item_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## sdk/api/item/rpc.proto


 

 

 


<a name="sdk-saga-api-item-ItemService"></a>

### ItemService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| GetItem | [GetItemRequest](#sdk-saga-api-item-GetItemRequest) | [ItemProto](#sdk-saga-api-item-ItemProto) | Get an item |
| GetItems | [GetItemsRequest](#sdk-saga-api-item-GetItemsRequest) | [ItemsProto](#sdk-saga-api-item-ItemsProto) | Get items based on filters |
| GetItemsForPlayer | [GetItemsForPlayerRequest](#sdk-saga-api-item-GetItemsForPlayerRequest) | [ItemsProto](#sdk-saga-api-item-ItemsProto) | Get all Items for a player |
| IssueItem | [IssueItemRequest](#sdk-saga-api-item-IssueItemRequest) | [.sdk.saga.common.ReceivedResponse](#sdk-saga-common-ReceivedResponse) | Issue an Item |
| TransferItem | [TransferItemRequest](#sdk-saga-api-item-TransferItemRequest) | [.sdk.saga.common.ReceivedResponse](#sdk-saga-common-ReceivedResponse) | Transfer Item between users |
| TransferItemBulk | [TransferItemBulkRequest](#sdk-saga-api-item-TransferItemBulkRequest) | [.sdk.saga.common.ReceivedResponse](#sdk-saga-common-ReceivedResponse) | Transfer Items between users |
| BurnItem | [BurnItemRequest](#sdk-saga-api-item-BurnItemRequest) | [.sdk.saga.common.ReceivedResponse](#sdk-saga-common-ReceivedResponse) | Burn an Item |
| DepositItem | [DepositItemRequest](#sdk-saga-api-item-DepositItemRequest) | [.sdk.saga.common.ReceivedResponse](#sdk-saga-common-ReceivedResponse) | Deposit an Item |

 



<a name="sdk_api_itemtype_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## sdk/api/itemtype/definition.proto



<a name="sdk-saga-api-itemtype-CreateItemTypeRequest"></a>

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
| mint_mode | [MintMode](#sdk-saga-api-itemtype-MintMode) |  |  |






<a name="sdk-saga-api-itemtype-EndMintRequest"></a>

### EndMintRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| item_type_id | [string](#string) |  |  |






<a name="sdk-saga-api-itemtype-FailedItemTypeBatch"></a>

### FailedItemTypeBatch



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| item_type_id | [string](#string) |  |  |






<a name="sdk-saga-api-itemtype-GetItemTypeRequest"></a>

### GetItemTypeRequest
Get ItemType call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| item_type_id | [string](#string) |  |  |






<a name="sdk-saga-api-itemtype-GetItemTypesRequest"></a>

### GetItemTypesRequest
Get ItemTypes call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| game_title_id | [string](#string) |  |  |
| publisher_address | [string](#string) |  |  |
| query_options | [sdk.saga.common.QueryOptionsProto](#sdk-saga-common-QueryOptionsProto) |  |  |






<a name="sdk-saga-api-itemtype-ItemTypeProto"></a>

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
| mint_mode | [MintMode](#sdk-saga-api-itemtype-MintMode) |  |  |
| bridgeable | [bool](#bool) |  |  |






<a name="sdk-saga-api-itemtype-ItemTypesProto"></a>

### ItemTypesProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| item_types | [ItemTypeProto](#sdk-saga-api-itemtype-ItemTypeProto) | repeated |  |






<a name="sdk-saga-api-itemtype-StartMintRequest"></a>

### StartMintRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| item_type_id | [string](#string) |  |  |





 


<a name="sdk-saga-api-itemtype-MintMode"></a>

### MintMode


| Name | Number | Description |
| ---- | ------ | ----------- |
| MINT_MODE_UNSPECIFIED | 0 |  |
| MINT_MODE_SERIAL | 1 |  |
| MINT_MODE_RANDOM | 2 |  |
| MINT_MODE_SELECTED | 3 |  |


 

 

 



<a name="sdk_api_itemtype_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## sdk/api/itemtype/rpc.proto


 

 

 


<a name="sdk-saga-api-itemtype-ItemTypeService"></a>

### ItemTypeService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| GetItemType | [GetItemTypeRequest](#sdk-saga-api-itemtype-GetItemTypeRequest) | [ItemTypeProto](#sdk-saga-api-itemtype-ItemTypeProto) | Get an ItemType by Id |
| GetItemTypes | [GetItemTypesRequest](#sdk-saga-api-itemtype-GetItemTypesRequest) | [ItemTypesProto](#sdk-saga-api-itemtype-ItemTypesProto) | Get ItemTypes based on filters |
| CreateItemType | [CreateItemTypeRequest](#sdk-saga-api-itemtype-CreateItemTypeRequest) | [.sdk.saga.common.ReceivedResponse](#sdk-saga-common-ReceivedResponse) | Create an ItemType |
| StartMint | [StartMintRequest](#sdk-saga-api-itemtype-StartMintRequest) | [.sdk.saga.common.ReceivedResponse](#sdk-saga-common-ReceivedResponse) | Update the ItemType |
| EndMint | [EndMintRequest](#sdk-saga-api-itemtype-EndMintRequest) | [.sdk.saga.common.ReceivedResponse](#sdk-saga-common-ReceivedResponse) |  |

 



<a name="sdk_api_listing_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## sdk/api/listing/definition.proto



<a name="sdk-saga-api-listing-CancelListingRequest"></a>

### CancelListingRequest
Cancel the Listing call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  | User of listing to cancel |
| listing_id | [string](#string) |  | Id of Listing to cancel |






<a name="sdk-saga-api-listing-ConfirmListingRequest"></a>

### ConfirmListingRequest
Confirm the Listing call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  | User of this Listing |
| quote_id | [string](#string) |  | Quote Id of this Listing |






<a name="sdk-saga-api-listing-CreateListingQuoteRequest"></a>

### CreateListingQuoteRequest
Create Quote for a Listing call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  | User of this Listing Quote |
| inventory_id | [string](#string) |  | Id of GameInventory for this Listing |
| total | [string](#string) |  | Total cost amount for the Listing |
| currency | [string](#string) |  | Currency that the total is in |






<a name="sdk-saga-api-listing-GetListingsRequest"></a>

### GetListingsRequest
Get Listings call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| query_options | [sdk.saga.common.QueryOptionsProto](#sdk-saga-common-QueryOptionsProto) |  | Filter/Sorting options for the call |
| item_type_id | [string](#string) |  | Id of ItemType to get Listings for |
| token | [string](#string) |  |  |
| oauth_id | [string](#string) |  | User to get Listings for |






<a name="sdk-saga-api-listing-ListingProto"></a>

### ListingProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  | User of this Listing |
| inventory_id | [string](#string) |  | Item associated with this Listing |
| currency | [string](#string) |  | Type of currency the total is in |
| total | [string](#string) |  | Total price of Listing |
| created_at | [google.protobuf.Timestamp](#google-protobuf-Timestamp) |  | When the Listing was created |






<a name="sdk-saga-api-listing-ListingQuoteProto"></a>

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






<a name="sdk-saga-api-listing-ListingsProto"></a>

### ListingsProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| listings | [ListingProto](#sdk-saga-api-listing-ListingProto) | repeated |  |





 

 

 

 



<a name="sdk_api_listing_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## sdk/api/listing/rpc.proto


 

 

 


<a name="sdk-saga-api-listing-ListingService"></a>

### ListingService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| CreateListingQuote | [CreateListingQuoteRequest](#sdk-saga-api-listing-CreateListingQuoteRequest) | [.sdk.saga.common.ReceivedResponse](#sdk-saga-common-ReceivedResponse) | Get a quote for a Listing |
| ConfirmListing | [ConfirmListingRequest](#sdk-saga-api-listing-ConfirmListingRequest) | [.sdk.saga.common.ReceivedResponse](#sdk-saga-common-ReceivedResponse) | Confirm the Listing |
| CancelListing | [CancelListingRequest](#sdk-saga-api-listing-CancelListingRequest) | [.sdk.saga.common.ReceivedResponse](#sdk-saga-common-ReceivedResponse) | Cancel the Listing |
| GetListings | [GetListingsRequest](#sdk-saga-api-listing-GetListingsRequest) | [ListingsProto](#sdk-saga-api-listing-ListingsProto) | Get Listings based on filters |

 



<a name="sdk_api_metadata_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## sdk/api/metadata/definition.proto



<a name="sdk-saga-api-metadata-UpdateItemMetadataRequest"></a>

### UpdateItemMetadataRequest
Update Metadata call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| inventory_id | [string](#string) |  | Update Metadata for the Item with this id |
| metadata | [sdk.saga.common.Metadata](#sdk-saga-common-Metadata) |  | Metadata to update with |





 

 

 

 



<a name="sdk_api_metadata_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## sdk/api/metadata/rpc.proto


 

 

 


<a name="sdk-saga-api-metadata-MetadataService"></a>

### MetadataService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| UpdateItemMetadata | [UpdateItemMetadataRequest](#sdk-saga-api-metadata-UpdateItemMetadataRequest) | [.sdk.saga.common.ReceivedResponse](#sdk-saga-common-ReceivedResponse) | Update an Item&#39;s metadata |

 



<a name="sdk_api_nftbridge_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## sdk/api/nftbridge/definition.proto



<a name="sdk-saga-api-nftbridge-GetNftBridgeRequest"></a>

### GetNftBridgeRequest
Get NftBridge Call






<a name="sdk-saga-api-nftbridge-NftBridgeProto"></a>

### NftBridgeProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |
| mythical_address | [string](#string) |  |  |
| mainnet_address | [string](#string) |  | Address on Mainnet |
| chain_name | [string](#string) |  | Name of the chain |






<a name="sdk-saga-api-nftbridge-QuoteBridgeNFTRequest"></a>

### QuoteBridgeNFTRequest
Get Bridge Quote Call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| origin_chain_id | [int32](#int32) |  |  |
| target_chain_id | [int32](#int32) |  |  |
| game_title_id | [string](#string) |  |  |
| inventory_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |






<a name="sdk-saga-api-nftbridge-QuoteBridgeNFTResponse"></a>

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






<a name="sdk-saga-api-nftbridge-WithdrawItemRequest"></a>

### WithdrawItemRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| quote_request | [QuoteBridgeNFTRequest](#sdk-saga-api-nftbridge-QuoteBridgeNFTRequest) |  |  |
| fee_in_originchain_native_token | [string](#string) |  |  |
| expires_at | [string](#string) |  |  |
| signature | [string](#string) |  |  |
| item_type_id | [string](#string) |  |  |
| targetchain_wallet_address | [string](#string) |  |  |





 

 

 

 



<a name="sdk_api_nftbridge_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## sdk/api/nftbridge/rpc.proto


 

 

 


<a name="sdk-saga-api-nftbridge-NftBridgeService"></a>

### NftBridgeService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| WithdrawItem | [WithdrawItemRequest](#sdk-saga-api-nftbridge-WithdrawItemRequest) | [.sdk.saga.common.ReceivedResponse](#sdk-saga-common-ReceivedResponse) | Withdraw an Item |
| GetBridge | [GetNftBridgeRequest](#sdk-saga-api-nftbridge-GetNftBridgeRequest) | [NftBridgeProto](#sdk-saga-api-nftbridge-NftBridgeProto) | Get Bridge |
| GetBridgeQuote | [QuoteBridgeNFTRequest](#sdk-saga-api-nftbridge-QuoteBridgeNFTRequest) | [QuoteBridgeNFTResponse](#sdk-saga-api-nftbridge-QuoteBridgeNFTResponse) | Get Bridge Quote |

 



<a name="sdk_api_offer_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## sdk/api/offer/definition.proto



<a name="sdk-saga-api-offer-CancelOfferRequest"></a>

### CancelOfferRequest
Cancel Offer call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  | User that the Offer belongs to |
| offer_id | [string](#string) |  | Id of the Offer to Cancel |






<a name="sdk-saga-api-offer-ConfirmOfferRequest"></a>

### ConfirmOfferRequest
Confirm Offer call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  |  |
| quote_id | [string](#string) |  | Quote Id of the Offer

@exclude TODO: where&#39;s the payment data? Also are offers fronted payment or we somehow storing it (securely) then charging when needed |






<a name="sdk-saga-api-offer-CreateOfferQuoteRequest"></a>

### CreateOfferQuoteRequest
Create Offer Quote call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  |  |
| inventory_id | [string](#string) |  | GameInventory Id of the Offer |
| total | [string](#string) |  | Total quoted for the Offer |
| currency | [string](#string) |  | Currency the total is in |






<a name="sdk-saga-api-offer-GetOffersRequest"></a>

### GetOffersRequest
Get Offers call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| query_options | [sdk.saga.common.QueryOptionsProto](#sdk-saga-common-QueryOptionsProto) |  | Filter/Sort options for the call |
| item_type_id | [string](#string) |  | Id of ItemType to get Offers for |
| token | [string](#string) |  | Token to get Offers for |
| oauth_id | [string](#string) |  | User to get Offers for |






<a name="sdk-saga-api-offer-OfferProto"></a>

### OfferProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  | User of the Offer |
| inventory_id | [string](#string) |  | GameInventory Id of the Offer |
| currency | [string](#string) |  | Currency of the total |
| total | [string](#string) |  | Total cost of the offer |
| created_at | [google.protobuf.Timestamp](#google-protobuf-Timestamp) |  | When the offer was created |






<a name="sdk-saga-api-offer-OfferQuoteProto"></a>

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






<a name="sdk-saga-api-offer-OffersProto"></a>

### OffersProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| offers | [OfferProto](#sdk-saga-api-offer-OfferProto) | repeated |  |





 

 

 

 



<a name="sdk_api_offer_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## sdk/api/offer/rpc.proto


 

 

 


<a name="sdk-saga-api-offer-OfferService"></a>

### OfferService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| CreateOfferQuote | [CreateOfferQuoteRequest](#sdk-saga-api-offer-CreateOfferQuoteRequest) | [.sdk.saga.common.ReceivedResponse](#sdk-saga-common-ReceivedResponse) | Create an Offer quote |
| ConfirmOffer | [ConfirmOfferRequest](#sdk-saga-api-offer-ConfirmOfferRequest) | [.sdk.saga.common.ReceivedResponse](#sdk-saga-common-ReceivedResponse) | Confirm the Offer |
| CancelOffer | [CancelOfferRequest](#sdk-saga-api-offer-CancelOfferRequest) | [.sdk.saga.common.ReceivedResponse](#sdk-saga-common-ReceivedResponse) | Cancel the Offer |
| GetOffers | [GetOffersRequest](#sdk-saga-api-offer-GetOffersRequest) | [OffersProto](#sdk-saga-api-offer-OffersProto) | Get Offers based on filters |

 



<a name="sdk_api_playerwallet_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## sdk/api/playerwallet/definition.proto



<a name="sdk-saga-api-playerwallet-CreatePlayerWalletRequest"></a>

### CreatePlayerWalletRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  |  |






<a name="sdk-saga-api-playerwallet-GetPlayerWalletRequest"></a>

### GetPlayerWalletRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  |  |
| wallet_address | [string](#string) |  |  |






<a name="sdk-saga-api-playerwallet-PlayerWalletProto"></a>

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





 

 

 

 



<a name="sdk_api_playerwallet_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## sdk/api/playerwallet/rpc.proto


 

 

 


<a name="sdk-saga-api-playerwallet-PlayerWalletService"></a>

### PlayerWalletService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| CreatePlayerWallet | [CreatePlayerWalletRequest](#sdk-saga-api-playerwallet-CreatePlayerWalletRequest) | [.sdk.saga.common.ReceivedResponse](#sdk-saga-common-ReceivedResponse) | Create a Player Wallet |
| GetPlayerWallet | [GetPlayerWalletRequest](#sdk-saga-api-playerwallet-GetPlayerWalletRequest) | [PlayerWalletProto](#sdk-saga-api-playerwallet-PlayerWalletProto) | Get a Player Wallet |

 



<a name="sdk_api_reservation_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## sdk/api/reservation/definition.proto



<a name="sdk-saga-api-reservation-CreateReservationRequest"></a>

### CreateReservationRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| reservation_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |
| item_reservations | [ItemReservationProto](#sdk-saga-api-reservation-ItemReservationProto) | repeated |  |
| ttl | [google.protobuf.Int64Value](#google-protobuf-Int64Value) |  |  |






<a name="sdk-saga-api-reservation-ItemReservationProto"></a>

### ItemReservationProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| item_type_id | [string](#string) |  |  |
| count | [int64](#int64) |  |  |






<a name="sdk-saga-api-reservation-RedeemItemProto"></a>

### RedeemItemProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| inventory_id | [string](#string) |  |  |
| item_type_id | [string](#string) |  |  |
| metadata | [sdk.saga.common.Metadata](#sdk-saga-common-Metadata) |  |  |
| token_id | [uint64](#uint64) |  |  |






<a name="sdk-saga-api-reservation-RedeemReservationRequest"></a>

### RedeemReservationRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| reservation_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |
| items | [RedeemItemProto](#sdk-saga-api-reservation-RedeemItemProto) | repeated |  |






<a name="sdk-saga-api-reservation-ReleaseReservationRequest"></a>

### ReleaseReservationRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| reservation_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |





 

 

 

 



<a name="sdk_api_reservation_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## sdk/api/reservation/rpc.proto


 

 

 


<a name="sdk-saga-api-reservation-ReservationService"></a>

### ReservationService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| CreateReservation | [CreateReservationRequest](#sdk-saga-api-reservation-CreateReservationRequest) | [.sdk.saga.common.ReceivedResponse](#sdk-saga-common-ReceivedResponse) |  |
| RedeemReservation | [RedeemReservationRequest](#sdk-saga-api-reservation-RedeemReservationRequest) | [.sdk.saga.common.ReceivedResponse](#sdk-saga-common-ReceivedResponse) |  |
| ReleaseReservation | [ReleaseReservationRequest](#sdk-saga-api-reservation-ReleaseReservationRequest) | [.sdk.saga.common.ReceivedResponse](#sdk-saga-common-ReceivedResponse) |  |

 



<a name="sdk_common_common-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## sdk/common/common.proto



<a name="sdk-saga-common-ErrorData"></a>

### ErrorData



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| error_code | [string](#string) |  | The error code for this type of error |
| message | [string](#string) |  | Description of the error |
| source | [string](#string) |  | Indicator of which service the error occurred in |
| trace | [string](#string) |  | Trace id for this operation, if any |
| metadata | [google.protobuf.Struct](#google-protobuf-Struct) |  | Error metadata |
| suberrors | [SubError](#sdk-saga-common-SubError) | repeated | Sub-errors assocated with this incident |






<a name="sdk-saga-common-Metadata"></a>

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
| attributes | [MetadataAttribute](#sdk-saga-common-MetadataAttribute) | repeated | Metadata attributes |






<a name="sdk-saga-common-MetadataAttribute"></a>

### MetadataAttribute



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trait_type | [string](#string) |  | Name of the trait/attribute |
| display_type | [string](#string) |  | Display type (number, date, etc.). Not needed for string traits |
| max_value | [google.protobuf.DoubleValue](#google-protobuf-DoubleValue) |  | For numeric traits, a maximum allowed value |
| str_value | [string](#string) |  |  |
| int_value | [int64](#int64) |  |  |
| double_value | [double](#double) |  |  |






<a name="sdk-saga-common-ReceivedResponse"></a>

### ReceivedResponse



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |






<a name="sdk-saga-common-SubError"></a>

### SubError



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| error_code | [string](#string) |  | The error code for this type of error |
| message | [string](#string) |  | Description of the error |
| source | [string](#string) |  | Indicator of which service the error occurred in |
| metadata | [google.protobuf.Struct](#google-protobuf-Struct) |  | Error metadata |





 

 

 

 



<a name="sdk_common_currency_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## sdk/common/currency/definition.proto


 


<a name="sdk-saga-proto-common-currency-CurrencyState"></a>

### CurrencyState


| Name | Number | Description |
| ---- | ------ | ----------- |
| FAILED | 0 | Failed to issue/transfer/burn Currency |
| ISSUED | 1 | Currency issued successfully |
| TRANSFERRED | 2 | Currency transferred successfully |
| BURNED | 3 | Currency burned successfully |


 

 

 



<a name="sdk_common_currencytype_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## sdk/common/currencytype/definition.proto


 


<a name="sdk-saga-proto-common-currencytype-CurrencyTypeState"></a>

### CurrencyTypeState


| Name | Number | Description |
| ---- | ------ | ----------- |
| FAILED | 0 | CurrencyType failed to perform current action |
| CREATED | 1 | CurrencyType issued successfully |


 

 

 



<a name="sdk_common_finalization-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## sdk/common/finalization.proto


 


<a name="sdk-saga-common-Finalized"></a>

### Finalized


| Name | Number | Description |
| ---- | ------ | ----------- |
| ALL | 0 |  |
| YES | 1 |  |
| NO | 2 |  |


 

 

 



<a name="sdk_common_item_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## sdk/common/item/definition.proto


 


<a name="sdk-saga-proto-common-item-BlockChains"></a>

### BlockChains


| Name | Number | Description |
| ---- | ------ | ----------- |
| ETH | 0 |  |



<a name="sdk-saga-proto-common-item-ItemState"></a>

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


 

 

 



<a name="sdk_common_itemtype_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## sdk/common/itemtype/definition.proto


 


<a name="sdk-saga-proto-common-itemtype-ItemTypeState"></a>

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


 

 

 



<a name="sdk_common_listing_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## sdk/common/listing/definition.proto


 


<a name="sdk-saga-proto-common-listing-ListingState"></a>

### ListingState


| Name | Number | Description |
| ---- | ------ | ----------- |
| FAILED | 0 | Listing has failed current action |
| CREATED | 1 | Listing created successfully |
| SOLD | 2 | Listing sold successfully |
| CANCELLED | 3 | Listing cancelled successfully |


 

 

 



<a name="sdk_common_offer_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## sdk/common/offer/definition.proto


 


<a name="sdk-saga-proto-common-offer-OfferState"></a>

### OfferState


| Name | Number | Description |
| ---- | ------ | ----------- |
| FAILED | 0 | Offer failed current action |
| CREATED | 1 | Offer created successfully |
| CONFIRMED | 2 | Offer confirmed successfully |
| CANCELLED | 3 | Offer canceled |


 

 

 



<a name="sdk_common_query-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## sdk/common/query.proto



<a name="sdk-saga-common-QueryOptionsProto"></a>

### QueryOptionsProto
Options allowed when querying


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| page_size | [int32](#int32) |  | Size of the page of results |
| sort_order | [SortOrder](#sdk-saga-common-SortOrder) |  | Which order to sort |
| created_at_cursor | [google.protobuf.Timestamp](#google-protobuf-Timestamp) | optional | Cursor-based pagination based on created_at |





 

 

 

 



<a name="sdk_common_sort-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## sdk/common/sort.proto


 


<a name="sdk-saga-common-SortOrder"></a>

### SortOrder


| Name | Number | Description |
| ---- | ------ | ----------- |
| ASC | 0 | Sort ascending |
| DESC | 1 | Sort descending |


 

 

 



<a name="sdk_streams_common-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## sdk/streams/common.proto



<a name="sdk-saga-rpc-streams-Subscribe"></a>

### Subscribe



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| title_id | [string](#string) |  |  |
| stream_id | [string](#string) |  |  |
| replay_since | [google.protobuf.Timestamp](#google-protobuf-Timestamp) | optional |  |





 

 

 

 



<a name="sdk_streams_currency_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## sdk/streams/currency/definition.proto



<a name="sdk-saga-rpc-streams-currency-BalanceProto"></a>

### BalanceProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  |  |
| currency_type_id | [string](#string) |  |  |
| balance_in_wei | [string](#string) |  |  |






<a name="sdk-saga-rpc-streams-currency-CurrencyStatusUpdate"></a>

### CurrencyStatusUpdate
Results from a Currency status update gRPC stream call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| transaction_id | [string](#string) |  | Id given from chain |
| balances | [BalanceProto](#sdk-saga-rpc-streams-currency-BalanceProto) | repeated |  |
| idempotency_id | [string](#string) |  |  |
| currency_state | [sdk.saga.proto.common.currency.CurrencyState](#sdk-saga-proto-common-currency-CurrencyState) |  | State of the Currency, see CurrencyState |






<a name="sdk-saga-rpc-streams-currency-CurrencyUpdate"></a>

### CurrencyUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| error | [sdk.saga.common.ErrorData](#sdk-saga-common-ErrorData) |  |  |
| status_update | [CurrencyStatusUpdate](#sdk-saga-rpc-streams-currency-CurrencyStatusUpdate) |  |  |





 

 

 

 



<a name="sdk_streams_currencytype_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## sdk/streams/currencytype/definition.proto



<a name="sdk-saga-rpc-streams-currencytype-CurrencyTypeStatusUpdate"></a>

### CurrencyTypeStatusUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| currency_type_id | [string](#string) |  |  |
| currency_type_state | [sdk.saga.proto.common.currencytype.CurrencyTypeState](#sdk-saga-proto-common-currencytype-CurrencyTypeState) |  |  |
| transaction_id | [string](#string) |  |  |
| contract_address | [string](#string) |  |  |
| idempotency_id | [string](#string) |  |  |






<a name="sdk-saga-rpc-streams-currencytype-CurrencyTypeUpdate"></a>

### CurrencyTypeUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| error | [sdk.saga.common.ErrorData](#sdk-saga-common-ErrorData) |  |  |
| status_update | [CurrencyTypeStatusUpdate](#sdk-saga-rpc-streams-currencytype-CurrencyTypeStatusUpdate) |  |  |





 

 

 

 



<a name="sdk_streams_item_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## sdk/streams/item/definition.proto



<a name="sdk-saga-rpc-streams-item-ItemStatusUpdate"></a>

### ItemStatusUpdate
Results from an Item status update gRPC stream call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| inventory_id | [string](#string) |  | Game&#39;s unique Id for the Item |
| item_type_id | [string](#string) |  | Game&#39;s ItemTypeId for the ItemType for this Item |
| oauth_id | [string](#string) |  | User for this Item |
| token_id | [int64](#int64) |  |  |
| metadata_url | [string](#string) |  | Metadata address |
| item_state | [sdk.saga.proto.common.item.ItemState](#sdk-saga-proto-common-item-ItemState) |  | State of the Item, see ItemState |






<a name="sdk-saga-rpc-streams-item-ItemStatusUpdates"></a>

### ItemStatusUpdates



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| status_updates | [ItemStatusUpdate](#sdk-saga-rpc-streams-item-ItemStatusUpdate) | repeated |  |






<a name="sdk-saga-rpc-streams-item-ItemUpdate"></a>

### ItemUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| error | [sdk.saga.common.ErrorData](#sdk-saga-common-ErrorData) |  |  |
| status_update | [ItemStatusUpdate](#sdk-saga-rpc-streams-item-ItemStatusUpdate) |  |  |
| status_updates | [ItemStatusUpdates](#sdk-saga-rpc-streams-item-ItemStatusUpdates) |  |  |





 

 

 

 



<a name="sdk_streams_itemtype_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## sdk/streams/itemtype/definition.proto



<a name="sdk-saga-rpc-streams-itemtype-ItemTypeStatusUpdate"></a>

### ItemTypeStatusUpdate
Results from a ItemType status update gRPC stream call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| item_type_id | [string](#string) |  | Game&#39;s unique id for this ItemType |
| item_type_state | [sdk.saga.proto.common.itemtype.ItemTypeState](#sdk-saga-proto-common-itemtype-ItemTypeState) |  | State of the ItemType, see ItemTypeState |






<a name="sdk-saga-rpc-streams-itemtype-ItemTypeUpdate"></a>

### ItemTypeUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| error | [sdk.saga.common.ErrorData](#sdk-saga-common-ErrorData) |  |  |
| status_update | [ItemTypeStatusUpdate](#sdk-saga-rpc-streams-itemtype-ItemTypeStatusUpdate) |  |  |





 

 

 

 



<a name="sdk_streams_metadata_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## sdk/streams/metadata/definition.proto



<a name="sdk-saga-rpc-streams-metadata-MetadataUpdate"></a>

### MetadataUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| error | [sdk.saga.common.ErrorData](#sdk-saga-common-ErrorData) |  |  |
| metadata_updated | [MetadataUpdateProto](#sdk-saga-rpc-streams-metadata-MetadataUpdateProto) |  |  |






<a name="sdk-saga-rpc-streams-metadata-MetadataUpdateProto"></a>

### MetadataUpdateProto
Results from an metadata status update gRPC stream call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| inventory_id | [string](#string) |  | Game&#39;s unique Id for the Item |
| metadata_url | [string](#string) |  | Metadata address |





 

 

 

 



<a name="sdk_streams_playerwallet_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## sdk/streams/playerwallet/definition.proto



<a name="sdk-saga-rpc-streams-playerwallet-PlayerWalletStatusUpdate"></a>

### PlayerWalletStatusUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  | Wallet owner OAuth ID |






<a name="sdk-saga-rpc-streams-playerwallet-PlayerWalletUpdate"></a>

### PlayerWalletUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| error | [sdk.saga.common.ErrorData](#sdk-saga-common-ErrorData) |  |  |
| status_update | [PlayerWalletStatusUpdate](#sdk-saga-rpc-streams-playerwallet-PlayerWalletStatusUpdate) |  |  |





 

 

 

 



<a name="sdk_streams_reservation_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## sdk/streams/reservation/definition.proto



<a name="sdk-saga-rpc-streams-reservation-ReservationCreatedProto"></a>

### ReservationCreatedProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| reservation_id | [string](#string) |  |  |






<a name="sdk-saga-rpc-streams-reservation-ReservationRedeemedProto"></a>

### ReservationRedeemedProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| reservation_id | [string](#string) |  |  |
| items | [sdk.saga.api.item.ItemProto](#sdk-saga-api-item-ItemProto) | repeated |  |
| failed_batches | [sdk.saga.api.itemtype.FailedItemTypeBatch](#sdk-saga-api-itemtype-FailedItemTypeBatch) | repeated |  |






<a name="sdk-saga-rpc-streams-reservation-ReservationReleasedProto"></a>

### ReservationReleasedProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| reservation_id | [string](#string) |  |  |






<a name="sdk-saga-rpc-streams-reservation-ReservationUpdate"></a>

### ReservationUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| error | [sdk.saga.common.ErrorData](#sdk-saga-common-ErrorData) |  |  |
| reservation_created | [ReservationCreatedProto](#sdk-saga-rpc-streams-reservation-ReservationCreatedProto) |  |  |
| reservation_released | [ReservationReleasedProto](#sdk-saga-rpc-streams-reservation-ReservationReleasedProto) |  |  |
| reservation_redeemed | [ReservationRedeemedProto](#sdk-saga-rpc-streams-reservation-ReservationRedeemedProto) |  |  |





 

 

 

 



<a name="sdk_streams_stream-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## sdk/streams/stream.proto



<a name="sdk-saga-rpc-streams-StatusConfirmRequest"></a>

### StatusConfirmRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |






<a name="sdk-saga-rpc-streams-StatusUpdate"></a>

### StatusUpdate
Returned results on sending a Status stream call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |
| currency_update | [currency.CurrencyUpdate](#sdk-saga-rpc-streams-currency-CurrencyUpdate) |  |  |
| item_update | [item.ItemUpdate](#sdk-saga-rpc-streams-item-ItemUpdate) |  |  |
| item_type_update | [itemtype.ItemTypeUpdate](#sdk-saga-rpc-streams-itemtype-ItemTypeUpdate) |  |  |
| player_wallet_update | [playerwallet.PlayerWalletUpdate](#sdk-saga-rpc-streams-playerwallet-PlayerWalletUpdate) |  |  |
| reservation_update | [reservation.ReservationUpdate](#sdk-saga-rpc-streams-reservation-ReservationUpdate) |  |  |
| metadata_update | [metadata.MetadataUpdate](#sdk-saga-rpc-streams-metadata-MetadataUpdate) |  |  |
| currency_type_update | [currencytype.CurrencyTypeUpdate](#sdk-saga-rpc-streams-currencytype-CurrencyTypeUpdate) |  |  |
| created_at | [google.protobuf.Timestamp](#google-protobuf-Timestamp) |  |  |





 

 

 


<a name="sdk-saga-rpc-streams-StatusStream"></a>

### StatusStream


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| StatusStream | [Subscribe](#sdk-saga-rpc-streams-Subscribe) | [StatusUpdate](#sdk-saga-rpc-streams-StatusUpdate) stream | Send a call to update the status |
| StatusConfirmation | [StatusConfirmRequest](#sdk-saga-rpc-streams-StatusConfirmRequest) | [.google.protobuf.Empty](#google-protobuf-Empty) | Get verification of status |

 



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

