package ec.editer.amqp.publisher.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Data;

/**
 *
 * @author Edison Teran
 */
@Data
public class UserDTO {
    @Email(message = "Email is required")
    @NotEmpty(message = "Email is required")
    private String username;
    
    @NotEmpty(message = "Password is required")
    private String password;
    
    @NotEmpty(message = "Roles are required")
    private List<String> roles;
}
