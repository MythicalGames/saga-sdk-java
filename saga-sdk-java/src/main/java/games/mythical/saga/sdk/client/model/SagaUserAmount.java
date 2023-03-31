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
    private String oauth_id;
    private String amount_in_wei;
}
