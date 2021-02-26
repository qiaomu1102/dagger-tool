package com.springdagger.core.web.annotation;

import com.htbb.core.web.common.EncryptTypeEnum;
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
public @interface DecryptAndEncrypt {

    /** 解密后的参数类型 */
    Class<?> decryptClass();

    /** MD5加密盐值 切忌修改默认值*/
    String md5Key() default "ht&MD5key&202012";

    /** 加密密钥 切忌修改默认值*/
    String signKey() default "HTBBEncrypt&2020";

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
     * 涉及到MD5加密，需要加盐值
     */
    EncryptTypeEnum getInEncryptType() default EncryptTypeEnum.AES_MD5;

    /**
     * 出参加密类型
     */
    EncryptTypeEnum getOutEncryptType() default EncryptTypeEnum.AES;
}
