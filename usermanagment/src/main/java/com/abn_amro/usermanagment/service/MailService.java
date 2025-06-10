package com.abn_amro.usermanagment.service;

import com.abn_amro.usermanagment.dto.request.Mail;
import com.abn_amro.usermanagment.exceptions.MailServerException;
import org.thymeleaf.TemplateEngine;

public interface MailService {
    void sendEmail(Mail mail, TemplateEngine templateEngine) throws MailServerException;
void sendEmailAsync(Mail mail,TemplateEngine templateEngine) throws MailServerException;
void sendEmail(Mail mail,String htmlTemplate,TemplateEngine templateEngine) throws MailServerException;
}
