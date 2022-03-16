package games.mythical.saga.sdk.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Struct;
import com.google.protobuf.util.JsonFormat;
import games.mythical.saga.sdk.exception.SagaErrorCode;
import games.mythical.saga.sdk.exception.SagaException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.util.Map;

@Slf4j
public class ConversionUtils {
    public static Struct convertProperties(Map<String, Object> properties) throws SagaException {
        try {
            var metadata = (JsonObject) new Gson().toJsonTree(properties);
            var structBuilder = Struct.newBuilder();
            JsonFormat.parser().merge(metadata.toString(), structBuilder);
            return structBuilder.build();
        } catch (InvalidProtocolBufferException e) {
            log.error("ConversionUtils: couldn't convert properties map!", e);
            throw new SagaException(SagaErrorCode.PARSING_DATA_EXCEPTION);
        }
    }

    public static Map<String, Object> convertProperties(Struct properties) throws SagaException {
        try {
            var objectMapper = new ObjectMapper();
            var propertiesString = JsonFormat.printer().print(properties);
            return objectMapper.readValue(propertiesString, new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("ConversionUtils: couldn't convert properties struct!", e);
            throw new SagaException(SagaErrorCode.PARSING_DATA_EXCEPTION);
        }
    }

    public static Map<String, Object> toStringObjectMap(String stringObjectJson) {
        Gson gson = new Gson();
        Type mapType = new TypeToken<Map<String, Object>>(){}.getType();
        return gson.fromJson(stringObjectJson, mapType);
    }
}
