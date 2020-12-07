package com.springdagger.core.tool.utils.security;

import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @package: com.qiaomu.utils
 * @author: kexiong
 * @date: 2019/5/17 11:34
 * @Description: TODO
 *
 * 实现基本上就是这样，都是大同小异。不过，问题来了，结下来才是重点。
 * 1. RSA加密算法对于加密数据的长度是有要求的。一般来说，明文长度小于等于密钥长度（Bytes）-11。
 *      解决这个问题需要对较长的明文进行分段加解密，这个上面的代码已经实现了。
 * 2. 一旦涉及到双方开发，语言又不相同，不能够采用同一个工具的时候，切记要约定以下内容。
 *   a）约定双方的BASE64编码
 *   b）约定双方分段加解密的方式。我踩的坑也主要是这里，不仅仅是约定大家分段的大小，
 *      更重要的是分段加密后的拼装方式。doFinal方法加密完成后得到的仍然是byte[]，
 *   因为最终呈现的是编码后的字符串，所以你可以分段加密，分段编码和分段加密，
 *   一次编码两种方式（上面的代码采用的是后一种，也推荐采用这一种）。相信我不是所有人的脑回路都一样的，尤其是当他采用的开发语言和你不通时。
 */
public class RSAUtil {
    public static final String CHARSET = "UTF-8";
    public static final String RSA_ALGORITHM = "RSA";


    public static Map<String, String> createKeys(int keySize){
        //为RSA算法创建一个KeyPairGenerator对象
        KeyPairGenerator kpg;
        try{
            kpg = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        }catch(NoSuchAlgorithmException e){
            throw new IllegalArgumentException("No such algorithm-->[" + RSA_ALGORITHM + "]");
        }

        //初始化KeyPairGenerator对象,密钥长度
        kpg.initialize(keySize);
        //生成密匙对
        KeyPair keyPair = kpg.generateKeyPair();
        //得到公钥
        Key publicKey = keyPair.getPublic();
        String publicKeyStr = Base64Utils.encodeToString(publicKey.getEncoded());
        //得到私钥
        Key privateKey = keyPair.getPrivate();
        String privateKeyStr = Base64Utils.encodeToString(privateKey.getEncoded());
        Map<String, String> keyPairMap = new HashMap<String, String>();
        keyPairMap.put("publicKey", publicKeyStr);
        keyPairMap.put("privateKey", privateKeyStr);

        return keyPairMap;
    }

