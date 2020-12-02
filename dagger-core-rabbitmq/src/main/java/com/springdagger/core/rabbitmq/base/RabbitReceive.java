package com.springdagger.core.rabbitmq.base;

import com.rabbitmq.client.Channel;
import com.springdagger.core.rabbitmq.MqConfig2;
import com.springdagger.core.rabbitmq.MqProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @package: com.qiaomu.mq
 * @author: kexiong
 * @date: 2020/4/7 17:39
 * @Description: 消息接收及处理
 */
@Component
public class RabbitReceive {

    public final static Logger logger = LoggerFactory.getLogger(RabbitReceive.class);

    @RabbitListener(queues = MqProperties.QUEUE_NAME_TEST)
    @RabbitHandler
    public void onMessage(Channel channel, Message message) throws Exception {
        try {
            String type = message.getMessageProperties().getType();

            IMessageHandler iMessagehandler = HandlerManager.getInstance().getHandler(type);
            if (iMessagehandler != null) {
                iMessagehandler.handle(new String(message.getBody()));
            } else {
                logger.error("接收到新消息类型:" + type + "；消息内容：");

                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            }
        } catch (Exception e) {
            logger.error("mq接收处理消息异常： " + e , e);
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            return;
        }

        //手工ACK
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    /**
     * 死信队列
     */
    @RabbitListener(queues=MqProperties.QUEUE_NAME_DLX)
    public void onMessageDlx(Channel channel, Message message) throws Exception {
        logger.error("============================进入了死信队列==== " + new String(message.getBody()));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    /**
     * 备份队列
     * @param channel
     * @param message
     * @throws Exception
     */
    @RabbitListener(queues=MqProperties.QUEUE_NAME_AE)
    public void onMessageAe(Channel channel, Message message) throws Exception {
        logger.error("============================进入了备份队列==== " + new String(message.getBody()));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    /**
     *
     */
    @RabbitListener(queues=MqConfig2.QUEUE_NAME_TEST)
    public void onMessage2(Channel channel, Message message) throws Exception {
        logger.error("==========正常发送接收==========2222222222222============ " + new String(message.getBody()));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }



    /**
     * 备份队列
     * @param channel
     * @param message
     * @throws Exception
     */
    @RabbitListener(queues= MqConfig2.QUEUE_NAME_AE)
    public void onMessageAe2(Channel channel, Message message) throws Exception {
        logger.error("======进入了备份队列==============2222222222222============ " + new String(message.getBody()));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    /**
     * 处理业务处理失败，回滚业务
     */
    @RabbitListener(queues=MqProperties.QUEUE_NAME_TX_RETURN_FAILURE)
    public void process1(String orderId, Channel channel, Message message) throws Exception {
        logger.error(message.toString());
//        try {
////            orderService.shoppingRollback(Integer.valueOf(orderId));
//        } catch (Exception e) {
//            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
//            e.printStackTrace();
//        }
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
    /**
     * 处理业务处理成功，修改订单状态
     */
    @RabbitListener(queues= MqProperties.QUEUE_NAME_TX_RETURN_SUCCESS)
    public void process2(String orderId, Channel channel, Message message) throws Exception {
        try {
//            orderService.shoppingCommit(Integer.valueOf(orderId));
        } catch (Exception e) {
//            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
            e.printStackTrace();
        }
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    //    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(value = "queue-1", durable="true"),
//            exchange = @Exchange(value = "exchange-1",
//                                durable="true",
//                                type= "topic",
//                                ignoreDeclarationExceptions = "true"),
//            key = "springboot.*")
//    )
//    @RabbitHandler
//    public void onMessage(Message message, Channel channel) throws Exception {
//        System.err.println("--------------------------------------");
//        System.err.println("消费端Payload: " + message.getPayload());
//        Long deliveryTag = (Long)message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
//        //手工ACK,获取deliveryTag
//        channel.basicAck(deliveryTag, false);
//    }
}
