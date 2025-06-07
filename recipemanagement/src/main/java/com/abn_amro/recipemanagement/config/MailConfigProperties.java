package com.abn_amro.recipemanagement.config;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Slf4j
@ConfigurationProperties(prefix = "spring.mail")
@Data
@Configuration
public class MailConfigProperties {
    private String emailToA;
    private String emailToB;
    private String emailToC;
}
