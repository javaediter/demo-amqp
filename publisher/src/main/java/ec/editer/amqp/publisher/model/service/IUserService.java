package ec.editer.amqp.publisher.model.service;

import ec.editer.amqp.publisher.dto.UserDTO;
import ec.editer.amqp.publisher.security.UserEntity;
import java.sql.SQLException;
import java.util.Optional;

/**
 *
 * @author Edison Teran
 */
public interface IUserService {
    Optional<UserEntity> create(UserDTO userRequest) throws SQLException;
}
