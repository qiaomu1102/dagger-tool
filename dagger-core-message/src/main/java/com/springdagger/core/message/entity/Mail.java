package com.springdagger.core.message.entity;

import com.springdagger.core.message.mail.MailAuthenticator;

import java.io.Serializable;

public class Mail implements Serializable {

    private static final long serialVersionUID = -5418418756976852453L;

    public static final String SPLIT_REGEX = ",";
    /**
     * 发送地址（多个邮件地址以","分隔）
     */
    private String toEmails;

    /**
     * 抄送地址（多个邮件地址以","分隔）
     */
    private String ccEmails;
    /**
     * 邮件主题
     */
    private String subject;
    /**
     * 邮件内容
     */
    private String content;

    /**
     * 内容是否是html
     */
    private boolean isHtml;

    /**
     * 附件路径（多个路径地址以","分隔）
     */
    private String path;
    /**
     * 附件名称（多个名称以","分隔），跟路径一一对应
     */
    private String fileName;

    /**
     * 发件人地址和密码，若为空，则使用默认的
     * userName=order1@htbaobao.com
     * password=HaiOrder1Tun
     */
    private MailAuthenticator mailAuthenticator;

    /**
     * 发送邮件失败的ID
     */
    private String failEmailId;

    /**
     * 模板名称
     */
    private String tplName;

    public String getCcEmails() {
        return ccEmails;
    }

    public void setCcEmails(String ccEmails) {
        this.ccEmails = ccEmails;
    }

    public MailAuthenticator getMailAuthenticator() {
        return mailAuthenticator;
    }

    public void setMailAuthenticator(MailAuthenticator mailAuthenticator) {
        this.mailAuthenticator = mailAuthenticator;
    }

    public String getToEmails() {
        return toEmails;
    }

    public void setToEmails(String toEmails) {
        this.toEmails = toEmails;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean getIsHtml() {
        return isHtml;
    }

    public void setIsHtml(boolean isHtml) {
        this.isHtml = isHtml;
    }

    public String getFailEmailId() {
        return failEmailId;
    }

    public void setFailEmailId(String failEmailId) {
        this.failEmailId = failEmailId;
    }

    public String getTplName() {
        return tplName;
    }

    public void setTplName(String tplName) {
        this.tplName = tplName;
    }
}
