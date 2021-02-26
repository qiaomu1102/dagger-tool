package com.springdagger.core.web.aop;

import com.htbb.core.web.context.UserContextHolder;
import com.htbb.core.web.exception.CloseLimitException;
import com.htbb.core.web.exception.RedupSubmitException;
import com.springdagger.core.tool.api.ResultCode;
import com.springdagger.core.tool.utils.RedisUtil;
import com.springdagger.core.tool.utils.StringUtil;
import com.springdagger.core.tool.utils.WebUtil;
import com.springdagger.core.web.annotation.RedupSubmit;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
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
public class RedupSubmitAspect {

    @Autowired
    RedisUtil redisUtil;

    @Before(
            "@annotation(redupSubmit)&&" +
            "(@within(org.springframework.stereotype.Controller) ||" +
            "@within(org.springframework.web.bind.annotation.RestController))"
             )
    public void requestLimit(RedupSubmit redupSubmit) {
        String requestURI = Objects.requireNonNull(WebUtil.getRequest()).getRequestURI();
        log.info("requestUri: " + requestURI);
        String key = redupSubmit.key();
        if (StringUtil.isBlank(key)) {
            key = requestURI + ":" + UserContextHolder.getUserId();
        }

        if (redisUtil.hasKey(key)) {
            String msg = StringUtil.isBlank(redupSubmit.msg()) ? ResultCode.REDUP_SUBMIT_ERROR.getMessage() : redupSubmit.msg();
            throw new RedupSubmitException(msg);
        }

        redisUtil.set(key, "redupSubmit", redupSubmit.expire());
    }
}
