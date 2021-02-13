package com.example.rabbitmq.consumerdemo.config.queue;

import cn.hutool.extra.spring.SpringUtil;
import com.example.rabbitmq.rabbitmqdemo.config.outer.exchange.DelayExchangeConfig;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author jacksparrow414
 * @date 2021/2/13
 */
@Configuration
@Import(value = {DelayExchangeConfig.class})
public class DelayQueueConfig {
    
    @Bean
    public Queue delayQueue() {
        return new Queue("delay");
    }
    
    @Bean
    public Binding delayBinding() {
       return BindingBuilder.bind(delayQueue()).to(getDelayExchange()).with("*.delay");
    }
    
    private TopicExchange getDelayExchange() {
        return SpringUtil.getBean("delayTopicExchange");
    }
}
