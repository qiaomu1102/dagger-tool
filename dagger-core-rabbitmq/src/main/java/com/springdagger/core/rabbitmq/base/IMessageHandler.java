package com.springdagger.core.rabbitmq.base;

/**
 * @package: com.qiaomu.mq.handler
 * @author: kexiong
 * @date: 2020/4/9 11:01
 * @Description: TODO
 */
public interface IMessageHandler {
    void handle(String data) throws Exception;
}
