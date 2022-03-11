package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.*;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.exception.SagaErrorCode;
import games.mythical.saga.sdk.exception.SagaException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import static games.mythical.saga.sdk.config.Constants.*;

@Slf4j
public class SagaClientFactory {
    private final SagaSdkConfig config;
    private static SagaClientFactory instance;

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

    public SagaItemClient createSagaItemClient(SagaItemExecutor executor) throws SagaException {
        return new SagaItemClient(config, executor);
    }

    public SagaItemTypeClient createSagaItemTypeClient(SagaItemTypeExecutor executor) throws SagaException {
        return new SagaItemTypeClient(config, executor);
    }

    public SagaMythTokenClient createSagaMythTokenClient(SagaMythTokenExecutor executor) throws SagaException {
        return new SagaMythTokenClient(config, executor);
    }

    public SagaPaymentClient createSagaPaymentClient() throws SagaException {
        return new SagaPaymentClient(config);
    }

    public SagaUserClient createSagaUserClient(SagaUserExecutor executor) throws SagaException {
        return new SagaUserClient(config, executor);
    }

    public SagaGameCoinClient createSagaGameCoinClient(SagaGameCoinExecutor executor) throws SagaException {
        return new SagaGameCoinClient(config, executor);
    }

    public SagaTitleClient createSagaTitleClient() throws SagaException {
        return new SagaTitleClient(config);
    }

    public SagaTransactionClient createSagaTransactionClient() throws SagaException {
        return new SagaTransactionClient(config);
    }

    private SagaClientFactory(SagaSdkConfig config) throws SagaException {
        this.config = config;
        SagaCredentialsFactory.initialize(config);
    }

    private static void validateConfig(SagaSdkConfig config) throws SagaException {
        if (StringUtils.isBlank(config.getTitleId())) {
            throw new SagaException("Title Id not set!", SagaErrorCode.TITLE_ID_NOT_SET);
        }

        if (StringUtils.isBlank(config.getTitleSecret())) {
            throw new SagaException("Title Secret not set!", SagaErrorCode.TITLE_SECRET_NOT_SET);
        }

        if (StringUtils.isBlank(config.getAuthUrl())) {
            throw new SagaException("Authorization Url not set!", SagaErrorCode.AUTH_URL_NOT_SET);
        }

        if (StringUtils.isBlank(config.getHost())) {
            throw new SagaException("Host not set!", SagaErrorCode.HOST_NOT_SET);
        }

        if (config.getPort() < MIN_PORT || config.getPort() > MAX_PORT) {
            throw new SagaException("Invalid port number: " + config.getPort(), SagaErrorCode.INVALID_PORT);
        }

        if (config.getKeepAlive() < MIN_KEEP_ALIVE) {
            throw new SagaException("Invalid keep alive value: " + config.getKeepAlive(), SagaErrorCode.INVALID_KEEP_ALIVE);
        }

        if (config.getTokenRefresh() < MIN_TOKEN_REFRESH) {
            throw new SagaException("Invalid token refresh value: " + config.getTokenRefresh(), SagaErrorCode.INVALID_TOKEN_REFRESH);
        }

        if (config.isPlainText() && !config.getHost().equals("localhost")) {
            throw new SagaException("Plain text connection can only be used for localhost", SagaErrorCode.NON_LOCAL_PLAIN_TEXT);
        }
    }
}
