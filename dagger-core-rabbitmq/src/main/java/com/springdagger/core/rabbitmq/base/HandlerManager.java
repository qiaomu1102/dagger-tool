package com.springdagger.core.rabbitmq.base;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @package: com.qiaomu.mq
 * @author: kexiong
 * @date: 2020/4/9 11:10
 * @Description: TODO
 */
public class HandlerManager {

    private static volatile Map<String, IMessageHandler> handler = new ConcurrentHashMap<>();

    private HandlerManager(){}

    private static class HandlerManagerHolder{
        private static final HandlerManager INSTANCE = new HandlerManager();
    }

    public static HandlerManager getInstance() {
        return HandlerManagerHolder.INSTANCE;
    }

    public void registerHandler(String type, IMessageHandler messagehandler) {
        handler.put(type, messagehandler);
    }

    public IMessageHandler getHandler(String type) {
        return handler.get(type);
    }
}
