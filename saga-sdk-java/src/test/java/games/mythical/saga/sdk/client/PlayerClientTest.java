package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.MockPlayerExecutor;
import games.mythical.ivi.sdk.proto.api.player.IVIPlayer;
import games.mythical.ivi.sdk.proto.common.SortOrder;
import games.mythical.ivi.sdk.proto.common.player.PlayerState;
import games.mythical.saga.sdk.server.player.MockPlayerServer;
import games.mythical.saga.sdk.util.ConcurrentFinisher;
import io.grpc.ManagedChannelBuilder;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PlayerClientTest extends AbstractClientTest {
    private MockPlayerServer playerServer;
    private MockPlayerExecutor playerExecutor;
    private IVIPlayerClient playerClient;
    private Map<String, IVIPlayer> iviPlayers;

    @BeforeEach
    void setUp() throws Exception {
        playerServer = new MockPlayerServer();
        playerServer.start();
        port = playerServer.getPort();
        setUpConfig();

        iviPlayers = generateIVIPlayers(3);
        playerServer.getPlayerService().setPlayers(iviPlayers.values());

        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();

        playerExecutor = MockPlayerExecutor.builder().build();
        playerClient = new IVIPlayerClient(playerExecutor, channel);
    }

    @AfterEach
    void tearDown() {
        playerServer.stop();
        ConcurrentFinisher.reset();
    }

    @Test
    void linkPlayer() throws Exception {
        var playerId = UUID.randomUUID().toString();
        var email = "user@game.com";
        var displayName = RandomStringUtils.randomAlphanumeric(20, 50);

        playerExecutor.setPlayerId(playerId);

        playerClient.linkPlayer(playerId, email, displayName, "127.0.0.1");

        assertEquals(playerId, playerExecutor.getPlayerId());
        assertFalse(StringUtils.isEmpty(playerExecutor.getTrackingId()));
        assertEquals(PlayerState.PENDING_LINKED, playerExecutor.getPlayerState());

        playerServer.verifyCalls("LinkPlayer", 1);

        ConcurrentFinisher.start(playerExecutor.getTrackingId());
        playerServer.getPlayerStream().sendStatus(environmentId, IVIPlayer.newBuilder()
                .setPlayerId(playerId)
                .setTrackingId(playerExecutor.getTrackingId())
                .build(), PlayerState.LINKED);

        ConcurrentFinisher.wait(playerExecutor.getTrackingId());

        assertEquals(playerId, playerExecutor.getPlayerId());
        assertFalse(StringUtils.isEmpty(playerExecutor.getTrackingId()));
        assertEquals(PlayerState.LINKED, playerExecutor.getPlayerState());

        playerServer.verifyCalls("PlayerStatusStream", 1);
        playerServer.verifyCalls("PlayerStatusConfirmation", 1);
    }

    @Test
    void getPlayer() throws Exception {
        var mockPlayer = iviPlayers.entrySet().iterator().next().getValue();
        var player = playerClient.getPlayer(mockPlayer.getPlayerId());

        assertTrue(player.isPresent());
        assertEquals(mockPlayer.getPlayerId(), player.get().getPlayerId());
        assertEquals(mockPlayer.getEmail(), player.get().getEmail());
        assertEquals(mockPlayer.getDisplayName(), player.get().getDisplayName());
        assertEquals(mockPlayer.getSidechainAccountName(), player.get().getSidechainAccountName());
        assertEquals(mockPlayer.getTrackingId(), player.get().getTrackingId());
        assertEquals(mockPlayer.getPlayerState(), player.get().getPlayerState());

        playerServer.verifyCalls("GetPlayer", 1);
    }

    @Test
    void getPlayerNonExisting() throws Exception {
        var player = playerClient.getPlayer(RandomStringUtils.randomAlphanumeric(30));
        assertTrue(player.isEmpty());
        playerServer.verifyCalls("GetPlayer", 1);
    }

    @Test
    void getPlayers() throws Exception {
        var players = playerClient.getPlayers(null, 30, SortOrder.ASC);

        for(var player : players) {
            assertEquals(iviPlayers.get(player.getPlayerId()).getPlayerId(), player.getPlayerId());
            assertEquals(iviPlayers.get(player.getPlayerId()).getEmail(), player.getEmail());
            assertEquals(iviPlayers.get(player.getPlayerId()).getDisplayName(), player.getDisplayName());
            assertEquals(iviPlayers.get(player.getPlayerId()).getSidechainAccountName(), player.getSidechainAccountName());
            assertEquals(iviPlayers.get(player.getPlayerId()).getTrackingId(), player.getTrackingId());
            assertEquals(iviPlayers.get(player.getPlayerId()).getPlayerState(), player.getPlayerState());
        }

        playerServer.verifyCalls("GetPlayers", 1);
    }
}