    /**
     * 得到公钥
     * @param publicKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    private static RSAPublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //通过X509编码的Key指令获得公钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64Utils.decodeFromString(publicKey));
        return (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
    }

    /**
     * 得到私钥
     * @param privateKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    private static RSAPrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //通过PKCS#8编码的Key指令获得私钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64Utils.decodeFromString(privateKey));
        return (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
    }

    /**
     * 公钥加密(长字符串)
     * @param data
     * @param publicKey
     * @return
     */
    public static String publicEncryptLong(String data, String publicKey){
        try{
            RSAPublicKey rasPublicKey = getPublicKey(publicKey);
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, rasPublicKey);
            return Base64Utils.encodeToString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), rasPublicKey.getModulus().bitLength()));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 私钥解密(长字符串)
     * @param data
     * @param privateKey
     * @return
     */

    public static String privateDecryptLong(String data, String privateKey){
        try{
            RSAPrivateKey rsaPrivateKey = getPrivateKey(privateKey);
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64Utils.decodeFromString(data), rsaPrivateKey.getModulus().bitLength()), CHARSET);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 私钥加密(长字符串)
     * @param data
     * @param privateKey
     * @return
     */

    public static String privateEncryptLong(String data, String privateKey){
        try{
            RSAPrivateKey rsaPrivateKey = getPrivateKey(privateKey);
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, rsaPrivateKey);
            return Base64Utils.encodeToString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), rsaPrivateKey.getModulus().bitLength()));
        }catch(Exception e){
           e.printStackTrace();
        }

        return null;
    }

    /**
     * 公钥解密(长字符串)
     * @param data
     * @param publicKey
     * @return
     */

    public static String publicDecryptLong(String data, String publicKey){
        try{
            RSAPublicKey rasPublicKey = getPublicKey(publicKey);
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, rasPublicKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64Utils.decodeFromString(data), rasPublicKey.getModulus().bitLength()), CHARSET);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 分段加密
     * @param cipher
     * @param opmode
     * @param datas
     * @param keySize
     * @return
     */
    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize){
        int maxBlock = 0;
        if(opmode == Cipher.DECRYPT_MODE){
            maxBlock = keySize / 8;
        }else{
            maxBlock = keySize / 8 - 11;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buff;
        int i = 0;
        try{
            while(datas.length > offSet){
                if(datas.length-offSet > maxBlock){
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                }else{
                    buff = cipher.doFinal(datas, offSet, datas.length-offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
        }catch(Exception e){
            throw new RuntimeException("加解密阀值为["+maxBlock+"]的数据时发生异常", e);
        }
        byte[] resultDatas = out.toByteArray();
        try {
            out.close();
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
        return resultDatas;
    }

    /**
     * 公钥加密（短字符串）
     * @param content 加密内容
     * @param publicKey 公钥
     * @return
     * @throws Exception
     */
    public static String encrypt( String content, String publicKey){
        try {
            //base64编码的公钥
            byte[] decoded = Base64Utils.decodeFromString(publicKey);
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance(RSA_ALGORITHM).generatePublic(new X509EncodedKeySpec(decoded));
            //RSA加密
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            return Base64Utils.encodeToString(cipher.doFinal(content.getBytes(CHARSET)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * RSA 解密（短字符串）
     * @param content 解密的密文
     * @param privateKey 私钥
     * @return
     * @throws Exception
     */
    public static String decrypt(String content, String privateKey){
        try {
            //64位解码加密后的字符串
            byte[] inputByte = Base64Utils.decode(content.getBytes(CHARSET));
            //base64编码的私钥
            byte[] decoded = Base64Utils.decodeFromString(privateKey);
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance(RSA_ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(decoded));
            //RSA解密
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            return new String(cipher.doFinal(inputByte));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static void main (String[] args) throws Exception {
//        Map<String, String> keyMap = RSAUtil.createKeys(2048);
//        String  publicKey = keyMap.get("publicKey");
//        String  privateKey = keyMap.get("privateKey");
//        System.out.println("公钥: \n\r" + publicKey);
//        System.out.println("公钥长度: \n\r" + publicKey.length());
//        System.out.println("私钥： \n\r" + privateKey);
//        System.out.println("私钥长度： \n\r" + privateKey.length());
//
//        System.out.println("公钥加密——私钥解密");
//        long startTime = System.currentTimeMillis();
//        String str = "站在大明门前守卫的禁卫军，事先没有接到\n" +
//                "有关的命令，但看到大批盛装的官员来临，也就\n" +
//                "以为确系举行大典，因而未加询问。进大明门即\n" +
//                "为皇城。文武百官看到端门午门之前气氛平静，\n" +
//                "城楼上下也无朝会的迹象，既无几案，站队点名\n" +
//                "的御史和御前侍卫“大汉将军”也不见踪影，不免\n" +
//                "心中揣测，互相询问：所谓午朝是否讹传？";
//        System.out.println("\r明文：\r\n" + str);
//        System.out.println("\r明文大小：\r\n" + str.getBytes().length);
//        String encodedData = RSAUtil.publicEncryptLong(str, publicKey);
////        String encodedData = encrypt(str, publicKey);
//        System.out.println("密文：\r\n" + encodedData);
//        String md5 = Md5Util.md5(encodedData);
//        System.out.println("md5: \r\n" + md5);
//        long endTime = System.currentTimeMillis();
//        System.out.println("加密时间：\r\n" + (endTime - startTime));
//        String decodedData = RSAUtil.privateDecryptLong(encodedData, privateKey);
////        String decodedData = decrypt(encodedData, privateKey);
//        System.out.println("解密后文字: \r\n" + decodedData);
//        System.out.println("解密时间: \r\n" + (System.currentTimeMillis() - endTime));
//
//    }

}

