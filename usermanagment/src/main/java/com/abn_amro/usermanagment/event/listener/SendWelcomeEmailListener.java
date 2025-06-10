package com.abn_amro.usermanagment.event.listener;


import com.abn_amro.usermanagment.config.ClientConfigProperties;
import com.abn_amro.usermanagment.dto.request.Mail;
import com.abn_amro.usermanagment.dto.request.NameEmailDTO;
import com.abn_amro.usermanagment.event.UserRegisteredEvent;
import com.abn_amro.usermanagment.exceptions.MailServerException;
import com.abn_amro.usermanagment.service.MailService;
import com.abn_amro.usermanagment.service.ThymeLeafContextService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class SendWelcomeEmailListener {
    private final static Logger log = LoggerFactory.getLogger(SendWelcomeEmailListener.class);
    private  final MailService mailService ;

    private final TemplateEngine templateEngine;
    private final ResourceLoader resourceLoader;
    private final ClientConfigProperties clientConfigProperties;
    private final ThymeLeafContextService thymeLeafContextService ;


    @Value("${api.activationlink}")
    private String activationLink;

    public SendWelcomeEmailListener(MailService mailService, TemplateEngine templateEngine, ResourceLoader resourceLoader, ClientConfigProperties clientConfigProperties, ThymeLeafContextService thymeLeafContextService) {
        this.mailService = mailService;
        this.templateEngine = templateEngine;
        this.resourceLoader = resourceLoader;
        this.clientConfigProperties = clientConfigProperties;
        this.thymeLeafContextService = thymeLeafContextService;
    }

    @EventListener
    @Async
    public void handleUserRegistered(UserRegisteredEvent event) throws IOException, MailServerException {
        log.info("Sending welcome email to: " + event.getEmail());
        NameEmailDTO nameEmailDTO = new NameEmailDTO();
        nameEmailDTO.setEmail(event.getEmail());
        nameEmailDTO.setName(event.getFirstname()+" "+event.getLastname());
        Map<String,Object> context = thymeLeafContextService.initializeContextForUserRegistration(nameEmailDTO);
        String html = templateEngine.process("mail/registration_email", (Context)context.get("emailContext"));
        String modifiedTemplate = html
                .replace("{firstName}", event.getFirstname())
                .replace("{lastName}", event.getLastname())
                .replace("{activationLink}", activationLink);
        Mail mail = Mail.builder()
                        .mailTo(clientConfigProperties.getEmailtoA())  //pssl@trustbancgroup.com
                        .mailFrom(clientConfigProperties.getEmailtoC())
                        .ccTo(new String[]{})
                        .mailSubject(clientConfigProperties.getEmailsubjectA())
                        .thymeleafTemplateName(modifiedTemplate)
                        .thymeleafContext((Context)context.get("emailContext"))
                        .build() ;
        mailService.sendEmailAsync(mail,templateEngine);
    }
}



































































