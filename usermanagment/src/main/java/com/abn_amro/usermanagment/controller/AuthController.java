package com.abn_amro.usermanagment.controller;


import com.abn_amro.usermanagment.config.LogRequestResponse;
import com.abn_amro.usermanagment.dto.request.LoginRequest;
import com.abn_amro.usermanagment.dto.response.ApiResponse;
import com.abn_amro.usermanagment.dto.response.ResponseConstants;
import com.abn_amro.usermanagment.dto.response.TokenResponse;
import com.abn_amro.usermanagment.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Validated
@LogRequestResponse(excludeFields = {"password"})
@RequestMapping("/api/v1")
@Tag(name = "Authentication Management Controller", description = "Authentication operations")
@Slf4j
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/user/login")
    @Operation(summary = "Authenticate user and get token")
    public ResponseEntity<ApiResponse<TokenResponse>> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        TokenResponse tokenResponse = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(ApiResponse.success(tokenResponse,null, ResponseConstants.STATUS_200,ResponseConstants.MESSAGE_200,false));
    }
}






















































