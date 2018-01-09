package com.xkupc.framework.mq.base;

import org.springframework.context.annotation.Bean;

/**
 * @author xk
 * @createTime 2018/1/5 0005 下午 3:06
 * @description 生产者抽象配置类
 */
public abstract class PublisherConfig {
    @Bean
    public Sender sender() {
        return new Sender();
    }
}
