package com.springdagger.web.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springdagger.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: qiaomu
 * @date: 2020/10/30 11:47
 * @Description: TODO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("uc_user")
public class UcUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId
    @ApiModelProperty(value = "用户ID")
    private Long userId;

    /**
     * 用户姓名
     */
    @ApiModelProperty(value = "用户姓名")
    private String userName;

    /**
     * 电话
     */
    @ApiModelProperty(value = "电话")
    private String phone;

    /**
     * 年龄
     */
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
    @ApiModelProperty(value = "性别1男2女")
    private Integer sex;

    /**
     * 身份证号码
     */
    @ApiModelProperty(value = "身份证号码")
    private String idNum;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

}


