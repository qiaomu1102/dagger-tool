package com.springdagger.core.web.aop;

import cn.hutool.core.util.ObjectUtil;
import com.springdagger.core.tool.api.ResultCode;
import com.springdagger.core.web.annotation.CloseLimit;
import com.springdagger.core.web.exception.CloseLimitException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
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
@Aspect
@Configuration
public class CloseLimitAspect {

    @Before(
            "(@within(org.springframework.stereotype.Controller) ||" +
            "@within(org.springframework.web.bind.annotation.RestController))&&" +
             "@annotation(limit) ")
    public void requestLimit(JoinPoint joinpoint, CloseLimit limit) {
        log.info("requestLimit: ===================================");
        ServletRequestAttributes attributes = Objects.requireNonNull((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        log.error(url + "该接口被调用");
        throw new CloseLimitException(ResultCode.REQ_FORBIDDEN);
    }
}
