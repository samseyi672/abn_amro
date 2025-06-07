package com.abn_amro.usermanagment.integrationtest;


import com.abn_amro.usermanagment.dto.request.RoleDTO;
import com.abn_amro.usermanagment.dto.request.UserDTO;
import com.abn_amro.usermanagment.dto.response.ApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTests {
    @Autowired
    private TestRestTemplate testRestTemplate ;


    @Test
    void testCreateUser() {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName("user");
        List<RoleDTO> roles  = List.of(roleDTO);
        UserDTO userDTO = UserDTO
                .builder().firstName("")
                .lastName("fun2")
                .userName("fun2")
                .email("fun@gmail.com")
                .enabled(false)
                .userName("funnysam")
                .roles(roles)
                .build();
        ResponseEntity<ApiResponse> response = testRestTemplate.postForEntity(
                "/api/v1/user/",
                userDTO,
                ApiResponse.class
        );
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}
