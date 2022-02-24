package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.SagaItemExecutor;
import games.mythical.saga.sdk.client.model.SagaObject;
import games.mythical.saga.sdk.client.observer.SagaItemObserver;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.api.item.GetItemRequest;
import games.mythical.saga.sdk.proto.api.item.ItemServiceGrpc;
import games.mythical.saga.sdk.proto.streams.Subscribe;
import games.mythical.saga.sdk.proto.streams.item.ItemStreamGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SagaItemClient extends AbstractSagaClient {
    // TODO: need those protos defined first
    private ItemServiceGrpc.ItemServiceBlockingStub serviceBlockingStub;
    private final SagaItemExecutor sagaItemExecutor;

    @SuppressWarnings("unused")
    public SagaItemClient(SagaItemExecutor sagaItemExecutor) throws SagaException {
        super();

        this.sagaItemExecutor = sagaItemExecutor;
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .keepAliveTime(keepAlive, TimeUnit.SECONDS)
                .build();
        initStub();
    }

    SagaItemClient(SagaItemExecutor sagaItemExecutor, ManagedChannel channel) throws SagaException {
        this.sagaItemExecutor = sagaItemExecutor;
        this.channel = channel;
        initStub();
    }

    @Override
    void initStub() {
        serviceBlockingStub = ItemServiceGrpc.newBlockingStub(channel).withCallCredentials(addAuthentication());
        var streamBlockingStub = ItemStreamGrpc.newBlockingStub(channel)
                .withCallCredentials(addAuthentication());
        subscribeToStream(new SagaItemObserver(sagaItemExecutor, streamBlockingStub, this::subscribeToStream));
    }

    void subscribeToStream(SagaItemObserver observer) {
        // set up server stream
        var streamStub = ItemStreamGrpc.newStub(channel).withCallCredentials(addAuthentication());
        var subscribe = Subscribe.newBuilder()
                .setEnvironmentId(environmentId)
                .build();

        streamStub.itemStatusStream(subscribe, observer);
    }

    public Optional<SagaObject> getItem(String gameInventoryId, boolean history) throws SagaException {
        var request = GetItemRequest.newBuilder()
                .setEnvironmentId(environmentId)
                .setGameInventoryId(gameInventoryId)
                .setHistory(history)
                .build();

        try {
            var item = serviceBlockingStub.getItem(request);
            return Optional.of(SagaObject.fromProto(item));
        } catch (StatusRuntimeException e) {
            if (e.getStatus() == Status.NOT_FOUND) {
                return Optional.empty();
            }

            throw SagaException.fromGrpcException(e);
        }
    }
}
