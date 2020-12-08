package com.springdagger.core.message.mail;

import org.springframework.beans.factory.annotation.Value;

public class MailConfig {

	/**
	 * 发送人邮箱
	 */
	public static String from;
	/**
	 * 发送人姓名
	 */
	public static String fromName;

	/**
	 * 邮箱主机
	 */
	public static String host;
	/**
	 * 发送邮件服务器账号
	 */
	public static String account;
	/**
	 * 发送邮件服务器密码
	 */
	public static String password;
	/**
	 * 发送邮件服务器用户名
	 */
	public static String nick;
	/**
	 * 发送邮件服务器端口
	 */
	public static int port;
	/**
	 * 模板路径
	 */
	public static String tplPath;

	@Value("${from}")
	public void setFrom(String from) {
		MailConfig.from = from;
	}

	@Value("${from_name}")
	public void setFromName(String fromName) {
		MailConfig.fromName = fromName;
	}

	@Value("${host}")
	public void setHost(String host) {
		MailConfig.host = host;
	}

	@Value("${account}")
	public void setAccount(String account) {
		MailConfig.account = account;
	}

	@Value("${password}")
	public void setPassword(String password) {
		MailConfig.password = password;
	}

	@Value("${nick}")
	public void setNick(String nick) {
		MailConfig.nick = nick;
	}

	@Value("${port}")
	public void setPort(int port) {
		MailConfig.port = port;
	}

	@Value("${tpl_path}")
	public void setPort(String tplPath) {
		MailConfig.tplPath = tplPath;
	}

}
