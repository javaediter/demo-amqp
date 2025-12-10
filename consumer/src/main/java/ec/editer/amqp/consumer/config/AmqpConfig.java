package ec.editer.amqp.consumer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.editer.amqp.consumer.service.ILoanBookService;
import ec.editer.amqp.message.dto.LoanMessageDTO;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Transformers;

/**
 *
 * @author Edison Teran
 */
@Configuration
public class AmqpConfig {
    
    @Value("${amqp.loan.queue.name}")
    private String queueLoanName;

    @Value("${amqp.book.queue.name}")
    private String queueBookName;
    
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter(new ObjectMapper());
    }
    
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, final MessageConverter messageConverter){
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }
    
    @Bean
    public TopicExchange topicExchange(@Value("${amqp.loans.topic.name}") String exchangeName){
        return ExchangeBuilder.topicExchange(exchangeName).durable(true).build();
    }
    
    @Bean
    public Queue queueLoans(){
        return QueueBuilder.durable(queueLoanName).build();
    }
    
    @Bean
    public Binding bindingLoanQueueToExchange(final Queue queueLoans, final TopicExchange topicExchange){
        return BindingBuilder.bind(queueLoans).to(topicExchange).with("#.id");
    }

    @Bean
    public Queue queueBooks(){
        return QueueBuilder.durable(queueBookName).build();
    }

    @Bean
    public Binding bindingBookQueueToExchange(final Queue queueBooks, final  TopicExchange topicExchange){
        return BindingBuilder.bind(queueBooks).to(topicExchange).with("#.ava");
    }

    @Bean
    public IntegrationFlow inboundFlowLoan(
            ConnectionFactory connectionFactory,
            final ILoanBookService service){
        return IntegrationFlow.from(
                Amqp.inboundAdapter(connectionFactory, queueLoanName)
        )
                .transform(Transformers.toJson())
                .transform(Transformers.fromJson(LoanMessageDTO.class))
                .handle(LoanMessageDTO.class, (payload, header) -> service.create(payload))
                .get();
    }

    @Bean
    public IntegrationFlow inboundFlowBook(ConnectionFactory connectionFactory){
        return IntegrationFlow.from(
                Amqp.inboundAdapter(connectionFactory, queueBookName)
        )
                .handle(Long.class, (payload, header) -> {
                    System.out.println(">>>>> available books " + payload);
                    return null;
                })
                .get();
    }
}
