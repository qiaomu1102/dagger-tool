package com.springdagger.core.tool.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.springdagger.core.tool.redis.RedisKeySerializer;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import javax.annotation.Resource;

/**
 * RedisTemplate 配置
 *
 */
@Configuration
@AutoConfigureBefore(RedisAutoConfiguration.class)
public class RedisTemplateConfiguration {

	@Resource
    RedisConnectionFactory redisConnectionFactory;

	@Bean(name = "redisTemplate")
	@ConditionalOnMissingBean(RedisTemplate.class)
	public RedisTemplate<String, Object> redisTemplate(RedisSerializer<Object> redisSerializer) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		RedisKeySerializer redisKeySerializer = new RedisKeySerializer();
		// key 序列化
		redisTemplate.setKeySerializer(redisKeySerializer);
		redisTemplate.setHashKeySerializer(redisKeySerializer);
		// value 序列化
		redisTemplate.setValueSerializer(redisSerializer);
		redisTemplate.setHashValueSerializer(redisSerializer);
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		return redisTemplate;
	}


	/**
	 *  redis值序列化使用json序列化器
	 */
	@Bean
	@ConditionalOnMissingBean(RedisSerializer.class)
	public RedisSerializer<Object> valueSerializer() {
		return new FastJsonRedisSerializer<>(Object.class);
	}

//	@Bean
//	public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
//		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
//			.entryTtl(Duration.ofHours(1));
//		return RedisCacheManager
//			.builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
//			.cacheDefaults(redisCacheConfiguration).build();
//	}

}
