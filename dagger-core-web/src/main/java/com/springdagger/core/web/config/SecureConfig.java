package com.springdagger.core.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: qiaomu
 * @date: 2020/12/3 14:23
 * @Description: TODO
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "dagger.secure")
public class SecureConfig {

    private boolean enable;

    private final List<String> defaultExcludePatterns = new ArrayList<>();

    private final List<String> excludePatterns = new ArrayList<>();

    private final List<String> skipUrls = new ArrayList<>();

    /** ErrorPage 页面是否激活 */
    private boolean error_page_enable;

    /** errorPage页面地址 */
    private String error_page_path = "/default_error.html";

    public SecureConfig() {
        this.defaultExcludePatterns.add("/actuator/health/**");
        this.defaultExcludePatterns.add("/v2/api-docs/**");
        this.defaultExcludePatterns.add("/v2/api-docs-ext/**");
        this.defaultExcludePatterns.add("/swagger-resources");
        this.defaultExcludePatterns.add("/webjars/**");
        this.defaultExcludePatterns.add("/doc.html");
        this.defaultExcludePatterns.add("/service-worker.js");
        this.defaultExcludePatterns.add("/error");
        if (error_page_enable) {
            this.defaultExcludePatterns.add(error_page_path);
        }
    }

}
