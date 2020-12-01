package com.springdagger.core.web.jwt;

import com.springdagger.core.tool.api.BizException;
import com.springdagger.core.tool.api.ResultCode;
import com.springdagger.core.web.config.PropertiesConfig;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.context.annotation.Configuration;

import java.security.SignatureException;

/**
 * Created by ace on 2017/9/15.
 */
@Configuration
public class UserAuthUtil {

    public IJWTInfo checkToken(String token) throws Exception {
        try {
            return JWTHelper.getInfoFromToken(token, PropertiesConfig.JWT_PUBLIC_KEY);
        }catch (ExpiredJwtException ex){
            throw new BizException(ResultCode.LOGIN_EXPIRED, "User token expired!");
        }catch (SignatureException ex){
            throw new BizException(ResultCode.LOGIN_EXPIRED, "User token signature error!");
        }catch (IllegalArgumentException ex){
            throw new BizException(ResultCode.LOGIN_EXPIRED, "User token is null or empty!");
        }
    }
}
