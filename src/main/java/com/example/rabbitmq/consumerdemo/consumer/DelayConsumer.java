package com.example.rabbitmq.consumerdemo.consumer;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author jacksparrow414
 * @date 2021/2/13
 */
@Component
@Slf4j
public class DelayConsumer {
    
    @RabbitListener(queues = "${queue.delay.delay}")
    @RabbitHandler
    public void receiveDelayQueueMessage(Message message) {
        String delayMessage = new String(message.getBody(), Charset.defaultCharset());
        Integer delayTime = message.getMessageProperties().getReceivedDelay();
        log.info("收到延迟消息{},时间为{}, 延迟时间为{}", delayMessage, LocalDateTime.now().toString(), delayTime);
    }
}
