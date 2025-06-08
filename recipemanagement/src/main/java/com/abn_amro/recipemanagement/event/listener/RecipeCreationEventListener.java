package com.abn_amro.recipemanagement.event.listener;


import com.abn_amro.recipemanagement.config.ClientConfigProperties;
import com.abn_amro.recipemanagement.domain.dto.request.Mail;
import com.abn_amro.recipemanagement.event.RecipeRegisteredEvent;
import com.abn_amro.recipemanagement.exception.MailServerException;
import com.abn_amro.recipemanagement.service.MailService;
import com.abn_amro.recipemanagement.service.ThymeLeafContextService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;
import java.util.Map;


@Component
@RequiredArgsConstructor
@Slf4j
public class RecipeCreationEventListener {

    private final static Logger log = LoggerFactory.getLogger(RecipeCreationEventListener.class);
    private  final MailService mailService ;
    private final ResourceLoader resourceLoader;
    private final ClientConfigProperties clientConfigProperties;
    private final ThymeLeafContextService thymeLeafContextService ;

    @EventListener
    @Async
    public void handleRecipeCreationEvent(RecipeRegisteredEvent recipeRegisteredEvent) throws MailServerException {
      log.info("recipeRegisteredEvent "+recipeRegisteredEvent);
        Map<String,Object> context = thymeLeafContextService.initializeContextForRecipeCreation(recipeRegisteredEvent);
        Mail mail = Mail.builder()
                .mailTo(clientConfigProperties.getEmailtoA())  //pssl@trustbancgroup.com
                .mailFrom(clientConfigProperties.getEmailtoC())
                .ccTo(new String[]{})
                .mailSubject(clientConfigProperties.getEmailsubjectA())
                .thymeleafTemplateName("recipe-created")
                .thymeleafContext((Context)context.get("emailContext"))
                .build() ;
        mailService.sendEmailAsync(mail);
    }
}
