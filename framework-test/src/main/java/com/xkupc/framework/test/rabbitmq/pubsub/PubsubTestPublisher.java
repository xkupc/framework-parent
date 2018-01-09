package com.xkupc.framework.test.rabbitmq.pubsub;

import com.xkupc.framework.mq.base.PublisherConfirmHandler;
import com.xkupc.framework.mq.base.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xk
 * @createTime 2018/1/9 0009 下午 2:46
 * @description
 */
@Component
public class PubsubTestPublisher extends PublisherConfirmHandler {

    private static Logger logger = LoggerFactory.getLogger(PubsubTestPublisher.class);

    @Autowired
    Sender sender;

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void sendMessage(String exchange, String message) {
        rabbitTemplate.setConfirmCallback(this);
        logger.info("send message:{}", message);
        sender.send(exchange, "", message);
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
