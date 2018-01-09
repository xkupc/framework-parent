package com.xkupc.framework.test.rabbitmq.pubsub;

import com.rabbitmq.client.Channel;
import com.xkupc.framework.mq.base.MqHandlerResult;
import com.xkupc.framework.mq.base.ReceiverHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * @author xk
 * @createTime 2018/1/9 0009 下午 2:53
 * @description
 */
@Component
public class PubsubTestReceiver1 extends ReceiverHandler{
    private static Logger logger = LoggerFactory.getLogger(PubsubTestReceiver1.class);

    @RabbitListener(queues = "#{T(com.xkupc.framework.test.rabbitmq.common.TestMqQueueName).TEST_MQ_QUEUE_NAME_1.queueName}",
            containerFactory = "rabbitListenerContainerFactoryWithManual")
    public void doMessage(Message message, @Header(org.springframework.amqp.support.AmqpHeaders.CHANNEL) Channel channel){
        try {
            onMessage(message,channel);
        }catch (Exception e){
            logger.error("handle message error:{}", e);
        }
    }
    @Override
    public MqHandlerResult handleMessage(Message message) {
        logger.info("1 receive the message:{}", message);
        return MqHandlerResult.SUCCESS;
    }
}
