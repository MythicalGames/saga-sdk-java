package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.SagaMetadataExecutor;
import games.mythical.saga.sdk.client.model.SagaMetadata;
import games.mythical.saga.sdk.client.observer.SagaStatusUpdateObserver;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.api.metadata.*;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SagaMetadataClient extends AbstractSagaStreamClient {
    private final SagaMetadataExecutor executor;
    private MetadataServiceGrpc.MetadataServiceBlockingStub serviceBlockingStub;

    SagaMetadataClient(SagaSdkConfig config) throws SagaException {
        this(config, null);
    }

    SagaMetadataClient(SagaSdkConfig config, SagaMetadataExecutor executor) throws SagaException {
        super(config);
        this.executor = executor;
        initStub();
    }

    @Override
    void initStub() {
        serviceBlockingStub = MetadataServiceGrpc.newBlockingStub(channel).withCallCredentials(addAuthentication());
        initStreamStub();
        SagaStatusUpdateObserver.getInstance().with(executor);
    }

    public String updateItemMetadata(String inventoryId, SagaMetadata metadata) throws SagaException {
        try {
            var request = UpdateItemMetadataRequest.newBuilder()
                    .setInventoryId(inventoryId)
                    .setMetadata(SagaMetadata.toProto(metadata))
                    .build();

            var response = serviceBlockingStub.updateItemMetadata(request);
            return response.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        }
    }
}

