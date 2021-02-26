package com.springdagger.core.web.jwt;

import com.springdagger.core.tool.api.ResultCode;
import com.springdagger.core.web.config.CommonConfig;
import com.springdagger.core.web.context.UserContextHolder;
import com.springdagger.core.web.exception.SecureException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ace on 2017/9/15.
 */
@Configuration
@Slf4j
public class UserAuthUtil {

    @Autowired
    CommonConfig commonConfig;

    public String checkToken(String token) {
        try {
            String tokenInfo = (String) JWTHelper.getInfoFromToken(token, commonConfig.getJwt_public_key());
            log.info("user token : {}", tokenInfo);
            UserContextHolder.create(tokenInfo);
            return tokenInfo;
        } catch (ExpiredJwtException ex) {
            throw new SecureException(ResultCode.LOGIN_EXPIRED, "User token expired!");
        } catch (IllegalArgumentException ex) {
            throw new SecureException(ResultCode.TOKEN_MISSING, "User token is null or empty!");
        } catch (Exception ex) {
            throw new SecureException(ResultCode.TOKEN_UN_AUTHORIZED, "User token signature error!");
        }
    }
}
