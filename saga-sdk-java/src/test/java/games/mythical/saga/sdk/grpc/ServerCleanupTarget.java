package games.mythical.saga.sdk.grpc;

import io.grpc.Server;

import java.util.concurrent.TimeUnit;

public class ServerCleanupTarget implements CleanupTarget {
    private final Server server;

    public ServerCleanupTarget(Server server) {
        this.server = server;
    }

    @Override
    public void shutdown() {
        if(!server.isShutdown()) {
            server.shutdown();
        }
    }

    @Override
    public boolean awaitTermination(Long timeout, TimeUnit timeUnit) throws InterruptedException {
        return server.awaitTermination(timeout, timeUnit);
    }

    @Override
    public boolean isTerminated() {
        return server.isTerminated();
    }
}
