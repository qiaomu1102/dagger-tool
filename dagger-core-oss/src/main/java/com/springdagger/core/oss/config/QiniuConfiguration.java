package com.springdagger.core.oss.config;

import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.springdagger.core.oss.QiniuTemplate;
import com.springdagger.core.oss.props.OssProperties;
import com.springdagger.core.oss.rule.BladeOssRule;
import com.springdagger.core.oss.rule.OssRule;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: qiaomu
 * @date: 2020/11/30 16:58
 * @Description: TODO
 */
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(OssProperties.class)
@ConditionalOnProperty(value = "oss.name", havingValue = "qiniu")
public class QiniuConfiguration {

	private OssProperties ossProperties;

	@Bean
	@ConditionalOnMissingBean(OssRule.class)
	public OssRule ossRule() {
		return new BladeOssRule();
	}

	@Bean
	public com.qiniu.storage.Configuration qiniuConfiguration() {
		return new com.qiniu.storage.Configuration(Zone.zone0());
	}

	@Bean
	public Auth auth() {
		return Auth.create(ossProperties.getAccessKey(), ossProperties.getSecretKey());
	}

	@Bean
	@ConditionalOnBean(com.qiniu.storage.Configuration.class)
	public UploadManager uploadManager(com.qiniu.storage.Configuration cfg) {
		return new UploadManager(cfg);
	}

	@Bean
	@ConditionalOnBean(com.qiniu.storage.Configuration.class)
	public BucketManager bucketManager(com.qiniu.storage.Configuration cfg) {
		return new BucketManager(auth(), cfg);
	}

	@Bean
	@ConditionalOnMissingBean(QiniuTemplate.class)
	@ConditionalOnBean({Auth.class, UploadManager.class, BucketManager.class, OssRule.class})
	public QiniuTemplate qiniuTemplate(Auth auth, UploadManager uploadManager, BucketManager bucketManager, OssRule ossRule) {
		return new QiniuTemplate(auth, uploadManager, bucketManager, ossProperties, ossRule);
	}


}
