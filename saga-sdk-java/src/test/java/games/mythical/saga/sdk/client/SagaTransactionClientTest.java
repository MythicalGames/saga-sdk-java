package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.proto.api.transaction.TransactionProto;
import games.mythical.saga.sdk.proto.api.transaction.TransactionServiceGrpc;
import games.mythical.saga.sdk.proto.api.transaction.TransactionsProto;
import games.mythical.saga.sdk.proto.common.SortOrder;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SagaTransactionClientTest extends AbstractClientTest {

    @Mock
    private TransactionServiceGrpc.TransactionServiceBlockingStub mockServiceBlockingStub;

    private SagaTransactionClient transactionClient;

    @BeforeEach
    void setUp() throws Exception {
        port = 65001; // Doesn't matter since there isn't anything to connect to.
        transactionClient = setUpFactory().createSagaTransactionClient();

        // mocking the service blocking stub clients are connected to
        FieldUtils.writeField(transactionClient, "serviceBlockingStub", mockServiceBlockingStub, true);
    }

    @Test
    public void getTransactionsForPlayer() throws Exception {
        var expectedResponse = TransactionsProto.newBuilder()
                .addTransactions(TransactionProto.newBuilder()
                        .setTransactionId("id-1234")
                        .build())
                .build();

        when(mockServiceBlockingStub.getTransactionsForPlayer(any())).thenReturn(expectedResponse);
        var transactionsResponse = transactionClient.getTransactionsForPlayer(
                RandomStringUtils.randomAlphanumeric(30),
                10,
                SortOrder.ASC,
                Instant.EPOCH
        );
        assertEquals(1, transactionsResponse.size());

        var transaction = transactionsResponse.iterator().next();
        assertEquals(expectedResponse.getTransactions(0).getTransactionId(), transaction.getTransactionId());
        assertEquals(expectedResponse.getTransactions(0).getTitleId(), transaction.getTitleId());
    }

    @Test
    public void getTransactionsForItemType() throws Exception {
        var expectedResponse = TransactionsProto.newBuilder()
                .addTransactions(TransactionProto.newBuilder()
                        .setTransactionId("id-1234")
                        .setTitleId("game1")
                        .build())
                .build();

        when(mockServiceBlockingStub.getTransactionsForItemType(any())).thenReturn(expectedResponse);
        var transactionsResponse = transactionClient.getTransactionsForItemType(
                "item-type-1234", "token1234", 10, SortOrder.ASC, Instant.EPOCH);
        assertEquals(1, transactionsResponse.size());

        var transaction = transactionsResponse.iterator().next();
        assertEquals(expectedResponse.getTransactions(0).getTransactionId(), transaction.getTransactionId());
        assertEquals(expectedResponse.getTransactions(0).getTitleId(), transaction.getTitleId());
    }
}
