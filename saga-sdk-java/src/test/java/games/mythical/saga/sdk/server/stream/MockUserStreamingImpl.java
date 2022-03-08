package games.mythical.saga.sdk.server.stream;

import com.google.protobuf.Empty;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.ProtocolMessageEnum;
import games.mythical.saga.sdk.proto.api.user.UserProto;
import games.mythical.saga.sdk.proto.common.user.UserState;
import games.mythical.saga.sdk.proto.streams.Subscribe;
import games.mythical.saga.sdk.proto.streams.user.UserStatusConfirmRequest;
import games.mythical.saga.sdk.proto.streams.user.UserStatusUpdate;
import games.mythical.saga.sdk.proto.streams.user.UserStreamGrpc;
import games.mythical.saga.sdk.server.StreamingService;
import games.mythical.saga.sdk.util.ConcurrentFinisher;
import io.grpc.stub.StreamObserver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MockUserStreamingImpl extends UserStreamGrpc.UserStreamImplBase implements StreamingService {
    private final Map<String, StreamObserver<UserStatusUpdate>> streamObservers = new ConcurrentHashMap<>();

    @Override
    public void userStatusStream(Subscribe request, StreamObserver<UserStatusUpdate> responseObserver) {
        streamObservers.putIfAbsent(request.getEnvironmentId(), responseObserver);
    }

    @Override
    public void userStatusConfirmation(UserStatusConfirmRequest request, StreamObserver<Empty> responseObserver) {
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();

        ConcurrentFinisher.finish(request.getTraceId());
    }

    @Override
    public void sendStatus(String environmentId, GeneratedMessageV3 genericProto, ProtocolMessageEnum genericState) {
        var proto = (UserProto) genericProto;
        var state = (UserState) genericState;
        if (streamObservers.containsKey(environmentId)) {
            var observer = streamObservers.get(environmentId);
            var userStatus = UserStatusUpdate.newBuilder()
                    .setOauthId(proto.getOauthId())
                    .setTraceId(proto.getTraceId())
                    .setUserState(state)
                    .build();
            observer.onNext(userStatus);
        }
    }

    @Override
    public void reset() {
        for (var observer : streamObservers.values()) {
            observer.onCompleted();
        }

        streamObservers.clear();
    }
}
