package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.MockPlayerWalletExecutor;
import games.mythical.saga.sdk.proto.api.playerwallet.PlayerWalletServiceGrpc;
import games.mythical.saga.sdk.proto.common.ReceivedResponse;
import games.mythical.saga.sdk.proto.common.playerwallet.PlayerWalletState;
import games.mythical.saga.sdk.proto.streams.StatusUpdate;
import games.mythical.saga.sdk.proto.streams.playerwallet.PlayerWalletStatusUpdate;
import games.mythical.saga.sdk.proto.streams.playerwallet.PlayerWalletUpdate;
import games.mythical.saga.sdk.server.MockServer;
import games.mythical.saga.sdk.server.stream.MockStatusStreamingImpl;
import games.mythical.saga.sdk.util.ConcurrentFinisher;
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

        final var oauthId = RandomStringUtils.randomAlphanumeric(30);
        final var address = RandomStringUtils.randomAlphanumeric(30);

        final var traceId = client.createPlayerWallet(oauthId);

        checkTraceAndStart(expectedResponse, traceId);

        final var update = PlayerWalletStatusUpdate.newBuilder()
                .setOauthId(oauthId)
                .setAddress(address)
                .setState(PlayerWalletState.CREATED)
                .build();

        playerWalletServer.getStatusStream().sendStatus(titleId, StatusUpdate.newBuilder()
                .setTraceId(traceId)
                .setPlayerWalletUpdate(PlayerWalletUpdate.newBuilder().setStatusUpdate(update).build())
                .build());

        ConcurrentFinisher.wait(traceId);

        assertEquals(expectedResponse.getTraceId(), executor.getTraceId());
        assertEquals(oauthId, executor.getOauthId());
        assertEquals(address, executor.getAddress());
        assertEquals(PlayerWalletState.CREATED, executor.getPlayerWalletState());

        playerWalletServer.verifyCalls("StatusStream", 1);
        playerWalletServer.verifyCalls("StatusConfirmation", 1);
    }
}
