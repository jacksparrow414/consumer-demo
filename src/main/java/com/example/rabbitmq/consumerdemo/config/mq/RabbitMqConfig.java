package com.example.rabbitmq.consumerdemo.config.mq;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.PooledChannelConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author jacksparrow414
 * @date 2020/12/17
 */
@Configuration
@Import(SpringUtil.class)
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
        result.setConnectionNameStrategy(factory -> "consumer-connection");
        result.setPoolConfigurer((pool, tx) -> {
            if (tx) {

            }else {
                // 设置channel数量，此数量应该为消费者端的Queue的数量. 为什么要等于?
                // 客户端要监听所有的Queue，监听Queue的时候，客户端会同Channel建立连接，
                // 如果Channel连接小于Queue的数量，则有的Queue不会建立Channel连接，
                // 那么当Exchange向对应的Queue发送消息时，到达该Queue的消息不会被消费者消费(都没有建立连接，自然消息推不下去)
                pool.setMaxTotal(9);
            }
        });
        return result;
    }

    /**
     * AmqpAdmin负责声明Exchange、Queue、Binding.<br/>
     * 其作用是通过RabbitTemplate调用Channel完成上述部分的声明.<br/>
     * RabbitTemplate最底层执行的还是调用了rabbitMQ的Java客户端的方式<b>channel.exchangeDeclare</b>这种
     */
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
        result.setConnectionFactory(connectionFactory());
        result.setPrefetchCount(2);
        result.setMessageConverter(new Jackson2JsonMessageConverter());
        return result;
    }
}
