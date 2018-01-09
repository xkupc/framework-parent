package com.xkupc.framework.test.rabbitmq.pubsub;

import com.xkupc.framework.mq.base.MqExchange;
import com.xkupc.framework.mq.base.MqQueueName;
import com.xkupc.framework.mq.base.RabbitConfig;
import com.xkupc.framework.mq.pubsub.PubsubPublisherConfig;
import com.xkupc.framework.mq.pubsub.PubsubReceiverConfig;
import com.xkupc.framework.mq.pubsub.PubsubWorkQueueConfig;
import com.xkupc.framework.test.rabbitmq.common.TestMqExchange;
import com.xkupc.framework.test.rabbitmq.common.TestMqQueueName;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author xk
 * @createTime 2018/1/9 0009 下午 2:28
 * @description 发布订阅模式-生产者发布消息被同时多个消费者消费
 */
@AutoConfigureAfter(value = RabbitConfig.class)
@Configuration
@Import({PubsubWorkQueueConfig.class, PubsubPublisherConfig.class, PubsubReceiverConfig.class})
public class PubsubTestMqConfig {
    public PubsubTestMqConfig() {
        //初始化exchange
        MqExchange mqExchange = TestMqExchange.TEST_MQ_EXCHANGE;
        PubsubWorkQueueConfig.setMqExchange(mqExchange);
        //消费者初始化
        PubsubReceiverConfig.setMqExchange(mqExchange);
        PubsubReceiverConfig.setQueueArray(new MqQueueName[]{TestMqQueueName.TEST_MQ_QUEUE_NAME_1,TestMqQueueName.TEST_MQ_QUEUE_NAME_2});
    }
}
