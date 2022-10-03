package games.mythical.saga.sdk.config;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class SagaSdkConfig {
    @Builder.Default
    private final String authUrl = Constants.DEFAULT_AUTH_URL;
    private final String titleId;
    private final String titleSecret;
    @Builder.Default
    private final String host = Constants.DEFAULT_GATEWAY_HOST;
    @Builder.Default
    private final int port = Constants.DEFAULT_GATEWAY_PORT;
    @Builder.Default
    private final int keepAlive = Constants.DEFAULT_KEEPALIVE_IN_SECONDS;
    @Builder.Default
    private final int tokenRefresh = Constants.DEFAULT_TOKEN_REFRESH_INTERVAL_IN_SECONDS;
    @Builder.Default
    private final boolean plainText = false;
    @Builder.Default
    private final boolean authenticated = true;
    private final String streamId;
    @Builder.Default
    private final Instant streamReplaySince = null;
}
