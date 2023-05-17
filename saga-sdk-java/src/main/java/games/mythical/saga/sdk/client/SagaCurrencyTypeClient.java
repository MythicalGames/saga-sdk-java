package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.executor.SagaCurrencyTypeExecutor;
import games.mythical.saga.sdk.client.model.SagaCurrencyType;
import games.mythical.saga.sdk.client.observer.SagaStatusUpdateObserver;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.factory.CommonFactory;
import games.mythical.saga.sdk.proto.api.currencytype.CurrencyTypeServiceGrpc;
import games.mythical.saga.sdk.proto.api.currencytype.GetCurrencyTypeRequest;
import games.mythical.saga.sdk.proto.api.currencytype.GetCurrencyTypesRequest;
import games.mythical.saga.sdk.proto.common.SortOrder;
import games.mythical.saga.sdk.util.ValidateUtil;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class SagaCurrencyTypeClient extends AbstractSagaStreamClient {
    private final SagaCurrencyTypeExecutor executor;
    private CurrencyTypeServiceGrpc.CurrencyTypeServiceBlockingStub serviceBlockingStub;

    public SagaCurrencyTypeClient(SagaSdkConfig config, SagaCurrencyTypeExecutor executor) throws SagaException {
        super(config);
        this.executor = executor;
        initStub();
    }

    @Override
    void initStub() {
        serviceBlockingStub = CurrencyTypeServiceGrpc.newBlockingStub(channel).withCallCredentials(addAuthentication());
        initStreamStub();
        SagaStatusUpdateObserver.getInstance().with(executor);
    }

    public SagaCurrencyType getCurrencyType(String currencyTypeId) throws SagaException {
        var request = GetCurrencyTypeRequest.newBuilder()
                .setCurrencyTypeId(currencyTypeId)
                .build();

        try {
            var currencyType = serviceBlockingStub.getCurrencyType(request);
            ValidateUtil.checkFound(currencyType,
                    String.format("Currency type %s not found", request.getCurrencyTypeId()));
            return SagaCurrencyType.fromProto(currencyType);
        } catch (StatusRuntimeException e) {
            throw SagaException.fromGrpcException(e);
        }
    }

    public List<SagaCurrencyType> getCurrencyTypes(int pageSize,
                                                   SortOrder sortOrder,
                                                   Instant createdAtCursor) throws SagaException {
        var request = GetCurrencyTypesRequest.newBuilder()
                .setQueryOptions(CommonFactory.toProto(pageSize, sortOrder, createdAtCursor))
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
