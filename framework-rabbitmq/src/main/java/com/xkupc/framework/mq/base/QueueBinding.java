package com.xkupc.framework.mq.base;

/**
 * @author xk
 * @createTime 2018/1/8 0008 下午 2:04
 * @description 队列与exchange绑定关系实体
 */
public class QueueBinding {

    private MqQueueName queueName;

    private MqRoutingKey routingKey;

    private MqExchange exchangeName;

    public QueueBinding(MqQueueName queueName, MqRoutingKey routingKey, MqExchange exchangeName) {
        this.queueName = queueName;
        this.routingKey = routingKey;
        this.exchangeName = exchangeName;
    }

    public MqQueueName getQueueName() {
        return queueName;
    }

    public void setQueueName(MqQueueName queueName) {
        this.queueName = queueName;
    }

    public MqRoutingKey getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(MqRoutingKey routingKey) {
        this.routingKey = routingKey;
    }

    public MqExchange getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(MqExchange exchangeName) {
        this.exchangeName = exchangeName;
    }
}
