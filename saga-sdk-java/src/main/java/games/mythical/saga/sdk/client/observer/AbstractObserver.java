package games.mythical.saga.sdk.client.observer;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;

import java.util.concurrent.TimeUnit;

@Slf4j
public abstract class AbstractObserver<V> implements StreamObserver<V> {
    // retry settings
    private boolean retry = false;
    private int requestCount = 1;
    private final int maxCount = 16;

    protected void sleepBetweenReconnects() {
        if (!retry) {
            retry = true;
            return;
        }

        var sleepTimeMillis = (int) Math.pow(2, requestCount) + RandomUtils.nextInt(1, 1000);
        try {
            log.trace("Sleeping {} milliseconds before reconnect", sleepTimeMillis);
            TimeUnit.MILLISECONDS.sleep(sleepTimeMillis);

            if (requestCount < maxCount) {
                requestCount++;
            }
        } catch (InterruptedException e) {
            log.error("Retry interrupted, exiting...");
            Thread.currentThread().interrupt();
        }
    }

    protected void resetConnectionRetry() {
        retry = false;
        requestCount = 1;
    }
}
