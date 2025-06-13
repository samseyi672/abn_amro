package com.abn_amro.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;

import java.time.Duration;

@Configuration
public class RedisRetryConfiguration {
    @Bean
    public LettuceClientConfiguration clientConfiguration() {
        return LettuceClientConfiguration.builder()
                .commandTimeout(Duration.ofSeconds(10))
                .shutdownTimeout(Duration.ofMillis(100))
                .build();
    }
}
