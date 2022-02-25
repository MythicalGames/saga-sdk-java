package games.mythical.saga.sdk.client.executor;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MockUserExecutor implements SagaUserExecutor {
    private String oauthId;

    @Override
    public void updateUser(String oauthId) {
        this.oauthId = oauthId;
    }

//    public void setFromItem(SagaObject item) {
//        updateItem("temp");
//    }
}
