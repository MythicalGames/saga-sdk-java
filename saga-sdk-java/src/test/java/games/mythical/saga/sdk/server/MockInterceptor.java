package games.mythical.saga.sdk.server;

import io.grpc.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class MockInterceptor implements ServerInterceptor {
    private final Map<String, Integer> tracker = new ConcurrentHashMap<>();

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call,
                                                                 Metadata headers,
                                                                 ServerCallHandler<ReqT, RespT> next) {

        var methodName = call.getMethodDescriptor().getBareMethodName();
        tracker.merge(methodName, 1, Integer::sum);
        log.info("Method {} incremented", methodName);
        return next.startCall(call, headers);
    }

    public int count(String methodName) {
        return tracker.getOrDefault(methodName, 0);
    }

    public void reset() {
        tracker.clear();
    }
}
