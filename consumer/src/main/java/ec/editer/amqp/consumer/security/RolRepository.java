package ec.editer.amqp.consumer.security;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 *
 * @author Edison Teran
 */
public interface RolRepository extends CrudRepository<RolEntity, Integer> {
    Optional<RolEntity> findByName(String name);
}
