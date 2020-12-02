package com.springdagger.core.rabbitmq.base;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @package: com.qiaomu.mq.handler
 * @author: kexiong
 * @date: 2020/4/9 11:19
 * @Description: TODO
 */
@Component
public abstract class AbstractMessageHandler implements IMessageHandler {

    /**
     * 设置类型
     * @return
     */
    public abstract String setType();

    @PostConstruct
    public void init() {
        HandlerManager.getInstance().registerHandler(setType(), this);
    }
}
