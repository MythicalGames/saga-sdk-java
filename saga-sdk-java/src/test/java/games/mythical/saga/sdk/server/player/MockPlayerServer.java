package games.mythical.saga.sdk.server.player;

import games.mythical.saga.sdk.server.AbstractMockServer;

public class MockPlayerServer extends AbstractMockServer {
    public MockPlayerServer() throws Exception {
        super(new MockPlayerServiceImpl(), new MockPlayerStreamImpl());
    }

    @Override
    public void stop() {
        var stream = getPlayerStream();
        stream.reset();

        var service = getPlayerService();
        service.reset();

        super.stop();
    }

    public MockPlayerServiceImpl getPlayerService() {
        for(var service : super.getServices()) {
            if(service instanceof MockPlayerServiceImpl)  {
                return (MockPlayerServiceImpl) service;
            }
        }
        throw new RuntimeException("Couldn't find MockPlayerServiceImpl!");
    }

    public MockPlayerStreamImpl getPlayerStream() {
        for(var service : super.getServices()) {
            if(service instanceof MockPlayerStreamImpl)  {
                return (MockPlayerStreamImpl) service;
            }
        }
        throw new RuntimeException("Couldn't find MockPlayerStreamImpl!");
    }
}
