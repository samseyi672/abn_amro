package com.abn_amro.recipemanagement.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class FilterConfig {

    @Value("${security.gateway-ip}")
    private String gatewayIp;
    @Value("${jwt.secret-key}")
    private String secret;
    @Bean
    public FilterRegistrationBean<GatewayTrustFilter> gatewayTrustFilter() {
        FilterRegistrationBean<GatewayTrustFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new GatewayTrustFilter(secret));
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1); // Highest priority
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<GatewayIpFilter> myFilter() {
        FilterRegistrationBean<GatewayIpFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new GatewayIpFilter(gatewayIp));
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(2);
        return registrationBean;
    }
}














































