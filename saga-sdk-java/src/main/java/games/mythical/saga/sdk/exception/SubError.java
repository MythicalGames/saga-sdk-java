package games.mythical.saga.sdk.exception;

import java.io.Serializable;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubError implements Serializable {
    private static final long serialVersionUID = 3359089224729835546L;

    private String code;
    private String message;
    private String source;
    private Map<String, String> metadata;
}
