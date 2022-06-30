package games.mythical.saga.sdk.client.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SagaItemMetadataUpdate {
    private String inventoryId;
    private SagaMetadata metadata;
}
