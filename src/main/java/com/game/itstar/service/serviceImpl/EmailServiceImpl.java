package com.game.itstar.service.serviceImpl;

import com.game.itstar.service.EmailService;
import com.game.itstar.utile.RandCodeUtil;
import com.sun.mail.util.MailSSLSocketFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @Author 朱斌
 * @Date 2019/10/9  17:03
 * @Desc
 */
@Service
public class EmailServiceImpl implements EmailService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${spring.mail.username}")
    public String from;
    @Value("${spring.mail.password}")
    public String password;// 登录密码
    @Value("${spring.mail.protocol}")
    public String protocol;// 协议
    @Value("${spring.mail.port}")
    public String port;// 端口
    @Value("${spring.mail.host}")
    public String host;// 服务器地址
    @Value("${redis_key.sms_code}")
    private String smsCodeKey;

    private String reset = "验证码";

    //初始化参数
    public Session initProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", protocol);
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", port);
        // 使用smtp身份验证
        properties.put("mail.smtp.auth", "true");
        // 使用SSL,企业邮箱必需 start
        // 开启安全协议
        MailSSLSocketFactory mailSSLSocketFactory = null;
        try {
            mailSSLSocketFactory = new MailSSLSocketFactory();
            mailSSLSocketFactory.setTrustAllHosts(true);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        properties.put("mail.smtp.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", mailSSLSocketFactory);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");
        properties.put("mail.smtp.socketFactory.port", port);
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
        // 使用SSL,企业邮箱必需 end
        // TODO 显示debug信息 正式环境注释掉
        session.setDebug(true);
        return session;
    }

    /**
     * 发送邮箱验证码
     *
     * @param sender
     * @return
     */
    @Override
    public Boolean sendHtmlEmailCZ(String sender) {
        String key = smsCodeKey.replace("sender", sender);
        Boolean lean = false;
        try {
            Session session = initProperties();
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(from, "Tesra"));// 发件人,可以设置发件人的别名

            // 收件人,多人接收
            InternetAddress[] internetAddressTo = new InternetAddress().parse(sender);
            mimeMessage.setRecipients(Message.RecipientType.TO, internetAddressTo);

            // 主题
            mimeMessage.setSubject(reset);

            // 时间
            mimeMessage.setSentDate(new Date());

            // 容器类 附件
            MimeMultipart mimeMultipart = new MimeMultipart();

            // 可以包装文本,图片,附件
            MimeBodyPart bodyPart = new MimeBodyPart();
            String smsCode = RandCodeUtil.generateVerifyCode(6, RandCodeUtil.NUMBER);

            // 设置内容 getEmailReset是发送邮箱的html模板
//            bodyPart.setContent(getEmailReset(sender,smsCode), "text/html; charset=UTF-8");
            bodyPart.setContent(smsCode, "text/html; charset=UTF-8");

            mimeMultipart.addBodyPart(bodyPart);

            mimeMessage.setContent(mimeMultipart);
            mimeMessage.saveChanges();
            Transport.send(mimeMessage);

            Map<String, String> val = new HashMap<>();
            val.put("code", smsCode);
            Long time = System.currentTimeMillis();
            val.put("time", time.toString());
            redisTemplate.opsForHash().putAll(key, val);
            redisTemplate.expire(key, 10, TimeUnit.MINUTES);
            lean = true;
        } catch (MessagingException e) {
            e.printStackTrace();
            lean = false;
            logger.error("发送重置邮件失败：" + sender + " ----" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            lean = false;
            logger.error("发送重置邮件失败：" + sender + " ----" + e.getMessage());
        }
        return lean;
    }

    /**
     * 检测验证码是否正确
     *
     * @param sender
     * @param code
     * @return
     */
    public Boolean checkSMSCode(String sender, String code) {
        String key = smsCodeKey.replace("$sender", sender);
        Map<String, Object> val = redisTemplate.opsForHash().entries(key);
        if (val != null && val.size() > 0) {
            String redisCode = val.get("code").toString();
            return redisCode.equals(code);
        }
        return false;
    }
}
