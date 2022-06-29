package games.mythical.saga.sdk.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Struct;
import com.google.protobuf.Timestamp;
import com.google.protobuf.util.JsonFormat;
import com.google.protobuf.util.Timestamps;
import games.mythical.saga.sdk.exception.SagaErrorCode;
import games.mythical.saga.sdk.exception.SagaException;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class ConversionUtils {
    private final static ObjectMapper objectMapper = new ObjectMapper()
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

    public static Struct convertProperties(Map<String, Object> properties) throws SagaException {
        try {
            final var metadataJson = objectMapper.writeValueAsString(properties);
            final var structBuilder = Struct.newBuilder();
            JsonFormat.parser().merge(metadataJson, structBuilder);
            return structBuilder.build();
        } catch (InvalidProtocolBufferException | JsonProcessingException e) {
            log.error("ConversionUtils: couldn't convert properties map!", e);
            throw new SagaException(SagaErrorCode.PARSING_DATA_EXCEPTION);
        }
    }

    public static Map<String, Object> convertProperties(Struct properties) throws SagaException {
        try {
            final var propertiesString = JsonFormat.printer().print(properties);
            return objectMapper.readValue(propertiesString, new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("ConversionUtils: couldn't convert properties struct!", e);
            throw new SagaException(SagaErrorCode.PARSING_DATA_EXCEPTION);
        }
    }

    public static Map<String, Object> toStringObjectMap(String stringObjectJson) throws SagaException {
        try {
            return objectMapper.readValue(stringObjectJson, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            log.error("ConversionUtils: couldn't convert JSON into String, Object map", e);
            throw new SagaException(SagaErrorCode.PARSING_DATA_EXCEPTION);
        }
    }

    public static Instant protoTimestampToInstant(Timestamp timestamp) {
        return Instant.ofEpochMilli(Timestamps.toMillis(timestamp));
    }

    public static Timestamp instantToProtoTimestamp(Instant instant) {
        return Timestamp.newBuilder()
            .setSeconds(instant.getEpochSecond())
            .setNanos(instant.getNano())
            .build();
    }
}
