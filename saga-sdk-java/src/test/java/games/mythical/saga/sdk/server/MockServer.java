package games.mythical.saga.sdk.server;

import games.mythical.saga.sdk.server.stream.*;
import io.grpc.BindableService;

public class MockServer extends AbstractMockServer {
    public MockServer(BindableService streamingImpl) throws Exception {
        super(streamingImpl);
    }

    @Override
    public void stop() {
        super.getServices().forEach(stream -> {
            if (StreamingService.class.isAssignableFrom(stream.getClass())) {
                ((StreamingService) stream).reset();
            }
        });

        super.stop();
    }

    public StreamingService getItemStream() {
        for (var service : super.getServices()) {
            if (service instanceof MockItemStreamingImpl) {
                return (MockItemStreamingImpl) service;
            }
        }
        throw new RuntimeException("Couldn't find MockItemStreamingImpl!");
    }

    public StreamingService getItemTypeStream() {
        for (var service : super.getServices()) {
            if (service instanceof MockItemTypeStreamingImpl) {
                return (MockItemTypeStreamingImpl) service;
            }
        }
        throw new RuntimeException("Couldn't find MockItemTypeStreamingImpl!");
    }

    public MockMythTokenStreamingImpl getMythTokenStream() {
        for (var service : super.getServices()) {
            if (service instanceof MockMythTokenStreamingImpl) {
                return (MockMythTokenStreamingImpl) service;
            }
        }
        throw new RuntimeException("Couldn't find MockMythTokenStreamingImpl");
    }

    public MockUserStreamingImpl getUserStream() {
        for (var service : super.getServices()) {
            if (service instanceof MockUserStreamingImpl) {
                return (MockUserStreamingImpl) service;
            }
        }
        throw new RuntimeException("Couldn't find MockUserStreamingImpl!");
    }

    public MockGameCoinStreamingImpl getGameCoinStream() {
        for (var service : super.getServices()) {
            if (service instanceof MockGameCoinStreamingImpl) {
                return (MockGameCoinStreamingImpl) service;
            }
        }
        throw new RuntimeException("Couldn't find MockGameCoinStreamingImpl!");
    }

    public MockOrderStreamingImpl getOrderStream() {
        for (var service : super.getServices()) {
            if (service instanceof MockOrderStreamingImpl) {
                return (MockOrderStreamingImpl) service;
            }
        }
        throw new RuntimeException("Couldn't find MockOrderStreamingImpl!");
    }

    public MockBridgeStreamingImpl getBridgeStream() {
        for (var service : super.getServices()) {
            if (service instanceof MockBridgeStreamingImpl) {
                return (MockBridgeStreamingImpl) service;
            }
        }
        throw new RuntimeException("Couldn't find MockBridgeStreamingImpl!");
    }
}
