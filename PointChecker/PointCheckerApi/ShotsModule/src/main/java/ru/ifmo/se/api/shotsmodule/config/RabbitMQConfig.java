package ru.ifmo.se.api.shotsmodule.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String SHOT_ADD_QUEUE = "shot.add.queue";
    public static final String SHOT_GET_QUEUE = "shot.get.queue";
    public static final String SHOT_CLEAR_QUEUE = "shot.clear.queue";

    public static final String SHOT_REQUEST_EXCHANGE = "shot.request.exchange";
    public static final String SHOT_EVENTS_EXCHANGE = "shot.events.exchange";

    public static final String SHOT_ADD_ROUTING_KEY = "shot.add";
    public static final String SHOT_GET_ROUTING_KEY = "shot.get";
    public static final String SHOT_CLEAR_ROUTING_KEY = "shot.clear";

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
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}