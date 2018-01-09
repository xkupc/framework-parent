package com.xkupc.framework.mq.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * @author xk
 * @createTime 2018/1/4 0004 下午 2:00
 * @description 生产者
 */
public class Sender {
    private static Logger logger = LoggerFactory.getLogger(Sender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送默认Exchange消息
     *
     * @param queueName
     * @param message
     */
    public void send(String queueName, String message) {
        send("", queueName, message);
    }

    /**
     * 发送json格式消息
     *
     * @param exchangeName
     * @param routingKey
     * @param message
     */
    public void send(String exchangeName, String routingKey, String message) {
        //生成消息唯一标识，回调确认使用
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        //设置消息体属性参数messageProperties
        MessagePostProcessor messagePostProcessor = messagePostProcessor(correlationId.getId());
        logger.info("消息routing key：{}，消息id：{}，消息内容：{}", routingKey, correlationId.getId(), message);
        rabbitTemplate.convertAndSend(exchangeName, routingKey, message, messagePostProcessor, correlationId);
    }

    MessagePostProcessor messagePostProcessor(String uuid) {
        return message ->
        {
            MessageProperties messageProperties = message.getMessageProperties();
            if (null != messageProperties) {
                messageProperties.setHeader("id", uuid);
                messageProperties.setHeader("timestamp", System.currentTimeMillis() / 1000);
            }
            return message;
        };
    }
}
