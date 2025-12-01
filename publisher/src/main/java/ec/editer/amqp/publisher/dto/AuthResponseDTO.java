package ec.editer.amqp.publisher.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Edison Teran
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthResponseDTO {
    private String token;
}
