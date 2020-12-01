package com.springdagger.web.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author: qiaomu
 * @date: 2020/10/30 15:18
 * @Description: TODO
 */
@Data
public class UcUserBody {
    /**
     * 用户姓名
     */
    @NotEmpty
    @ApiModelProperty(value = "用户姓名")
    private String userName;

    /**
     * 电话
     */
    @NotEmpty
    @ApiModelProperty(value = "电话")
    private String phone;

    /**
     * 年龄
     */
    @NotNull
    @ApiModelProperty(value = "年龄")
    private Integer age;

    /**
     * 邮件
     */
    @ApiModelProperty(value = "邮件")
    private String email;

    /**
     * 性别1男2女
     */
    @NotNull
    @ApiModelProperty(value = "性别1男2女")
    private Integer sex;

    /**
     * 身份证号码
     */
    @NotEmpty
    @ApiModelProperty(value = "身份证号码")
    private String idNum;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
}
