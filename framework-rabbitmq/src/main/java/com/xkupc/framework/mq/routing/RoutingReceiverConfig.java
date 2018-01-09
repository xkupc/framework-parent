package com.xkupc.framework.mq.routing;

import com.xkupc.framework.mq.base.*;
import com.xkupc.framework.mq.factory.BindingFactory;
import com.xkupc.framework.mq.factory.QueueFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

/**
 * @author xk
 * @createTime 2018/1/8 0008 上午 11:03
 * @description 由消费者创建队列，并完成与exchange的绑定
 */
public class RoutingReceiverConfig extends RabbitListenerConfig {

    private static QueueBinding[] queueBindings;

    public static void setQueueBindings(QueueBinding[] queueBindings) {
        RoutingReceiverConfig.queueBindings = queueBindings;
    }

    @Autowired
    RabbitAdmin rabbitAdmin;

    @Bean
    public QueueFactory queueFactory() {
        return new QueueFactory(rabbitAdmin, queueBindings);
    }

    @Bean
    public BindingFactory bindingFactory() {
        queueFactory();
        return new BindingFactory(rabbitAdmin, queueBindings);
    }
}
