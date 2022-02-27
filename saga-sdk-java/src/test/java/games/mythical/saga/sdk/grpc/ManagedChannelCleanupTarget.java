package games.mythical.saga.sdk.grpc;

import io.grpc.ManagedChannel;

import java.util.concurrent.TimeUnit;

public class ManagedChannelCleanupTarget implements CleanupTarget {
    private final ManagedChannel managedChannel;

    public ManagedChannelCleanupTarget(ManagedChannel managedChannel) {
        this.managedChannel = managedChannel;
    }

    @Override
    public void shutdown() {
        if(!managedChannel.isShutdown()) {
            managedChannel.shutdown();
        }
    }

    @Override
    public boolean awaitTermination(Long timeout, TimeUnit timeUnit) throws InterruptedException {
        return managedChannel.awaitTermination(timeout, timeUnit);
    }

    @Override
    public boolean isTerminated() {
        return managedChannel.isTerminated();
    }
}
