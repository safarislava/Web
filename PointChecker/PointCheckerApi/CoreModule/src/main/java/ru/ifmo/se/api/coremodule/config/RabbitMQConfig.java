package ru.ifmo.se.api.coremodule.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String SHOT_ADD_QUEUE = "shot.add.queue";
    public static final String SHOT_GET_QUEUE = "shot.get.queue";
    public static final String SHOT_CLEAR_QUEUE = "shot.clear.queue";

    public static final String SHOT_REQUEST_EXCHANGE = "shot.request.exchange";

    public static final String SHOT_ADD_ROUTING_KEY = "shot.add";
    public static final String SHOT_GET_ROUTING_KEY = "shot.get";
    public static final String SHOT_CLEAR_ROUTING_KEY = "shot.clear";

    public static final String USER_REGISTER_QUEUE = "user.register.queue";
    public static final String USER_GET_QUEUE = "user.get.queue";
    public static final String USER_LOGIN_QUEUE = "user.login.queue";
    public static final String USER_REFRESH_QUEUE = "user.refresh.queue";
    public static final String USER_LOGOUT_QUEUE = "user.logout.queue";

    public static final String USER_REQUEST_EXCHANGE = "user.request.exchange";

    public static final String USER_REGISTER_ROUTING_KEY = "user.register";
    public static final String USER_GET_ROUTING_KEY = "user.get";
    public static final String USER_LOGIN_ROUTING_KEY = "user.login";
    public static final String USER_REFRESH_ROUTING_KEY = "user.refresh";
    public static final String USER_LOGOUT_ROUTING_KEY = "user.logout";

    @Bean
    public DirectExchange shotRequestExchange() {
        return new DirectExchange(SHOT_REQUEST_EXCHANGE);
    }

    @Bean
    public Queue shotAddQueue() {
        return new Queue(SHOT_ADD_QUEUE, true);
    }

    @Bean
    public Queue shotGetQueue() {
        return new Queue(SHOT_GET_QUEUE, true);
    }

    @Bean
    public Queue shotClearQueue() {
        return new Queue(SHOT_CLEAR_QUEUE, true);
    }

    @Bean
    public Binding shotAddBinding() {
        return BindingBuilder.bind(shotAddQueue())
                .to(shotRequestExchange())
                .with(SHOT_ADD_ROUTING_KEY);
    }

    @Bean
    public Binding shotGetBinding() {
        return BindingBuilder.bind(shotGetQueue())
                .to(shotRequestExchange())
                .with(SHOT_GET_ROUTING_KEY);
    }

    @Bean
    public Binding shotClearBinding() {
        return BindingBuilder.bind(shotClearQueue())
                .to(shotRequestExchange())
                .with(SHOT_CLEAR_ROUTING_KEY);
    }

    @Bean
    public DirectExchange userRequestExchange() {
        return new DirectExchange(USER_REQUEST_EXCHANGE);
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
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        rabbitTemplate.setUseTemporaryReplyQueues(true);
        rabbitTemplate.setReplyTimeout(10000);
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();

        converter.setClassMapper(new ClassMapper() {
            @Override
            public void fromClass(Class<?> aClass, MessageProperties messageProperties) {}

            @Override
            public Class<?> toClass(MessageProperties messageProperties) {
                return java.util.Map.class;
            }
        });
        return converter;
    }
}
