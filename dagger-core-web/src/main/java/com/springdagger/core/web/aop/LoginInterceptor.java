package com.springdagger.core.web.aop;

import com.springdagger.core.tool.api.BizException;
import com.springdagger.core.tool.api.ResultCode;
import com.springdagger.core.tool.utils.ClassUtil;
import com.springdagger.core.web.annotation.IgnoreUserToken;
import com.springdagger.core.web.config.CommonConstants;
import com.springdagger.core.web.jwt.IJWTInfo;
import com.springdagger.core.web.jwt.UserAuthUtil;
import com.springdagger.core.web.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @package: com.qiaomu.common.aop
 * @author: kexiong
 * @date: 2020/4/10 18:37
 * @Description: 拦截登陆状态
 */
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserAuthUtil userAuthUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("LoginInterceptor: ===================================");
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 配置该注解，说明不进行用户拦截
        IgnoreUserToken annotation = ClassUtil.getAnnotation(handlerMethod, IgnoreUserToken.class);
        if (annotation != null) {
            return true;
        }

        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            throw new BizException(ResultCode.LOGIN_EXPIRED);
        }
        IJWTInfo ijwtInfo = userAuthUtil.checkToken(token);
        UserModel userModel = new UserModel();
        userModel.setUserId(ijwtInfo.getId());
        userModel.setUserName(ijwtInfo.getUniqueName());
        request.setAttribute(CommonConstants.CURRENT_USER, userModel);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }
}
