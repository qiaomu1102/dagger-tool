package com.springdagger.core.rabbitmq.config;

import com.springdagger.core.rabbitmq.MqConfig2;

import static com.springdagger.core.rabbitmq.MqProperties.*;

/**
 * @package: com.qiaomu.common.enums
 * @author: kexiong
 * @date: 2020/4/9 17:10
 * @Description: TODO
 */
public enum EnumMQSend {

    COMMON(QUEUE_NAME_TEST, EXCHANGE_NAME_TEST, ROUTE_KEY_TEST),

    ERROR(MqConfig2.QUEUE_NAME_TEST, MqConfig2.EXCHANGE_NAME_TEST, MqConfig2.ROUTE_KEY_AE),

    TX_EXCHANGE(QUEUE_NAME_TX, EXCHANGE_NAME_TX, ROUTE_KEY_TX);

    private String queueName;
    private String exchangeName;
    private String route;

    EnumMQSend(String queueName, String exchangeName, String route) {
        this.queueName = queueName;
        this.exchangeName = exchangeName;
        this.route = route;
    }

    public String getQueueName() {
        return queueName;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public String getRoute() {
        return route;
    }}

