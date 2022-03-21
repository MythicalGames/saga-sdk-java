package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.model.SagaTransaction;
import games.mythical.saga.sdk.client.model.query.QueryOptions;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.api.transaction.GetTransactionsForItemTypeRequest;
import games.mythical.saga.sdk.proto.api.transaction.GetTransactionsForPlayerRequest;
import games.mythical.saga.sdk.proto.api.transaction.TransactionServiceGrpc;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class SagaTransactionClient extends AbstractSagaClient {

    private TransactionServiceGrpc.TransactionServiceBlockingStub serviceBlockingStub;

    public SagaTransactionClient(SagaSdkConfig config) throws SagaException {
        super(config);
        initStub();
    }

    @Override
    void initStub() {
        serviceBlockingStub = TransactionServiceGrpc.newBlockingStub(channel).withCallCredentials(addAuthentication());
    }

    public List<SagaTransaction> getTransactionsForPlayer(String oauthId, QueryOptions queryOptions) throws SagaException {
        if (queryOptions == null) {
            queryOptions = new QueryOptions();
        }
        var request = GetTransactionsForPlayerRequest.newBuilder()
                .setOauthId(oauthId)
                .setQueryOptions(QueryOptions.toProto(queryOptions))
                .build();

        try {
            var transactions = serviceBlockingStub.getTransactionsForPlayer(request);
            return transactions.getTransactionsList().stream().map(SagaTransaction::fromProto).collect(Collectors.toList());
        } catch (StatusRuntimeException e) {
            if (e.getStatus() == Status.NOT_FOUND) {
                return List.of();
            }
            throw SagaException.fromGrpcException(e);
        }
    }

    public List<SagaTransaction> getTransactionsForItemType(String itemTypeId, String tokenId, QueryOptions queryOptions) throws SagaException {
        if (queryOptions == null) {
            queryOptions = new QueryOptions();
        }
        var request = GetTransactionsForItemTypeRequest.newBuilder()
                .setItemTypeId(itemTypeId)
                .setTokenId(tokenId)
                .setQueryOptions(QueryOptions.toProto(queryOptions))
                .build();

        try {
            var transactions = serviceBlockingStub.getTransactionsForItemType(request);
            return transactions.getTransactionsList().stream().map(SagaTransaction::fromProto).collect(Collectors.toList());
        } catch (StatusRuntimeException e) {
            if (e.getStatus() == Status.NOT_FOUND) {
                return List.of();
            }
            throw SagaException.fromGrpcException(e);
        }
    }
}
