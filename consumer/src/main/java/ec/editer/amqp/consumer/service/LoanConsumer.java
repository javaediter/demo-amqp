package ec.editer.amqp.consumer.service;

import ec.editer.amqp.message.dto.LoanMessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 *
 * @author Edison Teran
 */
@Slf4j
@Service
public class LoanConsumer {
    
    @RabbitListener(queues = "${amqp.queue.name}")
    public void consume(LoanMessageDTO loanMessageDTO) {
        log.info("----- consume for {} -----", loanMessageDTO.getIdBook());
        System.out.println(String.format("MSG: idBook %d - idPerson %s - strDate %s", 
                loanMessageDTO.getIdBook(), 
                loanMessageDTO.getIdPerson(), 
                loanMessageDTO.getStrDate()));
    }
}
