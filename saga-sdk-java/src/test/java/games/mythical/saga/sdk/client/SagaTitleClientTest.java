package games.mythical.saga.sdk.client;

import games.mythical.saga.sdk.proto.api.title.TitleProto;
import games.mythical.saga.sdk.proto.api.title.TitleServiceGrpc;
import games.mythical.saga.sdk.proto.api.title.TitlesProto;
import games.mythical.saga.sdk.proto.common.SortOrder;
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
public class SagaTitleClientTest extends AbstractClientTest {

    @Mock
    private TitleServiceGrpc.TitleServiceBlockingStub mockServiceBlockingStub;

    private SagaTitleClient titleClient;

    @BeforeEach
    void setUp() throws Exception {
        port = 65001; // Doesn't matter since there isn't anything to connect to.
        titleClient = setUpFactory().createSagaTitleClient();

        // mocking the service blocking stub clients are connected to
        FieldUtils.writeField(titleClient, "serviceBlockingStub", mockServiceBlockingStub, true);

    }

    @Test
    public void getTitles() throws Exception {
        var expectedResponse = TitlesProto.newBuilder()
                .addTitles(TitleProto.newBuilder()
                        .setTitleId("title1")
                        .setName("first game")
                        .build())
                .build();

        when(mockServiceBlockingStub.getTitles(any())).thenReturn(expectedResponse);
        var titlesResponse = titleClient.getTitles(10, SortOrder.ASC, Instant.EPOCH);
        assertEquals(1, titlesResponse.size());

        var title = titlesResponse.iterator().next();
        assertEquals(expectedResponse.getTitles(0).getTitleId(), title.getTitleId());
        assertEquals(expectedResponse.getTitles(0).getName(), title.getName());
    }
}
