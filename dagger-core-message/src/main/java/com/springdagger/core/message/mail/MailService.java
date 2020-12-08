package com.springdagger.core.message.mail;

import com.springdagger.core.message.entity.Mail;
import com.springdagger.core.message.entity.SysMail;
import com.springdagger.core.message.service.SysMailService;
import com.springdagger.core.tool.utils.IDCreator;
import com.springdagger.core.tool.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.activation.FileDataSource;
import javax.annotation.PostConstruct;
import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

@Slf4j
@Service("mailService")
public class MailService {

    private static final String IMG_PATH = "/nas/htbx_share/nfs_usr/usr_img/mail_img/";
    private Properties properties = new Properties();
    private VelocityEngine velocityEngine = new VelocityEngine();

    @Autowired
    private MailSenderHelper mailSenderHelper;
    @Autowired
    private SysMailService sysMailService;

    @PostConstruct
    public void initProperties() {
        try {
            ClassLoader classLoader = MailService.class.getClassLoader();
            URL url = classLoader.getResource("/mailtpl.properties");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(url.getPath()));
            properties.load(bufferedReader);

            Properties prop = new Properties();
            prop.setProperty("resource.loader", "class");
            prop.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            prop.setProperty(Velocity.INPUT_ENCODING, "utf-8");
            prop.setProperty(Velocity.OUTPUT_ENCODING, "utf-8");
            velocityEngine.init(prop);
        } catch (Exception e) {
            log.error("邮件模板初始化失败",e);
        }
    }

    private void checkMailInfo(Mail mail) {
        if (mail == null) {
            throw new RuntimeException("发送邮件失败，发送邮件信息体不能为空");
        }

        if (StringUtil.isBlank(mail.getToEmails())) {
            throw new RuntimeException("发送邮件失败，邮件收件人地址不能为空");
        }

        if (StringUtil.isBlank(mail.getSubject())) {
            throw new RuntimeException("发送邮件失败，subject不能为空");
        }

        if (StringUtil.isBlank(mail.getContent())) {
            throw new RuntimeException("发送邮件失败，content不能为空");
        }

        if (mail.getMailAuthenticator() == null) {
            MailAuthenticator authenticator = new MailAuthenticator();
            authenticator.setUserName("order1@htbaobao.com");
            authenticator.setPassword("HaiOrder1Tun");
            mail.setMailAuthenticator(authenticator);
        }
    }

    public void sendEmail(Mail mail){
        checkMailInfo(mail);
        String mailId = saveEmail(mail);
        try {
            doSendEmail(mail);
            updateEmailStatus(mailId, 1);
        } catch (Exception e) {
            log.error("发送邮件失败", e);
            updateEmailStatus(mailId, 0);
        }
    }

    public void doSendEmail(Mail mail) throws Exception {
        JavaMailSenderImpl senderImpl = mailSenderHelper.getJavaMailSender(mail.getMailAuthenticator());
        MimeMessage mimeMessage = senderImpl.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true,"UTF-8");

        mimeMessageHelper.setFrom(mail.getMailAuthenticator().getUserName());
        mimeMessageHelper.setTo(mail.getToEmails().split(Mail.SPLIT_REGEX));
        if (StringUtil.isNotBlank(mail.getCcEmails())) {
            mimeMessageHelper.setCc(mail.getCcEmails().split(Mail.SPLIT_REGEX));
        }
        mimeMessageHelper.setSubject(mail.getSubject());
        mimeMessageHelper.setText(mail.getContent(), mail.getIsHtml());
        if (StringUtil.isNotBlank(mail.getPath()) && StringUtil.isNotBlank(mail.getFileName())){
            String[] pathArr = mail.getPath().split(Mail.SPLIT_REGEX);
            String[] fileName = mail.getFileName().split(Mail.SPLIT_REGEX);
            for(int i=0; i<pathArr.length; i++){
                FileSystemResource reFile = new FileSystemResource(new File(pathArr[i]));
                mimeMessageHelper.addAttachment(MimeUtility.encodeWord(fileName[i]), reFile);
            }
        }

        if (StringUtil.isNotBlank(mail.getTplName()) && "order-approve".equals(mail.getTplName())) {
            mimeMessageHelper.addInline("mail_bg", new FileDataSource(IMG_PATH +"mail_bg.png"));
            mimeMessageHelper.addInline("top_banner", new FileDataSource(IMG_PATH +"top_banner.png"));
            mimeMessageHelper.addInline("wdbd_btn", new FileDataSource(IMG_PATH +"wdbd_btn.png"));
            mimeMessageHelper.addInline("fsfl_btn", new FileDataSource(IMG_PATH +"fsfl_btn.png"));
            mimeMessageHelper.addInline("fxcp_btn", new FileDataSource(IMG_PATH +"fxcp_btn.png"));
        }
        senderImpl.send(mimeMessage);
        log.info("邮件发送成功，收件人：" + Arrays.toString(mail.getToEmails().split(Mail.SPLIT_REGEX)));
    }

    public void reSendFailEmail(Mail mail){
        checkMailInfo(mail);
        try {
            doSendEmail(mail);
            updateEmailStatus(mail.getFailEmailId(), 1);
        } catch (Exception e) {
            log.error("发送邮件失败", e);
            updateEmailStatus(mail.getFailEmailId(), 0);
        }
    }

    private void updateEmailStatus(String mailId, int result) {
        if (StringUtil.isNotBlank(mailId)) {
            SysMail entity = new SysMail();
            entity.setMailId(mailId);
            entity.setResult(result);
            entity.setModifyDate(new Date());
            sysMailService.updateById(entity);
        }
    }

    private String saveEmail(Mail mail) {
        SysMail entity = new SysMail();
        String mailId = IDCreator.getIdStr();
        entity.setMailId(mailId);
        entity.setContent(mail.getContent());
        entity.setSubject(mail.getSubject());
        entity.setUserName(mail.getMailAuthenticator().getUserName());
        entity.setPassword(mail.getMailAuthenticator().getPassword());
        entity.setCcMails(mail.getCcEmails());
        entity.setToMails(mail.getToEmails());
        entity.setFileName(mail.getFileName());
        entity.setIsHtml(mail.getIsHtml() ? 1 : 0);
        entity.setPath(mail.getPath());
        entity.setTplName(mail.getTplName());
        entity.setCreateUser("admin");
        entity.setModifyUser("admin");
        sysMailService.save(entity);
        return mailId;
    }

    public void sendEmailByTemplate(Mail mail, Map<String, Object> kv) {
        String tplName = mail.getTplName();
        if (StringUtil.isBlank(tplName)){
            throw new RuntimeException("模板名称不能为空");
        }
        String templateName = properties.getProperty(tplName);
        if (StringUtil.isBlank(templateName)) {
            throw new RuntimeException("没有" + tplName + "对应的模板信息");
        }
        // 加载模板，相对于classpath路径
        Template template = velocityEngine.getTemplate("/mailvm/" + templateName);
        VelocityContext context = new VelocityContext();
        if (kv != null && !kv.isEmpty()) {
            for (Entry<String, Object> entry : kv.entrySet()) {
                Object key = entry.getKey();
                Object val = entry.getValue();
                context.put(key.toString(), val.toString());
            }
        }
        StringWriter writer = new StringWriter();
        template.merge(context, writer); // 转换

        mail.setContent(writer.toString());
        sendEmail(mail);
    }

    @Deprecated
    public MimeMessage createMimeMessage(MailPack mail) throws UnsupportedEncodingException, MessagingException {

//        MimeMessage mimeMessage = new MimeMessage(mailSenderHelper.getSession());
        MimeMessage mimeMessage = new MimeMessage(Session.getInstance(null));
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        messageHelper.setFrom(mail.getFrom());
        messageHelper.setSubject(mail.getMail().getSubject());
        String toEmails = mail.getMail().getToEmails();
        String[] to = toEmails.split(";");
        messageHelper.setTo(to);
        String path = mail.getMail().getPath();
        if (StringUtil.isNotBlank(path)) {
            FileSystemResource file = new FileSystemResource(new File(path));
            messageHelper.addAttachment(MimeUtility.encodeWord(mail.getMail().getFileName()), file);
        }
        return mimeMessage;
    }

    @Deprecated
    public MimeMessage createHtmlMessage(MailPack mail) throws UnsupportedEncodingException, MessagingException {
//        MimeMessage mimeMessage = new MimeMessage(mailSenderHelper.getSession());
        MimeMessage mimeMessage = new MimeMessage(Session.getInstance(null));
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        messageHelper.setFrom(mail.getFrom());
        messageHelper.setSubject(mail.getMail().getSubject());
        String toEmails = mail.getMail().getToEmails();
        String[] to = toEmails.split(";");
        messageHelper.setTo(to);
        // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
        Multipart mainPart = new MimeMultipart("mixed");
        // 创建一个包含HTML内容的MimeBodyPart
        BodyPart html = new MimeBodyPart();
        // 设置HTML内容
        html.setContent(mail.getMail().getContent(), "text/html; charset=utf-8");
        mainPart.addBodyPart(html);
        // 将MiniMultipart对象设置为邮件内容
        mimeMessage.setContent(mainPart);
        return mimeMessage;
    }

    @Deprecated
    public void sendHtmlMail(MailPack mail) throws UnsupportedEncodingException, MessagingException {
        mail.setFrom(MailConfig.from);
        mail.setFromName(MailConfig.fromName);
        MimeMessage msg = createHtmlMessage(mail);
        Transport.send(msg);
    }

    @Deprecated
    public void sendMail(MailPack mail) throws UnsupportedEncodingException, MessagingException {
        mail.setFrom(MailConfig.from);
        mail.setFromName(MailConfig.fromName);
        MimeMessage msg = createMimeMessage(mail);
        Transport.send(msg);
    }

}
