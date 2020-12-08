package com.springdagger.core.message.service.impl;

import com.springdagger.core.message.entity.Mail;
import com.springdagger.core.message.mail.MailService;
import com.springdagger.core.message.service.CommonService;
import com.springdagger.core.tool.utils.ThreadPools;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author: qiaomu
 * @date: 2020/12/8 14:21
 * @Description: TODO
 */
@Service
public class CommonServiceImpl implements CommonService {

    @Resource
    MailService mailService;

    @Override
    public void sendEmail(Mail mail) {
        mailService.sendEmail(mail);
    }

    @Override
    public void sendEmailAsyn(Mail mail) {
        ThreadPools.THREAD_POOL.execute(() -> mailService.sendEmail(mail));
    }

    @Override
    public void reSendFailEmail(Mail mail) {
        ThreadPools.THREAD_POOL.execute(() -> mailService.reSendFailEmail(mail));
    }

    @Override
    public void sendEmailByTemplateId(Mail mail, Map<String, Object> kv) {
        ThreadPools.THREAD_POOL.execute(() -> mailService.sendEmailByTemplate(mail, kv));
    }
}
