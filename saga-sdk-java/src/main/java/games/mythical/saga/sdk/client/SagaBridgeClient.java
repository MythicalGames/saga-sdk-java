package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.SagaBridgeExecutor;
import games.mythical.saga.sdk.client.model.SagaBridge;
import games.mythical.saga.sdk.client.observer.SagaStatusUpdateObserver;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.exception.SagaErrorCode;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.api.bridge.*;
import games.mythical.saga.sdk.util.ValidateUtil;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SagaBridgeClient extends AbstractSagaStreamClient {

    private final SagaBridgeExecutor executor;
    private BridgeServiceGrpc.BridgeServiceBlockingStub serviceBlockingStub;

    SagaBridgeClient(SagaSdkConfig config, SagaBridgeExecutor executor) throws SagaException {
        super(config);
        this.executor = executor;
        initStub();
    }

    @Override
    void initStub() {
        serviceBlockingStub = BridgeServiceGrpc.newBlockingStub(channel).withCallCredentials(addAuthentication());
        initStreamStub();
        SagaStatusUpdateObserver.getInstance().with(executor);
    }

    public String withdrawItem(String oauthId,
                               String itemTypeId,
                               String inventoryId,
                               String destinationAddress,
                               String destinationChain,
                               String originChain) throws SagaException {
        var request = WithdrawItemRequest.newBuilder()
                .setOauthId(oauthId)
                .setItemTypeId(itemTypeId)
                .setInventoryId(inventoryId)
                .setDestinationAddress(destinationAddress)
                .setDestinationChain(destinationChain)
                .setOriginAddress(originChain)
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

    public SagaBridge getBridge() throws SagaException {
        var request = GetBridgeRequest.newBuilder().build();

        try {
            var bridge = serviceBlockingStub.getBridge(request);
            ValidateUtil.checkFound(bridge, "Bridge not found");
            return SagaBridge.fromProto(bridge);
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        }
    }

    public String getBridgeQuote(
        Integer originChainId,
        Integer targetChainId,
        String itemTypeId,
        String originChainWalletAddress
    ) throws SagaException {
        var request = QuoteBridgeNFTRequest.newBuilder()
                .setOriginChainId(originChainId)
                .setTargetChainId(targetChainId)
                .setItemTypeId(itemTypeId)
                .setOriginChainWalletAddress(originChainWalletAddress)
                .build();
        try {
            var receivedResponse = serviceBlockingStub.getBridgeQuote(request);
            return receivedResponse.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling getBridgeQuote on ItemType", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }
}
