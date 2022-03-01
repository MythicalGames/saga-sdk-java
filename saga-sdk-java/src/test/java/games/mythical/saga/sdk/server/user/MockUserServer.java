package games.mythical.saga.sdk.server.user;

import games.mythical.saga.sdk.server.AbstractMockServer;

// TODO: there needs to be a mockserverfactory, also is this level of network test really needed?
public class MockUserServer extends AbstractMockServer {
    public MockUserServer() throws Exception {
        super(new MockUserStreamingImpl());
    }

    @Override
    public void stop() {
        var userStream = getUserStream();
        userStream.reset();

        super.stop();
    }

    public MockUserStreamingImpl getUserStream() {
        for (var service : super.getServices()) {
            if (service instanceof MockUserStreamingImpl) {
                return (MockUserStreamingImpl) service;
            }
        }
        throw new RuntimeException("Couldn't find MockUserStreamingImpl!");
    }
}
