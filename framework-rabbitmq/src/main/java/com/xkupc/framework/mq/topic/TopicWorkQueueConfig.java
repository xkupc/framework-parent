package com.xkupc.framework.mq.topic;

import com.xkupc.framework.mq.base.DefaultMqExchange;
import com.xkupc.framework.mq.base.MqExchange;
import com.xkupc.framework.mq.pubsub.PubsubWorkQueueConfig;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

/**
 * @author xk
 * @createTime 2018/1/8 0008 下午 2:21
 * @description 初始化topic exchange
 */
public class TopicWorkQueueConfig {

    public static MqExchange mqExchange;

    public static void setMqExchange(MqExchange mqExchange) {
        PubsubWorkQueueConfig.mqExchange = mqExchange;
    }

    @Bean
    public TopicExchange fanoutExchange() {
        if (null != mqExchange || StringUtils.isEmpty(mqExchange.getExchangeName())) {
            return new TopicExchange(mqExchange.getExchangeName());
        }
        return new TopicExchange(DefaultMqExchange.DEFAULT_TOPIC_MQ_EXCHANGE.getExchangeName());
    }

}
