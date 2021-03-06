package com.springdagger.web.controller;

import com.alibaba.fastjson.JSON;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.springdagger.core.tool.api.R;
import com.springdagger.core.tool.support.Kv;
import com.springdagger.core.tool.utils.RedisUtil;
import com.springdagger.core.web.annotation.CloseLimit;
import com.springdagger.core.web.annotation.DecryptAndEncrypt;
import com.springdagger.core.web.annotation.EncryptParameter;
import com.springdagger.core.web.annotation.IgnoreUserToken;
import com.springdagger.core.web.model.EncryptedReq;
import com.springdagger.web.config.Const;
import com.springdagger.web.entity.User;
import com.springdagger.web.entity.UserBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
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
@Api(tags = "1.0测试类")
public class TestController {

    @Resource
    RedisUtil redisUtil;

    @IgnoreUserToken
    @GetMapping("/env")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "1.0获取环境")
    public R<String> getEnv() {
//        redisUtil.set("test:redis:", "乔木", 10);
        return R.data(Const.HTBB_URL);
    }

    @GetMapping("/test")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "1.0获取环境")
    public R<String> getTest() {
//        redisUtil.set("test:redis:", "乔木", 10);
        return R.data("aaaaaaaaaaaaa");
    }

    @GetMapping("/map/{id}")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "2.0path路径参数")
    public R<Map<String, Object>> getMap(@PathVariable(value = "id", required = false) String id) {
        return R.data(Kv.init().set("name", "qiaomu").set("age", 25));
    }

    @CloseLimit
    @GetMapping("/userInfo")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "3.0获取用户信息")
    public R<User> userInfo() {
        User user = new User("name", 25);
//        String name = (String) redisUtil.get("test:redis:");
//        user.setName(name);
        return R.data(user);
    }

    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "4.0获取url")
    @GetMapping(value = "/htbb_url", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public R<Object> htbbUrl(@ApiParam(value = "name", required = true) String name,
                             @ApiParam(value = "age", name = "bbbb") Integer age) {
        log.info("name=====" + name);
        User user = new User("name", 25);
        return R.data(user);
    }

    @PostMapping("/save")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "5.0测试保存", notes = "测试保存用户信息功能")
    public R<UserBody> save(@Validated @RequestBody UserBody userBody) {
//        log.info(JSON.toJSONString(userBody));
        return R.data(userBody);
    }

    @DecryptAndEncrypt(decryptClass = UserBody.class)
    @PostMapping("/save2")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "5.0测试保存", notes = "测试保存用户信息功能")
    public R<UserBody> save2(@Validated @RequestBody EncryptedReq<UserBody> userBody) {
        log.info("save============" + JSON.toJSONString(userBody));
        UserBody userBody1 = new UserBody();
        userBody1.setAge(30);
        userBody1.setName("asdfasf");
        return R.data(userBody1);
    }

    @DecryptAndEncrypt(decryptClass = UserBody.class, outEncode = false)
    @PostMapping("/save3")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "5.0测试保存", notes = "测试保存用户信息功能")
    public R<UserBody> save3(@Validated @RequestBody EncryptedReq<UserBody> userBody) {
        log.info("save============" + JSON.toJSONString(userBody));
        UserBody userBody1 = new UserBody();
        userBody1.setAge(30);
        userBody1.setName("asdfasf");
        return R.data(userBody1);
    }


}
