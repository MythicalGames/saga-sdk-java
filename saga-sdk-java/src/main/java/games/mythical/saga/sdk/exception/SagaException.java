package games.mythical.saga.sdk.exception;

import io.grpc.Metadata;
import io.grpc.Status.Code;
import io.grpc.StatusException;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.HttpURLConnection;

@Slf4j
public class SagaException extends Exception {
    public static final int UNPROCESSABLE_ENTITY = 422;
    public static final String HTTP_CODE_KEY = "HttpCode";
    private final SagaErrorCode code;

    public SagaException(SagaErrorCode code) {
        super();
        this.code = code;
    }

    public SagaException(String message, SagaErrorCode code) {
        super(message);
        this.code = code;
    }

    public SagaException(String message, Throwable cause, SagaErrorCode code) {
        super(message, cause);
        this.code = code;
    }

    public SagaException(Throwable cause, SagaErrorCode code) {
        super(cause);
        this.code = code;
    }

    public SagaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, SagaErrorCode code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public static SagaException fromGrpcException(StatusException exception) {
        return fromGrpcExceptionMessaging(exception.getStatus().getCode(), exception.getTrailers(), exception.getMessage());
    }

    public static SagaException fromGrpcException(StatusRuntimeException exception) {
        return fromGrpcExceptionMessaging(exception.getStatus().getCode(), exception.getTrailers(), exception.getMessage());
    }

    private static SagaException fromGrpcExceptionMessaging(Code exception, Metadata metadata, String message) {
        // GRPC Status doesn't handle all http codes, so check if one was added
        if (metadata != null) {
            var httpCodeString = metadata.get(Metadata.Key.of(HTTP_CODE_KEY, Metadata.ASCII_STRING_MARSHALLER));
            if (StringUtils.isNotBlank(httpCodeString)) {
                var iviErrorCode = fromStatusCode(Integer.parseInt(httpCodeString));
                return logError(message, iviErrorCode);
            }
        }

        switch (exception) {
            case INVALID_ARGUMENT:
                return logError(message, SagaErrorCode.INVALID_ARGUMENT);
            case NOT_FOUND:
                return logError(message, SagaErrorCode.NOT_FOUND);
            case PERMISSION_DENIED:
                return logError(message, SagaErrorCode.PERMISSION_DENIED);
            case UNIMPLEMENTED:
                return logError(message, SagaErrorCode.UNIMPLEMENTED);
            case UNAUTHENTICATED:
                return logError(message, SagaErrorCode.UNAUTHENTICATED);
            case UNAVAILABLE:
                return logError(message, SagaErrorCode.UNAVAILABLE);
            case RESOURCE_EXHAUSTED:
                return logError(message, SagaErrorCode.RESOURCE_EXHAUSTED);
            case ABORTED:
                return logError(message, SagaErrorCode.ABORTED);
            case DEADLINE_EXCEEDED:
            case FAILED_PRECONDITION:
            case OUT_OF_RANGE:
                return logError(message, SagaErrorCode.BAD_REQUEST);
            case ALREADY_EXISTS:
                return logError(message, SagaErrorCode.CONFLICT);
            case DATA_LOSS:
            case INTERNAL:
            case UNKNOWN:
                return logError(message, SagaErrorCode.SERVER_ERROR);
            default:
                return logError(message, SagaErrorCode.UNKNOWN_GRPC_ERROR);
        }
    }

    private static SagaException logError(String message, SagaErrorCode code) {
        log.error("gRPC error from IVI server: code: {} message: {}", code, message);
        return new SagaException(message, code);
    }

    private static SagaErrorCode fromStatusCode(int statusCode) {
        switch (statusCode) {
            case HttpURLConnection.HTTP_BAD_REQUEST:
                return SagaErrorCode.BAD_REQUEST;
            case HttpURLConnection.HTTP_UNAUTHORIZED:
                return SagaErrorCode.NOT_AUTHORIZED;
            case HttpURLConnection.HTTP_FORBIDDEN:
                return SagaErrorCode.FORBIDDEN;
            case HttpURLConnection.HTTP_NOT_FOUND:
                return SagaErrorCode.NOT_FOUND;
            case HttpURLConnection.HTTP_CONFLICT:
                return SagaErrorCode.CONFLICT;
            case HttpURLConnection.HTTP_CLIENT_TIMEOUT:
                return SagaErrorCode.TIMEOUT;
            case UNPROCESSABLE_ENTITY:
                return SagaErrorCode.UNPROCESSABLE_ENTITY;
            default:
                return SagaErrorCode.SERVER_ERROR;
        }
    }

    public SagaErrorCode getCode() {
        return code;
    }
}
