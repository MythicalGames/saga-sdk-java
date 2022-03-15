package games.mythical.saga.sdk.server;

import com.google.protobuf.GeneratedMessageV3;
import io.grpc.BindableService;

public interface StreamingService extends BindableService {
    void sendStatus(String titleId, GeneratedMessageV3 genericStatusUpdateProto);

    void reset();
}
