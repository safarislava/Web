package ru.ifmo.se.api.service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String SHOT_POLL_RESPONSE_QUEUE =  "shot.poll.response.queue";
    public static final String SHOT_RESPONSE_EXCHANGE = "shot.response.exchange";
    public static final String SHOT_POLL_ROUTING_KEY = "shot.poll";

    public static final String TOKEN_REQUEST_EXCHANGE = "token.request.exchange";
    public static final String TOKEN_PARSE_CLAIMS_ROUTING_KEY = "token.parse.claims";

    @Bean
    public DirectExchange shotResponseExchange() {
        return new DirectExchange(SHOT_RESPONSE_EXCHANGE);
    }

    @Bean
    public Queue shotPollResponseQueue() {
        return new Queue(SHOT_POLL_RESPONSE_QUEUE, true);
    }

    @Bean
    public Binding shotResponsePollBinding() {
        return BindingBuilder.bind(shotPollResponseQueue())
                .to(shotResponseExchange())
                .with(SHOT_POLL_ROUTING_KEY);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        rabbitTemplate.setUseTemporaryReplyQueues(true);
        rabbitTemplate.setReplyTimeout(10000);
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
