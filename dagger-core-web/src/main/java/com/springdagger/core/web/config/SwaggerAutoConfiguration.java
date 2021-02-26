package com.springdagger.core.web.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.htbb.core.web.util.SwaggerUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
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
@EnableConfigurationProperties(com.htbb.core.web.config.SwaggerProperties.class)
public class SwaggerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public com.htbb.core.web.config.SwaggerProperties swaggerProperties() {
        return new com.htbb.core.web.config.SwaggerProperties();
    }

    @Bean
    public Docket createRestApi(com.htbb.core.web.config.SwaggerProperties swaggerProperties) {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo(swaggerProperties))
                .select()
                .apis(SwaggerUtil.basePackages(swaggerProperties.getBasePackages()))
                .paths(PathSelectors.any())
                .build();

    }

    private ApiInfo apiInfo(com.htbb.core.web.config.SwaggerProperties swaggerProperties) {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
                .contact(new Contact(swaggerProperties.getContact().getName(), swaggerProperties.getContact().getUrl(), swaggerProperties.getContact().getEmail()))
                .version(swaggerProperties.getVersion())
                .build();
    }
}
