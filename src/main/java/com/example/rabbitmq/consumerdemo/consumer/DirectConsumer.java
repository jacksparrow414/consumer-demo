package com.example.rabbitmq.consumerdemo.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author jacksparrow414
 * @date 2020/12/13
 */
@Component
@Slf4j
public class DirectConsumer {
    
    @RabbitListener(queues = "${queue.direct.first}")
    @RabbitHandler
    public void receiveFirstDirectQueueMessage(String firstQueueMessage) {
        log.info("This is firstDirectQueue received message: " + firstQueueMessage);
    }
    
    @RabbitListener(queues = "${queue.direct.second}")
    @RabbitHandler
    public void  receiveSecondDirectQueueMessage(String secondQueueMessage) {
        log.info("This is secondDirectQueue received message: " + secondQueueMessage);
    }
}
