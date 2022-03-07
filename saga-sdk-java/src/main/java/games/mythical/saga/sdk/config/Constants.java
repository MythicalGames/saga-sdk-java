package games.mythical.saga.sdk.config;

public class Constants {
  // Authentication
  public final static String DEFAULT_AUTH_URL = "https://auth-internal.mythicalgames.com/oauth2/token";
  public final static String AUTH_TYPE = "Bearer";

  // HTTP
  public final static String USER_AGENT_TYPE = "Java 11 HttpClient Bot";
  public final static String WWW_FORM_URL_ENCODED = "application/x-www-form-urlencoded";

  // SDK Gateway
  public final static String DEFAULT_GATEWAY_HOST = "sdk-api.sagaengine.com";
  public final static int DEFAULT_GATEWAY_PORT = 443;
  public final static int DEFAULT_KEEPALIVE_IN_SECONDS = 30;
  public final static int DEFAULT_TOKEN_REFRESH_INTERVAL_IN_SECONDS = 25;

  // Validation
  public final static int MIN_PORT = 1;
  public final static int MAX_PORT = 65535;
  public final static int MIN_KEEP_ALIVE = 0;
  public final static int MIN_TOKEN_REFRESH = 5;
}
