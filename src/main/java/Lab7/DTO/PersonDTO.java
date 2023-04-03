package Lab7.DTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@RequiredArgsConstructor
public class PersonDTO {
  //  @NotEmpty(message = "Person 'id' can't be empty")
  //  private int id; //IDENTITY field
    @Min(value = 0, message = "Age should be greater than 0")
    private int age;
    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min = 2, max = 30, message = "Name length should be between 2 and 30 char")
    private String name;
}
