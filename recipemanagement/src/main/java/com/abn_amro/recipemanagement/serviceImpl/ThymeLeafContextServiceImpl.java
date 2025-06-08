package com.abn_amro.recipemanagement.serviceImpl;


import com.abn_amro.recipemanagement.domain.dto.request.NameEmailDTO;
import com.abn_amro.recipemanagement.event.RecipeRegisteredEvent;
import com.abn_amro.recipemanagement.exception.MailServerException;
import com.abn_amro.recipemanagement.service.ThymeLeafContextService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ThymeLeafContextServiceImpl implements ThymeLeafContextService {
    private final Logger logger = LoggerFactory.getLogger(ThymeLeafContextServiceImpl.class);
    @Override
    public Map<String, Object> initializeContextForUserRegistration(NameEmailDTO nameEmailDto) throws MailServerException {
        try {
            var emailContext = new Context();
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);
            Map<String, Object> contextRes = new HashMap<>();
            emailContext.setVariable("CurrentYear",formattedDateTime);
            contextRes.put("emailContext", emailContext);
            return contextRes;
        }catch (Exception e){
            logger.error(">>>Exception >>>>{}", e.getMessage(), e);
            throw new MailServerException(e.getMessage());
        }
    }

    @Override
    public Map<String, Object> initializeContextForServiceFailure(String cause) {
        Map<String, Object> contextRes = new HashMap<>();
        try {
            Context emailContext = new Context();
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);
            emailContext.setVariable("CurrentYear", formattedDateTime);
            emailContext.setVariable("cause", cause);
            contextRes.put("emailContext", emailContext);
        } catch (Exception e) {
            logger.error(">>> Exception occurred while initializing email context: {}", e.getMessage(), e);
        }
        return contextRes;
    }

    @Override
    public Map<String, Object> initializeContextForRecipeCreation(RecipeRegisteredEvent recipeRegisteredEvent) {
        Context context = new Context();
        context.setVariable("firstName", recipeRegisteredEvent.getFirstName());
        context.setVariable("lastName", recipeRegisteredEvent.getLastName());
        context.setVariable("userName", recipeRegisteredEvent.getUserName());
        context.setVariable("title", recipeRegisteredEvent.getTitle());
        context.setVariable("vegetarian", recipeRegisteredEvent.isVegetarian());
        context.setVariable("servings", recipeRegisteredEvent.getServings());
        context.setVariable("instructions", recipeRegisteredEvent.getInstructions());
        context.setVariable("ingredients", recipeRegisteredEvent.getIngredients());
        Map<String, Object> contextRes = new HashMap<>();
        contextRes.put("emailContext",context);
        return contextRes;
    }

}

















































































