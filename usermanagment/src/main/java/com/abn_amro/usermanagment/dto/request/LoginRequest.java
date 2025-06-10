package com.abn_amro.usermanagment.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "userName is required")
    private String userName;
    @NotBlank(message = "password is required")
    private String password;
}