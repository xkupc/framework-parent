package com.xkupc.framework.test.rabbitmq.routing;

import com.xkupc.framework.mq.base.MqExchange;
import com.xkupc.framework.mq.base.MqQueueName;
import com.xkupc.framework.mq.base.QueueBinding;
import com.xkupc.framework.mq.base.RabbitConfig;
import com.xkupc.framework.mq.routing.RoutingPublisherConfig;
import com.xkupc.framework.mq.routing.RoutingReceiverConfig;
import com.xkupc.framework.mq.routing.RoutingWorkQueueConfig;
import com.xkupc.framework.test.rabbitmq.common.TestMqExchange;
import com.xkupc.framework.test.rabbitmq.common.TestMqQueueName;
import com.xkupc.framework.test.rabbitmq.common.TestMqRoutingkey;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author xk
 * @createTime 2018/1/9 0009 下午 4:20
 * @description 路由模式-生产者将指定routingKey的消息同时推送给多个消费者，只有匹配routingKey的消费者可以接收到消息
 */
@AutoConfigureAfter(value = RabbitConfig.class)
@Configuration
@Import({RoutingWorkQueueConfig.class, RoutingPublisherConfig.class, RoutingReceiverConfig.class})
public class RoutingTestMqConfig {

    public RoutingTestMqConfig(){
        //初始化exchange
        MqExchange mqExchange = TestMqExchange.TEST_DIRECT_MQ_EXCHANGE;
        RoutingWorkQueueConfig.setMqExchange(mqExchange);
        //消费者队列初始化并绑定exchange
        RoutingReceiverConfig.setQueueBindings(new QueueBinding[]{
                new QueueBinding(TestMqQueueName.TEST_MQ_QUEUE_NAME_1, TestMqRoutingkey.TEST_MQ_ROUTINGKEY_1,mqExchange),
                new QueueBinding(TestMqQueueName.TEST_MQ_QUEUE_NAME_2,TestMqRoutingkey.TEST_MQ_ROUTINGKEY_2,mqExchange)});
    }
}
