package ru.ifmo.se.api.service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String SHOT_POLL_RESPONSE_QUEUE = "shot.response.queue";

    public static final String SHOT_REQUEST_EXCHANGE = "shot.request.exchange";
    public static final String SHOT_RESPONSE_EXCHANGE = "shot.response.exchange";

    public static final String SHOT_ADD_ROUTING_KEY = "shot.add";
    public static final String SHOT_GET_ROUTING_KEY = "shot.get";
    public static final String SHOT_CLEAR_ROUTING_KEY = "shot.clear";
    public static final String SHOT_POLL_ROUTING_KEY = "shot.poll";

    public static final String USER_REQUEST_EXCHANGE = "user.request.exchange";
    public static final String TOKEN_REQUEST_EXCHANGE = "token.request.exchange";

    public static final String USER_REGISTER_ROUTING_KEY = "user.register";
    public static final String USER_GET_ROUTING_KEY = "user.get";
    public static final String USER_LOGIN_ROUTING_KEY = "user.login";
    public static final String USER_REFRESH_ROUTING_KEY = "user.refresh";
    public static final String USER_LOGOUT_ROUTING_KEY = "user.logout";
    public static final String USER_GET_ROLES_ROUTING_KEY = "user.get.roles";
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
