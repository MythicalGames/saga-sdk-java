package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.*;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.exception.SagaErrorCode;
import games.mythical.saga.sdk.exception.SagaException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static games.mythical.saga.sdk.config.Constants.*;

@Slf4j
public class SagaClientFactory {
    final static List<String> ALLOWED_UNENCRYPTED_HOSTS = List.of("localhost", "saga-gateway");
    private static SagaClientFactory instance;
    private final SagaSdkConfig config;

    private SagaClientFactory(SagaSdkConfig config) throws SagaException {
        this.config = config;
        SagaCredentialsFactory.initialize(config);
    }

    public static SagaClientFactory getInstance() throws SagaException {
        if (instance == null) {
            log.error("Tried to get uninitialized Client Factory instance");
            throw new SagaException(SagaErrorCode.FACTORY_NOT_INITIALIZED);
        }
        return instance;
    }

    public static SagaClientFactory initialize(SagaSdkConfig config) throws SagaException {
        if (config.isAuthenticated() && instance != null) {
            /*
             * If we are using authentication, re-initializing will mess up existing streams. If authentication is
             * not used, which is only the case when testing, then re-initializing shouldn't have any negative effects.
             */
            log.error("Tried to re-initialize Factory.");
            throw new SagaException(SagaErrorCode.REINITIALIZATION_ATTEMPTED);
        }
        validateConfig(config);
        instance = new SagaClientFactory(config);
        return instance;
    }

    private static void validateConfig(SagaSdkConfig config) throws SagaException {
        if (StringUtils.isBlank(config.getTitleId())) {
            throw new SagaException(SagaErrorCode.TITLE_ID_NOT_SET, "Title Id not set!");
        }

        if (StringUtils.isBlank(config.getTitleSecret())) {
            throw new SagaException(SagaErrorCode.TITLE_SECRET_NOT_SET, "Title Secret not set!");
        }

        if (StringUtils.isBlank(config.getAuthUrl())) {
            throw new SagaException(SagaErrorCode.AUTH_URL_NOT_SET, "Authorization Url not set!");
        }

        if (StringUtils.isBlank(config.getHost())) {
            throw new SagaException(SagaErrorCode.HOST_NOT_SET, "Host not set!");
        }

        if (config.getPort() < MIN_PORT || config.getPort() > MAX_PORT) {
            throw new SagaException(SagaErrorCode.INVALID_PORT,
                                    "Invalid port number: " + config.getPort());
        }

        if (config.getKeepAlive() < MIN_KEEP_ALIVE) {
            throw new SagaException(SagaErrorCode.INVALID_KEEP_ALIVE,
                                    "Invalid keep alive value: " + config.getKeepAlive());
        }

        if (config.getTokenRefresh() < MIN_TOKEN_REFRESH) {
            throw new SagaException(SagaErrorCode.INVALID_TOKEN_REFRESH,
                                    "Invalid token refresh value: " + config.getTokenRefresh());
        }

        if (config.isPlainText() && !ALLOWED_UNENCRYPTED_HOSTS.contains(config.getHost())) {
            throw new SagaException(SagaErrorCode.NON_LOCAL_PLAIN_TEXT,
                                    "Plain text connection can only be used for allowed hosts: " + ALLOWED_UNENCRYPTED_HOSTS);
        }
    }

    public SagaMetadataClient createSagaMetadataClient() throws SagaException {
        return new SagaMetadataClient(config);
    }
    public SagaMetadataClient createSagaMetadataClient(SagaMetadataExecutor executor) throws SagaException {
        return new SagaMetadataClient(config, executor);
    }

    public SagaItemClient createSagaItemClient() throws SagaException {
        return new SagaItemClient(config);
    }
    public SagaItemClient createSagaItemClient(SagaItemExecutor executor) throws SagaException {
        return new SagaItemClient(config, executor);
    }

    public SagaItemTypeClient createSagaItemTypeClient() throws SagaException {
        return new SagaItemTypeClient(config);
    }
    public SagaItemTypeClient createSagaItemTypeClient(SagaItemTypeExecutor executor) throws SagaException {
        return new SagaItemTypeClient(config, executor);
    }

    public SagaCurrencyClient createSagaCurrencyClient() throws SagaException {
        return new SagaCurrencyClient(config);
    }
    public SagaCurrencyClient createSagaCurrencyClient(SagaCurrencyExecutor executor) throws SagaException {
        return new SagaCurrencyClient(config, executor);
    }

    public SagaCurrencyTypeClient createSagaCurrencyTypeClient(SagaCurrencyTypeExecutor executor) throws SagaException {
        return new SagaCurrencyTypeClient(config, executor);
    }

    public SagaPlayerWalletClient createSagaPlayerWalletClient() throws SagaException {
        return new SagaPlayerWalletClient(config);
    }

    public SagaPlayerWalletClient createSagaPlayerWalletClient(SagaPlayerWalletExecutor executor) throws SagaException {
        return new SagaPlayerWalletClient(config, executor);
    }

    public SagaReservationClient createSagaReservationClient() throws SagaException {
        return new SagaReservationClient(config);
    }

    public SagaReservationClient createSagaReservationClient(SagaReservationExecutor executor) throws SagaException {
        return new SagaReservationClient(config, executor);
    }
}
