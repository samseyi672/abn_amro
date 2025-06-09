package com.abn_amro.usermanagment.service;


import com.abn_amro.usermanagment.dto.request.LoginRequest;
import com.abn_amro.usermanagment.dto.response.TokenResponse;

public interface AuthService {
    TokenResponse authenticateUser(LoginRequest loginRequest);
}
