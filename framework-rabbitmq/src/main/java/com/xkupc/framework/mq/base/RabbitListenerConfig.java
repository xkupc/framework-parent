package com.xkupc.framework.mq.base;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;

/**
 * @author xk
 * @createTime 2018/1/4 0004 下午 5:15
 * @description RabbitMQ监听器初始化配置
 */
@EnableRabbit
public abstract class RabbitListenerConfig {
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactoryWithManual(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        //最少消费者数量
        factory.setConcurrentConsumers(3);
        //最大消费数量
        factory.setMaxConcurrentConsumers(10);
        //队列里最大的未确认消息个数
        factory.setPrefetchCount(100);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }
}
