package ec.editer.amqp.publisher.model.service;

import ec.editer.amqp.message.dto.LoanMessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
    
}
