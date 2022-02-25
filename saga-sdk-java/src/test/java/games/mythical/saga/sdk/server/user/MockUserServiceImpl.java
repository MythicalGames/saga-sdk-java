package games.mythical.saga.sdk.server.user;

import games.mythical.saga.sdk.client.model.SagaUser;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.api.user.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
// TODO: should we call this MockUserController to relate it to how we do gateway
public class MockUserServiceImpl extends UserServiceGrpc.UserServiceImplBase {
    private final Map<String, SagaUser> users = new ConcurrentHashMap<>();

    @Override
    public void getUser(GetUserRequest request, StreamObserver<UserProto> responseObserver) {
        if (users.containsKey(request.getOauthId())) {
            try {
                var user = users.get(request.getOauthId());
                responseObserver.onNext(toProto(user));
                responseObserver.onCompleted();
            } catch (SagaException e) {
                log.error("Couldn't convert user!", e);
                responseObserver.onError(e);
            }
        } else {
            responseObserver.onError(Status.NOT_FOUND.asException());
        }
    }

    @Override
    public void getUsers(GetUsersRequest request, StreamObserver<UsersProto> responseObserver) {
        try {
            var result = new ArrayList<UserProto>();

            for (var user : users.values()) {
                result.add(toProto(user));
            }

            responseObserver.onNext(UsersProto.newBuilder().addAllSagaUsers(result).build());
            responseObserver.onCompleted();
        } catch (SagaException e) {
            log.error("Couldn't convert user!", e);
            responseObserver.onError(e);
        }
    }

    public void setUsers(Collection<SagaUser> users) {
        for (var user : users) {
            this.users.putIfAbsent(user.getOauthId(), user);
        }
    }

    public void reset() {
        users.clear();
    }

    // TODO: ProtoUtils me
    private UserProto toProto(SagaUser user) throws SagaException {
        return UserProto.newBuilder()
                .setOauthId(user.getOauthId())
                .setChainAddress(user.getChainAddress())
                .build();
    }
}
