package com.springdagger.core.web.jwt;

import com.springdagger.core.tool.utils.DateUtil;
import com.springdagger.core.web.config.CommonConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Created by ace on 2017/9/10.
 */
public class JWTHelper {
    private static RsaKeyHelper rsaKeyHelper = new RsaKeyHelper();

    /**
     * 密钥加密token
     */
    public static String generateToken(IJWTInfo jwtInfo, String priKeyStr, int expire) throws Exception {
        return Jwts.builder()
                .setSubject(jwtInfo.getUniqueName())
                .claim(CommonConstants.JWT_KEY_USER_ID, jwtInfo.getId())
                .claim(CommonConstants.JWT_KEY_NAME, jwtInfo.getName())
                .setExpiration(DateUtil.plusDays(DateUtil.now(), expire))
                .signWith(SignatureAlgorithm.RS256, rsaKeyHelper.getPrivateKey(priKeyStr))
                .compact();
    }

    /**
     * 密钥加密token
     *
     */
    public static String generateToken(IJWTInfo jwtInfo, byte priKey[], int expire) throws Exception {
        return Jwts.builder()
                .setSubject(jwtInfo.getUniqueName())
                .claim(CommonConstants.JWT_KEY_USER_ID, jwtInfo.getId())
                .claim(CommonConstants.JWT_KEY_NAME, jwtInfo.getName())
                .claim(CommonConstants.JWT_ID, jwtInfo.getTokenId())
                .setExpiration(DateUtil.plusDays(DateUtil.now(), expire))
                .signWith(SignatureAlgorithm.RS256, rsaKeyHelper.getPrivateKey(priKey))
                .compact();
    }

    /**
     * 公钥解析token
     */
    public static Jws<Claims> parserToken(String token, String pubKeyPath) throws Exception {
        return Jwts.parser().setSigningKey(rsaKeyHelper.getPublicKey(pubKeyPath)).parseClaimsJws(token);
    }
    /**
     * 公钥解析token
     *
     */
    public static Jws<Claims> parserToken(String token, byte[] pubKey) throws Exception {
        return Jwts.parser().setSigningKey(rsaKeyHelper.getPublicKey(pubKey)).parseClaimsJws(token);
    }
    /**
     * 获取token中的用户信息
     *
     */
    public static IJWTInfo getInfoFromToken(String token, String pubKeyStr) throws Exception {
        if(token.startsWith("Bearer")){
            token = token.replace("Bearer ","");
        }
        Jws<Claims> claimsJws = parserToken(token, pubKeyStr);
        Claims body = claimsJws.getBody();
        return new JWTInfo(body.getSubject(), getObjectValue(body.get(CommonConstants.JWT_KEY_USER_ID)), getObjectValue(body.get(CommonConstants.JWT_KEY_NAME)));
    }
    /**
     * 获取token中的用户信息
     */
    public static IJWTInfo getInfoFromToken(String token, byte[] pubKey) throws Exception {
        if(token.startsWith("Bearer")){
            token = token.replace("Bearer ","");
        }
        Jws<Claims> claimsJws = parserToken(token, pubKey);
        Claims body = claimsJws.getBody();
        return new JWTInfo(body.getSubject(), getObjectValue(body.get(CommonConstants.JWT_KEY_USER_ID)), getObjectValue(body.get(CommonConstants.JWT_KEY_NAME)),getObjectValue(body.get(CommonConstants.JWT_ID)));
    }

    public static String getObjectValue(Object obj){
        return obj==null?"":obj.toString();
    }


}
