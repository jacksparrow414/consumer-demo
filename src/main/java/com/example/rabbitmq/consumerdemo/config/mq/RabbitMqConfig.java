package com.example.rabbitmq.consumerdemo.config.mq;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.PooledChannelConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jacksparrow414
 * @date 2020/12/17
 */
@Configuration
public class RabbitMqConfig {
    
    @Bean
    public ConnectionFactory connectionFactory() {
        com.rabbitmq.client.ConnectionFactory connectionFactory = new com.rabbitmq.client.ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("dhb");
        connectionFactory.setPassword("123456");
        connectionFactory.setVirtualHost("dhb");
        PooledChannelConnectionFactory result = new PooledChannelConnectionFactory(connectionFactory);
        return result;
    }
    
    @Bean
    public AmqpAdmin rabbitAdmin() {
        return new RabbitAdmin(connectionFactory());
    }
    
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate result = new RabbitTemplate(connectionFactory());
        result.setMessageConverter(new Jackson2JsonMessageConverter());
        return result;
    }
    
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory result = new SimpleRabbitListenerContainerFactory();
        result.setPrefetchCount(2);
        result.setMessageConverter(new Jackson2JsonMessageConverter());
        return result;
    }
}
