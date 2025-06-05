package com.abn_amro.usermanagment.controller;


import com.abn_amro.usermanagment.config.LogRequestResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@LogRequestResponse(excludeFields = {"password"})
@RequestMapping("/api/v1/user")
public class UserController {

}
