package games.mythical.saga.sdk.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class ConcurrentFinisher {
    public static final Map<String, AtomicBoolean> finishedTracker = new ConcurrentHashMap<>();

    public static void start(String trackingId) {
        var finished = new AtomicBoolean();
        finished.set(false);
        finishedTracker.put(trackingId, finished);
    }

    public static void finish(String trackingId) {
        if (finishedTracker.containsKey(trackingId)) {
            finishedTracker.get(trackingId).set(true);
        }
    }

    public static void wait(String trackingId) throws Exception {
        if (finishedTracker.containsKey(trackingId)) {
            while (!finishedTracker.get(trackingId).compareAndSet(true, false)) {
                Thread.sleep(10);
            }
        }
    }

    public static void reset() {
        finishedTracker.clear();
    }
}
