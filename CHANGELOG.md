# Changelog

## [0.5.xxx] - 2022
### Changed
* Maintenance mode feature, will return UNAVAILABLE error

## [0.5.29] - 2022-09-20
### Changed
* error source and code should come from originator of error again
* Switch stream consumer to earliest offset setting
* Reuse consumer group ID's for same titles
* Added createdAt/updatedAt to player wallet responses
* nmyth withdrawal()
* Moved update item metadata to its own client/executor/stream

## [0.5.26] - 2022-09-08
### Changed
* issue item automatically uses reservations
* withdrawable added to create item type request
* nmyth deposit() 
* Action proto used for kafka messages and trace IDs
* item type endMint()
* metadata validation
* removed orders, listings, offers, payments, bridge, and itemtype.freeze

## [0.5.17] - 2022-08-10
### Changed
* Removed LargeDecimal in favor of long for saga currency amounts
* serialNumber renamed tokenId
* New startMint() function
* New getBridgeQuote() function
* NFT Reservation system, reserve(), redeem(), release()
* Add issued and available supply to itemtype model
* Batch item mints
* New getPlayerWallet() function

## [0.5.5] - 2022-07-01
### Changed
* Fix response values for MythToken client.

## [0.5.1] - 2022-06-29
### Changed
* Fixed offer client not filling all values for confirm and cancel operations.

## [0.5.0] - 2022-06-29
### Removed
* User Client removed

## [0.4.15] - 2022-06-29
### Changed
* Timestamp values in SDK protos changed to protobuf Timestamp type.

## [0.4.3] - 2022-06-06
### Removed
* Removed update user from SDK
### Changed
* Updated dependencies

## [0.4.2] - 2022-06-03
### Added
* Add saga-gateway to hosts allowed without encryption.

## [0.4.1] - 2022-06-03
### Changed
* issueItem call can now batch issue items
* 
## [0.4.0] - 2022-06-02
### Removed
* emitReceived(...) method removed from all executor interfaces
### Changed
* stream confirmations are now non-blocking








