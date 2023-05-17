package games.mythical.saga.sdk.client;

import com.google.protobuf.util.Timestamps;
import games.mythical.saga.sdk.client.executor.MockCurrencyTypeExecutor;
import games.mythical.saga.sdk.exception.SagaException;
import games.mythical.saga.sdk.proto.api.currencytype.CurrencyTypeProto;
import games.mythical.saga.sdk.proto.api.currencytype.CurrencyTypeServiceGrpc;
import games.mythical.saga.sdk.proto.api.currencytype.CurrencyTypesProto;
import games.mythical.saga.sdk.proto.common.SortOrder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SagaCurrencyTypeClientTest extends AbstractClientTest {
    private static final String CURRENCY_ID = RandomStringUtils.randomAlphanumeric(30);

    private SagaCurrencyTypeClient currencyTypeClient;

    private final MockCurrencyTypeExecutor executor = MockCurrencyTypeExecutor.builder().build();
    @Mock
    private CurrencyTypeServiceGrpc.CurrencyTypeServiceBlockingStub mockServiceBlockingStub;

    @BeforeEach
    void setUp() throws Exception {
        port = 65001; // Doesn't matter since there isn't anything to connect to.
        currencyTypeClient = setUpFactory().createSagaCurrencyTypeClient(executor);
        // mocking the service blocking stub clients are connected to
        FieldUtils.writeField(currencyTypeClient, "serviceBlockingStub", mockServiceBlockingStub, true);
    }

    private CurrencyTypeProto generateCurrencyTypeProto() {
        return CurrencyTypeProto.newBuilder()
                .setContractAddress(RandomStringUtils.randomAlphanumeric(30))
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .setCreatedAt(Timestamps.fromMillis(Instant.now().toEpochMilli() - 86400))
                .setUpdatedAt(Timestamps.fromMillis(Instant.now().toEpochMilli()))
                .build();
    }

    @Test
    public void getCurrencyType() throws Exception {
        var expectedResponse = generateCurrencyTypeProto();
        when(mockServiceBlockingStub.getCurrencyType(any())).thenReturn(expectedResponse);
        var currencyResponse = currencyTypeClient.getCurrencyType(CURRENCY_ID);

        assertNotNull(currencyResponse);
        assertEquals(expectedResponse.getContractAddress(), currencyResponse.getContractAddress());

        when(mockServiceBlockingStub.getCurrencyType(any())).thenThrow(new StatusRuntimeException(Status.NOT_FOUND));
        assertThrows(SagaException.class, () -> currencyTypeClient.getCurrencyType("INVALID-CURRENCY-ID"));
    }

    @Test
    public void getCurrencyTypes() throws Exception {
        var proto_1 = generateCurrencyTypeProto();
        var proto_2 = generateCurrencyTypeProto();
        var proto_3 = generateCurrencyTypeProto();
        var expectedResponse = CurrencyTypesProto.newBuilder()
                .addAllCurrencyTypes(List.of(proto_1, proto_2, proto_3))
                .build();

        when(mockServiceBlockingStub.getCurrencyTypes(any())).thenReturn(expectedResponse);
        var currencyTypeResponseList = currencyTypeClient.getCurrencyTypes(10, SortOrder.ASC, Instant.now());

        assertFalse(currencyTypeResponseList.isEmpty());
        var currencyType = currencyTypeResponseList.get(0);
        assertEquals(proto_1.getContractAddress(), currencyType.getContractAddress());

        when(mockServiceBlockingStub.getCurrencyTypes(any())).thenReturn(CurrencyTypesProto.getDefaultInstance());
        currencyTypeResponseList = currencyTypeClient.getCurrencyTypes(10, SortOrder.ASC, Instant.now());

        assertTrue(currencyTypeResponseList.isEmpty());
    }
}