package com.springdagger.core.rabbitmq.base;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.tools.json.JSONUtil;
import com.springdagger.core.rabbitmq.config.EnumMQSend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

import static com.springdagger.core.rabbitmq.config.EnumMQSend.COMMON;


/**
 * @package: com.qiaomu.mq
 * @author: kexiong
 * @date: 2020/4/8 16:40
 * @Description: 消息发送
 */
@Component
public class RabbitSender {

    public final static Logger logger = LoggerFactory.getLogger(RabbitSender.class);

    @Resource
    RabbitTemplate rabbitTemplate;

    /**
     * 生产者确认机制
     */
    private RabbitTemplate.ConfirmCallback confirmCallBack = (correlationData, ack, cause) -> {
        logger.info("======================== correlationData: " + correlationData);
        if (ack) {
            //TODO 更新数据库，可靠性投递机制

        } else {
            logger.error("生产者确认异常 cause:  " + cause);
            //可以进行日志记录、异常处理、补偿处理等
        }

    };

    /**
     * 当mandatory 参数设为true 时，交换器无法根据自身的类型和路由键找到一个符合条件的队列，那么RabbitMQ 会调用Basic.Return 命令将消息返回给生产者。
     * 当mandatory参数设置为false 时，出现上述情形，则消息直接被丢弃；
     * 如果备份交换器和mandatory参数一起使用，那么mandatory参数无效
     */
//    private RabbitTemplate.ReturnCallback returnCallBack = (message, replyCode, replyText, exchange, routingKey) -> {
//        logger.error("===========交换器无法根据自身的类型和路由键找到一个符合条件的队列=============== return exchange: " + exchange + ", routingKey: "
//                + routingKey + ", replyCode: " + replyCode + ", replyText: " + replyText);
//    };

    public void send(AbstractNotify message) {
        send(message, COMMON);
    }

    public void send(AbstractNotify message, EnumMQSend enumMqSend) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setType(message.getType());
        String body = JSON.toJSONString(message);
        Message build = MessageBuilder.withBody(body.getBytes()).andProperties(messageProperties).build();

        doSend(build, enumMqSend);
    }

    /**
     * 需要设置消息类型
     * @param message
     * @param messageProperties
     * @param enumMqSend
     */
    public void send(String message, MessageProperties messageProperties, EnumMQSend enumMqSend) {
        Message build = MessageBuilder.withBody(message.getBytes()).andProperties(messageProperties).build();
        doSend(build, enumMqSend);
    }

    private void doSend(Message message, EnumMQSend enumMqSend) {
        rabbitTemplate.setConfirmCallback(confirmCallBack);
//        rabbitTemplate.setReturnCallback(returnCallBack);

        //id + 时间戳 全局唯一  用于ack保证唯一一条消息,这边做测试写死一个。但是在做补偿策略的时候，必须保证这是全局唯一的消息
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString().replace("-", ""));
        rabbitTemplate.convertAndSend(enumMqSend.getExchangeName(), enumMqSend.getRoute(), message, correlationData);
    }
}
