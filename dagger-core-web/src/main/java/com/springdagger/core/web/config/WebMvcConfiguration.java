package com.springdagger.core.web.config;

import com.springdagger.core.web.interceptor.SecureInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @package: com.qiaomu.common.aop
 * @author: kexiong
 * @date: 2020/4/10 18:44
 * @Description: TODO
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Resource
    private SecureInterceptor secureInterceptor;

    @Resource
    private SecureConfig secureConfig;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (secureConfig.isEnable()) {
            registry.addInterceptor(secureInterceptor)
                    .excludePathPatterns(secureConfig.getDefaultExcludePatterns())
                    .excludePathPatterns(secureConfig.getExcludePatterns())
                    .excludePathPatterns(secureConfig.getSkipUrls());
        }
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/static/**")
                .addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX +"/static/");
    }
}
