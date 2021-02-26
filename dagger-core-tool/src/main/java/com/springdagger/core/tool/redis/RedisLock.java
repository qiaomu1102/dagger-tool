package com.springdagger.core.tool.redis;

import com.springdagger.core.tool.utils.SpringUtil;
import io.lettuce.core.RedisClient;
import io.lettuce.core.ScriptOutputType;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * @package: com.qiaomu.common.cache
 * @author: kexiong
 * @date: 2019/11/22 16:48
 * @Description:
 * 单节点Redis的分布式锁的实现, 锁不具有可重入性  https://www.jianshu.com/p/47fd7f86c848
 * lettuce作为连接池
 */

public class RedisLock {

    private static final Logger logger = LoggerFactory.getLogger(RedisLock.class.getSimpleName());

    private static int ACQUIRY_RESOLUTION_MILLIS = (int)(50.0D * (1.0D + Math.random()));
    /** 单位为秒 */
    private static int DEFAULT_EXPIRE_MSECS = 5;
    private static int DEFAULT_TIMEOUT_MSECS = 30 * 1000;
    private String lockKey;
    private int expireMsecs;
    private int timeoutMsecs;
    /**lua脚本**/
    private static final String SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    /**存储随机数**/
    private static final ThreadLocal<String> LOCAL = new ThreadLocal<>();
    private RedisCommands<String, String> redisCommands;
    private StatefulRedisConnection<String, String> connection;

    public RedisLock(String lockKey) {
        this(lockKey, DEFAULT_EXPIRE_MSECS);
    }

    public RedisLock(String lockKey, int expireMsecs) {
        this(lockKey, expireMsecs, DEFAULT_TIMEOUT_MSECS);
    }

    public RedisLock(String lockKey, int expireMsecs, int timeoutMsecs) {
        this.lockKey = lockKey;
        this.expireMsecs = expireMsecs;
        this.timeoutMsecs = timeoutMsecs;
        RedisClient redisClient = SpringUtil.getBean(RedisClient.class);
        connection = redisClient.connect();
        redisCommands = connection.sync();
    }

    private boolean tryAcquire(String key, String uuid, long expire) {
        try {
            SetArgs setArgs = SetArgs.Builder.nx().ex(expire);
            String result = redisCommands.set(key, uuid, setArgs);
            return "OK".equalsIgnoreCase(result);
        } catch (Exception e) {
            logger.info("set redis occur an exception", e);
        }
        return false;
    }

    /**
     * 加锁
     * @return
     */
    public boolean lock() throws InterruptedException {
        // 产生随机数
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        while (timeoutMsecs > 0){
            if (tryAcquire(lockKey, uuid, expireMsecs)) {
                // 随机数绑定线程
                LOCAL.set(uuid);
                return true;
            }
            //否则循环等待，在timeout时间内仍未获取到锁，则获取失败

            timeoutMsecs -= ACQUIRY_RESOLUTION_MILLIS;
            Thread.sleep(ACQUIRY_RESOLUTION_MILLIS);
        }
        return false;
    }

    public boolean unLock() {
        try {
            String uuid = LOCAL.get();
            //当前线程没有绑定uuid
            //直接返回
            if (uuid == null || "".equals(uuid)) {
                return false;
            }


            String[] strings = {lockKey};
            Long result = redisCommands.eval(SCRIPT, ScriptOutputType.INTEGER, strings, uuid);

            return result == 0;
        } finally {
            if (connection != null) {
                connection.close();
            }
            LOCAL.remove();
        }
    }
}
