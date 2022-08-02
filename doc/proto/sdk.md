# Protocol Documentation
<a name="top"></a>

## Table of Contents

- [api/bridge/definition.proto](#api_bridge_definition-proto)
    - [BridgeProto](#saga-api-bridge-BridgeProto)
    - [GetBridgeRequest](#saga-api-bridge-GetBridgeRequest)
    - [WithdrawItemRequest](#saga-api-bridge-WithdrawItemRequest)
  
- [api/bridge/rpc.proto](#api_bridge_rpc-proto)
    - [BridgeService](#saga-api-bridge-BridgeService)
  
- [api/common/error.proto](#api_common_error-proto)
    - [ErrorProto](#saga-api-common-ErrorProto)
  
- [api/currency/definition.proto](#api_currency_definition-proto)
    - [BurnCurrencyRequest](#saga-api-currency-BurnCurrencyRequest)
    - [CurrencyProto](#saga-api-currency-CurrencyProto)
    - [GetCurrencyForPlayerRequest](#saga-api-currency-GetCurrencyForPlayerRequest)
    - [IssueCurrencyRequest](#saga-api-currency-IssueCurrencyRequest)
    - [TransferCurrencyRequest](#saga-api-currency-TransferCurrencyRequest)
  
- [api/currency/rpc.proto](#api_currency_rpc-proto)
    - [CurrencyService](#saga-api-currency-CurrencyService)
  
- [api/currencytype/definition.proto](#api_currencytype_definition-proto)
    - [CreateCurrencyTypeRequest](#saga-api-currencytype-CreateCurrencyTypeRequest)
    - [CurrencyTypeProto](#saga-api-currencytype-CurrencyTypeProto)
    - [CurrencyTypesProto](#saga-api-currencytype-CurrencyTypesProto)
    - [GetCurrencyTypeRequest](#saga-api-currencytype-GetCurrencyTypeRequest)
    - [GetCurrencyTypesRequest](#saga-api-currencytype-GetCurrencyTypesRequest)
  
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
    - [TransferItemRequest](#saga-api-item-TransferItemRequest)
    - [UpdateItemMetadataRequest](#saga-api-item-UpdateItemMetadataRequest)
  
- [api/item/rpc.proto](#api_item_rpc-proto)
    - [ItemService](#saga-api-item-ItemService)
  
- [api/itemtype/definition.proto](#api_itemtype_definition-proto)
    - [CreateItemTypeRequest](#saga-api-itemtype-CreateItemTypeRequest)
    - [FreezeItemTypePayload](#saga-api-itemtype-FreezeItemTypePayload)
    - [GetItemTypeRequest](#saga-api-itemtype-GetItemTypeRequest)
    - [GetItemTypesRequest](#saga-api-itemtype-GetItemTypesRequest)
    - [ItemTypeProto](#saga-api-itemtype-ItemTypeProto)
    - [ItemTypesProto](#saga-api-itemtype-ItemTypesProto)
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
  
- [api/order/rpc.proto](#api_order_rpc-proto)
    - [OrderService](#saga-api-order-OrderService)
  
- [api/payment/definition.proto](#api_payment_definition-proto)
    - [Address](#saga-api-payment-Address)
    - [CreateCybersourceCardProto](#saga-api-payment-CreateCybersourceCardProto)
    - [CreatePaymentMethodRequest](#saga-api-payment-CreatePaymentMethodRequest)
    - [CybersourcePaymentData](#saga-api-payment-CybersourcePaymentData)
    - [DeletePaymentMethodRequest](#saga-api-payment-DeletePaymentMethodRequest)
    - [GetPaymentMethodsRequest](#saga-api-payment-GetPaymentMethodsRequest)
    - [PaymentMethodData](#saga-api-payment-PaymentMethodData)
    - [PaymentMethodProto](#saga-api-payment-PaymentMethodProto)
    - [PaymentMethodProtos](#saga-api-payment-PaymentMethodProtos)
    - [UpdateCybersourceCardProto](#saga-api-payment-UpdateCybersourceCardProto)
    - [UpdatePaymentMethodRequest](#saga-api-payment-UpdatePaymentMethodRequest)
    - [UpholdCardProto](#saga-api-payment-UpholdCardProto)
    - [UpholdFinishLinkProto](#saga-api-payment-UpholdFinishLinkProto)
    - [UpholdPaymentData](#saga-api-payment-UpholdPaymentData)
    - [UpholdStartLinkProto](#saga-api-payment-UpholdStartLinkProto)
  
- [api/payment/rpc.proto](#api_payment_rpc-proto)
    - [PaymentService](#saga-api-payment-PaymentService)
  
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
  
- [common/common.proto](#common_common-proto)
    - [ErrorData](#saga-common-ErrorData)
    - [LargeDecimal](#saga-common-LargeDecimal)
    - [Metadata](#saga-common-Metadata)
    - [MetadataAttribute](#saga-common-MetadataAttribute)
    - [ReceivedResponse](#saga-common-ReceivedResponse)
    - [SubError](#saga-common-SubError)
  
- [common/currency/definition.proto](#common_currency_definition-proto)
    - [CurrencyState](#saga-proto-common-currency-CurrencyState)
  
- [common/finalization.proto](#common_finalization-proto)
    - [Finalized](#saga-common-Finalized)
  
- [common/item/definition.proto](#common_item_definition-proto)
    - [BlockChains](#saga-proto-common-item-BlockChains)
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
    - [QueryOptionsProto](#saga-common-QueryOptionsProto)
  
- [common/sort.proto](#common_sort-proto)
    - [SortOrder](#saga-common-SortOrder)
  
- [streams/bridge/definition.proto](#streams_bridge_definition-proto)
    - [BridgeStatusUpdate](#saga-rpc-streams-bridge-BridgeStatusUpdate)
    - [BridgeUpdate](#saga-rpc-streams-bridge-BridgeUpdate)
  
- [streams/common.proto](#streams_common-proto)
    - [Subscribe](#saga-rpc-streams-Subscribe)
  
- [streams/currency/definition.proto](#streams_currency_definition-proto)
    - [CurrencyStatusConfirmRequest](#saga-rpc-streams-currency-CurrencyStatusConfirmRequest)
    - [CurrencyStatusUpdate](#saga-rpc-streams-currency-CurrencyStatusUpdate)
    - [CurrencyUpdate](#saga-rpc-streams-currency-CurrencyUpdate)
  
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
| item_type_id | [string](#string) |  | Id of the ItemType this Item belongs to |
| inventory_id | [string](#string) |  | Id of the GameInventory this Item belongs to |
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



<a name="saga-api-currency-BurnCurrencyRequest"></a>

### BurnCurrencyRequest
Burn currency call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  | User to burn currency from |
| currency_id | [string](#string) |  |  |
| amount | [saga.common.LargeDecimal](#saga-common-LargeDecimal) |  | Amount of currency to burn |






<a name="saga-api-currency-CurrencyProto"></a>

### CurrencyProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |
| amount | [saga.common.LargeDecimal](#saga-common-LargeDecimal) |  | Amount of currency |
| currency_type_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  | Wallet address the currency belongs to |






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
| amount | [saga.common.LargeDecimal](#saga-common-LargeDecimal) |  | amount to issue |
| currency_type_id | [string](#string) |  | Currency Type the currency to issue belongs to |
| oauth_id | [string](#string) |  | Wallet address to issue currency to |






<a name="saga-api-currency-TransferCurrencyRequest"></a>

### TransferCurrencyRequest
Transfer currency call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| source_oauth_id | [string](#string) |  | User to transfer currency from |
| destination_oauth_id | [string](#string) |  | User to transfer currency to |
| currency_id | [string](#string) |  |  |
| amount | [saga.common.LargeDecimal](#saga-common-LargeDecimal) |  | Amount of currency to transfer |





 

 

 

 



<a name="api_currency_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/currency/rpc.proto


 

 

 


<a name="saga-api-currency-CurrencyService"></a>

### CurrencyService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| GetCurrencyForPlayer | [GetCurrencyForPlayerRequest](#saga-api-currency-GetCurrencyForPlayerRequest) | [CurrencyProto](#saga-api-currency-CurrencyProto) | Get a Currency for a user |
| IssueCurrency | [IssueCurrencyRequest](#saga-api-currency-IssueCurrencyRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Issue currency to a user |
| TransferCurrency | [TransferCurrencyRequest](#saga-api-currency-TransferCurrencyRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Transfer currency between users |
| BurnCurrency | [BurnCurrencyRequest](#saga-api-currency-BurnCurrencyRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Burn currency for a user |

 



<a name="api_currencytype_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/currencytype/definition.proto



<a name="saga-api-currencytype-CreateCurrencyTypeRequest"></a>

### CreateCurrencyTypeRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| currency_type_id | [string](#string) |  |  |
| name | [string](#string) |  |  |
| symbol | [string](#string) |  |  |
| decimal_places | [int64](#int64) |  |  |
| max_supply | [int64](#int64) |  |  |






<a name="saga-api-currencytype-CurrencyTypeProto"></a>

### CurrencyTypeProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |
| currency_type_id | [string](#string) |  |  |
| game_title_id | [string](#string) |  |  |
| publisher_address | [string](#string) |  |  |
| name | [string](#string) |  |  |
| symbol | [string](#string) |  |  |
| decimal_places | [int64](#int64) |  |  |
| contract_address | [string](#string) |  |  |
| finalized | [bool](#bool) |  |  |
| max_supply | [int64](#int64) |  |  |
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
| page_size | [int32](#int32) |  |  |
| sort_order | [saga.common.SortOrder](#saga-common-SortOrder) |  |  |





 

 

 

 



<a name="api_currencytype_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/currencytype/rpc.proto


 

 

 


<a name="saga-api-currencytype-CurrencyTypeService"></a>

### CurrencyTypeService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| GetCurrencyType | [GetCurrencyTypeRequest](#saga-api-currencytype-GetCurrencyTypeRequest) | [CurrencyTypeProto](#saga-api-currencytype-CurrencyTypeProto) |  |
| GetCurrencyTypes | [GetCurrencyTypesRequest](#saga-api-currencytype-GetCurrencyTypesRequest) | [CurrencyTypesProto](#saga-api-currencytype-CurrencyTypesProto) |  |
| CreateCurrencyType | [CreateCurrencyTypeRequest](#saga-api-currencytype-CreateCurrencyTypeRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) |  |

 



<a name="api_item_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/item/definition.proto



<a name="saga-api-item-BurnItemRequest"></a>

### BurnItemRequest
Burn item call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| inventory_id | [string](#string) |  | Game&#39;s id for the Item to burn |






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
| serial_number | [int32](#int32) |  |  |
| finalized | [bool](#bool) |  |  |
| block_explorer_url | [string](#string) |  |  |
| metadata_url | [string](#string) |  |  |
| created_at | [google.protobuf.Timestamp](#google-protobuf-Timestamp) |  | When was this Item created |
| updated_at | [google.protobuf.Timestamp](#google-protobuf-Timestamp) |  | When was this Item last updated |






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
| inventory_id | [string](#string) |  | Game&#39;s id for the Item to transfer |
| destination_oauth_id | [string](#string) |  | User of Item to transfer to |






<a name="saga-api-item-UpdateItemMetadataRequest"></a>

### UpdateItemMetadataRequest
Update Metadata call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| inventory_id | [string](#string) |  | Update Metadata for the Item with this id |
| metadata | [saga.common.Metadata](#saga-common-Metadata) |  | Metadata to update with |





 

 

 

 



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
| UpdateItemMetadata | [UpdateItemMetadataRequest](#saga-api-item-UpdateItemMetadataRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Update an Item&#39;s metadata |
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
| randomize | [bool](#bool) |  |  |
| date_finished | [google.protobuf.Timestamp](#google-protobuf-Timestamp) |  |  |
| withdrawable | [bool](#bool) |  |  |






<a name="saga-api-itemtype-FreezeItemTypePayload"></a>

### FreezeItemTypePayload



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
| withdrawable | [bool](#bool) |  |  |
| created_at | [google.protobuf.Timestamp](#google-protobuf-Timestamp) |  |  |
| updated_at | [google.protobuf.Timestamp](#google-protobuf-Timestamp) |  |  |
| issued_supply | [int64](#int64) |  |  |
| available_supply | [google.protobuf.Int64Value](#google-protobuf-Int64Value) |  |  |






<a name="saga-api-itemtype-ItemTypesProto"></a>

### ItemTypesProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| item_types | [ItemTypeProto](#saga-api-itemtype-ItemTypeProto) | repeated |  |






<a name="saga-api-itemtype-UpdateItemTypePayload"></a>

### UpdateItemTypePayload
Update ItemType call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| item_type_id | [string](#string) |  | Game&#39;s ItemTypeId for the ItemType to update |
| withdrawable | [bool](#bool) |  | withdrawable state to update to |





 

 

 

 



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
| FreezeItemType | [FreezeItemTypePayload](#saga-api-itemtype-FreezeItemTypePayload) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Freeze the ItemType |

 



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

 



<a name="api_myth_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/myth/definition.proto



<a name="saga-api-myth-ConfirmBuyingMythTokenRequest"></a>

### ConfirmBuyingMythTokenRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| quote_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |
| payment_provider_data | [saga.api.order.PaymentProviderData](#saga-api-order-PaymentProviderData) |  |  |






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
| payment_provider_data | [saga.api.order.PaymentProviderData](#saga-api-order-PaymentProviderData) |  | Payment provider data |
| denomination_currency | [string](#string) |  |  |
| origin_sub_account | [string](#string) |  |  |
| oauth_id | [string](#string) |  | User that is buying MYTH Tokens |






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
| oauth_id | [string](#string) |  |  |
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
| item_type_id | [string](#string) |  |  |
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
| payment_provider_id | [saga.proto.common.payment.PaymentProviderId](#saga-proto-common-payment-PaymentProviderId) |  |  |
| buyer_oauth_id | [string](#string) |  |  |
| seller_oauth_id | [string](#string) |  |  |
| conversion_rate | [string](#string) |  |  |
| created_at | [google.protobuf.Timestamp](#google-protobuf-Timestamp) |  |  |





 

 

 

 



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
| state_iso_alpha_2 | [string](#string) |  |  |
| postal_code | [string](#string) |  |  |
| country_iso_alpha_2 | [string](#string) |  |  |






<a name="saga-api-payment-CreateCybersourceCardProto"></a>

### CreateCybersourceCardProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| exp_month | [string](#string) |  |  |
| exp_year | [string](#string) |  |  |
| card_type | [string](#string) |  |  |
| instrument_id | [string](#string) |  |  |
| billing_address | [Address](#saga-api-payment-Address) |  |  |
| make_default | [bool](#bool) |  |  |






<a name="saga-api-payment-CreatePaymentMethodRequest"></a>

### CreatePaymentMethodRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  |  |
| cybersource | [CreateCybersourceCardProto](#saga-api-payment-CreateCybersourceCardProto) |  |  |
| uphold | [UpholdStartLinkProto](#saga-api-payment-UpholdStartLinkProto) |  |  |






<a name="saga-api-payment-CybersourcePaymentData"></a>

### CybersourcePaymentData



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| expiration_month | [string](#string) |  |  |
| expiration_year | [string](#string) |  |  |
| card_type | [string](#string) |  |  |
| instrument_id | [string](#string) |  |  |
| payment_method_token_id | [string](#string) |  |  |






<a name="saga-api-payment-DeletePaymentMethodRequest"></a>

### DeletePaymentMethodRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  |  |
| payment_provider_id | [saga.proto.common.payment.PaymentProviderId](#saga-proto-common-payment-PaymentProviderId) |  |  |
| payment_method_token | [string](#string) |  |  |






<a name="saga-api-payment-GetPaymentMethodsRequest"></a>

### GetPaymentMethodsRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  |  |
| payment_provider_id | [saga.proto.common.payment.PaymentProviderId](#saga-proto-common-payment-PaymentProviderId) |  |  |






<a name="saga-api-payment-PaymentMethodData"></a>

### PaymentMethodData



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| make_default | [bool](#bool) |  | Make this the default payment |
| cybersource | [CybersourcePaymentData](#saga-api-payment-CybersourcePaymentData) |  |  |
| uphold | [UpholdPaymentData](#saga-api-payment-UpholdPaymentData) |  |  |






<a name="saga-api-payment-PaymentMethodProto"></a>

### PaymentMethodProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  | User of this Payment Method |
| payment_method_data | [PaymentMethodData](#saga-api-payment-PaymentMethodData) |  | Payment method data |
| address | [Address](#saga-api-payment-Address) |  | Address of this Payment Method |






<a name="saga-api-payment-PaymentMethodProtos"></a>

### PaymentMethodProtos



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| payment_methods | [PaymentMethodProto](#saga-api-payment-PaymentMethodProto) | repeated |  |






<a name="saga-api-payment-UpdateCybersourceCardProto"></a>

### UpdateCybersourceCardProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| payment_method_token | [string](#string) |  |  |
| exp_month | [string](#string) |  |  |
| exp_year | [string](#string) |  |  |
| card_type | [string](#string) |  |  |
| instrument_id | [string](#string) |  |  |
| billing_address | [Address](#saga-api-payment-Address) |  |  |
| make_default | [bool](#bool) |  |  |






<a name="saga-api-payment-UpdatePaymentMethodRequest"></a>

### UpdatePaymentMethodRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  |  |
| cybersource | [UpdateCybersourceCardProto](#saga-api-payment-UpdateCybersourceCardProto) |  |  |
| uphold | [UpholdFinishLinkProto](#saga-api-payment-UpholdFinishLinkProto) |  |  |






<a name="saga-api-payment-UpholdCardProto"></a>

### UpholdCardProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| uphold_id | [string](#string) |  |  |
| currency | [string](#string) |  |  |
| balance | [string](#string) |  |  |
| normalized_currency | [string](#string) |  |  |
| normalized_balance | [string](#string) |  |  |
| label | [string](#string) |  |  |






<a name="saga-api-payment-UpholdFinishLinkProto"></a>

### UpholdFinishLinkProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| state_code | [string](#string) |  |  |
| generated_temporary_code | [string](#string) |  |  |






<a name="saga-api-payment-UpholdPaymentData"></a>

### UpholdPaymentData



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| temp_state_code | [string](#string) |  |  |
| email | [string](#string) |  |  |
| status | [string](#string) |  |  |
| verifications | [string](#string) |  |  |
| birth_date | [string](#string) |  |  |
| cards | [UpholdCardProto](#saga-api-payment-UpholdCardProto) | repeated |  |






<a name="saga-api-payment-UpholdStartLinkProto"></a>

### UpholdStartLinkProto






 

 

 

 



<a name="api_payment_rpc-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## api/payment/rpc.proto


 

 

 


<a name="saga-api-payment-PaymentService"></a>

### PaymentService


| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| CreatePaymentMethod | [CreatePaymentMethodRequest](#saga-api-payment-CreatePaymentMethodRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Create a Payment Method |
| GetPaymentMethods | [GetPaymentMethodsRequest](#saga-api-payment-GetPaymentMethodsRequest) | [PaymentMethodProtos](#saga-api-payment-PaymentMethodProtos) | Get Payment Methods for a user |
| UpdatePaymentMethod | [UpdatePaymentMethodRequest](#saga-api-payment-UpdatePaymentMethodRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Update the Payment Method for a User |
| DeletePaymentMethod | [DeletePaymentMethodRequest](#saga-api-payment-DeletePaymentMethodRequest) | [.saga.common.ReceivedResponse](#saga-common-ReceivedResponse) | Delete a Payment Method for a User |

 



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






<a name="saga-api-playerwallet-PlayerWalletProto"></a>

### PlayerWalletProto



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |
| oauth_id | [string](#string) |  |  |
| address | [string](#string) |  |  |





 

 

 

 



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
| created_at | [google.protobuf.Timestamp](#google-protobuf-Timestamp) |  | When this Title was created |






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
| created_at | [google.protobuf.Timestamp](#google-protobuf-Timestamp) |  | When this Transaction was created |






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






<a name="saga-common-LargeDecimal"></a>

### LargeDecimal
Decimal object for potential 18 decimal value support. Naming was done to not conflict with Java BigDecimal


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| units | [int64](#int64) |  | The whole units part of the amount |
| exa | [int64](#int64) |  | Number of exa (10^-18) units of the amount |






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
| CYBERSOURCE | 0 |  |
| UPHOLD | 1 |  |
| MYTHICAL | 2 |  |


 

 

 



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


 

 

 



<a name="streams_bridge_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## streams/bridge/definition.proto



<a name="saga-rpc-streams-bridge-BridgeStatusUpdate"></a>

### BridgeStatusUpdate
Results from a Bridge status update gRPC stream call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  |  |
| item_type_id | [string](#string) |  |  |
| inventory_id | [string](#string) |  |  |
| destination_address | [string](#string) |  |  |
| destination_chain | [string](#string) |  |  |
| origin_address | [string](#string) |  |  |
| mythical_transaction_id | [string](#string) |  |  |
| mainnet_transaction_id | [string](#string) |  |  |






<a name="saga-rpc-streams-bridge-BridgeUpdate"></a>

### BridgeUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| error | [saga.common.ErrorData](#saga-common-ErrorData) |  |  |
| status_update | [BridgeStatusUpdate](#saga-rpc-streams-bridge-BridgeStatusUpdate) |  |  |





 

 

 

 



<a name="streams_common-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## streams/common.proto



<a name="saga-rpc-streams-Subscribe"></a>

### Subscribe



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| title_id | [string](#string) |  |  |





 

 

 

 



<a name="streams_currency_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## streams/currency/definition.proto



<a name="saga-rpc-streams-currency-CurrencyStatusConfirmRequest"></a>

### CurrencyStatusConfirmRequest
Currency information sent on a confirm request


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| trace_id | [string](#string) |  |  |
| title_id | [string](#string) |  |  |
| currency_type_id | [string](#string) |  |  |
| currency_state | [saga.proto.common.currency.CurrencyState](#saga-proto-common-currency-CurrencyState) |  |  |






<a name="saga-rpc-streams-currency-CurrencyStatusUpdate"></a>

### CurrencyStatusUpdate
Results from a Currency status update gRPC stream call


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| oauth_id | [string](#string) |  | Wallet address of Currency |
| currency_type_id | [string](#string) |  |  |
| amount | [saga.common.LargeDecimal](#saga-common-LargeDecimal) |  | Amount of coins |
| currency_state | [saga.proto.common.currency.CurrencyState](#saga-proto-common-currency-CurrencyState) |  | State of the Currency, see CurrencyState |






<a name="saga-rpc-streams-currency-CurrencyUpdate"></a>

### CurrencyUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| error | [saga.common.ErrorData](#saga-common-ErrorData) |  |  |
| status_update | [CurrencyStatusUpdate](#saga-rpc-streams-currency-CurrencyStatusUpdate) |  |  |





 

 

 

 



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
| serial_number | [int32](#int32) |  |  |
| metadata_url | [string](#string) |  | Metadata address |
| item_state | [saga.proto.common.item.ItemState](#saga-proto-common-item-ItemState) |  | State of the Item, see ItemState |






<a name="saga-rpc-streams-item-ItemUpdate"></a>

### ItemUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| error | [saga.common.ErrorData](#saga-common-ErrorData) |  |  |
| status_update | [ItemStatusUpdate](#saga-rpc-streams-item-ItemStatusUpdate) |  |  |





 

 

 

 



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
| error | [saga.common.ErrorData](#saga-common-ErrorData) |  |  |
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
| error | [saga.common.ErrorData](#saga-common-ErrorData) |  |  |
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
| inventory_id | [string](#string) |  | Game&#39;s id for the Item associated with this Offer |
| total | [string](#string) |  | Total price for the offer |
| offer_state | [saga.proto.common.offer.OfferState](#saga-proto-common-offer-OfferState) |  | State of the Offer, see OfferState |






<a name="saga-rpc-streams-offer-OfferUpdate"></a>

### OfferUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| error | [saga.common.ErrorData](#saga-common-ErrorData) |  |  |
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
| error | [saga.common.ErrorData](#saga-common-ErrorData) |  |  |
| status_update | [OrderStatusUpdate](#saga-rpc-streams-order-OrderStatusUpdate) |  |  |





 

 

 

 



<a name="streams_payment_definition-proto"></a>
<p align="right"><a href="#top">Top</a></p>

## streams/payment/definition.proto



<a name="saga-rpc-streams-payment-PaymentMethodStatusUpdate"></a>

### PaymentMethodStatusUpdate
Result of payment method creation, update, or deletion


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| payment_method | [saga.api.payment.PaymentMethodProto](#saga-api-payment-PaymentMethodProto) |  |  |
| payment_method_status | [saga.proto.common.payment.PaymentMethodUpdateStatus](#saga-proto-common-payment-PaymentMethodUpdateStatus) |  |  |






<a name="saga-rpc-streams-payment-PaymentUpdate"></a>

### PaymentUpdate



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| error | [saga.common.ErrorData](#saga-common-ErrorData) |  |  |
| status_update | [PaymentMethodStatusUpdate](#saga-rpc-streams-payment-PaymentMethodStatusUpdate) |  |  |





 

 

 

 



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
| bridge_update | [bridge.BridgeUpdate](#saga-rpc-streams-bridge-BridgeUpdate) |  |  |
| currency_update | [currency.CurrencyUpdate](#saga-rpc-streams-currency-CurrencyUpdate) |  |  |
| item_update | [item.ItemUpdate](#saga-rpc-streams-item-ItemUpdate) |  |  |
| item_type_update | [itemtype.ItemTypeUpdate](#saga-rpc-streams-itemtype-ItemTypeUpdate) |  |  |
| listing_update | [listing.ListingUpdate](#saga-rpc-streams-listing-ListingUpdate) |  |  |
| myth_token_update | [myth.MythTokenUpdate](#saga-rpc-streams-myth-MythTokenUpdate) |  |  |
| offer_update | [offer.OfferUpdate](#saga-rpc-streams-offer-OfferUpdate) |  |  |
| order_update | [order.OrderUpdate](#saga-rpc-streams-order-OrderUpdate) |  |  |
| payment_update | [payment.PaymentUpdate](#saga-rpc-streams-payment-PaymentUpdate) |  |  |
| player_wallet_update | [playerwallet.PlayerWalletUpdate](#saga-rpc-streams-playerwallet-PlayerWalletUpdate) |  |  |
| reservation_update | [reservation.ReservationUpdate](#saga-rpc-streams-reservation-ReservationUpdate) |  |  |
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

