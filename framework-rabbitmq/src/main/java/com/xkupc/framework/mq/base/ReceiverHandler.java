package com.xkupc.framework.mq.base;

import com.rabbitmq.client.Channel;
import com.xkupc.framework.mq.exception.RabbitMQException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

/**
 * @author xk
 * @createTime 2018/1/5 0005 上午 10:49
 * @description 消费者处理类
 */
public abstract class ReceiverHandler implements ChannelAwareMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(ReceiverHandler.class);

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        logger.info("receive message:{}", message);
        MqHandlerResult result = MqHandlerResult.REDO;
        try {
            result = handleMessage(message);
        } catch (Exception e) {
            logger.error("message handle error:{}", e);
            //抛出自定义异常,供控制层捕获并统计
            throw new RabbitMQException();
        } finally {
            switch (result) {
                case SUCCESS:
                    logger.info("message success handle:{}", message);
                    //只确认当前消息
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                    break;
                case REDO:
                    logger.info("message should redo:{}", message);
                    //是否重新推送标示
                    boolean requeue = true;
                    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, requeue);
                    break;
                case REJECT:
                    logger.info("message should reject:{}", message);
                    channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
                    break;
                default:
                    logger.info("message handle return error");
                    throw new RabbitMQException();
            }
        }
    }

    public abstract MqHandlerResult handleMessage(Message message);
}
