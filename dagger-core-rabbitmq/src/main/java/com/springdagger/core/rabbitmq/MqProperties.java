package com.springdagger.core.rabbitmq;

/**
 * @package: com.qiaomu.mq
 * @author: kexiong
 * @date: 2020/4/7 17:40
 * @Description: TODO
 */
public class MqProperties {

    public static final String QUEUE_NAME_TEST="test.direct.queue";

    public static final String EXCHANGE_NAME_TEST="test.direct.exchange";

    public static final String ROUTE_KEY_TEST="test.direct.route";

    public static final String QUEUE_NAME_DLX="dlx.queue";

    public static final String EXCHANGE_NAME_DLX="dlx.exchange";

    public static final String ROUTE_KEY_DLX="dlx.route";

    public static final String QUEUE_NAME_AE="ae.queue";

    public static final String EXCHANGE_NAME_AE="ae.exchange";

    public static final String ROUTE_KEY_AE="ae.route";




    public static final String QUEUE_NAME_TX="tx.order.queue";

    public static final String EXCHANGE_NAME_TX="tx.order.exchange";

    public static final String ROUTE_KEY_TX="tx.order.route";

    //事务处理成功队列
    public static final String QUEUE_NAME_TX_RETURN_SUCCESS="tx.order.return.success";
    //事务回滚队列
    public static final String QUEUE_NAME_TX_RETURN_FAILURE="tx.order.return.failure";
    //事务交换器
    public static final String EXCHANGE_NAME_TX_RETURN="tx.order.exchange.return";

    public static final String ROUTE_KEY_TX_RETURN_SUCCESS="tx.order.create.success";

    public static final String ROUTE_KEY_TX_RETURN_FAILURE="tx.order.create.failure";
}
