package games.mythical.saga.sdk.grpc;

import java.util.concurrent.TimeUnit;

public interface CleanupTarget {
    void shutdown();
    boolean awaitTermination(Long timeout, TimeUnit timeUnit) throws InterruptedException;
    boolean isTerminated();
}
