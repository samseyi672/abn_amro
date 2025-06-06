package com.abn_amro.usermanagment.serviceimpl;


import com.abn_amro.usermanagment.dto.request.NameEmailDTO;
import com.abn_amro.usermanagment.exceptions.MailServerException;
import com.abn_amro.usermanagment.service.ThymeLeafContextService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
}
