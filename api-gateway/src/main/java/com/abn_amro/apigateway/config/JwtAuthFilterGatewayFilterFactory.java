package com.abn_amro.apigateway.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.List;

@Component("JwtAuthFilter")
public class JwtAuthFilterGatewayFilterFactory extends AbstractGatewayFilterFactory<JwtAuthFilterGatewayFilterFactory.Config> {
    private final static Logger log = LoggerFactory.getLogger(JwtAuthFilterGatewayFilterFactory.class);
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

    public JwtAuthFilterGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            log.info("in apigateway: " + exchange.getRequest().getPath());
            String token = extractToken(exchange.getRequest().getHeaders().getFirst("Authorization"));
            String path = exchange.getRequest().getPath().toString();
            if(path.startsWith("/api/v1/user/testserver")){
                return  chain.filter(exchange);
            }
            log.info("Request path in apigateway: " + path);
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

                String userId = claims.getSubject();
                String roles = String.join(",", claims.get("roles", List.class));

                String signature = generateHmacSignature(userId, path);

                var mutatedRequest = exchange.getRequest().mutate()
                        .header("X-User-ID", userId)
                        .header("X-User-Roles", roles)
                        .header("X-Gateway-Signature", signature)
                        .header("X-Auth-Time", Instant.now().toString())
                        .build();

                var mutatedExchange = exchange.mutate().request(mutatedRequest).build();

                return chain.filter(mutatedExchange);

            } catch (Exception e) {
                log.error("JWT validation failed", e);
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        };
    }

    private String generateHmacSignature(String userId, String path) {
        try {
            Mac hmac = Mac.getInstance("HmacSHA256");
            hmac.init(new SecretKeySpec(gatewaySecret.getBytes(), "HmacSHA256"));
//            String data = userId + "|" + path;
            String data = path  + "|" + userId;
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