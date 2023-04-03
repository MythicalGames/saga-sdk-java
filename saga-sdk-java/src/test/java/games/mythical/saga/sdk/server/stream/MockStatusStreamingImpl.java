package games.mythical.saga.sdk.server.stream;

import com.google.protobuf.Empty;
import games.mythical.saga.sdk.proto.streams.StatusConfirmRequest;
import games.mythical.saga.sdk.proto.streams.StatusStreamGrpc;
import games.mythical.saga.sdk.proto.streams.StatusUpdate;
import games.mythical.saga.sdk.proto.streams.Subscribe;
import games.mythical.saga.sdk.util.ConcurrentFinisher;
import io.grpc.stub.StreamObserver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MockStatusStreamingImpl extends StatusStreamGrpc.StatusStreamImplBase {
    private final Map<String, StreamObserver<StatusUpdate>> streamObservers = new ConcurrentHashMap<>();

    @Override
    public void statusStream(Subscribe request, StreamObserver<StatusUpdate> responseObserver) {
        streamObservers.putIfAbsent(request.getTitleId(), responseObserver);
    }

    @Override
    public void statusConfirmation(StatusConfirmRequest request, StreamObserver<Empty> responseObserver) {
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();

        // TODO: since 10/28 confirmation message is no longer being sent to Saga
        ConcurrentFinisher.finish(request.getTraceId());
    }

    public void sendStatus(String titleId, StatusUpdate statusUpdate) throws InterruptedException {
        if (streamObservers.containsKey(titleId)) {
            var observer = streamObservers.get(titleId);
            observer.onNext(statusUpdate);
            Thread.sleep(250);
            ConcurrentFinisher.finish(statusUpdate.getTraceId());
        }
    }

    public void reset() {
        for (var observer : streamObservers.values()) {
            observer.onCompleted();
        }

        streamObservers.clear();
    }
}
