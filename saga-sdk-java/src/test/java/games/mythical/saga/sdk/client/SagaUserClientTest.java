package games.mythical.saga.sdk.client;

import com.google.protobuf.Value;
import games.mythical.saga.sdk.client.executor.MockUserExecutor;
import games.mythical.saga.sdk.client.model.query.Filter;
import games.mythical.saga.sdk.client.model.query.FilterValue;
import games.mythical.saga.sdk.client.model.query.QueryOptions;
import games.mythical.saga.sdk.proto.api.user.UserProto;
import games.mythical.saga.sdk.proto.api.user.UserServiceGrpc;
import games.mythical.saga.sdk.proto.api.user.UsersProto;
import games.mythical.saga.sdk.proto.common.SortOrder;
import games.mythical.saga.sdk.proto.common.user.UserState;
import games.mythical.saga.sdk.server.MockServer;
import games.mythical.saga.sdk.server.stream.MockUserStreamingImpl;
import games.mythical.saga.sdk.util.ConcurrentFinisher;
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

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SagaUserClientTest extends AbstractClientTest {
    private static final String OAUTH_ID = UUID.randomUUID().toString();

    private final MockUserExecutor executor = MockUserExecutor.builder().build();
    private MockServer userServer;
    private SagaUserClient userClient;

    @Mock
    private UserServiceGrpc.UserServiceBlockingStub mockServiceBlockingStub;

    @BeforeEach
    void setUp() throws Exception {
        userServer = new MockServer(new MockUserStreamingImpl());
        userServer.start();
        port = userServer.getPort();

        userClient = setUpFactory().createSagaUserClient(executor);
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
    public void getUsers() throws Exception {
        Filter filter = new Filter();
        filter.equal("test", "test value")
                .and()
                .equal("test2", "other test value");

        var options = QueryOptions.builder()
                .filterOptions(filter)
                .pageSize(100)
                .sortOrder(SortOrder.ASC)
                .sortAttribute("test")
                .build();

        var expectedResponse = UsersProto.newBuilder()
                .addSagaUsers(UserProto.newBuilder()
                        .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                        .setOauthId(OAUTH_ID)
                        .setCreatedTimestamp(0)
                        .build())
                .build();
        when(mockServiceBlockingStub.getUsers(any())).thenReturn(expectedResponse);
        var usersResponse = userClient.getUsers(options);
        assertEquals(1, usersResponse.size());

        for (var user : usersResponse) {
            assertEquals(expectedResponse.getSagaUsers(0).getTraceId(), user.getTraceId());
            assertEquals(OAUTH_ID, user.getOauthId());
            assertEquals(expectedResponse.getSagaUsers(0).getChainAddress(), user.getChainAddress());
        }
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

        userServer.getUserStream().sendStatus(titleId, UserProto.newBuilder()
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