package com.xkupc.framework.mq.pubsub;

import com.xkupc.framework.mq.base.MqExchange;
import com.xkupc.framework.mq.base.MqQueueName;
import com.xkupc.framework.mq.base.RabbitListenerConfig;
import com.xkupc.framework.mq.factory.BindingFactory;
import com.xkupc.framework.mq.factory.QueueFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

/**
 * @author xk
 * @createTime 2018/1/5 0005 下午 4:49
 * @description 由消费者创建队列，并完成queueName与exchange的绑定
 */
public class PubsubReceiverConfig extends RabbitListenerConfig {

    private static MqQueueName[] queueArray;

    private static MqExchange mqExchange;

    public static void setQueueArray(MqQueueName[] queueArray) {
        PubsubReceiverConfig.queueArray = queueArray;
    }

    public static void setMqExchange(MqExchange mqExchange) {
        PubsubReceiverConfig.mqExchange = mqExchange;
    }

    @Autowired
    RabbitAdmin rabbitAdmin;

    @Bean
    public QueueFactory queueFactory() {
        System.err.println("********************init queue");
        return new QueueFactory(rabbitAdmin, queueArray);
    }

    @Bean
    public BindingFactory bindingFactory() {
        System.err.println("********************binding queue to exchange");
        queueFactory();
        return new BindingFactory(rabbitAdmin, queueArray, mqExchange);
    }
}
