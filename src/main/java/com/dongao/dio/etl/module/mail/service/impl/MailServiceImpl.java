package com.dongao.dio.etl.module.mail.service.impl;

import com.dongao.dio.etl.module.mail.service.facade.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @Title: MailServiceImpl
 * @Package: com.dongao.dio.questionetl.module.service.impl
 * @Author: jiaxiansun@dongao.com
 * @Date: 2020/2/17
 * @Time: 15:55
 * @Description: 邮件发送service
 * @Copyright: www.dongao.com@2020
 */
@Component
public class MailServiceImpl implements MailService {

    @Autowired
    JavaMailSender javaMailSender;

    /**
     *
     * @param from 发件人
     * @param to 收件人
     * @param subject 主题
     * @param content 邮件正文
     * @return
     */
    @Override
    public boolean send(String from, String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        javaMailSender.send(message);
        return true;
    }
}
