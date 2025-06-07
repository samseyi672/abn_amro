package com.abn_amro.usermanagment.controller;


import com.abn_amro.usermanagment.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserControlllerTest.class)
public class UserControlllerTest {

    @Autowired
    private MockMvc mockMvc ;

    @Autowired
    ObjectMapper objectMapper ;

    @MockitoBean
    UserService userService ;

}













