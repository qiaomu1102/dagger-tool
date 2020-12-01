package com.springdagger.core.web.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author: kexiong
 * @date: 2020/10/20 10:58
 * @Description: TODO
 */

@Configuration
@EnableSwagger2
@EnableKnife4j
@Profile({"dev", "test"})
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.htbx.controller"))
                .paths(PathSelectors.any())
                .build();

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("人寿保险后端接口API")
                .description("使用于人寿保险小程序后端API")
                .termsOfServiceUrl("http://testlife.haitunbx.com/htbx-life/")
                .contact(new Contact("qiaomu", "http:", "kexiong@hthu.com.cn"))
                .version("V1.0")
                .build();
    }
}
