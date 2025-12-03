package ec.editer.amqp.publisher.service;

import ec.editer.amqp.message.dto.LoanMessageDTO;
import ec.editer.amqp.publisher.dto.LoanDTO;
import ec.editer.amqp.publisher.model.Loan;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Edison Teran
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class LoanProducer implements ILoanProducer{
    
    @Value("${amqp.topic.name}")
    private String exchange;
    
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void send(LoanMessageDTO loanMessageDTO) {
        log.info("----- send for {} -----", loanMessageDTO.getIdBook());
        final String routingKey = "loan.book.id";
        rabbitTemplate.convertAndSend(exchange, routingKey, loanMessageDTO);
    }

    @Override
    public LoanMessageDTO convertToOkLoanMessageDTO(LoanDTO loanDTO) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        LoanMessageDTO loanMessageDTO = new LoanMessageDTO();
        loanMessageDTO.setIdBook(loanDTO.getIdBook());
        loanMessageDTO.setIdPerson(loanDTO.getIdPerson());
        loanMessageDTO.setStrDate(format.format(new Date()));
        loanMessageDTO.setReversed(false);
        return loanMessageDTO;
    }

    @Override
    public LoanMessageDTO convertToLoanMessageDTO(Loan loan) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        LoanMessageDTO loanMessageDTO = new LoanMessageDTO();
        loanMessageDTO.setIdBook(loan.getBook().getId());
        loanMessageDTO.setIdPerson(loan.getIdPerson());
        loanMessageDTO.setStrDate(format.format(new Date()));
        loanMessageDTO.setReversed(true);
        return loanMessageDTO;
    }
}
