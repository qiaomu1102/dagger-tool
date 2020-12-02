package com.springdagger.core.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: qiaomu
 * @date: 2020/11/3 13:54
 * @Description: TODO
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD, ElementType.TYPE})
public @interface IgnoreUserToken {

}
