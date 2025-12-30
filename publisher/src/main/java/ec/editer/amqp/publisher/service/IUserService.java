package ec.editer.amqp.publisher.service;

import ec.editer.amqp.publisher.dto.UpdateUserRequestDTO;
import ec.editer.amqp.publisher.dto.UserDTO;
import ec.editer.amqp.publisher.security.UserEntity;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Edison Teran
 */
public interface IUserService {
    Optional<UserDTO> create(UserDTO userRequest) throws SQLException;
    UserDTO update(UpdateUserRequestDTO updateUserRequestDTO);
    List<UserDTO> findAll();
    List<String> getRoles();
}
