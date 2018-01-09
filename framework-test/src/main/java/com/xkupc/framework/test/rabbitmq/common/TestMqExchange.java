package com.xkupc.framework.test.rabbitmq.common;

import com.xkupc.framework.mq.base.MqExchange;

/**
 * @author xk
 * @createTime 2018/1/9 0009 下午 2:31
 * @description
 */
public enum TestMqExchange implements MqExchange {
    TEST_MQ_EXCHANGE("TEST_MQ_EXCHANGE"),

    TEST_DIRECT_MQ_EXCHANGE("TEST_MQ_EXCHANGE");

    private String mqExchangeName;

    private TestMqExchange(String mqExchangeName) {
        this.mqExchangeName = mqExchangeName;
    }

    @Override
    public String getExchangeName() {
        return mqExchangeName;
    }
}
