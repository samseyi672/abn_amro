package com.abn_amro.usermanagment.serviceimpl;


import com.abn_amro.usermanagment.config.TokenService;
import com.abn_amro.usermanagment.dto.request.LoginRequest;
import com.abn_amro.usermanagment.dto.response.TokenResponse;
import com.abn_amro.usermanagment.exceptions.AuthenticationException;
import com.abn_amro.usermanagment.model.User;
import com.abn_amro.usermanagment.repositories.UserRepository;
import com.abn_amro.usermanagment.service.AuthService;
import com.abn_amro.usermanagment.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final TokenService tokenService ;
    private final Key jwtSecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Override
    public TokenResponse authenticateUser(LoginRequest loginRequest) {
        Optional<User> userOpt = userRepository.findByUserName(loginRequest.getUsername());
        if (userOpt.isEmpty()) {
            throw new AuthenticationException("Invalid username or password");
        }
        User user = userOpt.get();
        if (!PasswordUtil.verifyPassword(loginRequest.getPassword(), user.getPassword())) {
            throw new AuthenticationException("Invalid username or password");
        }
        //check if user is active
        if(!user.isEnabled()){
            throw new AuthenticationException("Your account is inactive.Please contact the admin");
        }
        String token = tokenService.generateToken(user);
        return new TokenResponse(token,Instant.now().plus(1, ChronoUnit.HOURS).toString());
    }
}



