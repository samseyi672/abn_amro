package com.abn_amro.usermanagment.servicetest;

import com.abn_amro.usermanagment.dto.request.RoleDTO;
import com.abn_amro.usermanagment.dto.request.UserDTO;
import com.abn_amro.usermanagment.mapper.UserMapper;
import com.abn_amro.usermanagment.model.User;
import com.abn_amro.usermanagment.repositories.UserRepositories;
import com.abn_amro.usermanagment.serviceimpl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepositories userRepositories;

    @Mock
    private UserMapper userMapper;

    @Mock
    private ApplicationEventPublisher eventPublisherAware;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testUserService() {
        assertNotNull(userService);
    }

    @Test
    void givenUserDtoWhenUserIsCreatedThenCallRepositorySave(){
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName("user");
        List<RoleDTO> roles  = List.of(roleDTO);
        UserDTO userDTO  = UserDTO.builder()
                .userName("")
                .email("")
                .roles(roles)
                .firstName("")
                .lastName("")
                .password("")
                .enabled(false)
                .build() ;
        User savedUser  = userMapper.toEntity(userDTO) ;
    }
}


































