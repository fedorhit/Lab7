package Lab7.DTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@RequiredArgsConstructor
public class BookDTO {
    @NotEmpty(message = "Book 'id' can't be empty")
    private int id; //IDENTITY field
    @NotEmpty(message = "Book title shouldn't be empty")
    private String title;
}
