package ec.editer.amqp.publisher.model.service;

import ec.editer.amqp.publisher.dto.LoanDTO;
import ec.editer.amqp.publisher.model.Loan;
import java.util.Optional;

/**
 *
 * @author Edison Teran
 */
public interface ILoanService {
    
    Optional<Loan> register(LoanDTO loanRequest);
    Optional<Loan> getLastByIdPerson(String idPerson);
    boolean deleteLoan(Loan loan);
}
