package games.mythical.saga.sdk.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.Status.Code;
import io.grpc.Metadata;
import io.grpc.StatusException;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class SagaException extends Exception {
    private final static ObjectMapper objMapper = new ObjectMapper();
    private final ErrorData errorData;

    public SagaException(ErrorData errorData) {
        super(errorData.getMessage());
        this.errorData = errorData;
    }

    public SagaException(SagaErrorCode errorCode) {
        this(errorCode, errorCode.toString());
    }

    public SagaException(SagaErrorCode errorCode, String message) {
        super(message);
        this.errorData = ErrorData.builder()
            .code(errorCode.toString())
            .message(message)
            .build();
    }

    public ErrorData getErrorData() {
        return errorData;
    }

    public static SagaException fromGrpcException(StatusException ex) {
        return fromGrpcException(ex.getStatus().getCode(), ex.getTrailers(), ex.getMessage());
    }

    public static SagaException fromGrpcException(StatusRuntimeException ex) {
        return fromGrpcException(ex.getStatus().getCode(), ex.getTrailers(), ex.getMessage());
    }

    private static SagaException fromGrpcException(Code code, Metadata trailers, String message) {
        if (trailers != null) {
            final var dataKey = Metadata.Key.of("ERROR_DATA", Metadata.ASCII_STRING_MARSHALLER);
            final var errDataJson = trailers.get(dataKey);
            if (StringUtils.isNotBlank(errDataJson)) {
                try {
                    final var errData = objMapper.readValue(errDataJson, ErrorData.class);
                    return new SagaException(errData);
                } catch (Exception parsingException) {
                    log.error("Saga SDK Error parsing error data: {}", errDataJson);
                }
            }
            return new SagaException(SagaErrorCode.INTERNAL);
        }
        return new SagaException(toSagaErrorCode(code), message);
    }

    private static SagaErrorCode toSagaErrorCode(Code grpcCode) {
        switch (grpcCode) {
            case INVALID_ARGUMENT:
                return SagaErrorCode.INVALID_ARGUMENT;
            case NOT_FOUND:
                return SagaErrorCode.NOT_FOUND;
            case PERMISSION_DENIED:
                return SagaErrorCode.PERMISSION_DENIED;
            case UNIMPLEMENTED:
                return SagaErrorCode.UNIMPLEMENTED;
            case UNAUTHENTICATED:
                return SagaErrorCode.UNAUTHENTICATED;
            case UNAVAILABLE:
                return SagaErrorCode.UNAVAILABLE;
            case RESOURCE_EXHAUSTED:
                return SagaErrorCode.RESOURCE_EXHAUSTED;
            case ABORTED:
                return SagaErrorCode.ABORTED;
            case DEADLINE_EXCEEDED:
            case FAILED_PRECONDITION:
            case OUT_OF_RANGE:
                return SagaErrorCode.BAD_REQUEST;
            case ALREADY_EXISTS:
                return SagaErrorCode.CONFLICT;
            case DATA_LOSS:
            case INTERNAL:
            case UNKNOWN:
                return SagaErrorCode.SERVER_ERROR;
            default:
                return SagaErrorCode.UNKNOWN_GRPC_ERROR;
        }
    }

}
