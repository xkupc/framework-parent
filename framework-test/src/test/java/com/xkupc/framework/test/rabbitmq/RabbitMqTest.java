package com.xkupc.framework.test.rabbitmq;

import com.xkupc.framework.test.BaseTestService;
import com.xkupc.framework.test.rabbitmq.common.TestMqExchange;
import com.xkupc.framework.test.rabbitmq.pubsub.PubsubTestPublisher;
import com.xkupc.framework.test.rabbitmq.routing.RoutingTestPublisher;
import com.xkupc.framework.test.rabbitmq.simple.TestPublisher;
import com.xkupc.framework.test.rabbitmq.simple.TestReceiver;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author xk
 * @createTime 2018/1/8 0008 下午 4:40
 * @description
 */
public class RabbitMqTest extends BaseTestService {

    @Autowired
    TestPublisher testPublisher;
    @Autowired
    PubsubTestPublisher pubsubTestPublisher;
    @Autowired
    RoutingTestPublisher routingTestPublisher;

    @Test
    public void testMQ() {
        testPublisher.testSimpleMq("hello world!");
    }

    @Test
    public void testPubsubMQ() {
        pubsubTestPublisher.sendMessage(TestMqExchange.TEST_MQ_EXCHANGE.getExchangeName(), "hello world!");
    }

    @Test
    public void testRoutingMQ() {
        routingTestPublisher.sendMessage(TestMqExchange.TEST_MQ_EXCHANGE.getExchangeName(), "hello1", "hello world!");
        routingTestPublisher.sendMessage(TestMqExchange.TEST_MQ_EXCHANGE.getExchangeName(), "hello2", "hello world!");
    }
}
