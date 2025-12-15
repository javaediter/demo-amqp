package ec.editer.amqp.publisher.repository;

import ec.editer.amqp.publisher.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Edison Teran
 */
public interface LoanRepository extends JpaRepository<Loan, Integer>{
    List<Loan> findAllByDateLessThanEqualOrderByDateDesc(Date date);
}
