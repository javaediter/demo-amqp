package ec.editer.amqp.consumer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.editer.amqp.consumer.model.LoanBook;
import ec.editer.amqp.consumer.service.ILoanBookService;
import ec.editer.amqp.consumer.service.ITransformLoan;
import ec.editer.amqp.message.dto.LoanMessageDTO;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.dsl.IntegrationFlow;

/**
 *
 * @author Edison Teran
 */
@Configuration
public class AmqpConfig {
    
    @Value("${amqp.queue.name}") 
    private String queueName;
    
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
    public TopicExchange topicExchange(@Value("${amqp.topic.name}") String exchangeName){
        return ExchangeBuilder.topicExchange(exchangeName).durable(true).build();
    }
    
    @Bean
    public Queue queueLoans(){
        return QueueBuilder.durable(queueName).build();
    }
    
    @Bean
    public Binding bindingQueueToExchange(final Queue queueLoans, final TopicExchange topicExchange){
        return BindingBuilder.bind(queueLoans).to(topicExchange).with("loan.#");
    }
    
    @Bean
    public SimpleMessageListenerContainer listenerContainer(ConnectionFactory connectionFactory){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        return container;
    }
    
    @Bean
    public IntegrationFlow inboundFlowLoan(
            final SimpleMessageListenerContainer listenerContainer, 
            final ITransformLoan transformLoan,
            final ILoanBookService service){
        return IntegrationFlow.from(
                Amqp.inboundAdapter(listenerContainer)
        )
                .transform(LoanMessageDTO.class, transformLoan::transform)
                .handle(LoanBook.class, (payload, header) -> service.create(payload))
                .get();
    }
}
