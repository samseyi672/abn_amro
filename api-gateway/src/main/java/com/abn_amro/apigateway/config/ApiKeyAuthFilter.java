package com.abn_amro.apigateway.config;


import org.springframework.stereotype.Component;

import java.util.List;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.AntPathMatcher;
import java.util.Arrays;

@Component("ApiKeyAuth")
public class ApiKeyAuthFilter extends AbstractGatewayFilterFactory<ApiKeyAuthFilter.Config> {

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    public ApiKeyAuthFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getPath().toString();

            // Check if path matches any anonymous endpoint
            boolean isAnonymous = Arrays.stream(config.getAnonymousEndpoints().split(","))
                    .anyMatch(pattern -> pathMatcher.match(pattern.trim(), path));

            if (isAnonymous) {
                return chain.filter(exchange);
            }
            // Check API key
            String apiKey = request.getHeaders().getFirst(config.getApiKeyHeader());
            if (apiKey == null || !config.getAllowedKeys().contains(apiKey)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {
        private String apiKeyHeader;
        private List<String> allowedKeys;
        private String anonymousEndpoints;

        // Getters and setters
        public String getApiKeyHeader() {
            return apiKeyHeader;
        }

        public void setApiKeyHeader(String apiKeyHeader) {
            this.apiKeyHeader = apiKeyHeader;
        }

        public List<String> getAllowedKeys() {
            return allowedKeys;
        }

        public void setAllowedKeys(String allowedKeys) {
            this.allowedKeys = Arrays.asList(allowedKeys.split(","));
        }

        public String getAnonymousEndpoints() {
            return anonymousEndpoints;
        }

        public void setAnonymousEndpoints(String anonymousEndpoints) {
            this.anonymousEndpoints = anonymousEndpoints;
        }
    }
}