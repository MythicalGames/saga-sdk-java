package games.mythical.saga.sdk.client;

import com.google.common.net.HttpHeaders;
import games.mythical.saga.sdk.config.Constants;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.exception.SagaErrorCode;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.util.ConversionUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class SagaCredentialsFactory {
    private final static String ACCESS_TOKEN_KEY = "access_token";
    private final static int HTTP_OK = 200;
    private final URI authUrl;
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final HttpRequest.BodyPublisher bodyPublisher;
    private final AtomicReference<String> accessToken = new AtomicReference<>();

    private static SagaCredentialsFactory instance;

    static SagaCredentialsFactory getInstance() throws SagaException {
        if (instance == null) {
            log.error("Tried to get uninitialized Credentials Factory instance");
            throw new SagaException(SagaErrorCode.CREDENTIALS_NOT_INITIALIZED);
        }
        return instance;
    }

    static void initialize(SagaSdkConfig config) throws SagaException {
        if (config.isAuthenticated() && instance != null) {
            /*
             * If we are using authentication, re-initializing will mess up existing streams. If authentication is
             * not used, which is only the case when testing, then re-initializing shouldn't have any negative effects.
             */
            log.error("Tried to re-initialize Credentials.");
            throw new SagaException(SagaErrorCode.REINITIALIZATION_ATTEMPTED);
        }
        instance = new SagaCredentialsFactory(config);
    }

    private SagaCredentialsFactory(SagaSdkConfig config) {
        this.authUrl = URI.create(config.getAuthUrl());
        this.bodyPublisher = buildClientCredentialsRequestBody(config.getTitleId(), config.getTitleSecret());
        if (config.isAuthenticated()) {
            final var token = getAccessToken();
            // TODO: Set refresh interval based on token result.
            accessToken.set(token);
            final var executor = new ScheduledThreadPoolExecutor(1);
            executor.scheduleAtFixedRate(
                    new RefreshToken(), config.getTokenRefresh(), config.getTokenRefresh(), TimeUnit.SECONDS);
        } else {
            accessToken.set("");
        }
    }

    String getToken() {
        return accessToken.get();
    }

    private String getAccessToken() {
        final var request = HttpRequest.newBuilder(authUrl)
                .POST(bodyPublisher)
                .setHeader(HttpHeaders.USER_AGENT, Constants.USER_AGENT_TYPE)
                .setHeader(HttpHeaders.CONTENT_TYPE, Constants.WWW_FORM_URL_ENCODED)
                .build();
        try {
            final var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == HTTP_OK) {
                log.debug("Refreshing Access Token");
                final var map = ConversionUtils.toStringObjectMap(response.body());
                return (String) map.get(ACCESS_TOKEN_KEY);
            }
        } catch (IOException | InterruptedException e) {
            log.error("Error while trying to retrieve client credentials.", e);
        }
        return "";
    }

    // TODO: Retry
    private class RefreshToken implements Runnable {
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
