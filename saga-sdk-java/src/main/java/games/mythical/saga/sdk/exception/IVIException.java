package games.mythical.saga.sdk.exception;

import io.grpc.Metadata;
import io.grpc.Status.Code;
import io.grpc.StatusException;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.HttpURLConnection;

@Slf4j
public class IVIException extends Exception {
    public static final int UNPROCESSABLE_ENTITY = 422;

    private final IVIErrorCode code;
    public static final String HTTP_CODE_KEY = "HttpCode";

    public IVIException(IVIErrorCode code) {
        super();
        this.code = code;
    }

    public IVIException(String message, IVIErrorCode code) {
        super(message);
        this.code = code;
    }

    public IVIException(String message, Throwable cause, IVIErrorCode code) {
        super(message, cause);
        this.code = code;
    }

    public IVIException(Throwable cause, IVIErrorCode code) {
        super(cause);
        this.code = code;
    }

    public IVIException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, IVIErrorCode code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public IVIErrorCode getCode() {
        return code;
    }

    public static IVIException fromGrpcException(StatusException exception) {
        return fromGrpcExceptionMessaging(exception.getStatus().getCode(), exception.getTrailers(), exception.getMessage());
    }

    public static IVIException fromGrpcException(StatusRuntimeException exception) {
        return fromGrpcExceptionMessaging(exception.getStatus().getCode(), exception.getTrailers(), exception.getMessage());
    }

    private static IVIException fromGrpcExceptionMessaging(Code exception, Metadata metadata, String message) {
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
                return logError(message, IVIErrorCode.INVALID_ARGUMENT);
            case NOT_FOUND:
                return logError(message, IVIErrorCode.NOT_FOUND);
            case PERMISSION_DENIED:
                return logError(message, IVIErrorCode.PERMISSION_DENIED);
            case UNIMPLEMENTED:
                return logError(message, IVIErrorCode.UNIMPLEMENTED);
            case UNAUTHENTICATED:
                return logError(message, IVIErrorCode.UNAUTHENTICATED);
            case UNAVAILABLE:
                return logError(message, IVIErrorCode.UNAVAILABLE);
            case RESOURCE_EXHAUSTED:
                return logError(message, IVIErrorCode.RESOURCE_EXHAUSTED);
            case ABORTED:
                return logError(message, IVIErrorCode.ABORTED);
            case DEADLINE_EXCEEDED:
            case FAILED_PRECONDITION:
            case OUT_OF_RANGE:
                return logError(message, IVIErrorCode.BAD_REQUEST);
            case ALREADY_EXISTS:
                return logError(message, IVIErrorCode.CONFLICT);
            case DATA_LOSS:
            case INTERNAL:
            case UNKNOWN:
                return logError(message, IVIErrorCode.SERVER_ERROR);
            default:
                return logError(message, IVIErrorCode.UNKNOWN_GRPC_ERROR);
        }
    }

    private static IVIException logError(String message, IVIErrorCode code) {
        log.error("gRPC error from IVI server: code: {} message: {}", code, message);
        return new IVIException(message, code);
    }
    private static IVIErrorCode fromStatusCode(int statusCode) {
        switch (statusCode) {
            case HttpURLConnection.HTTP_BAD_REQUEST:
                return IVIErrorCode.BAD_REQUEST;
            case HttpURLConnection.HTTP_UNAUTHORIZED:
                return IVIErrorCode.NOT_AUTHORIZED;
            case HttpURLConnection.HTTP_FORBIDDEN:
                return IVIErrorCode.FORBIDDEN;
            case HttpURLConnection.HTTP_NOT_FOUND:
                return IVIErrorCode.NOT_FOUND;
            case HttpURLConnection.HTTP_CONFLICT:
                return IVIErrorCode.CONFLICT;
            case HttpURLConnection.HTTP_CLIENT_TIMEOUT:
                return IVIErrorCode.TIMEOUT;
            case UNPROCESSABLE_ENTITY:
                return IVIErrorCode.UNPROCESSABLE_ENTITY;
            default:
                return IVIErrorCode.SERVER_ERROR;
        }
    }
}
