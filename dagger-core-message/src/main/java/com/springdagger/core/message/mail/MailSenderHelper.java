package com.springdagger.core.message.mail;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Properties;

@Component
public class MailSenderHelper {

	private Properties properties;

	@PostConstruct
	public void initProperties() {
		properties = new Properties();
		properties.setProperty("mail.mime.charset", "UTF-8");
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.port", "465");
		properties.setProperty("mail.smtp.socketFactory.port", "465");//设置ssl端口
		properties.setProperty("mail.smtp.socketFactory.fallback", "false");
		properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.setProperty("mail.smtp.connectiontimeout", "20000");// 连接时间限制
		properties.setProperty("mail.smtp.timeout", "20000");// 邮件接收时间限制
		properties.setProperty("mail.smtp.writetimeout", "20000");// 邮件发送时间限制
	}

	public JavaMailSenderImpl getJavaMailSender(MailAuthenticator mailAuthenticator) {
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		senderImpl.setHost("smtp.exmail.qq.com");
		senderImpl.setUsername(mailAuthenticator.getUserName());
		senderImpl.setJavaMailProperties(properties);
		senderImpl.setPassword(mailAuthenticator.getPassword());
		return senderImpl;
	}

}
