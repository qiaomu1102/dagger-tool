package com.springdagger.core.secure.interceptor;

import com.alibaba.fastjson.JSON;
import com.springdagger.core.secure.utils.SecureUtil;
import com.springdagger.core.tool.api.R;
import com.springdagger.core.tool.api.ResultCode;
import com.springdagger.core.tool.constant.DaggerConstant;
import com.springdagger.core.tool.utils.WebUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * jwt拦截器校验
 *
 * @author Chill
 */
@Slf4j
@AllArgsConstructor
public class SecureInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		if (null != SecureUtil.getUser()) {
			return true;
		} else {
			log.warn("签名认证失败，请求接口：{}，请求IP：{}，请求参数：{}", request.getRequestURI(), WebUtil.getIP(request), JSON.toJSONString(request.getParameterMap()));
			R result = R.fail(ResultCode.UN_AUTHORIZED);
			response.setCharacterEncoding(DaggerConstant.UTF_8);
			response.setHeader(DaggerConstant.CONTENT_TYPE_NAME, MediaType.APPLICATION_JSON_VALUE);
			response.setStatus(HttpServletResponse.SC_OK);
			try {
				response.getWriter().write(Objects.requireNonNull(JSON.toJSONString(result)));
			} catch (IOException ex) {
				log.error(ex.getMessage());
			}
			return false;
		}
	}

}
