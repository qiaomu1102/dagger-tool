package com.springdagger.web.controller;

import com.alibaba.fastjson.JSON;
import com.springdagger.core.tool.api.R;
import com.springdagger.core.tool.support.Kv;
import com.springdagger.core.tool.utils.RedisUtil;
import com.springdagger.web.config.Const;
import com.springdagger.web.entity.User;
import com.springdagger.web.entity.UserBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author: kexiong
 * @date: 2020/10/26 16:49
 * @Description: TODO
 */
@Slf4j
@RestController
@RequestMapping("/test")
@Api(value = "测试类")
public class TestController {

    @Resource
    RedisUtil redisUtil;

    @GetMapping("/env")
    public R<Boolean> getEnv() {
//        redisUtil.set("test:redis:", "乔木", 10);
        return R.data(Const.IS_PROD);
    }

    @GetMapping("/map/{id}")
    public R<Map<String, Object>> getMap(@PathVariable(value = "id", required = false) String id) {

        return R.data(Kv.init().set("name", "qiaomu").set("age", 25));
    }

    @GetMapping("/userInfo")
    public R<User> userInfo() {
        User user = new User("name", 25);
        return R.data(user);
    }

    @GetMapping(value = "/htbb_url", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public R<Object> htbbUrl(@ApiParam(value = "name", required = true) String name,
                             @ApiParam(value = "age", name = "bbbb") Integer age) {
        return R.data(Const.HTBB_URL);
    }

    @PostMapping("/save")
    public R<UserBody> save(@RequestBody UserBody userBody) {
        log.info(JSON.toJSONString(userBody));
        return R.data(userBody);
    }
}
