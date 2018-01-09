package com.xkupc.framework.test.rabbitmq.routing;

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
 * @createTime 2018/1/9 0009 下午 4:56
 * @description 消费者1，监听队列1
 */
@Component
public class RoutingTestReceiver1 extends ReceiverHandler {
    private static Logger logger = LoggerFactory.getLogger(RoutingTestReceiver1.class);

    @RabbitListener(queues = "#{T(com.xkupc.framework.test.rabbitmq.common.TestMqQueueName).TEST_MQ_QUEUE_NAME_1.queueName}",
            containerFactory = "rabbitListenerContainerFactoryWithManual")
    public void doMessage(Message message, @Header(org.springframework.amqp.support.AmqpHeaders.CHANNEL) Channel channel) {
        try {
            onMessage(message, channel);
        } catch (Exception e) {
            logger.error(" 1 handle message error:{}", e);
        }
    }

    @Override
    public MqHandlerResult handleMessage(Message message) {
        logger.info("1 receive the message:{}", message);
        return MqHandlerResult.SUCCESS;
    }
}
