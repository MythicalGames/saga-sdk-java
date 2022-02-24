package games.mythical.saga.sdk.server.item;

import games.mythical.saga.sdk.server.AbstractMockServer;

public class MockItemServer extends AbstractMockServer {
    public MockItemServer() throws Exception {
        super(new MockItemServiceImpl(), new MockItemStreamingImpl());
    }

    @Override
    public void stop() {
        var itemService = getItemService();
        itemService.reset();

        var itemStream = getItemStream();
        itemStream.reset();

        super.stop();
    }

    public MockItemServiceImpl getItemService() {
        for (var service : super.getServices()) {
            if (service instanceof MockItemServiceImpl) {
                return (MockItemServiceImpl) service;
            }
        }
        throw new RuntimeException("Couldn't find MockItemServiceImpl!");
    }

    public MockItemStreamingImpl getItemStream() {
        for (var service : super.getServices()) {
            if (service instanceof MockItemStreamingImpl) {
                return (MockItemStreamingImpl) service;
            }
        }
        throw new RuntimeException("Couldn't find MockItemStreamingImpl!");
    }
}
