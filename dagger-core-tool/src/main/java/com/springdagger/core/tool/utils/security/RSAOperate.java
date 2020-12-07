package com.springdagger.core.tool.utils.security;

import java.util.Map;

/**
 * rsa相关操作
 *
 * @author liu yucheng
 * @date 2018/12/16
 */
public interface RSAOperate {
    /**
     * 公钥加密
     *
     * @param encryptedData 加密串
     */
    String encryptByPublicKey(String encryptedData);

    /**
     * 私钥加密
     *
     * @param encryptedData 加密串
     */
    String encryptByPrivateKey(String encryptedData);

    /**
     * 公钥解密
     *
     * @param decryptData 解密串
     * @return 结果
     */
    String decryptByPublicKey(String decryptData);

    /**
     * 私钥解密
     *
     * @param decryptData 解密串
     * @return 结果
     */
    String decryptByPrivateKey(String decryptData);

    /**
     * 获取私钥
     *
     * @param keyMap 密钥对
     */
    String getPrivateKey(Map<String, Object> keyMap);

    /**
     * 获取公钥
     *
     * @param keyMap 密钥对
     */
    String getPublicKey(Map<String, Object> keyMap);
}
