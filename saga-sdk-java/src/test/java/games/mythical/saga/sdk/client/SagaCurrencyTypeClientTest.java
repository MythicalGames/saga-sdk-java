package games.mythical.saga.sdk.client;

import com.google.protobuf.util.Timestamps;
import games.mythical.saga.sdk.proto.api.currencytype.CurrencyTypeProto;
import games.mythical.saga.sdk.proto.api.currencytype.CurrencyTypeServiceGrpc;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SagaCurrencyTypeClientTest extends AbstractClientTest {
    private static final String CURRENCY_ID = RandomStringUtils.randomAlphanumeric(30);
    private static final String ADDRESS = RandomStringUtils.randomAlphanumeric(30);

    private SagaCurrencyTypeClient currencyTypeClient;

    @Mock
    private CurrencyTypeServiceGrpc.CurrencyTypeServiceBlockingStub mockServiceBlockingStub;

    @BeforeEach
    void setUp() throws Exception {
        port = 65001; // Doesn't matter since there isn't anything to connect to.
        currencyTypeClient = setUpFactory().createSagaCurrencyTypeClient();
        // mocking the service blocking stub clients are connected to
        FieldUtils.writeField(currencyTypeClient, "serviceBlockingStub", mockServiceBlockingStub, true);
    }

    @Test
    public void getCurrencyType() throws Exception {
        var expectedResponse = CurrencyTypeProto.newBuilder()
                .setId(CURRENCY_ID)
                .setMaxSupply(RandomUtils.nextInt(0, 1000))
                .setContractAddress(ADDRESS)
                .setTraceId(RandomStringUtils.randomAlphanumeric(30))
                .setCreatedAt(Timestamps.fromMillis(Instant.now().toEpochMilli() - 86400))
                .setUpdatedAt(Timestamps.fromMillis(Instant.now().toEpochMilli()))
                .build();
        when(mockServiceBlockingStub.getCurrencyType(any())).thenReturn(expectedResponse);
        var currencyResponse = currencyTypeClient.getCurrencyType(CURRENCY_ID);

        assertTrue(currencyResponse.isPresent());
        var currency = currencyResponse.get();
        assertEquals(CURRENCY_ID, currency.getId());
        assertEquals(expectedResponse.getContractAddress(), currency.getContractAddress());
        assertEquals(expectedResponse.getMaxSupply(), currency.getMaxSupply());

        when(mockServiceBlockingStub.getCurrencyType(any())).thenThrow(new StatusRuntimeException(Status.NOT_FOUND));
        currencyResponse = currencyTypeClient.getCurrencyType("INVALID-CURRENCY-ID");

        assertTrue(currencyResponse.isEmpty());
    }
}