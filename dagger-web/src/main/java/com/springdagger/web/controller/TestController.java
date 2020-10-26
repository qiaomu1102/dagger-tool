package com.springdagger.web.controller;

import com.springdagger.core.tool.api.R;
import com.springdagger.core.tool.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: kexiong
 * @date: 2020/10/26 16:49
 * @Description: TODO
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    RedisUtil redisUtil;

    @GetMapping("/env")
    public R<String> getEnv() {
        redisUtil.set("test:redis:", "乔木", 10);
        return R.data("dev");
    }

    @GetMapping("/redis")
    public R<Object> getRedis() {
        return R.data(redisUtil.get("test:redis:"));
    }

    @GetMapping("/redis/scan")
    public R<Object> redisScan() {
        return R.data(".........");
    }
}
