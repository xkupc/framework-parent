package com.xkupc.framework.mq.pubsub;


import com.xkupc.framework.mq.base.DefaultMqExchange;
import com.xkupc.framework.mq.base.MqExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

/**
 * @author xk
 * @createTime 2018/1/5 0005 下午 3:44
 * @description
 */
public class PubsubWorkQueueConfig {

    public static MqExchange mqExchange;

    public static void setMqExchange(MqExchange mqExchange) {
        PubsubWorkQueueConfig.mqExchange = mqExchange;
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        if (null != mqExchange || StringUtils.isEmpty(mqExchange.getExchangeName())) {
            return new FanoutExchange(mqExchange.getExchangeName());
        }
        return new FanoutExchange(DefaultMqExchange.DEFAULT_FANOUT_MQ_EXCHANGE.getExchangeName());
    }

}
