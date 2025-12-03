package ec.editer.amqp.consumer.service;

import ec.editer.amqp.consumer.model.LoanBook;
import ec.editer.amqp.message.dto.LoanMessageDTO;

/**
 *
 * @author Edison Teran
 */
@FunctionalInterface
public interface ITransformLoan {
    LoanBook transform(LoanMessageDTO loanMessageDTO);
}
