package games.mythical.saga.sdk.util;

import games.mythical.saga.sdk.exception.SagaErrorCode;
import games.mythical.saga.sdk.exception.SagaException;
import org.apache.commons.lang3.StringUtils;

public class Validate {
    public static void notBlank(String value, String msg) throws SagaException {
        if (StringUtils.isBlank(value)) {
            throw new SagaException(msg, SagaErrorCode.INVALID_ARGUMENT);
        }
    }
}
