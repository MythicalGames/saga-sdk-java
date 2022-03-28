package games.mythical.saga.sdk.client.executor;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class MockUserExecutor extends MockBaseExecutor implements SagaUserExecutor {
    private String oauthId;
    private String traceId;

    @Override
    public void updateUser(String oauthId, String traceId) {
        this.oauthId = oauthId;
        this.traceId = traceId;
    }
}
