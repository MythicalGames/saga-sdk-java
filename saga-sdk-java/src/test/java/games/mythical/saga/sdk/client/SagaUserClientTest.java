package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.MockUserExecutor;
import games.mythical.saga.sdk.client.model.query.Filter;
import games.mythical.saga.sdk.client.model.query.QueryOptions;
import games.mythical.saga.sdk.proto.api.user.*;
import games.mythical.saga.sdk.proto.common.SortOrder;
import games.mythical.saga.sdk.proto.common.user.UserState;
import games.mythical.saga.sdk.proto.streams.StatusUpdate;
import games.mythical.saga.sdk.proto.streams.user.UserStatusUpdate;
import games.mythical.saga.sdk.proto.streams.user.UserUpdate;
import games.mythical.saga.sdk.server.MockServer;
import games.mythical.saga.sdk.server.stream.MockStatusStreamingImpl;
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
        userServer = new MockServer(new MockStatusStreamingImpl());
        userServer.start();
        port = userServer.getPort();

        userClient = setUpFactory().createSagaUserClient(executor);
        // mocking the service blocking stub clients are connected to
        FieldUtils.writeField(userClient, "serviceBlockingStub", mockServiceBlockingStub, true);
    }

    @AfterEach
    void tearDown() throws Exception {
        userClient.stop();
        // client shutdown is not immediate
        Thread.sleep(500);
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
                        .setCreatedAt(0)
                        .build())
                .build();
        when(mockServiceBlockingStub.getUsers(any())).thenReturn(expectedResponse);
        var usersResponse = userClient.getUsers(options);
        assertEquals(1, usersResponse.size());

        var user = usersResponse.iterator().next();
        assertEquals(expectedResponse.getSagaUsers(0).getTraceId(), user.getTraceId());
        assertEquals(OAUTH_ID, user.getOauthId());
        assertEquals(expectedResponse.getSagaUsers(0).getChainAddress(), user.getChainAddress());

    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.MINUTES)
    public void createUser() throws Exception {
        final var expectedResponse = genReceived();
        when(mockServiceBlockingStub.createUser(any())).thenReturn(expectedResponse);
        final var trace = userClient.createUser(OAUTH_ID);
        checkTraceAndStart(expectedResponse, trace);

        final var userStatusUpdate = UserStatusUpdate.newBuilder()
                .setOauthId(OAUTH_ID)
                .setUserState(UserState.LINKED);
        final var userUpdate = UserUpdate.newBuilder()
                .setStatusUpdate(userStatusUpdate);
        final var statusUpdate = StatusUpdate.newBuilder()
                .setTraceId(trace)
                .setUserUpdate(userUpdate)
                .build();
        userServer.getStatusStream().sendStatus(titleId, statusUpdate);

        ConcurrentFinisher.wait(trace);

        assertEquals(OAUTH_ID, executor.getOauthId());
        assertEquals(Boolean.TRUE, ConcurrentFinisher.get(executor.getTraceId()));

        userServer.verifyCalls("StatusStream", 1);
        userServer.verifyCalls("StatusConfirmation", 1);
    }

    @Test
    public void getWalletAssets() throws Exception {
        var expectedResponse = WalletAsset.newBuilder()
                .setAusdToken("AUSD")
                .setAusdTokenQuantity("100")
                .setNmythToken("MYTH")
                .setNmythTokenQuantity("200")
                .addNftItems(NftItem.newBuilder()
                        .setContractAddress("player1")
                        .build())
                .addFungibleTokens(FungibleToken.newBuilder()
                        .setContractAddress("player1")
                        .build())
                .build();
        when(mockServiceBlockingStub.getWalletAssets((any()))).thenReturn(expectedResponse);
        var response = userClient.getWalletAssets(OAUTH_ID, "1234", "asdf");

        assertTrue(response.isPresent());
        var walletAsset = response.get();
        compareObjectsByReflection(expectedResponse, walletAsset, null);
    }
}