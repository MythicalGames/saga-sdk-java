package games.mythical.saga.sdk.server.myth;

import games.mythical.saga.sdk.server.AbstractMockServer;

public class MockMythTokenServer extends AbstractMockServer {
    public MockMythTokenServer() throws Exception {
        super(new MockMythTokenStreamingImpl());
    }

    @Override
    public void stop() {
        var mythTokenStream = getMythTokenStream();
        mythTokenStream.reset();
        super.stop();
    }

    public MockMythTokenStreamingImpl getMythTokenStream() {
        for (var service : super.getServices()) {
            if (service instanceof MockMythTokenStreamingImpl) {
                return (MockMythTokenStreamingImpl) service;
            }
        }
        throw new RuntimeException("Couldn't find MockMythTokenStreamingImpl");
    }
}
