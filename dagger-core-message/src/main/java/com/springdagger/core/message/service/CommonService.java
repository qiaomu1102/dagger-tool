package com.springdagger.core.message.service;

import com.springdagger.core.message.entity.Mail;

import java.util.Map;

/**
 * @author: qiaomu
 * @date: 2020/12/8 14:19
 * @Description: TODO
 */
public interface CommonService {

    /**
     * 发送邮件（包含附件，html)
     */
    void sendEmail(Mail mail);

    /**
     * 异步发送邮件
     */
    void sendEmailAsyn(Mail mail);

    /**
     * 重新发送之前发送失败的邮件（定时任务）
     */
    void reSendFailEmail(Mail mail);

    /**
     * 根据模板ID发送模板邮件
     */
    void sendEmailByTemplateId(Mail mail, Map<String, Object> kv);

}
