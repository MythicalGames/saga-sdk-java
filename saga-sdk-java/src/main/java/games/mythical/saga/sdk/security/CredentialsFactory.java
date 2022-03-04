package games.mythical.saga.sdk.security;

import games.mythical.shared.util.JsonUtil;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CredentialsFactory {
  private final static String ACCESS_TOKEN_KEY = "access_token";
  private final static int HTTP_OK = 200;
  private final URI authUrl;
  private final HttpClient httpClient = HttpClient.newHttpClient();
  private final HttpRequest.BodyPublisher bodyPublisher;
  private final AtomicReference<String> accessToken = new AtomicReference<>();
  private final ScheduledExecutorService executor;
  private final int intervalSecs = 20;

  private final static CredentialsFactory instance = new CredentialsFactory();

  public static CredentialsFactory getInstance() {
    return instance;
  }

  private CredentialsFactory() {
    this.authUrl = URI.create("https://auth-internal.mythicalgames.com/oauth2/token");
    this.bodyPublisher = buildClientCredentialsRequestBody(
        "abdc6899-4b50-4ed6-8a3e-5c4984c7de22", "i2qu_PPm4go66GId1BI0C2pRpr2MToqMS-V_Zqnh4qs");
    final var token = getAccessToken();
    // TODO: Set refresh interval based on token result.
    accessToken.set(token);
    executor = new ScheduledThreadPoolExecutor(1);
    executor.scheduleAtFixedRate(new RefreshToken(), intervalSecs, intervalSecs, TimeUnit.SECONDS);
  }

  public String getToken() {
    return accessToken.get();
  }

  public String getAccessToken() {
    final var request = HttpRequest.newBuilder(authUrl)
        .POST(bodyPublisher)
        .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
        .header("Content-Type", "application/x-www-form-urlencoded")
        .build();
    try {
      final var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
      if (response.statusCode() == HTTP_OK) {
        log.debug("Refreshing Access Token");
        final var map = JsonUtil.toStringObjectMap(response.body());
        return (String) map.get(ACCESS_TOKEN_KEY);
      }
    } catch (IOException | InterruptedException e) {
      log.error("Error while trying to retrieve client credentials.", e);
    }
    return null;
  }

  // TODO: Retry
  class RefreshToken implements Runnable {
    @Override
    public void run() {
      final var token = getAccessToken();
      accessToken.set(token);
    }
  }

  private static HttpRequest.BodyPublisher buildClientCredentialsRequestBody(
      String clientId,
      String clientSecret) {
    return buildFormDataFromMap(Map.of(
        "grant_type", "client_credentials",
        "client_id", clientId,
        "client_secret", clientSecret
    ));
  }

  private static HttpRequest.BodyPublisher buildFormDataFromMap(Map<String, String> data) {
    final var builder = new StringBuilder();
    data.forEach((key, value) -> {
      if (builder.length() > 0) {
        builder.append("&");
      }
      builder.append(URLEncoder.encode(key, StandardCharsets.UTF_8));
      builder.append("=");
      builder.append(URLEncoder.encode(value, StandardCharsets.UTF_8));
    });
    return HttpRequest.BodyPublishers.ofString(builder.toString());
  }
}
