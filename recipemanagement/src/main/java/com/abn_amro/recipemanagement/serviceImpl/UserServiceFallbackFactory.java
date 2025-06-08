package com.abn_amro.recipemanagement.serviceImpl;


import com.abn_amro.recipemanagement.config.ClientConfigProperties;
import com.abn_amro.recipemanagement.constant.ResponseConstant;
import com.abn_amro.recipemanagement.domain.dto.request.Mail;
import com.abn_amro.recipemanagement.domain.dto.request.UserDTO;
import com.abn_amro.recipemanagement.domain.dto.response.ApiResponse;
import com.abn_amro.recipemanagement.exception.MailServerException;
import com.abn_amro.recipemanagement.service.MailService;
import com.abn_amro.recipemanagement.service.ThymeLeafContextService;
import com.abn_amro.recipemanagement.service.UserServiceClient;
import com.netflix.discovery.converters.Auto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserServiceFallbackFactory implements FallbackFactory<UserServiceClient> {

    private final ClientConfigProperties clientConfigProperties;
    private final ThymeLeafContextService thymeLeafContextService;
    private final MailService mailService ;

    @Override
    public UserServiceClient create(Throwable cause) {
        return new UserServiceClient() {
            @Override
            public ResponseEntity<ApiResponse<UserDTO>> getUserById(Long id) {
                log.error("Fallback factory triggered due to: {} {}", cause.getMessage(),"UserService down");
                UserDTO fallbackUser = UserDTO.builder()
                        .id(id)
                        .firstName("Unavailable")
                        .lastName("User")
                        .userName("unknown")
                        .email("itservice@abnamro.com")
                        .enabled(false)
                        .build();
                ApiResponse<UserDTO> response=
                        ApiResponse.failure(fallbackUser,null, ResponseConstant.STATUS_503,ResponseConstant.MESSAGE_503,true);
                notifyByEmailAsync(id,cause);
                return ResponseEntity.ok(response);
            }
        };
    }
    @Async
    void notifyByEmailAsync(Long id, Throwable cause) {
        try {
            Map<String,Object> context = thymeLeafContextService.initializeContextForServiceFailure(cause.getMessage());
        Mail mail = Mail.builder()
                .mailTo(clientConfigProperties.getEmailtoA())
                .mailSubject(clientConfigProperties.getEmailsubjectA())
                .ccTo(new String[]{})
                .thymeleafTemplateName("service-failure_email.html")
                .thymeleafContext((Context)context.get("emailContext"))
                .mailFrom(clientConfigProperties.getEmailtoC())
                .build();
            mailService.sendEmailAsync(mail);
        } catch (MailServerException e) {
           log.info("message "+e.getMessage());
        }
    }
}











