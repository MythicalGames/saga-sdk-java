package games.mythical.saga.sdk.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class ConcurrentFinisher {
    public static final Map<String, AtomicBoolean> finishedTracker = new ConcurrentHashMap<>();

    public static Boolean get(String traceId) {
        if (finishedTracker.containsKey(traceId)) {
            return finishedTracker.get(traceId).get();
        }

        return null;
    }

    public static void start(String traceId) {
        var finished = new AtomicBoolean(false);
        finishedTracker.putIfAbsent(traceId, finished);
    }

    public static void finish(String traceId) {
        if (finishedTracker.containsKey(traceId)) {
            finishedTracker.get(traceId).set(true);
        }
    }

    // wait until the finish method is called and boolean flips from false to true
    public static void wait(String traceId) throws Exception {
        if (finishedTracker.containsKey(traceId)) {
            while (!finishedTracker.get(traceId).compareAndSet(true, true)) {
                Thread.sleep(10);
            }
        }
    }

    public static void reset() {
        finishedTracker.clear();
    }
}
