package com.abn_amro.usermanagment.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "com.abnamro.notify")
@Data
public class ClientConfigProperties {
    private String emailtoA ;
    private String emailsubjectA;
    private String emailtoC ;
}
