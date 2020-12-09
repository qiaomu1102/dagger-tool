package com.springdagger.core.web.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author: qiaomu
 * @date: 2020/12/9 13:50
 * @Description: TODO
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {

    /**
     * 包名
     */
    private String basePackage;
    /**
     * 标题
     **/
    private String title = "SpringDagger 接口文档系统";
    /**
     * 描述
     **/
    private String description = "SpringDagger 接口文档系统";
    /**
     * 版本
     **/
    private String version = "1.0.0";
    /**
     * 服务条款URL
     **/
    private String termsOfServiceUrl = "";
    /**
     * 联系人信息
     */
    private Contact contact = new Contact();

    @Data
    @NoArgsConstructor
    public static class Contact {

        /**
         * 联系人
         **/
        private String name = "qiaomu";
        /**
         * 联系人url
         **/
        private String url = "";
        /**
         * 联系人email
         **/
        private String email = "kexiong1102@163.com";

    }

}
