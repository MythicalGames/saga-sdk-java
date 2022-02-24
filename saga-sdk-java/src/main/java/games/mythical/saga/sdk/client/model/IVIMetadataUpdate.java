package games.mythical.saga.sdk.client.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IVIMetadataUpdate {
    private String gameInventoryId;
    private IVIMetadata metadata;
}
