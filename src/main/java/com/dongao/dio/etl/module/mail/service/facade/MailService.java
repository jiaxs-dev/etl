package com.dongao.dio.etl.module.mail.service.facade;

/**
 * @Title: MailService
 * @Package: com.dongao.dio.questionetl.module.service.facade
 * @Author: jiaxiansun@dongao.com
 * @Date: 2020/2/17
 * @Time: 15:55
 * @Description: 邮件发送service
 * @Copyright: www.dongao.com@2020
 */
public interface MailService {

    /**
     * 发送邮件
     * @param from 发件人
     * @param to 收件人
     * @param subject 主题
     * @param content 邮件正文
     * @return 是否发送成功
     */
    boolean send(String from, String to, String subject, String content);
}
