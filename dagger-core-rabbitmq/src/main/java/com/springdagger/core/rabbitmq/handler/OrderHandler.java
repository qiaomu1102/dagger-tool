package com.springdagger.core.rabbitmq.handler;

import com.alibaba.fastjson.JSON;
import com.springdagger.core.rabbitmq.base.AbstractMessageHandler;
import com.springdagger.core.rabbitmq.structs.OrderNotify;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @package: com.qiaomu.mq.handler
 * @author: kexiong
 * @date: 2020/4/9 11:23
 * @Description: TODO
 */
@Component
public class OrderHandler extends AbstractMessageHandler {

    public final static Logger logger = LoggerFactory.getLogger(OrderHandler.class);

    @Override
    public String setType() {
        return "3";
    }

    @Override
    public void handle(String data) throws Exception {
        logger.error("OrderHandler ======== " + data);

        OrderNotify orderNotify = JSON.parseObject(data, OrderNotify.class);
        if (orderNotify.getPrice() % 2 == 1) {
            int i = 1 / 0;
        }
    }
}
