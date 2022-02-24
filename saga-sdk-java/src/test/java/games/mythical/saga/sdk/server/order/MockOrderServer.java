package games.mythical.saga.sdk.server.order;

import games.mythical.saga.sdk.server.AbstractMockServer;

public class MockOrderServer extends AbstractMockServer {
    public MockOrderServer() throws Exception {
        super(new MockOrderServiceImpl(), new MockOrderStreamImpl());

        getOrderStream().setOrderService(getOrderService());
    }

    @Override
    public void stop() {
        getOrderService().reset();
        getOrderStream().reset();

        super.stop();
    }

    public MockOrderServiceImpl getOrderService() {
        for(var service : super.getServices()) {
            if(service instanceof MockOrderServiceImpl)  {
                return (MockOrderServiceImpl) service;
            }
        }
        throw new RuntimeException("Couldn't find MockOrderServiceImpl!");
    }

    public MockOrderStreamImpl getOrderStream() {
        for(var service : super.getServices()) {
            if(service instanceof MockOrderStreamImpl)  {
                return (MockOrderStreamImpl) service;
            }
        }
        throw new RuntimeException("Couldn't find MockOrderStreamImpl!");
    }
}
