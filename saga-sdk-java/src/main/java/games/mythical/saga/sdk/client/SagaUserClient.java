package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.SagaUserExecutor;
import games.mythical.saga.sdk.client.model.SagaUser;
import games.mythical.saga.sdk.client.observer.SagaUserObserver;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.exception.SagaErrorCode;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.api.user.GetUserRequest;
import games.mythical.saga.sdk.proto.api.user.UpdateUserRequest;
import games.mythical.saga.sdk.proto.api.user.UserServiceGrpc;
import games.mythical.saga.sdk.proto.streams.Subscribe;
import games.mythical.saga.sdk.proto.streams.user.UserStreamGrpc;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class SagaUserClient extends AbstractSagaClient {
    private final SagaUserExecutor executor;
    private UserServiceGrpc.UserServiceBlockingStub serviceBlockingStub;

    SagaUserClient(SagaSdkConfig config, SagaUserExecutor executor) throws SagaException {
        super(config);
        this.executor = executor;
        initStub();
    }

    @Override
    void initStub() {
        serviceBlockingStub = UserServiceGrpc.newBlockingStub(channel).withCallCredentials(addAuthentication());
        var streamBlockingStub = UserStreamGrpc.newBlockingStub(channel)
                .withCallCredentials(addAuthentication());
        subscribeToStream(new SagaUserObserver(executor, streamBlockingStub, this::subscribeToStream));
    }

    void subscribeToStream(SagaUserObserver observer) {
        // set up server stream
        var streamStub = UserStreamGrpc.newStub(channel).withCallCredentials(addAuthentication());
        var subscribe = Subscribe.newBuilder()
                .setTitleId(config.getTitleId())
                .build();

        streamStub.userStatusStream(subscribe, observer);
    }

    public Optional<SagaUser> getUser(String oauthId) throws SagaException {
        var request = GetUserRequest.newBuilder()
                .setTitleId(config.getTitleId()) // TODO: check AbstractSagaClient for client settings
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
                .setTitleId(config.getTitleId())
                .setOauthId(oauthId)
                .build();

        try {
            var userProto = serviceBlockingStub.updateUser(request);
            executor.updateUser(userProto.getOauthId(), userProto.getTraceId());
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
