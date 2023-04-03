package games.mythical.saga.sdk.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SagaUserAmount {
    private String oauthId;
    private String amountInWei;
}
