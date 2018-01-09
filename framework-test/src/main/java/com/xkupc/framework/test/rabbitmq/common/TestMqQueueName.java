package com.xkupc.framework.test.rabbitmq.common;

import com.xkupc.framework.mq.base.MqQueueName;

/**
 * @author xk
 * @createTime 2018/1/8 0008 下午 3:05
 * @description
 */
public enum TestMqQueueName implements MqQueueName {

    TEST_MQ_QUEUE_NAME("TEST_MQ_QUEUE_NAME"),
    TEST_MQ_QUEUE_NAME_1("TEST_MQ_QUEUE_NAME_1"),
    TEST_MQ_QUEUE_NAME_2("TEST_MQ_QUEUE_NAME_2");

    private String queueName;

    private TestMqQueueName(String queueName) {
        this.queueName = queueName;
    }

    @Override
    public String getQueueName() {
        return queueName;
    }
}
