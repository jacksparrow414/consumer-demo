package com.example.rabbitmq.consumerdemo.config.queue;

import cn.hutool.extra.spring.SpringUtil;
import com.example.rabbitmq.rabbitmqdemo.config.outer.exchange.DirectExchangeConfig;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author jacksparrow414
 * @date 2020/12/17
 */
@Configuration
@Import(value = {DirectExchangeConfig.class})
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
    public Queue simpleStringConvertQueue() {return new Queue("stringConvertDirect"); }
    
    @Bean
    public Queue simplePoJoQueue() {
        return new Queue("pojoDirect");
    }
    
    @Bean
    public Binding firstDirectBinding() {
        return BindingBuilder.bind(firstDirectQueue()).to(SpringUtil.getBean(DirectExchange.class)).with("first");
    }
    
    @Bean
    public Binding secondDirectBinding() {
        return BindingBuilder.bind(secondDirectQueue()).to(SpringUtil.getBean(DirectExchange.class)).with("second");
    }
    
    @Bean
    public Binding simpleStringConvertBinding() {
        return BindingBuilder.bind(simpleStringConvertQueue()).to(SpringUtil.getBean(DirectExchange.class)).with("str");
    }
    
    @Bean
    public Binding simplePoJoBinding() {
        return BindingBuilder.bind(simplePoJoQueue()).to(SpringUtil.getBean(DirectExchange.class)).with("pojo");
    }
}
