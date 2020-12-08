package com.springdagger.core.web.aop;

import com.alibaba.fastjson.JSON;
import com.springdagger.core.tool.api.BizException;
import com.springdagger.core.tool.api.R;
import com.springdagger.core.tool.utils.security.AESUtil;
import com.springdagger.core.tool.utils.security.Md5Util;
import com.springdagger.core.web.annotation.DecryptAndEncrypt;
import com.springdagger.core.web.model.EncryptedReq;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: qiaomu
 * @date: 2020/12/7 17:01
 * @Description: 弃用
 */
@Slf4j
@Deprecated
public class DecryptAndVerifyAspect {

//    @Around(
//            "@annotation(decryptAndVerify)&&" +
//             "(@within(org.springframework.stereotype.Controller) ||" +
//             "@within(org.springframework.web.bind.annotation.RestController))"
//    )
    @SuppressWarnings("unchecked")
    public Object around(ProceedingJoinPoint joinPoint, DecryptAndEncrypt decryptAndEncrypt) throws Throwable {
        log.info("DecryptAndVerifyAspect=======================");
        if (decryptAndEncrypt.inDecode()) {
            Object[] args = joinPoint.getArgs();
            if (args == null || args.length == 0) {
                throw new BizException(joinPoint.getSignature().getName() + "，参数为空");
            }
            EncryptedReq<Object> encryptedReq = null;
            for (Object obj : args) {
                if (obj instanceof EncryptedReq) {
                    encryptedReq = (EncryptedReq<Object>) obj;
                    break;
                }
            }
            if (encryptedReq == null) {
                throw new BizException(joinPoint.getSignature().getName() + "，参数中无待解密类");
            }
            String decryptedData = decryptAndVerify(decryptAndEncrypt, encryptedReq);

            Object data = JSON.parseObject(decryptedData, decryptAndEncrypt.decryptClass());
            log.info("DecryptAndVerifyAspect====data=== " + JSON.toJSONString(data));
            encryptedReq.setData(data);
        }

        Object proceed = joinPoint.proceed();
        if (proceed instanceof R) {
            R<Object> r = (R<Object>) proceed;
            log.info("DecryptAndVerifyAspect====data=== " + JSON.toJSONString(r));
            r.setData(AESUtil.encrypt(decryptAndEncrypt.signKey(), JSON.toJSONString(r.getData())));
            return r;
        }
        return proceed;
    }

    private String decryptAndVerify(DecryptAndEncrypt decryptAndEncrypt, EncryptedReq<Object> encryptedReq) {
        String sign = Md5Util.md5(encryptedReq.getEncryptedData() + encryptedReq.getTimestamp());
        if (!sign.equals(encryptedReq.getSign())) {
            throw new BizException("验签失败：" + JSON.toJSONString(encryptedReq));
        }
        String decoderStr = AESUtil.decoder(decryptAndEncrypt.signKey(), encryptedReq.getEncryptedData());
        if (decoderStr == null) {
            throw new BizException("解密失败：" + JSON.toJSONString(encryptedReq));
        }
        return decoderStr;
    }

    public static void main(String[] args) {
        long currentTimeMillis = System.currentTimeMillis();
        System.out.println(currentTimeMillis);
        Map<String, Object> map = new HashMap<>();
        map.put("age", 28);
        map.put("name", "qiaomu111");
//        String content = "{\"name\": \"qiaomu\",\"age\":23}";
//        String content = "{\"age\":23}";
        String daggerEncryptKey = AESUtil.encrypt("daggerEncryptKey", JSON.toJSONString(map));
        System.out.println(daggerEncryptKey);
        String md5 = Md5Util.md5(daggerEncryptKey + currentTimeMillis);
        System.out.println(md5);

        String daggerEncryptKey1 = AESUtil.decoder("daggerEncryptKey", "sdb2LCvMIAWHxSfOUhoqkKBlLTITCVV7DFO+tKZDTI4=");
        System.out.println(daggerEncryptKey1);
    }
}
