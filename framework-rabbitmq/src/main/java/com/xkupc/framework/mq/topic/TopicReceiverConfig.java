package com.xkupc.framework.mq.topic;

import com.xkupc.framework.mq.base.QueueBinding;
import com.xkupc.framework.mq.base.RabbitListenerConfig;
import com.xkupc.framework.mq.factory.BindingFactory;
import com.xkupc.framework.mq.factory.QueueFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

/**
 * @author xk
 * @createTime 2018/1/8 0008 下午 2:30
 * @description 初始化队列，并通过routing key 与exchange绑定
 */
public class TopicReceiverConfig extends RabbitListenerConfig {

    public static QueueBinding[] queueBindings;

    public static void setQueueBindings(QueueBinding[] queueBindings) {
        TopicReceiverConfig.queueBindings = queueBindings;
    }

    @Autowired
    RabbitAdmin rabbitAdmin;

    /**
     * 完成队列的初始化
     *
     * @return
     */
    @Bean
    QueueFactory queueFactory() {
        return new QueueFactory(rabbitAdmin, queueBindings);
    }

    /**
     * 完成队列与exchange的绑定
     *
     * @return
     */
    @Bean
    BindingFactory bindingFactory() {
        queueFactory();
        return new BindingFactory(rabbitAdmin, queueBindings);
    }
}
