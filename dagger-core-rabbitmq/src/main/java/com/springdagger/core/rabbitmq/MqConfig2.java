package com.springdagger.core.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @package: com.qiaomu.mq
 * @author: kexiong
 * @date: 2020/4/9 17:50
 * @Description: TODO
 */
@Component
public class MqConfig2 {

    public static final String QUEUE_NAME_TEST="test.direct.queue2";

    public static final String EXCHANGE_NAME_TEST="test.direct.exchange2";

    public static final String ROUTE_KEY_TEST="test.direct.route2";

    public static final String QUEUE_NAME_AE="ae.queue2";

    public static final String EXCHANGE_NAME_AE="ae.exchange2";

    public static final String ROUTE_KEY_AE="ae.route2";

    /**
     * 测试的队列  绑定了死信交换器
     */
    @Bean(value = "queueMessageTest2")
    public Queue queueMessageTest(){
        return new Queue(QUEUE_NAME_TEST);
    }

    /**
     * 测试的交换器
     *
     * 通过alternate-exchange指定备份交换器。备份交换器建议设置成fanout类型，也可以设置成direct或topic的类型。
     *
     * 不过需要注意：消息被重新路由到备份交换器时的路由键和从 生产者 发出的路由键是一样的。
     */
    @Bean(value = "exchangeTest2")
    public DirectExchange exchangeTest(){
        Map<String, Object> args = new HashMap<>();
        args.put("alternate-exchange", EXCHANGE_NAME_AE);
        return new DirectExchange(EXCHANGE_NAME_TEST, true, false, args);
    }

    /**
     * 绑定测试的队列与交换器
     */
    @Bean
    public Binding bindingExchangeMessageTest2(@Qualifier("queueMessageTest2") Queue queueMessage, @Qualifier("exchangeTest2") DirectExchange exchange){
        return BindingBuilder.bind(queueMessage).to(exchange).with(ROUTE_KEY_TEST);
    }

    /**
     * 备份队列
     */
    @Bean(value = "queueAe2")
    public Queue queueAe(){
        return new Queue(QUEUE_NAME_AE);
    }

    /**
     *备份交换器
     */
    @Bean(value = "exchangeAe2")
    public DirectExchange exchangeAe(){
        return new DirectExchange(EXCHANGE_NAME_AE);
    }

    /**
     * 绑定测试的队列与交换器
     */
    @Bean
    public Binding bindingExchangeMessageAe2(@Qualifier("queueAe2") Queue queueMessage, @Qualifier("exchangeAe2") DirectExchange exchange){
        return BindingBuilder.bind(queueMessage).to(exchange).with(ROUTE_KEY_AE);
    }

}
