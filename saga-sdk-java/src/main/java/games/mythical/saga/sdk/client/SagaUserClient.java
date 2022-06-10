package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.SagaUserExecutor;
import games.mythical.saga.sdk.client.model.SagaUser;
import games.mythical.saga.sdk.client.model.SagaWalletAsset;
import games.mythical.saga.sdk.client.observer.SagaStatusUpdateObserver;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.exception.SagaErrorCode;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.factory.CommonFactory;
import games.mythical.saga.sdk.proto.api.user.*;
import games.mythical.saga.sdk.proto.common.SortOrder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class SagaUserClient extends AbstractSagaStreamClient {
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
        initStreamStub();
        SagaStatusUpdateObserver.getInstance().with(executor);
    }

    public Optional<SagaUser> getUser(String oauthId) throws SagaException {
        var request = GetUserRequest.newBuilder()
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

    public Collection<SagaUser> getUsers(int pageSize,
                                         SortOrder sortOrder,
                                         Instant createdAtCursor) throws SagaException {
        var request = GetUsersRequest.newBuilder()
                .setQueryOptions(CommonFactory.toProto(pageSize, sortOrder, createdAtCursor))
                .build();

        try {
            var users = serviceBlockingStub.getUsers(request);
            return users.getSagaUsersList().stream().map(SagaUser::fromProto).collect(Collectors.toList());
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        }
    }

    public String createUser(String oauthId) throws SagaException {
        var request = CreateUserRequest.newBuilder()
                .setOauthId(oauthId)
                .build();

        try {
            var receivedResponse = serviceBlockingStub.createUser(request);
            executor.updateUser(oauthId, receivedResponse.getTraceId());
            return receivedResponse.getTraceId();
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on createUser, user create may be lost!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }

    public Optional<SagaWalletAsset> getWalletAssets(String oauthId,
                                                     String publisherId,
                                                     String partnerId) throws SagaException {
        var request = GetWalletAssetsRequest.newBuilder()
                .setOauthId(oauthId)
                .setPublisherId(publisherId)
                .setPartnerId(partnerId)
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
