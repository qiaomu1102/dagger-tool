package com.springdagger.core.message.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springdagger.core.mp.base.BaseEntity;

@TableName("sys_mail")
public class SysMail extends BaseEntity {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;

	    
	/**
	  *邮件ID
	  */
    @TableId
	private String mailId;
	    
	/**
	  *发件人
	  */
	private String userName;
	    
	/**
	  *发件人授权码
	  */
	private String password;
	    
	/**
	  *接收地址（多个邮件地址以","分隔）
	  */
	private String toMails;
	    
	/**
	  *抄送地址（多个邮件地址以","分隔）
	  */
	private String ccMails;
	    
	/**
	  *主题
	  */
	private String subject;
	    
	/**
	  *内容
	  */
	private String content;
	    
	/**
	  *是否是html格式（0否1是）
	  */
	private Integer isHtml;
	    
	/**
	  *附件路径（多个路径地址以","分隔）
	  */
	private String path;
	    
	/**
	  *附件名称（多个名称以","分隔），跟路径一一对应
	  */
	private String fileName;

	private String tplName;
	    
	/**
	  *发送结果(0失败，1成功）
	  */
	private Integer result;
	    
	/**
	  *删除标志（1已删除，0未删除）
	  */
	private Integer isDelete;
	    
	/**
	  *创建时间
	  */
	private java.util.Date createDate;
	    
	/**
	  *创建用户
	  */
	private String createUser;
	    
	/**
	  *修改时间
	  */
	private java.util.Date modifyDate;
	    
	/**
	  *修改用户
	  */
	private String modifyUser;

	public String getMailId() {
        return mailId;
    }
    
    public void setMailId(String mailId) {
        this.mailId = mailId;
    }
    
	public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
	public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
	public String getToMails() {
        return toMails;
    }
    
    public void setToMails(String toMails) {
        this.toMails = toMails;
    }
    
	public String getCcMails() {
        return ccMails;
    }
    
    public void setCcMails(String ccMails) {
        this.ccMails = ccMails;
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
    
	public Integer getIsHtml() {
        return isHtml;
    }
    
    public void setIsHtml(Integer isHtml) {
        this.isHtml = isHtml;
    }
    
	public String getPath() {
        return path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }

	public String getTplName() {
		return tplName;
	}

	public void setTplName(String tplName) {
		this.tplName = tplName;
	}

	public String getFileName() {
        return fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
	public Integer getResult() {
        return result;
    }
    
    public void setResult(Integer result) {
        this.result = result;
    }
    
	public Integer getIsDelete() {
        return isDelete;
    }
    
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
    
	public java.util.Date getCreateDate() {
        return createDate;
    }
    
    public void setCreateDate(java.util.Date createDate) {
        this.createDate = createDate;
    }
    
	public String getCreateUser() {
        return createUser;
    }
    
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
    
	public java.util.Date getModifyDate() {
        return modifyDate;
    }
    
    public void setModifyDate(java.util.Date modifyDate) {
        this.modifyDate = modifyDate;
    }
    
	public String getModifyUser() {
        return modifyUser;
    }
    
    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }
    
}