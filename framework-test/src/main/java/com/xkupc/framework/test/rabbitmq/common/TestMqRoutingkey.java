package com.xkupc.framework.test.rabbitmq.common;

import com.xkupc.framework.mq.base.MqRoutingKey;

/**
 * @author xk
 * @createTime 2018/1/9 0009 下午 4:39
 * @description
 */
public enum TestMqRoutingkey implements MqRoutingKey {
    TEST_MQ_ROUTINGKEY_1("TEST_MQ_ROUTINGKEY_1"),
    TEST_MQ_ROUTINGKEY_2("TEST_MQ_ROUTINGKEY_2");

    private String keyName;

    private TestMqRoutingkey(String keyName) {
        this.keyName = keyName;
    }

    @Override
    public String getKeyName() {
        return keyName;
    }
}
