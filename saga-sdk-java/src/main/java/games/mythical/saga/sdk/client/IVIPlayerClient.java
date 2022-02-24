package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.IVIPlayerExecutor;
import games.mythical.saga.sdk.client.observer.IVIPlayerObserver;
import games.mythical.saga.sdk.exception.IVIErrorCode;
import games.mythical.saga.sdk.exception.IVIException;
import games.mythical.ivi.sdk.proto.api.player.*;
import games.mythical.ivi.sdk.proto.common.SortOrder;
import games.mythical.ivi.sdk.proto.streams.Subscribe;
import games.mythical.ivi.sdk.proto.streams.player.PlayerStreamGrpc;
import io.grpc.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
public class IVIPlayerClient extends AbstractIVIClient {
    private PlayerServiceGrpc.PlayerServiceBlockingStub serviceBlockingStub;
    private final IVIPlayerExecutor playerExecutor;

    @SuppressWarnings("unused")
    public IVIPlayerClient(IVIPlayerExecutor playerExecutor) throws IVIException {
        super();

        this.playerExecutor = playerExecutor;
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .keepAliveTime(keepAlive, TimeUnit.SECONDS)
                .build();

        initStub();
    }

    IVIPlayerClient(IVIPlayerExecutor playerExecutor, ManagedChannel channel) throws IVIException {
        this.playerExecutor = playerExecutor;
        this.channel = channel;
        initStub();
    }

    @Override
    protected void initStub() {
        serviceBlockingStub = PlayerServiceGrpc.newBlockingStub(channel).withCallCredentials(addAuthentication());
        var streamBlockingStub = PlayerStreamGrpc.newBlockingStub(channel)
                .withCallCredentials(addAuthentication());
        subscribeToStream(new IVIPlayerObserver(playerExecutor, streamBlockingStub, this::subscribeToStream));
    }

    void subscribeToStream(IVIPlayerObserver observer) {
        // set up server stream
        var streamStub  = PlayerStreamGrpc.newStub(channel).withCallCredentials(addAuthentication());
        var subscribe = Subscribe.newBuilder()
                .setEnvironmentId(environmentId)
                .build();
        streamStub.playerStatusStream(subscribe, observer);
    }

    public void linkPlayer(String playerId, String email, String displayName, String requestIp) throws IVIException {
        log.trace("PlayerClient.linkPlayer called from player: {}:{}:{}", playerId, email, displayName);
        try {
            var requestBuilder = LinkPlayerRequest.newBuilder()
                    .setEnvironmentId(environmentId)
                    .setPlayerId(playerId)
                    .setEmail(email)
                    .setDisplayName(displayName);

            if(StringUtils.isNotBlank(requestIp)) {
                requestBuilder.setRequestIp(requestIp);
            }

            var result = serviceBlockingStub.linkPlayer(requestBuilder.build());

            playerExecutor.updatePlayer(playerId, result.getTrackingId(), result.getPlayerState());
        } catch (StatusRuntimeException e) {
            throw IVIException.fromGrpcException(e);
        } catch (StatusException e) {
            throw IVIException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling updatePlayerState on linkPlayer, player will be in an invalid state!", e);
            throw new IVIException(IVIErrorCode.LOCAL_EXCEPTION);
        }
    }

    public Optional<IVIPlayer> getPlayer(String playerId) throws IVIException {
        log.trace("PlayerClient.getPlayer called from player: {}", playerId);
        try {
            var result = serviceBlockingStub.getPlayer(GetPlayerRequest.newBuilder()
                    .setEnvironmentId(environmentId)
                    .setPlayerId(playerId)
                    .build());
            return Optional.of(result);
        } catch (StatusRuntimeException e) {
            if (e.getStatus() == Status.NOT_FOUND) {
                return Optional.empty();
            }

            throw IVIException.fromGrpcException(e);
        }
    }

    public List<IVIPlayer> getPlayers(Instant createdTimestamp, int pageSize, SortOrder sortOrder) throws IVIException {
        log.trace("PlayerClient.getPlayers called with params: createdTimestamp {}, pageSize {}, sortOrder {}",
                createdTimestamp, pageSize, sortOrder);
        try {
            var request = GetPlayersRequest.newBuilder()
                    .setEnvironmentId(environmentId)
                    .setCreatedTimestamp(createdTimestamp == null ? -1 : createdTimestamp.getEpochSecond())
                    .setPageSize(pageSize)
                    .setSortOrder(sortOrder)
                    .build();

            var result = serviceBlockingStub.getPlayers(request);
            return result.getIviPlayersList();
        } catch (StatusRuntimeException e) {
            log.error("gRPC error from IVI server", e);
            throw IVIException.fromGrpcException(e);
        }
    }
}
