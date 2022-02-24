package games.mythical.saga.sdk.client.executor;

import games.mythical.saga.sdk.client.model.SagaObject;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MockItemExecutor implements SagaItemExecutor {
    private String gameInventoryId;

    @Override
    public void updateItem(String gameInventoryId) {
        this.gameInventoryId = gameInventoryId;
    }

    public void setFromItem(SagaObject item) {
        updateItem("temp");
    }
}
