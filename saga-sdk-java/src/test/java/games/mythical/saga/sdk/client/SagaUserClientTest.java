package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.MockUserExecutor;
import games.mythical.saga.sdk.proto.api.user.UserProto;
import games.mythical.saga.sdk.proto.api.user.UserServiceGrpc;
import games.mythical.saga.sdk.proto.common.user.UserState;
import games.mythical.saga.sdk.server.user.MockUserServer;
import games.mythical.saga.sdk.util.ConcurrentFinisher;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SagaUserClientTest extends AbstractClientTest {
    private static final String OAUTH_ID = UUID.randomUUID().toString();

    private final MockUserExecutor executor = MockUserExecutor.builder().build();
    private MockUserServer userServer;
    private SagaUserClient userClient;

    @Mock
    private UserServiceGrpc.UserServiceBlockingStub mockServiceBlockingStub;

    @BeforeEach
    void setUp() throws Exception {
        userServer = new MockUserServer();
        userServer.start();
        port = userServer.getPort();
        setUpConfig();

        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();

        userClient = new SagaUserClient(executor, channel);
        // mocking the service blocking stub clients are connected to
        FieldUtils.writeField(userClient, "serviceBlockingStub", mockServiceBlockingStub, true);
    }

    @AfterEach
    void tearDown() {
        userServer.stop();
    }

    @Test
    public void getUser() throws Exception {
        var expectedResponse = UserProto.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .setOauthId(OAUTH_ID)
                .setChainAddress(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(mockServiceBlockingStub.getUser(any())).thenReturn(expectedResponse);
        var userResponse = userClient.getUser(OAUTH_ID);

        assertTrue(userResponse.isPresent());
        var user = userResponse.get();
        assertEquals(expectedResponse.getTraceId(), user.getTraceId());
        assertEquals(OAUTH_ID, user.getOauthId());
        assertEquals(expectedResponse.getChainAddress(), user.getChainAddress());

        when(mockServiceBlockingStub.getUser(any())).thenThrow(new StatusRuntimeException(Status.NOT_FOUND));
        userResponse = userClient.getUser("INVALID-OAUTH-ID");

        assertTrue(userResponse.isEmpty());
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.MINUTES)
    public void updateUser() throws Exception {
        var expectedResponse = UserProto.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .setOauthId(OAUTH_ID)
                .setChainAddress(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(mockServiceBlockingStub.updateUser(any())).thenReturn(expectedResponse);
        var userResponse = userClient.updateUser(OAUTH_ID);

        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertEquals(expectedResponse.getOauthId(), executor.getOauthId());
        assertNotEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        // a short wait is needed so the status stream can be hooked up
        // before the emitting the event from the sendStatus method
        Thread.sleep(500);
        ConcurrentFinisher.start(executor.getTraceId());

        userServer.getUserStream().sendStatus(environmentId, UserProto.newBuilder()
                .setOauthId(executor.getOauthId())
                .setTraceId(executor.getTraceId())
                .build(), UserState.FAILED);

        ConcurrentFinisher.wait(executor.getTraceId());

        assertTrue(userResponse.isPresent());
        var user = userResponse.get();
        assertEquals(OAUTH_ID, user.getOauthId());
        assertEquals(expectedResponse.getTraceId(), user.getTraceId());
        assertEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        userServer.verifyCalls("UserStatusStream", 1);
        userServer.verifyCalls("UserStatusConfirmation", 1);
    }
}