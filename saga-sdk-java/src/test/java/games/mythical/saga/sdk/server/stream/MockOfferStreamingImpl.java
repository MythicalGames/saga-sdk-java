package games.mythical.saga.sdk.server.stream;

import com.google.protobuf.Empty;
import com.google.protobuf.GeneratedMessageV3;
import games.mythical.saga.sdk.proto.streams.Subscribe;
import games.mythical.saga.sdk.proto.streams.offer.OfferStatusConfirmRequest;
import games.mythical.saga.sdk.proto.streams.offer.OfferStatusUpdate;
import games.mythical.saga.sdk.proto.streams.offer.OfferStreamGrpc;
import games.mythical.saga.sdk.server.StreamingService;
import games.mythical.saga.sdk.util.ConcurrentFinisher;
import io.grpc.stub.StreamObserver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MockOfferStreamingImpl extends OfferStreamGrpc.OfferStreamImplBase implements StreamingService {
    private final Map<String, StreamObserver<OfferStatusUpdate>> streamObservers = new ConcurrentHashMap<>();

    @Override
    public void offerStatusStream(Subscribe request, StreamObserver<OfferStatusUpdate> responseObserver) {
        streamObservers.putIfAbsent(request.getTitleId(), responseObserver);
    }

    @Override
    public void offerStatusConfirmation(OfferStatusConfirmRequest request, StreamObserver<Empty> responseObserver) {
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();

        ConcurrentFinisher.finish(request.getTraceId());
    }

    @Override
    public void sendStatus(String titleId, GeneratedMessageV3 genericStatusUpdateProto) {
        if (streamObservers.containsKey(titleId)) {
            var observer = streamObservers.get(titleId);
            observer.onNext((OfferStatusUpdate) genericStatusUpdateProto);
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
