package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.model.SagaCurrencyType;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.exception.SagaErrorCode;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.api.currencytype.CreateCurrencyTypeRequest;
import games.mythical.saga.sdk.proto.api.currencytype.CurrencyTypeServiceGrpc;
import games.mythical.saga.sdk.proto.api.currencytype.GetCurrencyTypeRequest;
import games.mythical.saga.sdk.proto.api.currencytype.GetCurrencyTypesRequest;
import games.mythical.saga.sdk.proto.common.SortOrder;
import games.mythical.saga.sdk.util.ValidateUtil;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class SagaCurrencyTypeClient extends AbstractSagaStreamClient {

    private CurrencyTypeServiceGrpc.CurrencyTypeServiceBlockingStub serviceBlockingStub;

    public SagaCurrencyTypeClient(SagaSdkConfig config) throws SagaException {
        super(config);
        initStub();
    }

    @Override
    void initStub() {
        serviceBlockingStub = CurrencyTypeServiceGrpc.newBlockingStub(channel).withCallCredentials(addAuthentication());
    }

    public SagaCurrencyType getCurrencyType(String currencyTypeId) throws SagaException {
        var request = GetCurrencyTypeRequest.newBuilder()
                .setCurrencyTypeId(currencyTypeId)
                .build();

        try {
            var currencyType = serviceBlockingStub.getCurrencyType(request);
            ValidateUtil.checkFound(currencyType,
                                    String.format("Currency type %s not found",
                                                  request.getCurrencyTypeId()));
            return SagaCurrencyType.fromProto(currencyType);
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        }
    }

    public List<SagaCurrencyType> getCurrencyTypes(int pageSize, SortOrder sortOrder) throws SagaException {
        var request = GetCurrencyTypesRequest.newBuilder()
                .setPageSize(pageSize)
                .setSortOrder(sortOrder)
                .build();

        try {
            var currencies = serviceBlockingStub.getCurrencyTypes(request);
            return currencies.getCurrencyTypesList().stream()
                    .map(SagaCurrencyType::fromProto)
                    .collect(Collectors.toList());
        } catch (StatusRuntimeException e) {
            if (e.getStatus() == Status.NOT_FOUND) {
                return List.of();
            }
            throw SagaException.fromGrpcException(e);
        }
    }

    public String createCurrencyType(String currencyTypeId,
                                     String name,
                                     String symbol,
                                     String imageUrl,
                                     String idempotencyId) throws SagaException {
        try {
            if(currencyTypeId.isBlank()) {
                var msg = "currencyTypeId cannot be empty";
                log.error(msg);
                throw new SagaException(SagaErrorCode.PARSING_DATA_EXCEPTION);
            }
            var builder = CreateCurrencyTypeRequest.newBuilder()
                    .setCurrencyTypeId(currencyTypeId)
                    .setName(name)
                    .setSymbol(symbol)
                    .setImageUrl(imageUrl)
                    .setIdempotencyId(idempotencyId)
                    .build();

            var result = serviceBlockingStub.createCurrencyType(builder);
            return result.getTraceId();
        } catch (SagaException e) {
            throw e;
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        } catch (Exception e) {
            log.error("Exception calling emitReceived on createCurrencyType, currency type may be lost!", e);
            throw new SagaException(SagaErrorCode.LOCAL_EXCEPTION);
        }
    }

}
