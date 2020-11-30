package com.springdagger.core.oss.rule;

import com.springdagger.core.tool.utils.DateUtil;
import com.springdagger.core.tool.utils.FileUtil;
import com.springdagger.core.tool.utils.StringPool;
import com.springdagger.core.tool.utils.StringUtil;
import lombok.AllArgsConstructor;

/**
 * @author: qiaomu
 * @date: 2020/11/30 16:58
 * @Description: TODO
 */
@AllArgsConstructor
public class BladeOssRule implements OssRule {

	@Override
	public String bucketName(String bucketName) {
		return bucketName;
	}

	@Override
	public String fileName(String originalFilename) {
		return "upload" + StringPool.SLASH + DateUtil.today() + StringPool.SLASH + StringUtil.randomUUID() + StringPool.DOT + FileUtil.getFileExtension(originalFilename);
	}

}
