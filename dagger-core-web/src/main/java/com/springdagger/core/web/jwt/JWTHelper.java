package com.springdagger.core.web.jwt;

import com.springdagger.core.tool.utils.DateUtil;
import com.springdagger.core.web.config.CommonConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.concurrent.TimeUnit;

/**
 * Created by ace on 2017/9/10.
 */
public class JWTHelper {

    private static com.htbb.core.web.jwt.RsaKeyHelper rsaKeyHelper = new com.htbb.core.web.jwt.RsaKeyHelper();

    /**
     * 生成token
     */
    public static String generateToken(String value, String priKeyStr, int expireDay) throws Exception {
        if (expireDay < 0) {
            throw new IllegalArgumentException("过期时间不能为负数");
        }
        return Jwts.builder()
                .setSubject(CommonConstants.JWT_SUBJECT)
                .claim(CommonConstants.JWT_INFO_KEY, value)
                .setExpiration(DateUtil.plusDays(DateUtil.now(), expireDay))
                .signWith(SignatureAlgorithm.RS256, rsaKeyHelper.getPrivateKey(priKeyStr))
                .compact();
    }

    /**
     * 公钥解析token
     */
    private static Jws<Claims> parserToken(String token, String pubKeyStr) throws Exception {
        return Jwts.parser().setSigningKey(rsaKeyHelper.getPublicKey(pubKeyStr)).parseClaimsJws(token);
    }

    /**
     * 获取token中的用户信息
     *
     */
    public static Object getInfoFromToken(String token, String pubKeyStr) throws Exception {
        if(token.startsWith("Bearer")){
            token = token.replace("Bearer ","");
        }
        Jws<Claims> claimsJws = parserToken(token, pubKeyStr);
        Claims body = claimsJws.getBody();
        return body.get(CommonConstants.JWT_INFO_KEY);
    }

}
