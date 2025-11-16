package ru.ifmo.se.api.coremodule.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String SHOT_EVENTS_QUEUE = "shot.events.queue";

    public static final String SHOT_REQUEST_EXCHANGE = "shot.request.exchange";
    public static final String SHOT_EVENTS_EXCHANGE = "shot.events.exchange";

    public static final String SHOT_ADD_ROUTING_KEY = "shot.add";
    public static final String SHOT_GET_ROUTING_KEY = "shot.get";
    public static final String SHOT_CLEAR_ROUTING_KEY = "shot.clear";

    public static final String USER_REQUEST_EXCHANGE = "user.request.exchange";

    public static final String USER_REGISTER_ROUTING_KEY = "user.register";
    public static final String USER_GET_ROUTING_KEY = "user.get";
    public static final String USER_LOGIN_ROUTING_KEY = "user.login";
    public static final String USER_REFRESH_ROUTING_KEY = "user.refresh";
    public static final String USER_LOGOUT_ROUTING_KEY = "user.logout";

    @Bean
    public FanoutExchange shotEventsExchange() {
        return new FanoutExchange(SHOT_EVENTS_EXCHANGE);
    }

    @Bean
    public Queue shotEventsQueue() {
        return new Queue(SHOT_EVENTS_QUEUE, true);
    }

    @Bean
    public Binding shotEventsBinding() {
        return BindingBuilder.bind(shotEventsQueue())
                .to(shotEventsExchange());
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
