package ec.editer.amqp.publisher.repository;

import ec.editer.amqp.publisher.model.Loan;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Edison Teran
 */
public interface LoanRepository extends JpaRepository<Loan, Integer>{
    
    Optional<Loan> findTopByIdPersonAndReversedOrderByIdDesc(String idPerson, boolean reversed);
}
