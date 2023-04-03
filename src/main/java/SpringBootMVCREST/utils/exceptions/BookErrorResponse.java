package SpringBootMVCREST.utils.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookErrorResponse {
    private String message;
    private long timestamp;
}
