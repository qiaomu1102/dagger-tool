package com.springdagger.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author: kexiong
 * @date: 2020/10/26 19:33
 * @Description: TODO
 */
@Configuration
public class Const {

    public static boolean IS_PROD;
    public static String HTBB_URL;

    @Value("${is_prod}")
    public void setIsProd(boolean isProd) {
        Const.IS_PROD = isProd;
    }

    @Value("${htbb_url}")
    public void setHtbbUrl(String htbbUrl) {
        Const.HTBB_URL = htbbUrl;
    }
}
