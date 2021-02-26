package com.springdagger.core.web.aop;

import com.alibaba.fastjson.JSON;
import com.springdagger.core.tool.utils.BeanUtil;
import com.springdagger.core.tool.utils.IoUtil;
import com.springdagger.core.tool.utils.StringUtil;
import com.springdagger.core.tool.utils.security.AESUtil;
import com.springdagger.core.tool.utils.security.Md5Util;
import com.springdagger.core.tool.utils.security.RSAUtil;
import com.springdagger.core.web.annotation.DecryptAndEncrypt;
import com.springdagger.core.web.exception.VerifyException;
import com.springdagger.core.web.model.EncryptedReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.springdagger.core.web.common.EncryptTypeEnum.*;

/**
 * @author: qiaomu
 * @date: 2020/12/7 19:22
 * @Description: 对入参进行解密
 */
@Slf4j
@ControllerAdvice
public class DecryptRequestBodyAdvice implements RequestBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        DecryptAndEncrypt encryptAnnotation = methodParameter.getMethodAnnotation(DecryptAndEncrypt.class);
        return encryptAnnotation != null && encryptAnnotation.inDecode();
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) throws IOException {
        DecryptAndEncrypt encryptAnnotation = methodParameter.getMethodAnnotation(DecryptAndEncrypt.class);
        if (encryptAnnotation != null && encryptAnnotation.inDecode()) {
            return new DecryptVerifyHttpInputMessage(httpInputMessage, encryptAnnotation);
        }
        return httpInputMessage;
    }

    @Override
    public Object afterBodyRead(Object object, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return object;
    }

    @Override
    public Object handleEmptyBody(Object object, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return object;
    }

    static class DecryptVerifyHttpInputMessage implements HttpInputMessage {

        private final HttpInputMessage inputMessage;
        private final DecryptAndEncrypt decryptAndEncrypt;

        public DecryptVerifyHttpInputMessage(HttpInputMessage httpInputMessage, DecryptAndEncrypt encryptAnnotation) {
            this.inputMessage = httpInputMessage;
            this.decryptAndEncrypt = encryptAnnotation;
        }

        @Override
        public HttpHeaders getHeaders() {
            return inputMessage.getHeaders();
        }

        @SuppressWarnings("unchecked")
        @Override
        public InputStream getBody() throws IOException {
            String body = IoUtil.toString(inputMessage.getBody());
            EncryptedReq<Object> encryptedReq = JSON.parseObject(body, EncryptedReq.class);
            String decryptedData = "";
            switch (decryptAndEncrypt.getInEncryptType()) {
                case AES_MD5:
                    decryptedData = aesMd5Decrypt(decryptAndEncrypt, encryptedReq);
                    break;
                case RSA_MD5:
                    decryptedData = rsaMd5Decrypt(decryptAndEncrypt, encryptedReq);
                    break;
                case AES:
                    decryptedData = aesDecrypt(decryptAndEncrypt, encryptedReq);
                    break;
                case RSA:
                    decryptedData = rsaDecrypt(decryptAndEncrypt, encryptedReq);
                    break;
                case MD5:
                    boolean verify = md5Verify(decryptAndEncrypt, encryptedReq);
                    if (verify) {
                        return inputMessage.getBody();
                    } else {
                        throw new VerifyException("验签失败");
                    }
            }
            Object data = null;

            try {
                data = JSON.parseObject(decryptedData, decryptAndEncrypt.decryptClass());
            } catch (Exception e) {
                log.error("解析错误：" + e);
                throw new VerifyException("验签解析失败： " + decryptedData);
            }
            log.info("DecryptRequestBodyAdvice====data=== " + JSON.toJSONString(data));
            encryptedReq.setData(data);
            return new ByteArrayInputStream(JSON.toJSONString(encryptedReq).getBytes(StandardCharsets.UTF_8));
        }

        /**
         * 解密方式：密文 + 时间戳 + MD5盐值  ==  签名 ？ AES解密 ： "验签失败"
         */
        private String aesMd5Decrypt(DecryptAndEncrypt decryptAndEncrypt, EncryptedReq<Object> encryptedReq) {
            String sign = Md5Util.md5(encryptedReq.getEncryptedData() + encryptedReq.getTimestamp() + decryptAndEncrypt.md5Key());
            if (!sign.equals(encryptedReq.getSign())) {
                throw new VerifyException("验签失败：" + JSON.toJSONString(encryptedReq));
            }
            String decoderStr = AESUtil.decoder(decryptAndEncrypt.signKey(), encryptedReq.getEncryptedData());
            if (decoderStr == null) {
                throw new VerifyException("解密失败：" + JSON.toJSONString(encryptedReq));
            }
            return decoderStr;
        }

        /**
         * 解密方式：密文 + 时间戳 + MD5盐值  ==  签名 ？ RSA解密 ： "验签失败"
         */
        private String rsaMd5Decrypt(DecryptAndEncrypt decryptAndEncrypt, EncryptedReq<Object> encryptedReq) {
            String sign = Md5Util.md5(encryptedReq.getEncryptedData() + encryptedReq.getTimestamp() + decryptAndEncrypt.md5Key());
            if (!sign.equals(encryptedReq.getSign())) {
                throw new VerifyException("验签失败：" + JSON.toJSONString(encryptedReq));
            }
            String decoderStr = RSAUtil.decrypt(encryptedReq.getEncryptedData(), decryptAndEncrypt.signKey());
            if (decoderStr == null) {
                throw new VerifyException("解密失败：" + JSON.toJSONString(encryptedReq));
            }
            return decoderStr;
        }

        private String rsaDecrypt(DecryptAndEncrypt decryptAndEncrypt, EncryptedReq<Object> encryptedReq) {
            String decoderStr = RSAUtil.decrypt(encryptedReq.getEncryptedData(), decryptAndEncrypt.signKey());
            if (decoderStr == null) {
                throw new VerifyException("解密失败：" + JSON.toJSONString(encryptedReq));
            }
            return decoderStr;
        }

        private String aesDecrypt(DecryptAndEncrypt decryptAndEncrypt, EncryptedReq<Object> encryptedReq) {
            String decoderStr = AESUtil.decoder(decryptAndEncrypt.signKey(), encryptedReq.getEncryptedData());
            if (decoderStr == null) {
                throw new VerifyException("解密失败：" + JSON.toJSONString(encryptedReq));
            }
            return decoderStr;
        }

        private boolean md5Verify(DecryptAndEncrypt decryptAndEncrypt, EncryptedReq<Object> encryptedReq) {
            SortedMap<String, Object> paramMap = new TreeMap<>(BeanUtil.toMap(encryptedReq.getData()));
            String signStr = getSignStr(paramMap);
            String sign = Md5Util.md5(signStr + encryptedReq.getTimestamp()  + decryptAndEncrypt.md5Key());
            if (!sign.equals(encryptedReq.getSign())) {
                throw new VerifyException("验签失败：" + JSON.toJSONString(encryptedReq));
            }
            return true;
        }

        private String getSignStr(SortedMap<String, Object> paramMap) {
            StringBuilder sb = new StringBuilder();
            Set<Map.Entry<String, Object>> es = paramMap.entrySet();
            for (Map.Entry<String, Object> entry : es) {
                String key = entry.getKey();
                Object value = entry.getValue();
                String valueStr;
                if (value instanceof List) {
                    valueStr = JSON.toJSONString(value);
                } else {
                    valueStr = String.valueOf(value);
                }
                if (StringUtil.isNoneBlank(valueStr)) {
                    sb.append(key).append("=").append(value).append("&");
                }
            }
            return sb.substring(0, sb.length() - 1);
        }
    }

}
