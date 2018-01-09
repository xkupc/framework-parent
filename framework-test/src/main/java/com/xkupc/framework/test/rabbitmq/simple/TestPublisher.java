package com.xkupc.framework.test.rabbitmq.simple;

import com.xkupc.framework.mq.base.PublisherConfirmHandler;
import com.xkupc.framework.mq.base.Sender;
import com.xkupc.framework.test.rabbitmq.common.TestMqQueueName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xk
 * @createTime 2018/1/8 0008 下午 3:12
 * @description
 */
@Component
public class TestPublisher extends PublisherConfirmHandler{
    private static Logger logger = LoggerFactory.getLogger(TestPublisher.class);
    @Autowired
    Sender sender;
    @Autowired
    RabbitTemplate rabbitTemplate;

    public void testSimpleMq(String message) {
        rabbitTemplate.setConfirmCallback(this);
        sender.send(TestMqQueueName.TEST_MQ_QUEUE_NAME.getQueueName(), message);
        logger.info("send message:{}" , message);
    }

    @Override
    public void ackFail() {
       logger.warn("message did not had dispatch");
    }

    @Override
    public void ackSuccess() {
       logger.info("message had dispatch");
    }
}
