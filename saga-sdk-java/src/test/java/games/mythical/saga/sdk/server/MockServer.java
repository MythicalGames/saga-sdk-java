package games.mythical.saga.sdk.server;

import games.mythical.saga.sdk.server.stream.MockStatusStreamingImpl;
import io.grpc.BindableService;

public class MockServer extends AbstractMockServer {
    public MockServer(BindableService streamingImpl) throws Exception {
        super(streamingImpl);
    }

    @Override
    public void stop() {
        var statusStream = getStatusStream();
        statusStream.reset();

        super.stop();
    }

    public MockStatusStreamingImpl getStatusStream() {
        for (var service : super.getServices()) {
            if (service instanceof MockStatusStreamingImpl) {
                return (MockStatusStreamingImpl) service;
            }
        }
        throw new RuntimeException("Couldn't find MockStatusStreamingImpl!");
    }
}
