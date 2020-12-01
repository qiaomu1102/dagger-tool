package com.springdagger.core.web.jwt;

import com.springdagger.core.web.config.PropertiesConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ace on 2017/9/10.
 */
@Component
public class JwtTokenUtil {


    public String generateToken(IJWTInfo jwtInfo) throws Exception {
        return JWTHelper.generateToken(jwtInfo, PropertiesConfig.JWT_PRIVATE_KEY,3);
    }

    public IJWTInfo getInfoFromToken(String token) throws Exception {
        return JWTHelper.getInfoFromToken(token, PropertiesConfig.JWT_PUBLIC_KEY);
    }


}
