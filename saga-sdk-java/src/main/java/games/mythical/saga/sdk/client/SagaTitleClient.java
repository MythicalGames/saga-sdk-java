package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.client.model.SagaTitle;
import games.mythical.saga.sdk.config.SagaSdkConfig;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.factory.CommonFactory;
import games.mythical.saga.sdk.proto.api.title.GetTitlesRequest;
import games.mythical.saga.sdk.proto.api.title.TitleServiceGrpc;
import games.mythical.saga.sdk.proto.common.SortOrder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class SagaTitleClient extends AbstractSagaClient {

    private TitleServiceGrpc.TitleServiceBlockingStub serviceBlockingStub;

    public SagaTitleClient(SagaSdkConfig config) throws SagaException {
        super(config);
        initStub();
    }

    @Override
    void initStub() {
        serviceBlockingStub = TitleServiceGrpc.newBlockingStub(channel).withCallCredentials(addAuthentication());
    }

    public List<SagaTitle> getTitles(int pageSize, SortOrder sortOrder, Instant createdAtCursor) throws SagaException {
        var request = GetTitlesRequest.newBuilder()
                .setQueryOptions(CommonFactory.toProto(pageSize, sortOrder, createdAtCursor))
                .build();

        try {
            var titles = serviceBlockingStub.getTitles(request);
            return titles.getTitlesList().stream().map(SagaTitle::fromProto).collect(Collectors.toList());
        } catch (StatusRuntimeException e) {
            if (e.getStatus() == Status.NOT_FOUND) {
                return List.of();
            }
            throw SagaException.fromGrpcException(e);
        }
    }
}
