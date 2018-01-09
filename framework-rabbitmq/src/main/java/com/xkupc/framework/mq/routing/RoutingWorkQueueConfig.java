package com.xkupc.framework.mq.routing;

import com.xkupc.framework.mq.base.DefaultMqExchange;
import com.xkupc.framework.mq.base.MqExchange;
import com.xkupc.framework.mq.pubsub.PubsubWorkQueueConfig;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

/**
 * @author xk
 * @createTime 2018/1/5 0005 下午 6:29
 * @description 路由模式，队列通过routing key与exchange绑定
 */
public class RoutingWorkQueueConfig {
    public static MqExchange mqExchange;


    public static void setMqExchange(MqExchange mqExchange) {
        PubsubWorkQueueConfig.mqExchange = mqExchange;
    }

    @Bean
    public DirectExchange directExchange() {
        if (null != mqExchange || StringUtils.isEmpty(mqExchange.getExchangeName())) {
            return new DirectExchange(mqExchange.getExchangeName());
        }
        return new DirectExchange(DefaultMqExchange.DEFAULT_DIRECT_MQ_EXCHANGE.getExchangeName());
    }
}
