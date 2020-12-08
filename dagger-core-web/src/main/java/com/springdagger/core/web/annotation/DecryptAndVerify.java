package com.springdagger.core.web.annotation;

import com.springdagger.core.web.common.EncryptTypeEnum;
import org.springframework.core.annotation.Order;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: qiaomu
 * @date: 2020/12/7 17:15
 * @Description: TODO
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Order
public @interface DecryptAndVerify {

    /** 解密后的参数类型 */
    Class<?> decryptClass();

    /** 加密密钥 */
    String signKey() default "daggerEncryptKey";

    /**
     * 输入的参数是否加密
     */
    boolean inDecode() default true;

    /**
     * 输出的参数是否加密
     */
    boolean outEncode() default true;

    /**
     * 入参加密类型
     */
    EncryptTypeEnum getInEncryptType() default EncryptTypeEnum.AES_MD5;

    /**
     * 出参加密类型
     */
    EncryptTypeEnum getOutEncryptType() default EncryptTypeEnum.AES;
}
