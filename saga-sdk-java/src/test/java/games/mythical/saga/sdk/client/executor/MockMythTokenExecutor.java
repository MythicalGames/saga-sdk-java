package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.proto.common.myth.MythTokenState;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MockMythTokenExecutor implements SagaMythTokenExecutor {
    private String traceId;
    private MythTokenState tokenState;

    @Override
    public void updateMythToken(String traceId, MythTokenState mythTokenState) throws Exception {
        this.traceId = traceId;
        this.tokenState = mythTokenState;
    }

    @Override
    public void updateMythTokenState(String traceId, MythTokenState mythTokenState) throws Exception {
        this.traceId = traceId;
        this.tokenState = mythTokenState;
    }

    @Override
    public void emitReceived(String traceId) throws Exception {
        this.traceId = traceId;
    }
}
