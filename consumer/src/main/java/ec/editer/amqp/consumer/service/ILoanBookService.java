package ec.editer.amqp.consumer.service;

import ec.editer.amqp.consumer.dto.LoanBookDTO;
import ec.editer.amqp.consumer.model.LoanBook;

import java.util.List;

/**
 *
 * @author Edison Teran
 */
public interface ILoanBookService {
    LoanBook create(LoanBook loanBook);
    List<LoanBookDTO> getAll();
}
