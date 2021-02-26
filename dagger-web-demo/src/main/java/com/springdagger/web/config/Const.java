package com.springdagger.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: kexiong
 * @date: 2020/10/26 19:33
 * @Description: TODO
 */
@Component
@PropertySource(value = "classpath:application*.properties", ignoreResourceNotFound = true,encoding = "UTF-8" )
public class Const {

    public static boolean IS_PROD;
    public static String HTBB_URL;

    /**
     *没有限制，所有人都可访问的菜单
     */
//    @Value("#{'${menu.unlimited}'.split(',')}")
//    private List<String> unlimitedMenu;

    @Value("${is_prod}")
    public void setIsProd(boolean isProd) {
        Const.IS_PROD = isProd;
    }

    @Value("${htbb_url}")
    public void setHtbbUrl(String htbbUrl) {
        Const.HTBB_URL = htbbUrl;
    }
}
