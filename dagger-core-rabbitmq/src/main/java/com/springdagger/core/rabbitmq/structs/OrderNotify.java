package com.springdagger.core.rabbitmq.structs;


import com.springdagger.core.rabbitmq.base.AbstractNotify;

/**
 * @package: com.qiaomu.mq.structs
 * @author: kexiong
 * @date: 2020/4/9 11:30
 * @Description: TODO
 */
public class OrderNotify extends AbstractNotify {

    private String orderId;
    private String orderName;
    private int price;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String getType() {
        return "3";
    }
}
