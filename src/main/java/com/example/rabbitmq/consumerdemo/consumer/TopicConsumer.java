package com.example.rabbitmq.consumerdemo.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author jacksparrow414
 * @date 2020/12/14 14:31
 */
@Slf4j
@Component
public class TopicConsumer {

    @RabbitListener(queues = "${queue.topic.first}")
    @RabbitHandler
    public void receiveFirstTopicQueueMessage(String firstTopicQueueMessage) {
        log.info("This is firstTopicQueue received message: " + firstTopicQueueMessage);
    }

    @RabbitListener(queues = "${queue.topic.second}")
    @RabbitHandler
    public void receiveSecondTopicQueueMessage(String secondTopicQueueMessage) {
        log.info("This is secondTopicQueue received message: " + secondTopicQueueMessage);
    }

    @RabbitListener(queues = "${queue.topic.third}")
    @RabbitHandler
    public void receiveThirdTopicQueueMessage(String thirdTopicQueueMessage) {
        log.info("This is thirdTopicQueue received message: " + thirdTopicQueueMessage);
    }
}
