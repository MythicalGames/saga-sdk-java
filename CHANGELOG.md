# Changelog

## [0.4.0] - 2022-06-02
### Removed
* emitReceived(...) method removed from all executor interfaces
### Changed
* stream confirmations are now non-blocking

## [0.4.1] - 2022-06-03
### Changed
* issueItem call can now batch issue items

## [0.4.2] - 2022-06-03
### Added
* Add saga-gateway to hosts allowed without encryption.

## [0.4.3] - 2022-06-06
### Removed
* Removed update user from SDK
### Changed
* Updated dependencies

## [0.4.15] - 2022-06-29
### Changed
* Timestamp values in SDK protos changed to protobuf Timestamp type.

## [0.5.0] - 2022-06-29
### Removed
* User Client removed

## [0.5.1] - 2022-06-29
### Changed
* Fixed offer client not filling all values for confirm and cancel operations.