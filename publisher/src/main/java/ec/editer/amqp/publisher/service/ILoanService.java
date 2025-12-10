package ec.editer.amqp.publisher.service;

import ec.editer.amqp.publisher.dto.LoanDTO;
import ec.editer.amqp.publisher.dto.LoanFullDTO;
import ec.editer.amqp.publisher.model.Loan;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author Edison Teran
 */
public interface ILoanService {
    
    Optional<Loan> register(LoanDTO loanRequest);
    Optional<Loan> getById(Integer id);
    boolean updateLoan(Loan loan);
    List<LoanFullDTO> getAllByDates(String date);
}
