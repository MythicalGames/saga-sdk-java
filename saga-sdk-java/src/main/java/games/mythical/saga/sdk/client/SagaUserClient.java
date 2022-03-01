package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.SagaUserExecutor;
import games.mythical.saga.sdk.client.model.SagaUser;
import games.mythical.saga.sdk.client.observer.SagaUserObserver;
import games.mythical.saga.sdk.exception.SagaErrorCode;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.api.user.GetUserRequest;
import games.mythical.saga.sdk.proto.api.user.UpdateUserRequest;
import games.mythical.saga.sdk.proto.api.user.UserServiceGrpc;
import games.mythical.saga.sdk.proto.streams.Subscribe;
import games.mythical.saga.sdk.proto.streams.user.UserStreamGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SagaUserClient extends AbstractSagaClient {
    private final SagaUserExecutor sagaUserExecutor;
    private UserServiceGrpc.UserServiceBlockingStub serviceBlockingStub;

    public SagaUserClient(SagaUserExecutor sagaUserExecutor) throws SagaException {
        super();

        this.sagaUserExecutor = sagaUserExecutor;
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .keepAliveTime(keepAlive, TimeUnit.SECONDS)
                .build();
        initStub();
    }

    public SagaUserClient(SagaUserExecutor sagaUserExecutor, ManagedChannel channel) throws SagaException {
        this.sagaUserExecutor = sagaUserExecutor;
        this.channel = channel;
        initStub();
    }

    @Override
    void initStub() {
        serviceBlockingStub = UserServiceGrpc.newBlockingStub(channel).withCallCredentials(addAuthentication());
        var streamBlockingStub = UserStreamGrpc.newBlockingStub(channel)
                .withCallCredentials(addAuthentication());
        subscribeToStream(new SagaUserObserver(sagaUserExecutor, streamBlockingStub, this::subscribeToStream));
    }

    void subscribeToStream(SagaUserObserver observer) {
        // set up server stream
        var streamStub = UserStreamGrpc.newStub(channel).withCallCredentials(addAuthentication());
        var subscribe = Subscribe.newBuilder()
                .setEnvironmentId(environmentId)
                .build();

        streamStub.userStatusStream(subscribe, observer);
    }

    public Optional<SagaUser> getUser(String oauthId) throws SagaException {
        var request = GetUserRequest.newBuilder()
                .setTitleId(environmentId) // TODO: check AbstractSagaClient for client settings
                .setOauthId(oauthId)
                .build();

        try {
            var userProto = serviceBlockingStub.getUser(request);
            return Optional.of(SagaUser.fromProto(userProto));
        } catch (StatusRuntimeException e) {
            if (e.getStatus() == Status.NOT_FOUND) {
                return Optional.empty();
            }

            throw SagaException.fromGrpcException(e);
        }
    }

    public Optional<SagaUser> updateUser(String oauthId) throws SagaException {
        var request = UpdateUserRequest.newBuilder()
                .setTitleId(environmentId)
                .setOauthId(oauthId)
                .build();

        try {
            var userProto = serviceBlockingStub.updateUser(request);
            sagaUserExecutor.updateUser(userProto.getOauthId(), userProto.getTraceId());
            return Optional.of(SagaUser.fromProto(userProto));
        } catch (StatusRuntimeException e) {
            if (e.getStatus() == Status.NOT_FOUND) {
                return Optional.empty();
            }

            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling updateItemState on issueItem, item will be in an invalid state!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }
}
