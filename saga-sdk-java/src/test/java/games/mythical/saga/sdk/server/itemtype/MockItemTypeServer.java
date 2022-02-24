package games.mythical.saga.sdk.server.itemtype;

import games.mythical.saga.sdk.server.AbstractMockServer;

public class MockItemTypeServer extends AbstractMockServer {
    public MockItemTypeServer() throws Exception {
        super(new MockItemTypeServiceImpl(), new MockItemTypeStreamImpl());
    }

    @Override
    public void stop() {
        var stream = getItemTypeService();
        stream.reset();

        var service = getItemStream();
        service.reset();

        super.stop();
    }

    public MockItemTypeServiceImpl getItemTypeService() {
        for(var service : super.getServices()) {
            if(service instanceof MockItemTypeServiceImpl)  {
                return (MockItemTypeServiceImpl) service;
            }
        }
        throw new RuntimeException("Couldn't find MockItemTypeServiceImpl!");
    }

    public MockItemTypeStreamImpl getItemStream() {
        for(var service : super.getServices()) {
            if(service instanceof MockItemTypeStreamImpl)  {
                return (MockItemTypeStreamImpl) service;
            }
        }
        throw new RuntimeException("Couldn't find MockItemTypeStreamImpl!");
    }
}
