package games.mythical.saga.sdk.config;

// TODO: change me
public class SagaConfiguration {
    private static String environmentId;
    private static String apiKey;
    private static String host = "sdk-api.iviengine.com";
    private static Integer port = 443;
    private static int keepAlive = 30;

    public static String getEnvironmentId() {
        return SagaConfiguration.environmentId;
    }

    public static void setEnvironmentId(String environmentId) {
        SagaConfiguration.environmentId = environmentId;
    }

    public static String getApiKey() {
        return SagaConfiguration.apiKey;
    }

    public static void setApiKey(String apiKey) {
        SagaConfiguration.apiKey = apiKey;
    }

    public static String getHost() {
        return SagaConfiguration.host;
    }

    public static void setHost(String host) {
        SagaConfiguration.host = host;
    }

    public static Integer getPort() {
        return SagaConfiguration.port;
    }

    public static void setPort(Integer port) {
        SagaConfiguration.port = port;
    }

    public static int getKeepAlive() {
        return keepAlive;
    }

    public static void setKeepAlive(int keepAlive) {
        SagaConfiguration.keepAlive = keepAlive;
    }
}
