package com.springdagger.core.tool.utils.security;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * @package: com.qiaomu.utils
 * @author: kexiong
 * @date: 2019/5/17 11:42
 * @Description: MD5加密
 */
public class Md5Util {

    /***
     * MD5加码 生成32位md5码
     */
    public static String md5(String data) {
        MessageDigest md5 = null;
        try{
            md5 = MessageDigest.getInstance("MD5");
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
        byte[] md5Bytes = md5.digest(data.getBytes(StandardCharsets.UTF_8));
        return byte2Hex(md5Bytes);
    }

    private static String byte2Hex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        String stmp = "";
        for (int n = 0; (bytes != null) && (n < bytes.length); ++n) {
            stmp = Integer.toHexString(bytes[n] & 0xFF);
            if (stmp.length() == 1) {
                builder.append("0").append(stmp);
            } else {
                builder.append(stmp);
            }
        }
        return builder.toString().toLowerCase();
    }

    // 测试主函数
//    public static void main(String args[]) {
//        String s = "htapp1233";
//        System.out.println("原始：" + s);
//        System.out.println("MD5后：" + md5(s));
//        System.out.println("MD5后：" + md5(s).length());
//    }
}
