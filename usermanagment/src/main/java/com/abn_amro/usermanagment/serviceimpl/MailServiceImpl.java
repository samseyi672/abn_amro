package com.abn_amro.usermanagment.serviceimpl;


import com.abn_amro.usermanagment.config.MailConfigProperties;
import com.abn_amro.usermanagment.dto.request.Attachment;
import com.abn_amro.usermanagment.dto.request.Mail;
import com.abn_amro.usermanagment.exceptions.InvalidEmailException;
import com.abn_amro.usermanagment.exceptions.MailServerException;
import com.abn_amro.usermanagment.service.MailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final static Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
    private final JavaMailSender mailSender;
//    private final TemplateEngine templateEngine;
    private final ObjectMapper mapper;
    private final MailConfigProperties mailConfigProperties;

    @Override
    public void sendEmail(Mail mail,TemplateEngine templateEngine) throws MailServerException {
        if (!isValidEmail(mail.getMailTo())) {
            throw new InvalidEmailException("Invalid 'To' email address: " + mail.getMailTo());
        }
        try {
            var mailMessage = mailSender.createMimeMessage();
            var mailHelper = new MimeMessageHelper(mailMessage, true, "UTF-8");
            mailHelper.setSubject(mail.getMailSubject());
            mailHelper.setFrom(mail.getMailFrom());
            mailHelper.setTo(mail.getMailTo());
            mailHelper.setCc(mail.getCcTo());
            final String stringyfiedHtmlContent = templateEngine.process(mail.getThymeleafTemplateName(), mail.getThymeleafContext());
            mailHelper.setText(stringyfiedHtmlContent, true);
            //Add Attachment if any
            mailAttachmentStream(mail.getAttachment()).forEach(attachment -> {
                try{
                    mailHelper.addAttachment(attachment.getAttachmentFileName(), attachment.getAttachmentSource(),
                            attachment.getAttachmentContentType());
                }catch (Exception ex){
                    logger.error(ex.getMessage(), ex);
                }
            });
            mailSender.send(mailHelper.getMimeMessage());
            logger.info(">>Email sending completed>>>");
        } catch (Exception e) {
            logger.info(">>Socket connection error>> {}", e.getMessage(), e);
            throw new MailServerException("Error trying to send mail");
        }
    }

    @Override
    public void sendEmail(Mail mail,String htmlTemplate,TemplateEngine templateEngine) throws MailServerException {
        if (!isValidEmail(mail.getMailTo())) {
            throw new InvalidEmailException("Invalid 'To' email address: " + mail.getMailTo());
        }
        try {
            var mailMessage = mailSender.createMimeMessage();
            var mailHelper = new MimeMessageHelper(mailMessage, true, "UTF-8");
            mailHelper.setSubject(mail.getMailSubject());
            mailHelper.setFrom(mail.getMailFrom());
            mailHelper.setTo(mail.getMailTo());
            mailHelper.setCc(mail.getCcTo());
            String html = mail.getThymeleafTemplateName()==null?htmlTemplate:mail.getThymeleafTemplateName();
            final String stringyfiedHtmlContent = templateEngine.process(html, mail.getThymeleafContext());
            mailHelper.setText(stringyfiedHtmlContent, true);
            //Add Attachment if any
            mailAttachmentStream(mail.getAttachment()).forEach(attachment -> {
                try{
                    mailHelper.addAttachment(attachment.getAttachmentFileName(), attachment.getAttachmentSource(),
                            attachment.getAttachmentContentType());
                }catch (Exception ex){
                    logger.error(ex.getMessage(), ex);
                }
            });
            mailSender.send(mailHelper.getMimeMessage());
            logger.info(">>Email sending completed>>>");
        } catch (Exception e) {
            logger.info(">>Socket connection error>> {}", e.getMessage(), e);
            throw new MailServerException("Error trying to send mail");
        }
    }
    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public void sendEmailAsync(Mail mail,TemplateEngine templateEngine) throws MailServerException {
                this.sendEmail(mail,templateEngine);
    }
    private Stream<Attachment> mailAttachmentStream(List<Attachment> attachments){
        if(Objects.nonNull(attachments)){
            return attachments.stream();
        }
        return Stream.empty();
    }
}





