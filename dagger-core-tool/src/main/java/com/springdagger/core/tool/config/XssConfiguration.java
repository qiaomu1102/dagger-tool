package com.springdagger.core.tool.config;

import lombok.AllArgsConstructor;
import com.springdagger.core.tool.support.xss.XssFilter;
import com.springdagger.core.tool.support.xss.XssProperties;
import com.springdagger.core.tool.support.xss.XssUrlProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.servlet.DispatcherType;

/**
 * Xss配置类
 *
 */
@Configuration
@AllArgsConstructor
@ConditionalOnProperty(value = "dagger.xss.enabled", havingValue = "true")
@EnableConfigurationProperties({XssProperties.class, XssUrlProperties.class})
public class XssConfiguration {

	private final XssProperties xssProperties;
	private final XssUrlProperties xssUrlProperties;

	/**
	 * 防XSS注入
	 */
	@Bean
	public FilterRegistrationBean<XssFilter> xssFilterRegistration() {
		FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<>();
		registration.setDispatcherTypes(DispatcherType.REQUEST);
		registration.setFilter(new XssFilter(xssProperties, xssUrlProperties));
		registration.addUrlPatterns("/*");
		registration.setName("xssFilter");
		registration.setOrder(Ordered.LOWEST_PRECEDENCE);
		return registration;
	}
}
