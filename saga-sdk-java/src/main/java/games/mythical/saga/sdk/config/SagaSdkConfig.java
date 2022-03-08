package games.mythical.saga.sdk.config;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(builderClassName = "SagaSdkConfigBuilder")
public class SagaSdkConfig {
    private final String authUrl;
    private final String titleId;
    private final String titleSecret;
    private final String host;
    private final int port;
    private final int keepAlive;
    private final int tokenRefresh;
    private final boolean plainText;
    private final boolean authenticated;

    public static class SagaSdkConfigBuilder {
        private String authUrl = Constants.DEFAULT_AUTH_URL;
        private String host = Constants.DEFAULT_GATEWAY_HOST;
        private int port = Constants.DEFAULT_GATEWAY_PORT;
        private int keepAlive = Constants.DEFAULT_KEEPALIVE_IN_SECONDS;
        private int tokenRefresh = Constants.DEFAULT_TOKEN_REFRESH_INTERVAL_IN_SECONDS;
        private boolean plainText = false;
        private boolean authenticated = true;
    }
}
