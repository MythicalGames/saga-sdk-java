package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.SagaNftBridgeExecutor;
import games.mythical.saga.sdk.client.model.SagaNftBridge;
import games.mythical.saga.sdk.client.model.SagaNftBridgeQuoteResponse;
import games.mythical.saga.sdk.client.observer.SagaStatusUpdateObserver;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.exception.SagaErrorCode;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.api.nftbridge.*;
import games.mythical.saga.sdk.util.ValidateUtil;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SagaNftBridgeClient extends AbstractSagaStreamClient {

    private final SagaNftBridgeExecutor executor;
    private NftBridgeServiceGrpc.NftBridgeServiceBlockingStub serviceBlockingStub;

    SagaNftBridgeClient(SagaSdkConfig config, SagaNftBridgeExecutor executor) throws SagaException {
        super(config);
        this.executor = executor;
        initStub();
    }

    @Override
    void initStub() {
        serviceBlockingStub = NftBridgeServiceGrpc.newBlockingStub(channel).withCallCredentials(addAuthentication());
        initStreamStub();
        SagaStatusUpdateObserver.getInstance().with(executor);
    }

    public String withdrawItem(Integer originChainId,
                               Integer targetChainId,
                               String gameTitleId,
                               String inventoryId,
                               String oauthId,
                               String feeInOriginChainNativeToken,
                               String expiresAt,
                               String signature,
                               String itemTypeId,
                               String targetChainWalletAddress) throws SagaException {
        var request = WithdrawItemRequest.newBuilder()
                .setQuoteRequest(QuoteBridgeNFTRequest.newBuilder()
                        .setOriginChainId(originChainId)
                        .setTargetChainId(targetChainId)
                        .setGameTitleId(gameTitleId)
                        .setInventoryId(inventoryId)
                        .setOauthId(oauthId)
                        .build())
                .setFeeInOriginchainNativeToken(feeInOriginChainNativeToken)
                .setExpiresAt(expiresAt)
                .setSignature(signature)
                .setItemTypeId(itemTypeId)
                .setTargetchainWalletAddress(targetChainWalletAddress)
                .build();

        try {
            var receivedResponse = serviceBlockingStub.withdrawItem(request);
            return receivedResponse.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on withdrawItem, item may be lost!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }

    public SagaNftBridge getNftBridge() throws SagaException {
        var request = GetNftBridgeRequest.newBuilder().build();

        try {
            var bridge = serviceBlockingStub.getBridge(request);
            ValidateUtil.checkFound(bridge, "Nft Bridge not found");
            return SagaNftBridge.fromProto(bridge);
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        }
    }

    public SagaNftBridgeQuoteResponse getNftBridgeQuote(
            Integer originChainId,
            Integer targetChainId,
            String gameTitleId,
            String inventoryId,
            String oauthId
    ) throws SagaException {
        var request = QuoteBridgeNFTRequest.newBuilder()
                .setOriginChainId(originChainId)
                .setTargetChainId(targetChainId)
                .setGameTitleId(gameTitleId)
                .setInventoryId(inventoryId)
                .setOauthId(oauthId)
                .build();
        try {
            var quoteResponse = serviceBlockingStub.getBridgeQuote(request);
            ValidateUtil.checkFound(quoteResponse, "Nft Bridge Quote response not found");
            return SagaNftBridgeQuoteResponse.fromProto(quoteResponse);
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling getNftBridgeQuote", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }
}
