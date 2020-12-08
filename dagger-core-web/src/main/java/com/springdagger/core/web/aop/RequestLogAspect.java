package com.springdagger.core.web.aop;

import com.alibaba.fastjson.JSON;
import com.springdagger.core.tool.utils.BeanUtil;
import com.springdagger.core.tool.utils.ClassUtil;
import com.springdagger.core.tool.utils.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.InputStreamSource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @package: com.qiaomu.common.annotation.aop
 * @author: kexiong
 * @date: 2019/8/23 17:43
 * @Description: 日志打印
 */
@Slf4j
@Aspect
@Configuration
@ConditionalOnProperty(prefix = "webLog",name = "enable",havingValue = "true")
public class RequestLogAspect {

	@Around(
			"execution(!static com.springdagger.core.tool.api.R *(..)) && " +
					"(@within(org.springframework.stereotype.Controller) || " +
					"@within(org.springframework.web.bind.annotation.RestController))"
	)
	public Object aroundApi(ProceedingJoinPoint point) throws Throwable {
		log.info("RequestLogAspect============aroundApi=====================");

		Map<String, Object> paraMap = getParams(point);

		HttpServletRequest request = WebUtil.getRequest();
		String requestURI = Objects.requireNonNull(request).getRequestURI();
		String requestMethod = request.getMethod();

		// 构建成一条长 日志，避免并发下日志错乱
		StringBuilder reqLog = new StringBuilder(500);
		List<Object> reqArgs = new ArrayList<>();
		reqLog.append("\n\n================  Request Start  ================\n");
		reqLog.append("===> {}: {}");
		reqArgs.add(requestMethod);
		reqArgs.add(requestURI);
		// 请求参数
		if (paraMap.isEmpty()) {
			reqLog.append("\n");
		} else {
			reqLog.append("\nParameters: {}\n");
			reqArgs.add(JSON.toJSONString(paraMap));
		}
		reqLog.append("token:  {}\n");
		reqArgs.add(request.getHeader("token"));
		// 打印请求头
//		Enumeration<String> headers = request.getHeaderNames();
//		while (headers.hasMoreElements()) {
//			String headerName = headers.nextElement();
//			String headerValue = request.getHeader(headerName);
//			reqLog.append("===Headers===  {} : {}\n");
//			reqArgs.add(headerName);
//			reqArgs.add(headerValue);
//		}
		// 打印执行时间
		long startNs = System.nanoTime();
		try {
			Object result = point.proceed();
			// 打印返回结构体
			reqLog.append("ResponseBody:  {}\n");
			reqArgs.add(JSON.toJSONString(result));
			return result;
		} finally {
			long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
			reqLog.append("TotalTime: {} ms\n");
			reqArgs.add(tookMs);
			reqLog.append("================  Response End   ================\n");
			log.info(reqLog.toString(), reqArgs.toArray());
		}
	}

	private Map<String, Object> getParams(JoinPoint point) {
		MethodSignature ms = (MethodSignature) point.getSignature();
		Method method = ms.getMethod();
		List<Object> list = Arrays.asList(point.getArgs());
		Map<String, Object> paramMap = new HashMap<>(16);
		for (int i = 0; i < list.size(); i++) {
			Object value = list.get(i);
			// 读取方法参数
			MethodParameter methodParam = ClassUtil.getMethodParameter(method, i);
			PathVariable pathVariable = methodParam.getParameterAnnotation(PathVariable.class);
			if (pathVariable != null) {
				continue;
			}
			RequestBody requestBody = methodParam.getParameterAnnotation(RequestBody.class);
			if (requestBody != null && value != null) {
				paramMap.putAll(BeanUtil.toMap(value));
				continue;
			}

			if (value instanceof MultipartFile) {
				MultipartFile multipartFile = (MultipartFile) value;
				String name = multipartFile.getName();
				String fileName = multipartFile.getOriginalFilename();
				paramMap.put(name, fileName);
			} else if (value instanceof HttpServletRequest) {
			} else if (value instanceof WebRequest) {
			} else if (value instanceof HttpServletResponse) {
			} else if (value instanceof InputStreamSource) {
			} else if (value instanceof InputStream) {
			} else {
				String parameterName = methodParam.getParameterName();
				paramMap.put(parameterName, list.get(i));
			}
		}
		return paramMap;
	}

}
