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

        if (config.isPlainText() && !config.getHost().equals("localhost")) {
            throw new SagaException(SagaErrorCode.NON_LOCAL_PLAIN_TEXT,
                                    "Plain text connection can only be used for localhost");
        }
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

    public SagaPaymentClient createSagaPaymentClient(SagaPaymentExecutor executor) throws SagaException {
        return new SagaPaymentClient(config, executor);
    }

    public SagaUserClient createSagaUserClient(SagaUserExecutor executor) throws SagaException {
        return new SagaUserClient(config, executor);
    }

    public SagaCurrencyClient createSagaCurrencyClient(SagaCurrencyExecutor executor) throws SagaException {
        return new SagaCurrencyClient(config, executor);
    }

    public SagaCurrencyTypeClient createSagaCurrencyTypeClient() throws SagaException {
        return new SagaCurrencyTypeClient(config);
    }

    public SagaTitleClient createSagaTitleClient() throws SagaException {
        return new SagaTitleClient(config);
    }

    public SagaTransactionClient createSagaTransactionClient() throws SagaException {
        return new SagaTransactionClient(config);
    }

    public SagaOrderClient createSagaOrderClient(SagaOrderExecutor executor) throws SagaException {
        return new SagaOrderClient(config, executor);
    }

    public SagaBridgeClient createSagaBridgeClient(SagaBridgeExecutor executor) throws SagaException {
        return new SagaBridgeClient(config, executor);
    }

    public SagaListingClient createSagaListingClient(SagaListingExecutor executor) throws SagaException {
        return new SagaListingClient(config, executor);
    }

    public SagaOfferClient createSagaOfferClient(SagaOfferExecutor executor) throws SagaException {
        return new SagaOfferClient(config, executor);
    }

    public SagaPlayerWalletClient createSagaPlayerWalletClient(SagaPlayerWalletExecutor executor) throws SagaException {
        return new SagaPlayerWalletClient(config, executor);
    }
}
