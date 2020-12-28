package com.example.rabbitmq.consumerdemo.consumer;

import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * 开启手动确认消息模式.消息的确认模式为三种,{@link AcknowledgeMode}
 * <ul>
 *     <li>NONE-自动</li>
 *     <li>MANUAL-手动</li>
 *     <li>AUTO-根据情况判断</li>
 * </ul>
 * 消息确认使用channel.basicAck(tag, true)即可.那么怎么拿到channel对象呢,直接当做参数写在方法上即可获取
 * @author jacksparrow414
 * @date 2020/12/14 14:31
 */
@Slf4j
@Component
public class TopicConsumer {
    
    /**
     * 监听Queue的时候，直接获取消息体.
     * 在注解上开启手动确认， 必须是ackMode的大写.
     * 在进行消息确认的时候，要带上Rabbit MQ Server发送过来头上的tag,可以通过@Header注解获取delivery tag,
     * @param firstTopicQueueMessage 消息体
     * @param channel Broker和Consumer建立的channel
     * @param tag 消息头中的tag
     */
    @RabbitListener(queues = "${queue.topic.first}", ackMode = "MANUAL")
    @RabbitHandler
    @SneakyThrows
    public void receiveFirstTopicQueueMessage(String firstTopicQueueMessage, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        log.info("This is firstTopicQueue received message: {}", firstTopicQueueMessage);
        try {
            // 处理实际业务
            doActualWork();
            TimeUnit.SECONDS.sleep(5);
            // 制造异常
            // int wrongNumber = 1/0;
            // 无异常，确认消息消费成功
            channel.basicAck(tag, true);
        }catch (IOException | ArithmeticException exception) {
            log.error("处理消息发生异常", exception);
            // 有异常，将消息返回给Queue里，第三个参数requeue可以直接看出来，是否返回到Queue中
            channel.basicNack(tag, true, true);
        }
    }
    
    /**
     * 处理实际业务.
     */
    private void doActualWork() {
        log.info("处理实际业务");
    }
    
    /**
     * 监听Queue的时候，不直接获取消息体.而是通过Message对象获取消息体
     * @param secondTopicQueueMessage Message对象
     * @param channel Broker和Consumer建立的channel
     */
    @RabbitListener(queues = "${queue.topic.second}", ackMode = "MANUAL")
    @RabbitHandler
    @SneakyThrows
    public void receiveSecondTopicQueueMessage(Message secondTopicQueueMessage, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        byte[] messageBody = secondTopicQueueMessage.getBody();
        String message = new String(messageBody, Charset.defaultCharset());
        log.info("This is secondTopicQueue received message: {}", message);
        // 开启休眠，可以在控制台上发现当前队列中的消息处于Unacked状态
        TimeUnit.SECONDS.sleep(60);
        channel.basicAck(deliveryTag, true);
    }

    @RabbitListener(queues = "${queue.topic.third}")
    @RabbitHandler
    public void receiveThirdTopicQueueMessage(String thirdTopicQueueMessage) {
        log.info("This is thirdTopicQueue received message: {}", thirdTopicQueueMessage);
    }
}
