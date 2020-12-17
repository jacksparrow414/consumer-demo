package com.example.rabbitmq.consumerdemo.config.queue;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jacksparrow414
 * @date 2020/12/17
 */
@Configuration
//@AutoConfigureAfter
public class TopicQueueConfig {
    
    @Bean
    public Queue firstTopicQueue() {
        return new Queue("firstTopic");
    }
    
    @Bean
    public Queue secondTopicQueue() {
        return new Queue("secondTopic");
    }
    
    @Bean
    public Queue thirdTopicQueue() {
        return new Queue("thirdTopic");
    }
    
    @Bean
    public Binding firstTopicBinding() {
        return BindingBuilder.bind(firstTopicQueue()).to(getTopicExchange()).with("*.first.*");
    }
    
    @Bean
    public Binding secondTopicBinding() {
        return BindingBuilder.bind(secondTopicQueue()).to(getTopicExchange()).with("*.second.*");
    }
    
    @Bean
    public Binding thirdTopicBinding() {
        return BindingBuilder.bind(thirdTopicQueue()).to(getTopicExchange()).with("*.*.Topic");
    }
    
    @Bean
    public Binding fourthTopicBinding() {
        return BindingBuilder.bind(thirdTopicQueue()).to(getTopicExchange()).with("message.#");
    }
    
    private TopicExchange getTopicExchange() {
       return SpringUtil.getBean(TopicExchange.class);
    }
}
