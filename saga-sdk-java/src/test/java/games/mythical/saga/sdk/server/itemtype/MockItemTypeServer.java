package games.mythical.saga.sdk.server.itemtype;

import games.mythical.saga.sdk.server.AbstractMockServer;

public class MockItemTypeServer extends AbstractMockServer {
    public MockItemTypeServer() throws Exception {
        super(new MockItemTypeStreamingImpl());
    }

    @Override
    public void stop() {
        var stream = getItemTypeStream();
        stream.reset();

        super.stop();
    }

    public MockItemTypeStreamingImpl getItemTypeStream() {
        for (var service : super.getServices()) {
            if (service instanceof MockItemTypeStreamingImpl) {
                return (MockItemTypeStreamingImpl) service;
            }
        }
        throw new RuntimeException("Couldn't find MockItemTypeStreamingImpl!");
    }
}
