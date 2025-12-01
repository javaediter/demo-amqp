package ec.editer.amqp.publisher.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 *
 * @author Edison Teran
 */
@Data
public class LoanDTO {
    
    @Min(value = 1, message = "Book Id is not valid")
    private Integer idBook;
    
    @NotEmpty(message = "Persona Id is not valid")
    private String idPerson;
    
    @NotEmpty(message = "First Name is required")
    private String firstName;
    
    @NotEmpty(message = "Last Name is required")
    private String lastName;
}
