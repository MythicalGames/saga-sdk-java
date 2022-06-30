package games.mythical.saga.sdk.util;

import games.mythical.saga.sdk.exception.SagaErrorCode;
import games.mythical.saga.sdk.exception.SagaException;
import org.apache.commons.lang3.StringUtils;

public class ValidateUtil {
    public static void notBlank(String value, String msg) throws SagaException {
        if (StringUtils.isBlank(value)) {
            throw new SagaException(SagaErrorCode.INVALID_ARGUMENT, msg);
        }
    }

    public static void checkFound(Object objToCheck, String msg, Object... args) throws SagaException {
        checkNotNull(objToCheck, SagaErrorCode.UNABLE_TO_CREATE_QUOTE, msg, args);
    }

    public static void checkQuote(Object objToCheck, String msg, Object... args) throws SagaException {
        checkNotNull(objToCheck, SagaErrorCode.UNABLE_TO_CREATE_QUOTE, msg, args);
    }

    public static void checkNotNull(Object objToCheck, SagaErrorCode errorCode, String msg, Object... args)
        throws SagaException {
        if (objToCheck == null) {
            throw new SagaException(errorCode, String.format(msg, args));
        }
    }
}
