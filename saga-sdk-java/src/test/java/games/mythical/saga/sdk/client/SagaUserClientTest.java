package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.MockUserExecutor;
import games.mythical.saga.sdk.client.model.SagaUser;
import games.mythical.saga.sdk.proto.api.user.UserProto;
import games.mythical.saga.sdk.proto.api.user.UserServiceGrpc;
import games.mythical.saga.sdk.server.user.MockUserServer;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SagaUserClientTest extends AbstractClientTest {
    private final MockUserExecutor executor = MockUserExecutor.builder().build();
    private MockUserServer userServer;
    private SagaUserClient userClient;
    private Map<String, SagaUser> users;

    private ManagedChannel channel;

    @Mock
    private UserServiceGrpc.UserServiceBlockingStub mockGrpcBlockingStub;

    @BeforeEach
    void setUp() throws Exception {
        userServer = new MockUserServer();
        userServer.start();
        setUpConfig();

        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();

        userClient = new SagaUserClient(executor, channel);
        // mocking the service blocking stub clients are connected to
        FieldUtils.writeField(userClient, "serviceBlockingStub", mockGrpcBlockingStub, true);

        users = generateUsers(3);
    }

    @AfterEach
    void tearDown() {
        userServer.stop();
        channel.shutdown();
    }

    @Test
    public void getUser() throws Exception {
        var oauthId = users.keySet().iterator().next();
        var expectedResponse = UserProto.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .setOauthId(oauthId)
                .setChainAddress(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(mockGrpcBlockingStub.getUser(any())).thenReturn(expectedResponse);
        var userResponse = userClient.getUser(oauthId);

        assertTrue(userResponse.isPresent());
        var user = userResponse.get();
        assertEquals(expectedResponse.getTraceId(), user.getTraceId());
        assertEquals(oauthId, user.getOauthId());
        assertEquals(expectedResponse.getChainAddress(), user.getChainAddress());

        when(mockGrpcBlockingStub.getUser(any())).thenThrow(new StatusRuntimeException(Status.NOT_FOUND));
        userResponse = userClient.getUser("INVALID-OAUTH-ID");

        assertTrue(userResponse.isEmpty());
    }

    @Test
    public void updateUser() throws Exception {
        var oauthId = users.keySet().iterator().next();

        var expectedResponse = UserProto.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .setOauthId(oauthId)
                .setChainAddress(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(mockGrpcBlockingStub.updateUser(any())).thenReturn(expectedResponse);
        var userResponse = userClient.updateUser(oauthId);

        assertTrue(userResponse.isPresent());
        var user = userResponse.get();
        assertEquals(oauthId, user.getOauthId());

        userServer.verifyCalls("UpdateUser", 1);
    }
}