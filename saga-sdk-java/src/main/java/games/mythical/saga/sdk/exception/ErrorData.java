package games.mythical.saga.sdk.exception;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorData implements Serializable {
    private static final long serialVersionUID = -3996698590804049920L;

    private String code;
    private String message;
    private String trace;
    private String source;
    private Map<String, String> metadata;
    private List<SubError> suberrors;
}
