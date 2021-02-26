package com.springdagger.core.web.annotation;

import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@Order(1)
public @interface CloseLimit {

    String explain() default "该接口已关闭, 禁止访问";

}
