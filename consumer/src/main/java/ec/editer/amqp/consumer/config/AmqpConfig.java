package ec.editer.amqp.consumer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Edison Teran
 */
@Configuration
public class AmqpConfig {
    
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
    public Queue queueLoans(@Value("${amqp.queue.name}") String queueName){
        return QueueBuilder.durable(queueName).build();
    }
    
    @Bean
    public Binding bindTopicExchangeToQueue(final Queue queueLoans, final TopicExchange topicExchange){
        return BindingBuilder.bind(queueLoans).to(topicExchange).with("loan.#");
    }
}
