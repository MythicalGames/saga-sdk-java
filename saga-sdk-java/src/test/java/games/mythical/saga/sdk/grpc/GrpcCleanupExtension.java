package games.mythical.saga.sdk.grpc;

import io.grpc.BindableService;
import io.grpc.ManagedChannel;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkNotNull;

// from: https://www.stackhawk.com/blog/grpc-cleanup-extension-for-junit-5/
@Slf4j
public class GrpcCleanupExtension implements AfterEachCallback {
    private static final long TERMINATION_TIMEOUT_MS = 250L;
    private static final int MAX_NUM_TERMINATIONS = 100;

    private final List<CleanupTarget> cleanupTargets = new ArrayList<>();

    public ManagedChannel addService(BindableService service) throws IOException {
        checkNotNull(service, "service");

        var serverName = InProcessServerBuilder.generateName();

        cleanupTargets.add(new ServerCleanupTarget(InProcessServerBuilder
                        .forName(serverName)
                        .directExecutor()
                        .addService(service)
                        .build()
                        .start()
                )
        );

        var channel = InProcessChannelBuilder.forName(serverName)
                .directExecutor()
                .build();

        cleanupTargets.add(new ManagedChannelCleanupTarget(channel));

        return channel;
    }

    public boolean isAllTerminated() {
        return cleanupTargets.stream().allMatch(CleanupTarget::isTerminated);
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        for(var cleanupTarget: cleanupTargets) {
            try {
                var count = 0;
                cleanupTarget.shutdown();
                do {
                    cleanupTarget.awaitTermination(TERMINATION_TIMEOUT_MS, TimeUnit.MILLISECONDS);
                    count++;
                    if(count > MAX_NUM_TERMINATIONS) {
                        log.error("Hit max count {} trying to shut down down cleanupTarget {}", count, cleanupTarget);
                        break;
                    }
                } while (!cleanupTarget.isTerminated());
            } catch (Exception ex) {
                log.error("Problem shutting down cleanupTarget {}", cleanupTarget, ex);
            }
        }

        if(isAllTerminated()) {
            cleanupTargets.clear();
        } else {
            log.error("Not all cleanupTargets are terminated");
        }
    }
}
