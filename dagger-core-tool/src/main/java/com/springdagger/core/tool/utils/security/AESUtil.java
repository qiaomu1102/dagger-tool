package com.springdagger.core.tool.utils.security;

import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;

/**
 * @author: kexiong
 * @date: 2020/6/19 14:57
 * @Description: TODO
 */
public class AESUtil {
    private static final String AES = "AES";
    private static final String UTF_ENCODE = "utf-8";

    /**
     * 算法
     */
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";// 加密算法/工作模式/填充方式


    /**
     * 密钥字符长度错误！长度必须为16、24和32
     *
     * @param keySpec 秘钥
     * @param content
     * @return
     * @throws Exception
     */
    public static String decoder(String keySpec, String content) {
        try {
            if (content == null || content.trim().length() == 0) {
                return null;
            }
            //先用Base64解码
            byte[] byteContent = Base64Utils.decodeFromString(content);
            Key key = new SecretKeySpec(keySpec.getBytes(), AES);
            Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
            //初始化，此方法可以采用三种方式，按服务器要求来添加。（1）无第三个参数（2）第三个参数为SecureRandom random = new SecureRandom();中random对象，随机数。(AES不可采用这种方法)（3）采用此代码中的IVParameterSpec
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] ret = cipher.doFinal(byteContent);
            return new String(ret, UTF_ENCODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 密钥字符长度错误！长度必须为16、24和32
     *
     * @param key     秘钥
     * @param content
     * @return
     * @throws Exception
     */
    public static String encrypt(String key, String content) {
        try {
            byte[] raw = key.getBytes(UTF_ENCODE);
            Key keySpec = new SecretKeySpec(raw, AES);
            Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
            //初始化，此方法可以采用三种方式，按服务器要求来添加。（1）无第三个参数（2）第三个参数为SecureRandom random = new SecureRandom();中random对象，随机数。(AES不可采用这种方法)（3）采用此代码中的IVParameterSpec
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] byteAes = cipher.doFinal(content.getBytes(UTF_ENCODE));
            return Base64Utils.encodeToString(byteAes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 加密
     * 1.构造密钥生成器
     * 2.根据ecnodeRules规则初始化密钥生成器
     * 3.产生密钥
     * 4.创建和初始化密码器
     * 5.内容加密
     * 6.返回字符串
     */
    public static String aesEncode(String encodeRules, String content) {
        try {
            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen = KeyGenerator.getInstance(AES);
            //2.根据ecnodeRules规则初始化密钥生成器
            //生成一个128位的随机源,根据传入的字节数组
            keygen.init(128, new SecureRandom(encodeRules.getBytes()));
            //3.产生原始对称密钥
            SecretKey originalKey = keygen.generateKey();
            //4.获得原始对称密钥的字节数组
            byte[] raw = originalKey.getEncoded();
            //5.根据字节数组生成AES密钥
            SecretKey key = new SecretKeySpec(raw, AES);
            //6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte[] byteEncode = content.getBytes(UTF_ENCODE);
            //9.根据密码器的初始化方式--加密：将数据加密
            byte[] byteAes = cipher.doFinal(byteEncode);
            //10.将加密后的数据转换为字符串
            //解决办法：
            //在项目的Build path中先移除JRE System Library，再添加库JRE System Library，重新编译后就一切正常了。
            return Base64Utils.encodeToString(byteAes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     * 解密过程：
     * 1.同加密1-4步
     * 2.将加密后的字符串反编成byte[]数组
     * 3.将加密内容解密
     */
    public static String aesDecode(String encodeRules, String content) {
        try {
            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            //2.根据ecnodeRules规则初始化密钥生成器
            //生成一个128位的随机源,根据传入的字节数组
            keygen.init(128, new SecureRandom(encodeRules.getBytes()));
            //3.产生原始对称密钥
            SecretKey originalKey = keygen.generateKey();
            //4.获得原始对称密钥的字节数组
            byte[] raw = originalKey.getEncoded();
            //5.根据字节数组生成AES密钥
            SecretKey key = new SecretKeySpec(raw, "AES");
            //6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, key);
            //8.将加密并编码后的内容解码成字节数组
            byte[] byteContent = Base64Utils.decodeFromString(content);
            /*
             * 解密
             */
            byte[] byteDecode = cipher.doFinal(byteContent);
            return new String(byteDecode, UTF_ENCODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String key = "htbx%phone202012";
        String phone = "13798455092";
        String encrypt = encrypt(key, phone);
        System.out.println("AES加密密钥： " + key);
        System.out.println("AES加密明文： " + phone);
        System.out.println("AES加密密文： " + encrypt);

        long currentTimeMillis = System.currentTimeMillis();
        String md5 = Md5Util.md5(encrypt + currentTimeMillis + "ht&MD5key&202012");
        System.out.println("当前时间戳： " + currentTimeMillis);
        System.out.println("md5加密盐值： " + "ht&MD5key&202012");
        System.out.println("md5密文： " + md5);

        String phone1 = decoder(key, encrypt);
        System.out.println(phone1);
    }
}
