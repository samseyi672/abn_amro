package com.abn_amro.usermanagment.config;


import com.abn_amro.usermanagment.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
//@RequiredArgsConstructor
public class TokenService {

    @Value("${jwt.secret-key}")
    private String secret;
    private Key jwtSecretKey;

//    public TokenService() {
//        log.info("secret "+secret);
//        this.jwtSecretKey = new SecretKeySpec(
//                Base64.getDecoder().decode(secret),
//                "HmacSHA256"
//        );
//    }

//    @Getter
//    private SecretKey key;

    @PostConstruct
    public void init() {
       // this.key = Keys.hmacShaKeyFor(secret.getBytes());
        log.info("secret "+secret);
        this.jwtSecretKey = new SecretKeySpec(
                Base64.getDecoder().decode(secret),
                "HmacSHA256"
        );
    }

//    public SignatureAlgorithm getSignatureAlgorithm() {
//        return SignatureAlgorithm.HS256;
//    }

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUserName())
                .claim("roles",user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList()))
                .claim("id",user.getId())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                .signWith(jwtSecretKey)
                .compact();
    }
}












































