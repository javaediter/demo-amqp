package ec.editer.amqp.publisher.security;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Edison Teran
 */
public interface UserRepository extends JpaRepository<UserEntity, Integer>{
    Optional<UserEntity> findByUsername(String username);
}
