package com.springdagger.core.secure.config;


import com.springdagger.core.secure.aspect.AuthAspect;
import com.springdagger.core.secure.interceptor.ClientInterceptor;
import com.springdagger.core.secure.interceptor.SecureInterceptor;
import com.springdagger.core.secure.props.BladeSecureProperties;
import com.springdagger.core.secure.provider.ClientDetailsServiceImpl;
import com.springdagger.core.secure.provider.IClientDetailsService;
import com.springdagger.core.secure.registry.SecureRegistry;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 安全配置类
 *
 * @author Chill
 */
@Order
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties({BladeSecureProperties.class})
public class SecureConfiguration implements WebMvcConfigurer {

	private final SecureRegistry secureRegistry;

	private final BladeSecureProperties secureProperties;

	private final JdbcTemplate jdbcTemplate;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		secureProperties.getClient().forEach(cs -> registry.addInterceptor(new ClientInterceptor(cs.getClientId())).addPathPatterns(cs.getPathPatterns()));

		if (secureRegistry.isEnabled()) {
			registry.addInterceptor(new SecureInterceptor())
				.excludePathPatterns(secureRegistry.getExcludePatterns())
				.excludePathPatterns(secureRegistry.getDefaultExcludePatterns())
				.excludePathPatterns(secureProperties.getSkipUrl());
		}
	}

	@Bean
	public AuthAspect authAspect() {
		return new AuthAspect();
	}

	@Bean
	@ConditionalOnMissingBean(IClientDetailsService.class)
	public IClientDetailsService clientDetailsService() {
		return new ClientDetailsServiceImpl(jdbcTemplate);
	}

}
