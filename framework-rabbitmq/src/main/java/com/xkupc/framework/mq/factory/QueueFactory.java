package com.xkupc.framework.mq.factory;

import com.xkupc.framework.mq.base.MqQueueName;
import com.xkupc.framework.mq.base.QueueBinding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;

/**
 * @author xk
 * @createTime 2018/1/5 0005 下午 3:30
 * @description
 */

public class QueueFactory {
    public QueueFactory(RabbitAdmin rabbitAdmin, MqQueueName[] queueArray) {
        if (null != queueArray) {
            for (MqQueueName queueName : queueArray) {
                //初始化默认exchange队列，默认为永久的
                rabbitAdmin.declareQueue(new Queue(queueName.getQueueName(), true));
            }
        }
    }

    public QueueFactory(RabbitAdmin rabbitAdmin, QueueBinding[] queueBindings) {
        if (null != queueBindings) {
            for (QueueBinding queue : queueBindings) {
                //初始化默认exchange队列，默认为永久的
                rabbitAdmin.declareQueue(new Queue(queue.getQueueName().getQueueName(), true));
            }
        }
    }
}
