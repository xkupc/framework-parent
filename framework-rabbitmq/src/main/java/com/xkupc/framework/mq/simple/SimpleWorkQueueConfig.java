package com.xkupc.framework.mq.simple;


import com.xkupc.framework.mq.base.MqQueueName;
import com.xkupc.framework.mq.factory.QueueFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xk
 * @createTime 2018/1/5 0005 下午 3:20
 * @description 点对点的工作队列消息模式配置，exchange为"",主要完成业务层给定的队列的初始化
 */
public class SimpleWorkQueueConfig{

    private static MqQueueName[] mqQueueName;

    public static void setMqQueueName(MqQueueName[] mqQueueName) {
        SimpleWorkQueueConfig.mqQueueName = mqQueueName;
    }

    @Autowired
    RabbitAdmin rabbitAdmin;

    @Bean
    public QueueFactory queueFactory() {
        return new QueueFactory(rabbitAdmin, mqQueueName);
    }
}
