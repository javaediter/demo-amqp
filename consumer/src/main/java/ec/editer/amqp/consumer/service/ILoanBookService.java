package ec.editer.amqp.consumer.service;

import ec.editer.amqp.consumer.dto.LoanBookDTO;
import ec.editer.amqp.consumer.model.LoanBook;
import ec.editer.amqp.message.dto.LoanMessageDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 *
 * @author Edison Teran
 */
public interface ILoanBookService {
    LoanBook create(LoanMessageDTO loanMessageDTO);
    List<LoanBookDTO> getAll(Pageable pageable);
}
