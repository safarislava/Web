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
    public static final String USER_REGISTER_QUEUE = "user.register.queue";
    public static final String USER_GET_QUEUE = "user.get.queue";
    public static final String USER_LOGIN_QUEUE = "user.login.queue";
    public static final String USER_REFRESH_QUEUE = "user.refresh.queue";
    public static final String USER_LOGOUT_QUEUE = "user.logout.queue";
    public static final String USER_GET_ROLES_QUEUE = "user.get.role.queue";
    public static final String TOKEN_PARSE_CLAIMS_QUEUE = "token.parse.claims.queue";

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
    public DirectExchange userRequestExchange() {
        return new DirectExchange(USER_REQUEST_EXCHANGE);
    }

    @Bean
    public DirectExchange tokenRequestExchange() {
        return new DirectExchange(TOKEN_REQUEST_EXCHANGE);
    }

    @Bean
    public Queue userRegisterQueue() {
        return new Queue(USER_REGISTER_QUEUE, true);
    }

    @Bean
    public Queue userGetQueue() {
        return new Queue(USER_GET_QUEUE, true);
    }

    @Bean
    public Queue userLoginQueue() {
        return new Queue(USER_LOGIN_QUEUE, true);
    }

    @Bean
    public Queue userRefreshQueue() {
        return new Queue(USER_REFRESH_QUEUE, true);
    }

    @Bean
    public Queue userLogoutQueue() {
        return new Queue(USER_LOGOUT_QUEUE, true);
    }

    @Bean
    public Queue userGetRolesQueue() {
        return new Queue(USER_GET_ROLES_QUEUE, true);
    }

    @Bean Queue tokenParseClaimsQueue() {
        return new Queue(TOKEN_PARSE_CLAIMS_QUEUE, true);
    }

    @Bean
    public Binding userRegisterBinding() {
        return BindingBuilder.bind(userRegisterQueue())
                .to(userRequestExchange())
                .with(USER_REGISTER_ROUTING_KEY);
    }

    @Bean
    public Binding userGetBinding() {
        return BindingBuilder.bind(userGetQueue())
                .to(userRequestExchange())
                .with(USER_GET_ROUTING_KEY);
    }

    @Bean
    public Binding userLoginBinding() {
        return BindingBuilder.bind(userLoginQueue())
                .to(userRequestExchange())
                .with(USER_LOGIN_ROUTING_KEY);
    }

    @Bean
    public Binding userRefreshBinding() {
        return BindingBuilder.bind(userRefreshQueue())
                .to(userRequestExchange())
                .with(USER_REFRESH_ROUTING_KEY);
    }

    @Bean
    public Binding userLogoutBinding() {
        return BindingBuilder.bind(userLogoutQueue())
                .to(userRequestExchange())
                .with(USER_LOGOUT_ROUTING_KEY);
    }

    @Bean
    public Binding userGetRolesBinding() {
        return BindingBuilder.bind(userGetRolesQueue())
                .to(userRequestExchange())
                .with(USER_GET_ROLES_ROUTING_KEY);
    }

    @Bean
    public Binding tokenParseClaimsBinding() {
        return BindingBuilder.bind(tokenParseClaimsQueue())
                .to(tokenRequestExchange())
                .with(TOKEN_PARSE_CLAIMS_ROUTING_KEY);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        rabbitTemplate.setUseTemporaryReplyQueues(true);
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
