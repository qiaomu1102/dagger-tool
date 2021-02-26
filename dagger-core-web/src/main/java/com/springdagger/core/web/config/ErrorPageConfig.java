package com.springdagger.core.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * @author: qiaomu
 * @date: 2021/2/4 15:06
 * @Description: TODO
 */
@Configuration
@ConditionalOnProperty(prefix = "dagger.common",name = "error-page-enable",havingValue = "true")
public class ErrorPageConfig implements ErrorPageRegistrar {

    @Autowired
    SecureConfig secureConfig;

    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        System.out.println("\nerror_page_path:  " + secureConfig.getError_page_path());

        ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, secureConfig.getError_page_path());
        ErrorPage error405Page = new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED, secureConfig.getError_page_path());
        ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, secureConfig.getError_page_path());
        registry.addErrorPages(error404Page, error405Page, error500Page);
    }
}
