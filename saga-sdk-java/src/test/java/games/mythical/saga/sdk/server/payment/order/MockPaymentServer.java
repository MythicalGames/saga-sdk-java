package games.mythical.saga.sdk.server.payment.order;

import games.mythical.saga.sdk.server.AbstractMockServer;

public class MockPaymentServer extends AbstractMockServer {
    public MockPaymentServer() throws Exception {
        super(new MockPaymentServiceImpl());
    }

    @Override
    public void stop() {
        getPaymentService().reset();

        super.stop();
    }

    public MockPaymentServiceImpl getPaymentService() {
        for(var service : super.getServices()) {
            if(service instanceof MockPaymentServiceImpl)  {
                return (MockPaymentServiceImpl) service;
            }
        }
        throw new RuntimeException("Couldn't find MockPaymentServiceImpl!");
    }

}
