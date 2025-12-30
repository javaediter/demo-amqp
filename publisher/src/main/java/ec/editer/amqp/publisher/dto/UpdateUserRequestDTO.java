package ec.editer.amqp.publisher.dto;

import lombok.Data;

@Data
public class UpdateUserRequestDTO {
    private String username;
    private boolean active;
}
