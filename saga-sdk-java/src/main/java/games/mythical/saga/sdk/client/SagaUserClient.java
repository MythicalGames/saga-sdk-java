package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.SagaUserExecutor;
import games.mythical.saga.sdk.client.model.SagaUser;
import games.mythical.saga.sdk.client.model.SagaWalletAsset;
import games.mythical.saga.sdk.client.model.query.QueryOptions;
import games.mythical.saga.sdk.client.observer.SagaStatusUpdateObserver;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.exception.SagaErrorCode;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.api.user.*;
import games.mythical.saga.sdk.proto.streams.StatusStreamGrpc;
import games.mythical.saga.sdk.proto.streams.Subscribe;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

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
        var streamBlockingStub = StatusStreamGrpc.newBlockingStub(channel)
                .withCallCredentials(addAuthentication());

        if (SagaStatusUpdateObserver.getInstance() == null) {
            subscribeToStream(SagaStatusUpdateObserver.initialize(streamBlockingStub, this::subscribeToStream));
        }
        SagaStatusUpdateObserver.getInstance().with(executor);
    }

    void subscribeToStream(SagaStatusUpdateObserver observer) {
        // set up server stream
        var streamStub = StatusStreamGrpc.newStub(channel).withCallCredentials(addAuthentication());
        var subscribe = Subscribe.newBuilder()
                .setTitleId(config.getTitleId())
                .build();

        streamStub.statusStream(subscribe, observer);
    }

    public Optional<SagaUser> getUser(String oauthId) throws SagaException {
        var request = GetUserRequest.newBuilder()
                .setTitleId(config.getTitleId())
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

    public Collection<SagaUser> getUsers(QueryOptions queryOptions) throws SagaException {
        var request = GetUsersRequest.newBuilder()
                .setQueryOptions(QueryOptions.toProto(queryOptions))
                .build();

        try {
            var users = serviceBlockingStub.getUsers(request);
            return users.getSagaUsersList().stream().map(SagaUser::fromProto).collect(Collectors.toList());
        } catch (StatusRuntimeException e) {
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

    public Optional<SagaWalletAsset> getWalletAssets(String oauthId,
                                                     String publisherId,
                                                     String partnerId,
                                                     String titleId) throws SagaException {
        var request = GetWalletAssetsRequest.newBuilder()
                .setOauthId(oauthId)
                .setPublisherId(publisherId)
                .setPartnerId(partnerId)
                .setTitleId(titleId)
                .build();

        try {
            var response = serviceBlockingStub.getWalletAssets(request);
            return Optional.of(SagaWalletAsset.fromProto(response));
        } catch (StatusRuntimeException e) {
            if (e.getStatus() == Status.NOT_FOUND) {
                return Optional.empty();
            }

            throw SagaException.fromGrpcException(e);
        }
    }
}
