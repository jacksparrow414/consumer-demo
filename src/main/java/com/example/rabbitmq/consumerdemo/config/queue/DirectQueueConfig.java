package com.example.rabbitmq.consumerdemo.config.queue;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jacksparrow414
 * @date 2020/12/17
 */
@Configuration
@AutoConfigureAfter()
public class DirectQueueConfig {
    
    @Bean
    public Queue firstDirectQueue() {
        return new Queue("firstDirect");
    }
    
    @Bean
    public Queue secondDirectQueue() {
        return new Queue("secondDirect");
    }
    
    @Bean
    public Binding firstDirectBinding() {
        return BindingBuilder.bind(firstDirectQueue()).to(SpringUtil.getBean(DirectExchange.class)).with("first");
    }
    
    @Bean
    public Binding secondDirectBinding() {
        return BindingBuilder.bind(secondDirectQueue()).to(SpringUtil.getBean(DirectExchange.class)).with("second");
    }
}
