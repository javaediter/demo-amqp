package ec.editer.amqp.publisher.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Edison Teran
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class BookProducer implements IBookProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${amqp.loans.topic.name}")
    private String topicExchange;

    @Override
    public void sendCountNotAvailableBook(Long count) {
        log.info("----- sendCountNotAvailableBook count {} -----", count);
        final String routingKey = "loan.book.ava";
        rabbitTemplate.convertAndSend(topicExchange, routingKey, count);
    }
}
