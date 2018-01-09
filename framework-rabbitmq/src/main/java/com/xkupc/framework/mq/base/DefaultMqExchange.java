package com.xkupc.framework.mq.base;

/**
 * @author xk
 * @createTime 2018/1/5 0005 下午 4:35
 * @description
 */
public enum DefaultMqExchange implements MqExchange {
    //默认fanout exchange
    DEFAULT_FANOUT_MQ_EXCHANGE("XKUPC.DEFAULT.FANOUT.EXCHANGE"),
    //默认direct exchange
    DEFAULT_DIRECT_MQ_EXCHANGE("XKUPC.DEFAULT.DIRECT.EXCHANGE"),
    //默认topic exchange
    DEFAULT_TOPIC_MQ_EXCHANGE("XKUPC.DEFAULT.TOPIC.EXCHANGE");

    private String name;

    DefaultMqExchange(String name) {
        this.name = name;
    }

    @Override
    public String getExchangeName() {
        return name;
    }
}
