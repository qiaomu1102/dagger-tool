package com.springdagger.core.tool.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.servlet.http.HttpServletResponse;

/**
 * 业务代码枚举
 *
 */
@Getter
@AllArgsConstructor
public enum ResultCode implements IResultCode {

	/**
	 * 操作成功200
	 */
	SUCCESS(HttpServletResponse.SC_OK, "操作成功"),

	/**
	 * 未知异常400
	 */
	FAILURE(HttpServletResponse.SC_BAD_REQUEST, "未知异常"),

	/**
	 * 请求未授权401
	 */
	UN_AUTHORIZED(HttpServletResponse.SC_UNAUTHORIZED, "请求未授权"),

	/**
	 * 404 没找到请求404
	 */
	NOT_FOUND(HttpServletResponse.SC_NOT_FOUND, "404 没找到请求"),

	/**
	 * 消息不能读取400
	 */
	MSG_NOT_READABLE(HttpServletResponse.SC_BAD_REQUEST, "消息不能读取"),

	/**
	 * 不支持当前请求方法405
	 */
	METHOD_NOT_SUPPORTED(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "不支持当前请求方法"),

	/**
	 * 不支持当前媒体类型415
	 */
	MEDIA_TYPE_NOT_SUPPORTED(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "不支持当前媒体类型"),

	/**
	 * 请求被拒绝403
	 */
	REQ_REJECT(HttpServletResponse.SC_FORBIDDEN, "请求被拒绝"),

	/**
	 * 服务器异常500
	 */
	INTERNAL_SERVER_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "服务器异常"),

	/**
	 * 缺少必要的请求参数400
	 */
	PARAM_MISS(HttpServletResponse.SC_BAD_REQUEST, "缺少必要的请求参数"),

	/**
	 * 请求参数类型错误400
	 */
	PARAM_TYPE_ERROR(HttpServletResponse.SC_BAD_REQUEST, "请求参数类型错误"),

	/**
	 * 请求参数绑定错误400
	 */
	PARAM_BIND_ERROR(HttpServletResponse.SC_BAD_REQUEST, "请求参数绑定错误"),

	/**
	 * 参数校验失败400
	 */
	PARAM_VALID_ERROR(HttpServletResponse.SC_BAD_REQUEST, "参数校验失败"),

	/**
	 * 缺失token
	 */
	TOKEN_MISSING(998, "User token is null or empty!"),

	/**
	 * token验证失败
	 */
	TOKEN_UN_AUTHORIZED(997, "User token signature error!"),


	/**
	 * 登陆失效996
	 */
	LOGIN_EXPIRED(996, "登陆失效"),

	/**
	 * 解密异常
	 */
	VERIFY_ERROR(995, "解密异常"),

	/**
	 * 该接口已关闭
	 */
	REQ_FORBIDDEN(994, "该接口已关闭, 禁止访问"),

	/**
	 * 该禁止重复提交
	 */
	REDUP_SUBMIT_ERROR(993, "禁止重复提交");



	/**
	 * code编码
	 */
	final int code;
	/**
	 * 中文信息描述
	 */
	final String message;

}
