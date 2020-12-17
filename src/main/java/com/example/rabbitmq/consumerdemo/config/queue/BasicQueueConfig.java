package com.example.rabbitmq.consumerdemo.config.queue;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jacksparrow414
 * @date 2020/12/17
 */
@Configuration
public class BasicQueueConfig {
    
    @Bean
    public Queue basicQueue() {
        return new Queue("basic");
    }
}
