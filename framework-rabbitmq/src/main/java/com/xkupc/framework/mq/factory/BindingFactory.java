package com.xkupc.framework.mq.factory;

import com.xkupc.framework.mq.base.MqExchange;
import com.xkupc.framework.mq.base.MqQueueName;
import com.xkupc.framework.mq.base.QueueBinding;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.core.RabbitAdmin;


/**
 * @author xk
 * @createTime 2018/1/5 0005 下午 4:56
 * @description
 */
public class BindingFactory {
    /**
     * 绑定fanout exchange
     *
     * @param rabbitAdmin
     * @param queueArray
     * @param mqExchange
     */
    public BindingFactory(RabbitAdmin rabbitAdmin, MqQueueName[] queueArray, MqExchange mqExchange) {
        for (MqQueueName mqQueueName : queueArray) {
            Binding binding = new Binding(mqQueueName.getQueueName(), Binding.DestinationType.QUEUE,
                    mqExchange.getExchangeName(), "", null);
            rabbitAdmin.declareBinding(binding);
        }
    }

    /**
     * 通过routingking完成对了与exchange的绑定
     * @param rabbitAdmin
     * @param queueBindings
     */
    public BindingFactory(RabbitAdmin rabbitAdmin, QueueBinding[] queueBindings) {
        for (QueueBinding queueBinding : queueBindings) {
            Binding binding = new Binding(queueBinding.getQueueName().getQueueName(), Binding.DestinationType.QUEUE,
                    queueBinding.getExchangeName().getExchangeName(), queueBinding.getRoutingKey().getKeyName(), null);
            rabbitAdmin.declareBinding(binding);
        }
    }
}
