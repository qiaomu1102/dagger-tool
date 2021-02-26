package com.springdagger.core.web.aop;

import com.springdagger.core.web.context.UserContextHolder;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author xiangbinbing
 * @date 2020-12-15 15:35
 */
@Aspect
@Component
@Order(999999999)
public class CleanUpAop {

    @Pointcut("@within(org.springframework.stereotype.Controller) || " +
            "@within(org.springframework.web.bind.annotation.RestController)")
    public void pointCut() {
    }

    @After("pointCut()")
    public void after() {
        UserContextHolder.clean();
    }

}
