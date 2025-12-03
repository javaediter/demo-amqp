package ec.editer.amqp.publisher.service;

import ec.editer.amqp.message.dto.LoanMessageDTO;
import ec.editer.amqp.publisher.dto.LoanDTO;
import ec.editer.amqp.publisher.model.Loan;

/**
 *
 * @author Edison Teran
 */
public interface ILoanProducer {
    void send(LoanMessageDTO loanMessageDTO);
    LoanMessageDTO convertToOkLoanMessageDTO(LoanDTO loanDTO);
    LoanMessageDTO convertToLoanMessageDTO(Loan loan);
}
