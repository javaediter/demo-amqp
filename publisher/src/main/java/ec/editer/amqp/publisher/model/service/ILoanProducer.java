package ec.editer.amqp.publisher.model.service;

import ec.editer.amqp.message.dto.LoanMessageDTO;

/**
 *
 * @author Edison Teran
 */
public interface ILoanProducer {
    void send(LoanMessageDTO loanMessageDTO);
}
