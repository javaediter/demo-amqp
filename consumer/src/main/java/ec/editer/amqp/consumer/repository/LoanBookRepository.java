package ec.editer.amqp.consumer.repository;

import ec.editer.amqp.consumer.model.LoanBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Edison Teran
 */
public interface LoanBookRepository extends MongoRepository<LoanBook, String>{
    Page<LoanBook> findAll(Pageable pageable);
}
