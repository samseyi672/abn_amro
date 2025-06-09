package com.abn_amro.apigateway.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.List;

@Component
@Slf4j
public class JwtAuthFilter extends AbstractGatewayFilterFactory<JwtAuthFilter.Config> {

    @Value("${gateway.hmac-secret}")
    private String gatewaySecret;
    public static class Config {
        private String signingKey;
        private List<String> requiredRoles;
        public String getSigningKey() {
            return signingKey;
        }

        public void setSigningKey(String signingKey) {
            this.signingKey = signingKey;
        }

        public List<String> getRequiredRoles() {
            return requiredRoles;
        }

        public void setRequiredRoles(List<String> requiredRoles) {
            this.requiredRoles = requiredRoles;
        }
    }

    public JwtAuthFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String token = extractToken(exchange.getRequest().getHeaders().getFirst("Authorization"));
            String path = exchange.getRequest().getPath().toString();
            log.info("Request path: " + path); // For debugging
            if (!StringUtils.hasText(token)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            try {
                SecretKey key = Keys.hmacShaKeyFor(config.getSigningKey().getBytes());
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();
                // Validate roles if configured
//                if (config.getRequiredRoles() != null && !config.getRequiredRoles().isEmpty()) {
//                    List<String> tokenRoles = claims.get("roles", List.class);
//                    if (tokenRoles == null || !tokenRoles.containsAll(config.getRequiredRoles())) {
//                        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
//                        return exchange.getResponse().setComplete();
//                    }
//                }
//                if (path.startsWith("/api/admin") && !claims.get("roles").contains("ADMIN")) {
//                    exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
//                    return exchange.getResponse().setComplete();
//                }
                //Add user info to header for other services
                exchange.getRequest().mutate()
                        .header("X-User-ID", claims.getSubject())
                        .header("X-User-Roles", String.join(",", claims.get("roles", List.class)))
                        .header("X-Gateway-Signature",
                                generateHmacSignature(
                                        claims.getSubject(),
                                        exchange.getRequest().getPath().toString()
                                ))
                        // Audit headers
                        .header("X-Auth-Time", Instant.now().toString())
                        .build();

            } catch (Exception e) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            return chain.filter(exchange);
        };
    }

    private String generateHmacSignature(String userId, String path) {
        try {
            Mac hmac = Mac.getInstance("HmacSHA256");
            hmac.init(new SecretKeySpec(gatewaySecret.getBytes(), "HmacSHA256"));
            String data = userId + "|" + path;
            return Base64.getEncoder().encodeToString(hmac.doFinal(data.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException("HMAC generation failed", e);
        }
    }
    private String extractToken(String authHeader) {
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}