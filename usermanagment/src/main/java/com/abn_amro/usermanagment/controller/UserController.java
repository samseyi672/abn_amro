package com.abn_amro.usermanagment.controller;


import com.abn_amro.usermanagment.config.LogRequestResponse;
import com.abn_amro.usermanagment.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@LogRequestResponse(excludeFields = {"password"})
@RequestMapping("/api/v1/user")
@Tag(name = "User Management Controller", description = "User Managment crud operations")
@Slf4j
public class UserController {

    private  final UserService userService ;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    


}
