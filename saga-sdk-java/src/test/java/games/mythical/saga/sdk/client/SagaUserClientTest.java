package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.MockUserExecutor;
import games.mythical.saga.sdk.client.model.SagaUser;
import games.mythical.saga.sdk.server.user.MockUserServer;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SagaUserClientTest extends AbstractClientTest {
    private MockUserServer userServer;
    private MockUserExecutor executor;
    private SagaUserClient userClient;
    private Map<String, SagaUser> users;

    @BeforeEach
    void setUp() throws Exception {
        userServer = new MockUserServer();
        userServer.start();
        port = userServer.getPort();
        setUpConfig();

        users = generateUsers(3);
        userServer.getUserService().setUsers(users.values());

        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();

        executor = MockUserExecutor.builder().build();
        userClient = new SagaUserClient(executor, channel);
    }

    @AfterEach
    void tearDown() {
        userServer.stop();
    }

    @Test
    public void getUser() throws Exception {
        var oauthId = users.keySet().iterator().next();

        var userResponse = userClient.getUser(oauthId);

        assertTrue(userResponse.isPresent());
        var user = userResponse.get();
        assertEquals(oauthId, user.getOauthId());

        oauthId = "INVALID-OAUTH-ID";
        userResponse = userClient.getUser(oauthId);

        assertTrue(userResponse.isEmpty());

        userServer.verifyCalls("GetUser", 2);
    }
}