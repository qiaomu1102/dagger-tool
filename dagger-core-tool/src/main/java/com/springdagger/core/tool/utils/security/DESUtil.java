package com.springdagger.core.tool.utils.security;

import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * @package: com.qiaomu.utils
 * @author: kexiong
 * @date: 2019/5/17 10:55
 * @Description: DES对称加密
 */
public class DESUtil {
    private final static String DES = "DES";
    private static final String CHARSET = "UTF-8";
    /**
     * 获得密钥
     *
     * @param secretKey
     */
    private static SecretKey generateKey(String secretKey) {

        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            DESKeySpec keySpec = new DESKeySpec(secretKey.getBytes());
            keyFactory.generateSecret(keySpec);
            return keyFactory.generateSecret(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * DES 加密
     * @param data 加密的内容
     * @param key   加密密钥
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String key) {
        try {
            if (data == null || data.trim().length() == 0) {
                throw new Exception("加密内容不能为空");
            }
            SecretKey secretKey = generateKey(key);
            Cipher cipher = Cipher.getInstance(DES);

            // 用密钥初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] bytes = cipher.doFinal(data.getBytes(CHARSET));
            return Base64Utils.encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * DES 解密
     * @param data 解密的密文
     * @param key   加密密钥
     * @return
             * @throws Exception
     */
    public static String decrypt(String data, String key) {

        try {
            if (data == null || data.trim().length() == 0) {
                throw new Exception("密文内容不能为空");
            }
            SecretKey secretKey = generateKey(key);
            Cipher cipher = Cipher.getInstance(DES);

            // 用密钥初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] bytes = cipher.doFinal(Base64Utils.decodeFromString(data));
            return new String(bytes, CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) throws Exception {
        String data = "htbx_saas_";
        String en = encrypt(data, "htbaobao");
        System.err.println("encrypt: " + en);
        String decrypt = decrypt(en, "htbaobao");
        System.err.println("decrypt: " + decrypt);
    }
}
