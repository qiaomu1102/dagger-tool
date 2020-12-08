package com.springdagger.core.message.mail;

import java.io.Serializable;

/**
 * @author: kexiong
 * @date: 2020/9/18 16:08
 * @Description: TODO
 */
public class MailAuthenticator implements Serializable {

    private static final long serialVersionUID = -1;

    /** 用户账号 */
    private String userName;
    /** 用户口令 */
    private String password;

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
}
