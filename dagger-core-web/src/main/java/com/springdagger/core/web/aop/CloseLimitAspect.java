package com.springdagger.core.web.aop;

import com.springdagger.core.web.annotation.CloseLimit;
import com.springdagger.core.web.exception.CloseLimitException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author: qiaomu
 * @date: 2020/12/1 14:41
 * @Description: TODO
 */
@Slf4j
@Component
@Aspect
@Order(1)
public class CloseLimitAspect {

    @Before(
            "@annotation(limit)&&" +
            "(@within(org.springframework.stereotype.Controller) ||" +
            "@within(org.springframework.web.bind.annotation.RestController))"
             )
    public void requestLimit(CloseLimit limit) {
        ServletRequestAttributes attributes = Objects.requireNonNull((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        log.error(url + "该接口被调用");
        throw new CloseLimitException(limit.explain());
    }
}
