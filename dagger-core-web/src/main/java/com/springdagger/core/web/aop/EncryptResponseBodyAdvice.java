package com.springdagger.core.web.aop;

import com.springdagger.core.web.EncryptTypeEnum;
import com.springdagger.core.web.annotation.EncryptParameter;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.OutputStream;


/**
 * @author: qiaomu
 * @date: 2020/12/7 15:01
 * @Description: TODO
 */
//@ControllerAdvice
public class EncryptResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        EncryptParameter encryptAnnotation = methodParameter.getMethodAnnotation(EncryptParameter.class);
        return encryptAnnotation != null && encryptAnnotation.outEncode() && methodParameter.hasMethodAnnotation(ResponseBody.class);
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        EncryptParameter encryptAnnotation = methodParameter.getMethodAnnotation(EncryptParameter.class);
//        OutputStream body = serverHttpResponse.getBody();

        return null;
    }
}
