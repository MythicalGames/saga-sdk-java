package games.mythical.saga.sdk.server.stream;

import com.google.protobuf.Empty;
import com.google.protobuf.GeneratedMessageV3;
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
        streamObservers.putIfAbsent(request.getTitleId(), responseObserver);
    }

    @Override
    public void userStatusConfirmation(UserStatusConfirmRequest request, StreamObserver<Empty> responseObserver) {
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();

        ConcurrentFinisher.finish(request.getTraceId());
    }

    @Override
    public void sendStatus(String environmentId, GeneratedMessageV3 genericStatusUpdateProto) {
        if (streamObservers.containsKey(environmentId)) {
            var observer = streamObservers.get(environmentId);
            observer.onNext((UserStatusUpdate) genericStatusUpdateProto);
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
