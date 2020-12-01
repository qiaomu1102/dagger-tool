package com.springdagger.web.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author: kexiong
 * @date: 2020/10/26 20:00
 * @Description: TODO
 */
@Data
@ApiModel(value = "entity", description = "用户信息", discriminator = "aaaa", reference = "bbbbb")
public class UserBody {
    @NotNull
    @ApiModelProperty(value = "姓名", notes = "aaaa", example = "qiaomu")
    private String name;

    @NotEmpty
    @ApiModelProperty(value = "年龄", notes = "bbbbb", example = "222")
    private Integer age;
}
