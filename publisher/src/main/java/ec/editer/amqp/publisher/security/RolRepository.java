package ec.editer.amqp.publisher.security;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Edison Teran
 */
public interface RolRepository extends CrudRepository<RolEntity, Integer> {
    Optional<RolEntity> findByName(String name);
    List<RolEntity> findAll();
}
