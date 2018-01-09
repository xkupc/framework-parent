package com.xkupc.framework.mq.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author xk
 * @createTime 2018/1/5 0005 下午 3:09
 * @description 生产者默认处理
 */
public abstract class PublisherConfirmHandler implements RabbitTemplate.ConfirmCallback {

    private static final Logger logger = LoggerFactory.getLogger(PublisherConfirmHandler.class);

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (!ack) {
            logger.error("message send failed,correlationId:{}",correlationData.getId());
            ackFail();
            return;
        }
        logger.info("message send Success,correlationId:{}",correlationData.getId());
        ackSuccess();
    }

    /**
     * 业务层实现对失败的处理
     */
    public abstract void ackFail();

    /**
     * 业务层实现成功的处理
     */
    public abstract void ackSuccess();
}
