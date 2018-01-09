package com.xkupc.framework.test.rabbitmq.simple;

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
 * @createTime 2018/1/8 0008 下午 3:13
 * @description 配置消费者监听队列，并实现处理消息方法
 */
@Component
public class TestReceiver extends ReceiverHandler {
    private static Logger logger = LoggerFactory.getLogger(TestReceiver.class);

    @RabbitListener(queues = "#{T(com.xkupc.framework.test.rabbitmq.common.TestMqQueueName).TEST_MQ_QUEUE_NAME.queueName}",
            containerFactory = "rabbitListenerContainerFactoryWithManual")
    public void doMessage(Message message, @Header(org.springframework.amqp.support.AmqpHeaders.CHANNEL) Channel channel) {
        try {
            onMessage(message, channel);
        } catch (Exception e) {
            logger.error("handle message error:{}", e);
        }
    }

    @Override
    public MqHandlerResult handleMessage(Message message) {
        logger.info("receive the message:" + message);
        return MqHandlerResult.SUCCESS;
    }
}
