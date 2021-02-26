package com.springdagger.core.web.interceptor;

import com.htbb.core.web.jwt.UserAuthUtil;
import com.htbb.core.web.model.UserModel;
import com.springdagger.core.tool.api.ResultCode;
import com.springdagger.core.tool.utils.ClassUtil;
import com.springdagger.core.tool.utils.StringUtil;
import com.springdagger.core.web.annotation.IgnoreUserToken;
import com.springdagger.core.web.exception.SecureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @package: com.qiaomu.common.aop
 * @author: kexiong
 * @date: 2020/4/10 18:37
 * @Description: 鉴权
 */
@Slf4j
@Component
public class SecureInterceptor implements HandlerInterceptor {

    private static final String SECURE_KEY = "token";

    @Autowired
    private UserAuthUtil userAuthUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            IgnoreUserToken annotation = ClassUtil.getAnnotation(handlerMethod, IgnoreUserToken.class);
            if (annotation != null) {
                return true;
            }
        }

        log.info("请求URI为{}，验证token begin ============", request.getRequestURI());

        String token = request.getHeader(SECURE_KEY);
        if (StringUtil.isBlank(token)) {
            Cookie cookie = WebUtils.getCookie(request, SECURE_KEY);
            if (cookie != null) {
                token = cookie.getValue();
            }
        }

        if (StringUtil.isBlank(token)) {
            throw new SecureException(ResultCode.TOKEN_MISSING);
        }
        userAuthUtil.checkToken(token);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }
}
