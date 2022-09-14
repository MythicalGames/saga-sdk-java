package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.proto.common.myth.MythTokenState;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class MockMythTokenExecutor extends MockBaseExecutor implements SagaMythTokenExecutor {
    private String traceId;
    private MythTokenState tokenState;
    private String oAuthId;
    private String quoteId;
    private String amountInNmyth;
    private String gasFeeInWei;

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
    public void onWithdrawalCompleted(String traceId, String oauthId, String quoteId, String amountInNmyth, String gasFeeInWei) throws Exception {
        this.traceId = traceId;
        this.oAuthId = oauthId;
        this.quoteId = quoteId;
        this.amountInNmyth = amountInNmyth;
        this.gasFeeInWei = gasFeeInWei;
    }
}
