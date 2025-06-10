package com.abn_amro.apigateway.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutesConfig {
    @Autowired
    private JwtAuthFilterGatewayFilterFactory jwtAuthFilter;
    @Value("${jwt.secret-key}")
    private String signingKey;

    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r.path("/api/v1/user/")
                        .filters(f -> f.filter(jwtAuthFilter.apply(config())))
                        .uri("lb://USER"))
                .build();
    }
    private JwtAuthFilterGatewayFilterFactory.Config config() {
        JwtAuthFilterGatewayFilterFactory.Config config = new JwtAuthFilterGatewayFilterFactory.Config();
        config.setSigningKey(signingKey);
        return config;
    }
}







