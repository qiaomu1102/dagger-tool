package com.springdagger.core.oss.model;

import lombok.Data;

/**
 * @author: qiaomu
 * @date: 2020/11/30 16:58
 * @Description: TODO
 */
@Data
public class BladeFile {
	/**
	 * 文件地址
	 */
	private String link;
	/**
	 * 文件名
	 */
	private String name;
	/**
	 * 原始文件名
	 */
	private String originalName;
}
