package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.MockPlayerWalletExecutor;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.api.playerwallet.PlayerWalletProto;
import games.mythical.saga.sdk.proto.api.playerwallet.PlayerWalletServiceGrpc;
import games.mythical.saga.sdk.proto.common.ReceivedResponse;
import games.mythical.saga.sdk.proto.streams.StatusUpdate;
import games.mythical.saga.sdk.proto.streams.playerwallet.PlayerWalletStatusUpdate;
import games.mythical.saga.sdk.proto.streams.playerwallet.PlayerWalletUpdate;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SagaPlayerWalletTest extends AbstractClientTest {
    private static final String OAUTH_ID = RandomStringUtils.randomAlphanumeric(30);

    private final MockPlayerWalletExecutor executor = new MockPlayerWalletExecutor();
    private MockServer playerWalletServer;
    private SagaPlayerWalletClient client;

    @Mock
    private PlayerWalletServiceGrpc.PlayerWalletServiceBlockingStub mockServiceBlockingStub;

    @BeforeEach
    void setUp() throws Exception {
        playerWalletServer = new MockServer(new MockStatusStreamingImpl());
        playerWalletServer.start();
        port = playerWalletServer.getPort();

        client = setUpFactory().createSagaPlayerWalletClient(executor);
        FieldUtils.writeField(client, "serviceBlockingStub", mockServiceBlockingStub, true);
    }

    @AfterEach
    void tearDown() throws Exception {
        client.stop();
        Thread.sleep(500);
        playerWalletServer.stop();
    }

    @Test
    public void createPlayerWallet() throws Exception {
        final var expectedResponse = ReceivedResponse.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .build();
        when(mockServiceBlockingStub.createPlayerWallet(any())).thenReturn(expectedResponse);

        final var traceId = client.createPlayerWallet(OAUTH_ID);

        checkTraceAndStart(expectedResponse, traceId);

        final var update = PlayerWalletStatusUpdate.newBuilder()
                .setOauthId(OAUTH_ID)
                .build();

        playerWalletServer.getStatusStream().sendStatus(titleId, StatusUpdate.newBuilder()
                .setTraceId(traceId)
                .setPlayerWalletUpdate(PlayerWalletUpdate.newBuilder().setStatusUpdate(update).build())
                .build());

        ConcurrentFinisher.wait(traceId);

        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertEquals(OAUTH_ID, executor.getOauthId());

        playerWalletServer.verifyCalls("StatusStream", 1);
    }

    @Test
    public void getPlayerWallet() throws Exception {
        var expectedResponse = PlayerWalletProto.newBuilder()
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .setOauthId(OAUTH_ID)
                .setAddress("address")
                .setBalanceInWei("1000")
                .build();
        when(mockServiceBlockingStub.getPlayerWallet(any())).thenReturn(expectedResponse);
        var playerWalletResponse = client.getPlayerWallet(OAUTH_ID);

        assertNotNull(playerWalletResponse);
        assertEquals(OAUTH_ID, playerWalletResponse.getOauthId());
        assertEquals(expectedResponse.getAddress(), playerWalletResponse.getAddress());
        assertEquals(expectedResponse.getBalanceInWei(), playerWalletResponse.getBalanceInWei());

        when(mockServiceBlockingStub.getPlayerWallet(any())).thenThrow(new StatusRuntimeException(Status.NOT_FOUND));
        assertThrows(SagaException.class, () -> client.getPlayerWallet("INVALID-OAUTH-ID"));
    }
}
