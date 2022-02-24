package games.mythical.saga.sdk.server;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public abstract class AbstractMockServer {
    private final List<BindableService> services;
    private final Server server;
    private final MockInterceptor mockInterceptor;

    public AbstractMockServer(BindableService... services) throws Exception {
        this.services = Arrays.asList(services);
        this.mockInterceptor = new MockInterceptor();
        var serverBuilder = ServerBuilder.forPort(findPort());
        for (var service : this.services) {
            serverBuilder.addService(service);
        }
        this.server = serverBuilder.intercept(this.mockInterceptor).build();
    }

    public void start() throws IOException {
        server.start();
        log.info("Started {} on port {}...", this.getClass().getName(), server.getPort());
    }

    public void stop() {
        log.info("Stopping {}...", this.getClass().getName());
        server.shutdown();
        mockInterceptor.reset();
    }

    public void verifyCalls(String methodName, int count) {
        assertEquals(count, mockInterceptor.count(methodName));
    }

    protected List<BindableService> getServices() {
        return services;
    }

    public int getPort() {
        return server.getPort();
    }

    private int findPort() throws IOException {
        try (var socket = new ServerSocket(0)) {
            socket.setReuseAddress(true);
            return socket.getLocalPort();
        } catch (IOException e) {
            log.error("Couldn't find available port", e);
            throw e;
        }
    }
}
