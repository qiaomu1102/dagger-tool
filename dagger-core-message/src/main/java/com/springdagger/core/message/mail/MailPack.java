package com.springdagger.core.message.mail;


import com.springdagger.core.message.entity.Mail;

import java.io.Serializable;

public class MailPack implements Serializable {

	private static final long serialVersionUID = -539301491932745126L;
	private String from;
	private String fromName;
	private Mail mail;

	public Mail getMail() {
		return mail;
	}

	public void setMail(Mail mail) {
		this.mail = mail;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

}
