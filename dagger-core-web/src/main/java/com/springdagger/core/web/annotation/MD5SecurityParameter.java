package com.springdagger.core.web.annotation;

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
public @interface MD5SecurityParameter {

    /**
     * 是否加密
     */
    boolean encrypt() default true;

    /**
     * 是否解密
     */
    boolean decrypt() default true;

}
