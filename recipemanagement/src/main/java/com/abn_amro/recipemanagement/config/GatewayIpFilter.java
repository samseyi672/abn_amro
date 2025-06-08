package com.abn_amro.recipemanagement.config;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class GatewayIpFilter extends OncePerRequestFilter {

    @Value("${security.gateway-ip}")
    private String gatewayIp;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String forwardedFor = request.getHeader("X-Forwarded-For");
        String sourceIp = (forwardedFor != null) ? forwardedFor.split(",")[0].trim() : request.getRemoteAddr();

        if (!gatewayIp.equals(sourceIp)) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write("Access denied: Requests must go through the API Gateway");
            return;
        }
        filterChain.doFilter(request, response);
    }
}




















