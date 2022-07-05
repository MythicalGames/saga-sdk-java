package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.model.SagaCurrencyType;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.api.currencytype.CurrencyTypeServiceGrpc;
import games.mythical.saga.sdk.proto.api.currencytype.GetCurrencyTypeRequest;
import games.mythical.saga.sdk.proto.api.currencytype.GetCurrencyTypesRequest;
import games.mythical.saga.sdk.proto.common.SortOrder;
import games.mythical.saga.sdk.util.ValidateUtil;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;

import java.util.List;
import java.util.stream.Collectors;

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

}
