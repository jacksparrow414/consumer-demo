package com.example.rabbitmq.consumerdemo.config.mq;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.PooledChannelConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
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
        // 设置心跳超时时间，默认60
        connectionFactory.setRequestedHeartbeat(60);
        PooledChannelConnectionFactory result = new PooledChannelConnectionFactory(connectionFactory);
        result.setConnectionNameStrategy(factory -> "consumer-connection");
        result.setPoolConfigurer((pool, tx) -> {
            if (tx) {

            }else {
                // 设置channel数量，此数量应该等于消费者端的Queue的数量. 为什么要等于?
                // 客户端要监听所有的Queue，监听Queue的时候，客户端会同Channel建立连接，
                // 如果Channel连接小于Queue的数量，则有的Queue不会建立Channel连接，
                // 那么当Exchange向对应的Queue发送消息时，到达该Queue的消息不会被消费者消费(都没有建立连接，自然消息推不下去)
                // 下面的RabbitAdmin初始化的时候也是需要初始化RabbitTemplate的，接着会调用Channel完成，会用到channel，但是此channel用完之后会被关闭，
                // 所以在后面的Queue订阅channel时，可以拿到连接
                pool.setMaxTotal(15);
            }
        });
        return result;
    }

    /**
     * AmqpAdmin负责声明Exchange、Queue、Binding.<br/>
     * 其作用是通过RabbitTemplate调用Channel完成上述部分的声明.<br/>
     * RabbitTemplate最底层执行的还是调用了rabbitMQ的Java客户端的方式<b>channel.exchangeDeclare</b>这种
     *
     * 但是这里可以不用配置，因为{@link RabbitAutoConfiguration}会开启自动配置.为什么自动配置会生效?因为有@ConditionalOnClass注解，
     * 并且@ConditionalOnClass({ RabbitTemplate.class, Channel.class })，这说明，当项目中引用了带有RabbitTemplate和Channel的包，就会开启自动配置
     * 并且有@ConditionalOnMissingBean.所以当这里不配置，自动配置也会注入RabbitAdmin的bean
     */
    @Bean
    public AmqpAdmin rabbitAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate result = new RabbitTemplate(connectionFactory());
        return result;
    }
    
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory result = new SimpleRabbitListenerContainerFactory();
        result.setConnectionFactory(connectionFactory());
        result.setPrefetchCount(2);
        result.setMessageConverter(new Jackson2JsonMessageConverter());
        // 这里根据实际情况来配置是否开启全局消息手动确认
//        result.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return result;
    }
}
