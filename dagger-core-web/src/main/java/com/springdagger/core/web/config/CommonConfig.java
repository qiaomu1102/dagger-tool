package com.springdagger.core.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author: kexiong
 * @date: 2020/10/20 11:02
 * @Description: TODO
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "dagger.common")
public class CommonConfig {
    /** 是否是生产环境 */
    private boolean webLog_enable;

    /** jwt RSA加密公钥 */
    private String jwt_public_key;

    /** jwt RSA加密私钥 */
    private String jwt_private_key;

}
