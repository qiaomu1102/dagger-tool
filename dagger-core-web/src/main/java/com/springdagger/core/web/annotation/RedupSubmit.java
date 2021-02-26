package com.springdagger.core.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: qiaomu
 * @date: 2020/12/23 11:09
 * @Description: TODO
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RedupSubmit {

    /** redis缓存的key ,不填默认 URI + userId*/
    String key() default "";

    /** 过期时间 s*/
    int expire() default 3;

    /** 提示信息 */
    String msg() default "";
}
