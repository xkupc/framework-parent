package com.xkupc.framework.test.rabbitmq.simple;

import com.xkupc.framework.mq.base.RabbitConfig;
import com.xkupc.framework.mq.simple.SimplePublisherConfig;
import com.xkupc.framework.mq.simple.SimpleReceiverConfig;
import com.xkupc.framework.mq.simple.SimpleWorkQueueConfig;
import com.xkupc.framework.test.rabbitmq.common.TestMqQueueName;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author xk
 * @createTime 2018/1/8 0008 下午 2:53
 * @description 简单工作队列模式-生产者发布消息被一个消息者消费
 */
@AutoConfigureAfter(value = RabbitConfig.class)
@Configuration
@Import({SimpleWorkQueueConfig.class, SimpleReceiverConfig.class, SimplePublisherConfig.class})
public class SimpleTestMqConfig {

    public SimpleTestMqConfig() {
        TestMqQueueName[] testMqQueueNames = {TestMqQueueName.TEST_MQ_QUEUE_NAME};
        SimpleWorkQueueConfig.setMqQueueName(testMqQueueNames);
    }
}
