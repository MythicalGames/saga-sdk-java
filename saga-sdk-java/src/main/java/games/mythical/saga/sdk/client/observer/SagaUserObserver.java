package games.mythical.saga.sdk.client.observer;

import games.mythical.saga.sdk.client.executor.SagaUserExecutor;
import games.mythical.saga.sdk.proto.common.user.UserState;
import games.mythical.saga.sdk.proto.streams.user.UserStatusConfirmRequest;
import games.mythical.saga.sdk.proto.streams.user.UserStatusUpdate;
import games.mythical.saga.sdk.proto.streams.user.UserStreamGrpc;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
public class SagaUserObserver extends AbstractObserver<UserStatusUpdate> {
    private final SagaUserExecutor sagaUserExecutor;
    private final UserStreamGrpc.UserStreamBlockingStub streamBlockingStub;
    private final Consumer<SagaUserObserver> resubscribe;

    public SagaUserObserver(SagaUserExecutor sagaUserExecutor,
                            UserStreamGrpc.UserStreamBlockingStub streamBlockingStub,
                            Consumer<SagaUserObserver> resubscribe) {
        this.sagaUserExecutor = sagaUserExecutor;
        this.streamBlockingStub = streamBlockingStub;
        this.resubscribe = resubscribe;
    }

    @Override
    public void onNext(UserStatusUpdate message) {
        log.trace("UserObserver.onNext for user: {}", message.getOauthId());
        resetConnectionRetry();
        try {
            sagaUserExecutor.updateUser(message.getOauthId());
            updateUserConfirmation(message.getOauthId(), message.getTrackingId(), message.getUserState());
        } catch (Exception e) {
            log.error("Exception calling updateUser for {}. {}", message.getOauthId(), e);
        }
    }

    @Override
    public void onError(Throwable t) {
        log.error("UserObserver.onError", t);
        sleepBetweenReconnects();
        resubscribe.accept(this);
    }

    @Override
    public void onCompleted() {
        log.info("UserObserver stream closed");
        sleepBetweenReconnects();
        resubscribe.accept(this);
    }

    private void updateUserConfirmation(String oauthId, String trackingId, UserState userState) {
        var request = UserStatusConfirmRequest.newBuilder()
                .setOauthId(oauthId)
                .setTrackingId(trackingId)
                .setUserState(userState)
                .build();
        streamBlockingStub.userStatusConfirmation(request);
    }
}
