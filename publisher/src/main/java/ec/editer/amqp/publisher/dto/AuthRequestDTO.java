package ec.editer.amqp.publisher.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 *
 * @author Edison Teran
 */
@Data
public class AuthRequestDTO {
    @NotBlank(message = "Username is required")
    private String username;
    
    @NotBlank(message = "Password is required")
    private String password;
}
