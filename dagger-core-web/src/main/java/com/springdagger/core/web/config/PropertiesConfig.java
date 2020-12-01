package com.springdagger.core.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author: kexiong
 * @date: 2020/10/20 11:02
 * @Description: TODO
 */
@Configuration
public class PropertiesConfig {
    public static boolean IS_PROD;
    public static String JWT_PUBLIC_KEY;
    public static String JWT_PRIVATE_KEY;

    @Value("${is_prod}")
    public void setIsProd(boolean is_prod) {
        PropertiesConfig.IS_PROD = is_prod;
    }

    @Value("${jwt_public_key}")
    public void setJwtPublicKey(String jwtPublicKey) {
        PropertiesConfig.JWT_PUBLIC_KEY = jwtPublicKey;
    }

    @Value("${jwt_private_key}")
    public void setJwtPrivateKey(String jwtPrivateKey) {
        PropertiesConfig.JWT_PRIVATE_KEY = jwtPrivateKey;
    }
}
