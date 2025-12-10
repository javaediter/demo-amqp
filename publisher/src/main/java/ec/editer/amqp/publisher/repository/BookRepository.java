package ec.editer.amqp.publisher.repository;

import ec.editer.amqp.publisher.model.Book;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Edison Teran
 */
public interface BookRepository extends JpaRepository<Book, Integer>{
    List<Book> findAllByTitleContaining(String title);
    long countByAvailable(boolean available);
}
