package com.springdagger.core.tool.utils.security;

import com.springdagger.core.tool.utils.Base64Util;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA公钥/私钥/签名工具包
 * 罗纳德·李维斯特（Ron [R]ivest）、阿迪·萨莫尔（Adi [S]hamir）和伦纳德·阿德曼（Leonard [A]dleman）
 * 字符串格式的密钥在未在特殊说明情况下都为BASE64编码格式<br/>
 * 由于非对称加密速度极其缓慢，一般文件不使用它来加密而是使用对称加密，<br/>
 * 非对称加密算法可以用来对对称加密的密钥加密，这样保证密钥的安全也就保证了数据的安全
 *
 * @author leo
 * @version 1.0
 */
public class DefaultRSAOperate implements RSAOperate {

    /**
     * 加密算法RSA
     */
    private static final String KEY_ALGORITHM = "RSA";

    /**
     * 签名算法
     */
    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";

    /**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    // 密钥对数据
    private CustomKeyPair customKeyPair;

    private DefaultRSAOperate(CustomKeyPair customKeyPair) {
        this.customKeyPair = customKeyPair;
    }

    public static DefaultRSAOperate from(CustomKeyPair customKeyPair) {
        return new DefaultRSAOperate(customKeyPair);
    }

    /**
     * 公钥加密
     *
     * @param encryptedData 加密串
     */
    @Override
    public String encryptByPublicKey(String encryptedData) {
        return encrypt(encryptedData, 0);
    }

    /**
     * 私钥加密
     *
     * @param encryptedData 加密串
     */
    @Override
    public String encryptByPrivateKey(String encryptedData) {

        return encrypt(encryptedData, 1);
    }

    /**
     * 公钥解密
     *
     * @param decryptData 解密串
     * @return 结果
     */
    @Override
    public String decryptByPublicKey(String decryptData) {

        return decrypt(decryptData, 0);
    }

    /**
     * 私钥解密
     *
     * @param decryptData 解密串
     * @return 结果
     */
    @Override
    public String decryptByPrivateKey(String decryptData) {

        return decrypt(decryptData, 1);
    }

    /**
     * 公钥加密
     *
     * @param encryptedData 加密串
     * @param type          0公钥/1私钥
     */
    private String encrypt(String encryptedData, int type) {
        if (StringUtils.isEmpty(encryptedData)) {
            return "";
        }

        byte[] decodedData;
        try {
            if (0 == type) {
                decodedData = encryptByPublicKey(encryptedData.getBytes());
            } else {
                decodedData = encryptByPrivateKey(encryptedData.getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();

            return "";
        }

        return Base64Utils.encodeToString(decodedData);
    }

    /**
     * 解密
     *
     * @param decryptData 解密串
     * @param type        0公钥/1私钥
     */
    private String decrypt(String decryptData, int type) {
        if (StringUtils.isEmpty(decryptData)) {
            return "";
        }
        byte[] data = Base64Utils.decode(decryptData.getBytes());
        byte[] decodedData;
        try {
            if (0 == type) {
                decodedData = decryptByPublicKey(data);
            } else {
                decodedData = decryptByPrivateKey(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return new String(decodedData, StandardCharsets.UTF_8);
    }

    /**
     * 私钥解密
     *
     * @param encryptedData 已加密数据
     */
    private byte[] decryptByPrivateKey(byte[] encryptedData) throws Exception {
        byte[] keyBytes = Base64Util.decodeFromString(customKeyPair.getPrivateKey());
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);

        return blockEncrypt(encryptedData, cipher, MAX_DECRYPT_BLOCK);
    }

    /**
     * 公钥解密
     *
     * @param encryptedData 已加密数据
     */
    private byte[] decryptByPublicKey(byte[] encryptedData) throws Exception {
        byte[] keyBytes = Base64Util.decodeFromString(customKeyPair.getPublicKey());
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);

        return blockEncrypt(encryptedData, cipher, MAX_DECRYPT_BLOCK);
    }

    /**
     * 公钥加密
     *
     * @param data 源数据
     */
    private byte[] encryptByPublicKey(byte[] data) throws Exception {
        byte[] keyBytes = Base64Util.decodeFromString(customKeyPair.getPublicKey());
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);

        return blockEncrypt(data, cipher, MAX_ENCRYPT_BLOCK);
    }

    /**
     * 私钥加密
     *
     * @param data 源数据
     */
    private byte[] encryptByPrivateKey(byte[] data) throws Exception {
        byte[] keyBytes = Base64Util.decodeFromString(customKeyPair.getPrivateKey());
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);

        return blockEncrypt(data, cipher, MAX_ENCRYPT_BLOCK);
    }

    /**
     * 分段加密
     */
    private byte[] blockEncrypt(byte[] data, Cipher cipher, int maxBlock) throws BadPaddingException, IllegalBlockSizeException, IOException {
        int inputLen = data.length;
        int offSet = 0;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] cache;
        int i = 0;
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > maxBlock) {
                cache = cipher.doFinal(data, offSet, maxBlock);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * maxBlock;
        }

        byte[] decryptedData = out.toByteArray();
        out.close();

        return decryptedData;
    }

    /**
     * 生成密钥对(公钥和私钥)
     */
    private Map<String, Object> genKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * 用私钥对信息生成数字签名
     *
     * @param data       已加密数据
     * @param privateKey 私钥(BASE64编码)
     */
    private String sign(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64Util.decodeFromString(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);

        return new String(Base64Util.decode(signature.sign()));
    }

    /**
     * 校验数字签名
     *
     * @param data      已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @param sign      数字签名
     */
    private boolean verify(byte[] data, String publicKey, String sign) throws Exception {
        byte[] keyBytes = Base64Util.decodeFromString(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(Base64Util.decodeFromString(sign));
    }

    /**
     * 获取私钥
     *
     * @param keyMap 密钥对
     */
    @Override
    public String getPrivateKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return new String(Base64Util.decode(key.getEncoded()));
    }

    /**
     * 获取公钥
     *
     * @param keyMap 密钥对
     */
    @Override
    public String getPublicKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return new String(Base64Util.decode(key.getEncoded()));
    }
}