package com.springdagger.web.controller;

import com.alibaba.fastjson.JSON;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.springdagger.core.tool.api.R;
import com.springdagger.core.tool.utils.Func;
import com.springdagger.web.entity.UcUser;
import com.springdagger.web.entity.UcUserBody;
import com.springdagger.web.service.UcUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author: qiaomu
 * @date: 2020/10/30 14:14
 * @Description: TODO
 */
@Slf4j
@RestController()
@RequestMapping("/testDB")
@Api(tags = "2.1测试DB")
public class TestDBController {

    @Autowired
    UcUserService ucUserService;

    @PostMapping("/save")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "保存用户信息", notes = "保存用户信息aaa")
    public R<String> save(@Validated @RequestBody UcUserBody ucUser) {
        boolean save = ucUserService.save(Func.copy(ucUser, UcUser.class));
        return R.status(save);
    }

    @PostMapping("/update/{userId}")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "更改用户信息", notes = "更改用户信息aaa")
    public R<String> updateById(@PathVariable("userId") Long userId, @Validated @RequestBody UcUserBody ucUserBody) {
        UcUser ucUser = Func.copy(ucUserBody, UcUser.class);
        ucUser.setUserId(userId);
        boolean update = ucUserService.updateById(ucUser);
        return R.status(update);
    }

    @PostMapping("/query/{userId}")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "查找用户信息", notes = "查找用户信息aaa")
    public R<UcUserBody> queryById(@PathVariable("userId") Long userId) {
        UcUser ucUser = ucUserService.getById(userId);
        UcUserBody ucUserBody = Func.copy(ucUser, UcUserBody.class);
        log.info(JSON.toJSONString(ucUserBody));
        return R.data(ucUserBody);
    }
}
