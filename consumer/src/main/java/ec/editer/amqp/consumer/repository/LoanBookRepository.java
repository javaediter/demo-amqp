package ec.editer.amqp.consumer.repository;

import ec.editer.amqp.consumer.model.LoanBook;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Edison Teran
 */
public interface LoanBookRepository extends MongoRepository<LoanBook, String>{
    
}
