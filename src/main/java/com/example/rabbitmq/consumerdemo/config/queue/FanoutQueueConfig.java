package com.example.rabbitmq.consumerdemo.config.queue;

import cn.hutool.extra.spring.SpringUtil;
import com.example.rabbitmq.rabbitmqdemo.config.exchange.FanoutExchangeConfig;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author jacksparrow414
 * @date 2020/12/17
 */
@Configuration
@Import(FanoutExchangeConfig.class)
public class FanoutQueueConfig {
    
    @Bean
    public Queue firstFanoutQueue() {
        return new Queue("firstFanout");
    }
    
    @Bean
    public Queue secondFanoutQueue() {
        return new Queue("secondFanout");
    }
    
    @Bean
    public Binding firstFanoutBinding() {
        return BindingBuilder.bind(firstFanoutQueue()).to(SpringUtil.getBean(FanoutExchange.class));
    }
    
    @Bean
    public Binding secondFanoutBinding() {
        return BindingBuilder.bind(secondFanoutQueue()).to(SpringUtil.getBean(FanoutExchange.class));
    }
}
