package com.abn_amro.usermanagment.config;


import com.abn_amro.usermanagment.model.User;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class TokenService {
    private final Key jwtSecretKey;

    public TokenService(@Value("${jwt.secret-key}") String secret) {
        this.jwtSecretKey = new SecretKeySpec(
                Base64.getDecoder().decode(secret),
                "HmacSHA256"
        );
    }

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
