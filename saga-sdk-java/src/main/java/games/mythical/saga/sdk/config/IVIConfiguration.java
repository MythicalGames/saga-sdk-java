package games.mythical.saga.sdk.config;

public class IVIConfiguration {
    private static String environmentId;
    private static String apiKey;
    private static String host = "sdk-api.iviengine.com";
    private static Integer port = 443;
    private static int keepAlive = 30;

    public static String getEnvironmentId() {
        return IVIConfiguration.environmentId;
    }

    public static void setEnvironmentId(String environmentId) {
        IVIConfiguration.environmentId = environmentId;
    }

    public static String getApiKey() {
        return IVIConfiguration.apiKey;
    }

    public static void setApiKey(String apiKey) {
        IVIConfiguration.apiKey = apiKey;
    }

    public static String getHost() {
        return IVIConfiguration.host;
    }

    public static void setHost(String host) {
        IVIConfiguration.host = host;
    }

    public static Integer getPort() {
        return IVIConfiguration.port;
    }

    public static void setPort(Integer port) {
        IVIConfiguration.port = port;
    }

    public static int getKeepAlive() {
        return keepAlive;
    }

    public static void setKeepAlive(int keepAlive) {
        IVIConfiguration.keepAlive = keepAlive;
    }
}
