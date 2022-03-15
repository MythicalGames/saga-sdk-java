package games.mythical.saga.sdk.server.stream;

import com.google.protobuf.Empty;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.ProtocolMessageEnum;
import games.mythical.saga.sdk.proto.api.listing.ListingQuoteProto;
import games.mythical.saga.sdk.proto.common.listing.ListingState;
import games.mythical.saga.sdk.proto.streams.Subscribe;
import games.mythical.saga.sdk.proto.streams.listing.ListingStatusConfirmRequest;
import games.mythical.saga.sdk.proto.streams.listing.ListingStatusUpdate;
import games.mythical.saga.sdk.proto.streams.listing.ListingStreamGrpc;
import games.mythical.saga.sdk.server.StreamingService;
import games.mythical.saga.sdk.util.ConcurrentFinisher;
import io.grpc.stub.StreamObserver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MockListingStreamingImpl extends ListingStreamGrpc.ListingStreamImplBase implements StreamingService {
    private final Map<String, StreamObserver<ListingStatusUpdate>> streamObservers = new ConcurrentHashMap<>();

    @Override
    public void listingStatusStream(Subscribe request, StreamObserver<ListingStatusUpdate> responseObserver) {
        streamObservers.putIfAbsent(request.getTitleId(), responseObserver);
    }

    @Override
    public void listingStatusConfirmation(ListingStatusConfirmRequest request, StreamObserver<Empty> responseObserver) {
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();

        ConcurrentFinisher.finish(request.getTraceId());
    }

    @Override
    public void sendStatus(String titleId, GeneratedMessageV3 genericProto, ProtocolMessageEnum genericState) {
        var proto = (ListingQuoteProto) genericProto;
        var state = (ListingState) genericState;
        if (streamObservers.containsKey(titleId)) {
            var observer = streamObservers.get(titleId);
            var listingStatus = ListingStatusUpdate.newBuilder()
                    .setOauthId(proto.getOauthId())
                    .setTraceId(proto.getTraceId())
                    .setQuoteId(proto.getQuoteId())
                    .setListingId(proto.getQuoteId())
                    .setTotal(proto.getTotal())
                    .setListingState(state)
                    .build();
            observer.onNext(listingStatus);
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
