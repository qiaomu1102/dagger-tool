package com.springdagger.core.secure;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户实体
 *
 * @author Chill
 */
@Data
public class DaggerUser implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 客户端id
	 */
	@ApiModelProperty(hidden = true)
	private String clientId;

	/**
	 * 用户id
	 */
	@ApiModelProperty(hidden = true)
	private Long userId;
	/**
	 * 昵称
	 */
	@ApiModelProperty(hidden = true)
	private String userName;
	/**
	 * 账号
	 */
	@ApiModelProperty(hidden = true)
	private String account;
	/**
	 * 角色id
	 */
	@ApiModelProperty(hidden = true)
	private String roleId;
	/**
	 * 角色名
	 */
	@ApiModelProperty(hidden = true)
	private String roleName;

}