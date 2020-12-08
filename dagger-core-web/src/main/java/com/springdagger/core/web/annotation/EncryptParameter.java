package com.springdagger.core.web.annotation;

import com.springdagger.core.web.common.EncryptTypeEnum;
import org.springframework.core.annotation.Order;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: qiaomu
 * @date: 2020/12/3 11:19
 * @Description: TODO
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Order
@Deprecated
public @interface EncryptParameter {

    /**
     * 加密方式
     */
    EncryptTypeEnum encryptType() default EncryptTypeEnum.MD5;

    /**
     * 加密盐值
     */
    String signKey() default "daggerEncryptKey";

    /**
     * 输入的参数是否加密
     */
    boolean inDecode() default true;

    /**
     * 输出的参数是否加密
     */
    boolean outEncode() default true;

}
