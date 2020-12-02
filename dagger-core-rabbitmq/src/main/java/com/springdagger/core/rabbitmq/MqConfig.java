package com.springdagger.core.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @package: com.qiaomu.mq
 * @author: kexiong
 * @date: 2020/4/7 17:41
 * @Description: TODO
 */
@Configuration
public class MqConfig {

    /**
     * 死信队列
     */
    @Bean(value = "queueDlx")
    public Queue queueDlx(){
        return new Queue(MqProperties.QUEUE_NAME_DLX);
    }

    /**
     *死信交换器
     */
    @Bean(value = "exchangeDlx")
    public DirectExchange exchangeDlx(){
        return new DirectExchange(MqProperties.EXCHANGE_NAME_DLX);
    }

    /**
     * 绑定死信队列与交换器
     */
    @Bean
    public Binding bindingExchangeMessageDlx(@Qualifier("queueDlx") Queue queueMessage, @Qualifier("exchangeDlx") DirectExchange exchange){
        return BindingBuilder.bind(queueMessage).to(exchange).with(MqProperties.ROUTE_KEY_DLX);
    }

    /**
     * 测试的队列  绑定了死信交换器
     */
    @Bean(value = "queueMessageTest")
    public Queue queueMessageTest(){
        Map<String, Object> arguments = new HashMap<>(2);
        arguments.put("x-dead-letter-exchange", MqProperties.EXCHANGE_NAME_DLX);
        arguments.put("x-dead-letter-routing-key",MqProperties.ROUTE_KEY_DLX);
        return new Queue(MqProperties.QUEUE_NAME_TEST, true, false, false, arguments);
    }

    /**
     * 测试的交换器
     *
     * 通过alternate-exchange指定备份交换器。备份交换器建议设置成fanout类型，也可以设置成direct或topic的类型。
     * 不过需要注意：消息被重新路由到备份交换器时的路由键和从生产者发出的路由键是一样的。
     */
    @Bean(value = "exchangeTest")
    public DirectExchange exchangeTest(){
        Map<String, Object> args = new HashMap<>();
        args.put("alternate-exchange", MqProperties.EXCHANGE_NAME_AE);
        return new DirectExchange(MqProperties.EXCHANGE_NAME_TEST, true, false, args);
    }

    /**
     * 绑定测试的队列与交换器
     */
    @Bean
    public Binding bindingExchangeMessageTest(@Qualifier("queueMessageTest") Queue queueMessage, @Qualifier("exchangeTest") DirectExchange exchange){
        return BindingBuilder.bind(queueMessage).to(exchange).with(MqProperties.ROUTE_KEY_TEST);
    }

    /**
     * 备份队列
     */
    @Bean(value = "queueAe")
    public Queue queueAe(){
        return new Queue(MqProperties.QUEUE_NAME_AE);
    }

    /**
     *备份交换器
     */
    @Bean(value = "exchangeAe")
    public FanoutExchange exchangeAe(){
        return new FanoutExchange(MqProperties.EXCHANGE_NAME_AE);
    }

    /**
     * 绑定备份队列与交换器
     */
    @Bean
    public Binding bindingExchangeMessageAe(@Qualifier("queueAe") Queue queueMessage, @Qualifier("exchangeAe") FanoutExchange exchange){
        return BindingBuilder.bind(queueMessage).to(exchange);
    }

    /**
     * 分布式事务队列
     */
    @Bean(value = "queueMessageTx")
    public Queue queueMessageTx(){
        return new Queue(MqProperties.QUEUE_NAME_TX);
    }

    /**
     * 定义分布式事务交换器
     */
    @Bean(value = "exchangeTx")
    public DirectExchange exchangeTx(){
        return new DirectExchange(MqProperties.EXCHANGE_NAME_TX);
    }

    /**
     * 绑定分布式事务队列与交换器
     */
    @Bean
    public Binding bindingExchangeMessageTx(@Qualifier("queueMessageTx") Queue queueMessage, @Qualifier("exchangeTx") DirectExchange exchange){
        return BindingBuilder.bind(queueMessage).to(exchange).with(MqProperties.ROUTE_KEY_TX);
    }

    /** 事务处理成功队列 */
    @Bean
    public Queue queueMessageTxReturnSuccess(){
        return new Queue(MqProperties.QUEUE_NAME_TX_RETURN_SUCCESS);
    }

    /** 定义事务回滚队列 */
    @Bean
    public Queue queueMessageTxReturnFailure(){
        return new Queue(MqProperties.QUEUE_NAME_TX_RETURN_FAILURE);
    }

    /** 定义事务交回滚换器 */
    @Bean
    public DirectExchange exchangeTxReturn(){
        return new DirectExchange(MqProperties.EXCHANGE_NAME_TX_RETURN);
    }

    /** 绑定分布式事务队列与交换器 */
    @Bean
    public Binding bindingExchangeMessageTxReturnSuccess(@Qualifier("queueMessageTxReturnSuccess") Queue queueMessage, @Qualifier("exchangeTxReturn") DirectExchange exchange){
        return BindingBuilder.bind(queueMessage).to(exchange).with(MqProperties.ROUTE_KEY_TX_RETURN_SUCCESS);
    }

    /** 绑定分布式事务队列与交换器 */
    @Bean
    public Binding bindingExchangeMessageTxReturnFailure(@Qualifier("queueMessageTxReturnFailure") Queue queueMessage, @Qualifier("exchangeTxReturn") DirectExchange exchange){
        return BindingBuilder.bind(queueMessage).to(exchange).with(MqProperties.ROUTE_KEY_TX_RETURN_FAILURE);
    }
}
