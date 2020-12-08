package com.springdagger.core.web.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.springdagger.core.tool.api.BizException;
import com.springdagger.core.tool.utils.BeanUtil;
import com.springdagger.core.tool.utils.ClassUtil;
import com.springdagger.core.tool.utils.StringUtil;
import com.springdagger.core.tool.utils.security.AESUtil;
import com.springdagger.core.tool.utils.security.Md5Util;
import com.springdagger.core.tool.utils.security.RSAUtil;
import com.springdagger.core.web.annotation.EncryptParameter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
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

/**
 * @author: qiaomu
 * @date: 2020/12/3 16:02
 * @Description: 弃用
 */
@Slf4j
@Deprecated
public class EncryptAspect {

    private static final String SIGN_PARAM = "sign";

//    @Before(
//            "@annotation(encryptParameter)&&" +
//            "(@within(org.springframework.stereotype.Controller) ||" +
//            "@within(org.springframework.web.bind.annotation.RestController))"
//    )
    public void decryptParameter(JoinPoint point, EncryptParameter encryptParameter) throws Throwable {
        log.info("decryptParameter: ===================================");
        if (!encryptParameter.inDecode()) {
            return;
        }

        SortedMap<String, Object> paramMap = getParams(point);

        log.info(JSON.toJSONString(paramMap));

        checkParams(paramMap);

        String signStr = getSignStr(paramMap);

        String sign = "";
        switch (encryptParameter.encryptType()) {
            case MD5:
                sign = Md5Util.md5(signStr + encryptParameter.signKey());
                break;
            case AES:
                sign = AESUtil.encrypt(encryptParameter.signKey(), signStr);
                break;
            case RSA:
                sign = RSAUtil.encrypt(signStr, encryptParameter.signKey());
                break;
        }
        if (sign == null || !sign.equals(paramMap.get(SIGN_PARAM))) {
            log.info("待签名数据=========== " + signStr);
            log.info("传过来的签名字段sign======== " + paramMap.get(SIGN_PARAM));
            log.info("签名后的字符串===== " + sign);
            throw new BizException("签名不正确");
        }


    }

    private String getSignStr(SortedMap<String, Object> paramMap) {
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<String, Object>> es = paramMap.entrySet();
        for (Map.Entry<String, Object> entry : es) {
            String key = entry.getKey();
            Object value = entry.getValue();
            String valueStr;
            if (value instanceof List ) {
                valueStr = JSON.toJSONString(value);
            } else {
                valueStr = String.valueOf(value);
            }
            if (StringUtil.isNoneBlank(valueStr) && !SIGN_PARAM.equals(key)) {
                sb.append(key).append("=").append(value).append("&");
            }
        }
        return sb.substring(0, sb.length() - 1);
    }

    private SortedMap<String, Object> getParams(JoinPoint point) {
        MethodSignature ms = (MethodSignature) point.getSignature();
        Method method = ms.getMethod();
        List<Object> list = Arrays.asList(point.getArgs());
        SortedMap<String, Object> paramMap = new TreeMap<>();
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

    private void checkParams(SortedMap<String, Object> paramMap) {
        if (!paramMap.containsKey("channelCode")) {
            throw new BizException("channelCode is missing !");
        }

        if (!paramMap.containsKey(SIGN_PARAM)) {
            throw new BizException("sign is missing !");
        }

        if (!paramMap.containsKey("timestamp")) {
            throw new BizException("timestamp is missing !");
        }
    }


//    @AfterReturning(returning = "result", pointcut = "@annotation(encryptParameter)&&" +
//            "(@within(org.springframework.stereotype.Controller) ||" +
//            "@within(org.springframework.web.bind.annotation.RestController))")
    public void encryptParameter(EncryptParameter encryptParameter, Object result) {
        String jsonString = JSON.toJSONString(result);
        log.info("encryptParameter =========== " + jsonString + "\n");
        if (!encryptParameter.outEncode()) {
            return;
        }
        JSONObject jsonObject = JSON.parseObject(jsonString);

        SortedMap<String, Object> data = (SortedMap) jsonObject.getJSONObject("data");
        String signStr = getSignStr(data);
        String sign = "";
        switch (encryptParameter.encryptType()) {
            case MD5:
                sign = Md5Util.md5(signStr + encryptParameter.signKey());
                break;
            case AES:
                sign = AESUtil.encrypt(encryptParameter.signKey(), signStr);
                break;
            case RSA:
                sign = RSAUtil.encrypt(signStr, encryptParameter.signKey());
                break;
        }
    }

}
