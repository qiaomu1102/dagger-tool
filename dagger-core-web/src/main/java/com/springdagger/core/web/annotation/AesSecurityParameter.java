package com.springdagger.core.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: qiaomu
 * @date: 2020/12/3 11:20
 * @Description: TODO
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface AesSecurityParameter {

    /**
     * 是否加密
     */
    boolean encrypt() default true;

    /**
     * 是否解密
     */
    boolean decrypt() default true;
}
