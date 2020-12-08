package com.springdagger.core.web.aop;

import com.alibaba.fastjson.JSON;
import com.springdagger.core.tool.api.R;
import com.springdagger.core.tool.utils.security.AESUtil;
import com.springdagger.core.tool.utils.security.RSAUtil;
import com.springdagger.core.web.annotation.DecryptAndEncrypt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


/**
 * @author: qiaomu
 * @date: 2020/12/7 15:01
 * @Description: 对出参进行加密
 */
@Slf4j
@ControllerAdvice
public class EncryptResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        DecryptAndEncrypt encryptAnnotation = methodParameter.getMethodAnnotation(DecryptAndEncrypt.class);
        return encryptAnnotation != null && encryptAnnotation.outEncode();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        DecryptAndEncrypt encryptAnnotation = methodParameter.getMethodAnnotation(DecryptAndEncrypt.class);
        if (encryptAnnotation == null || !encryptAnnotation.outEncode()) {
            return body;
        }

        if (body instanceof R) {
            R<Object> r = (R<Object>) body;
            String content = JSON.toJSONString(r.getData());
            log.info("beforeBodyWrite出参加密之前content=== " + content);
            switch (encryptAnnotation.getOutEncryptType()){
                case AES:
                    r.setData(AESUtil.encrypt(encryptAnnotation.signKey(), content));
                case RSA:
                    r.setData(RSAUtil.encrypt(content, encryptAnnotation.signKey()));
            }
            return r;
        }
        return body;
    }
}
