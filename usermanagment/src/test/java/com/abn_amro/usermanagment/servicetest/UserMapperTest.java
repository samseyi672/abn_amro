package com.abn_amro.usermanagment.servicetest;

import com.abn_amro.usermanagment.dto.request.RoleDTO;
import com.abn_amro.usermanagment.dto.request.UserDTO;
import com.abn_amro.usermanagment.mapper.UserMapper;
import com.abn_amro.usermanagment.model.Role;
import com.abn_amro.usermanagment.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.mapstruct.factory.Mappers;
import java.util.Set;

public class UserMapperTest {
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = Mappers.getMapper(UserMapper.class);
    }

    @Test
    void testMapperInitialization() {
        assertNotNull(userMapper);
    }

    @Test
    @DisplayName("Should map UserDTO to User")
    void shouldMapUserDtoToUser() {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName("user");
        UserDTO userDTO = UserDTO.builder()
                .userName("ade")
                .email("ade@gmail.com")
                .roles(List.of(roleDTO))
                .firstName("Ade")
                .lastName("Ola")
                .password("pass")
                .enabled(true)
                .build();
        User user = userMapper.toEntity(userDTO);
        assertNotNull(user);
        assertEquals(userDTO.getUserName(), user.getUserName());
        assertEquals(userDTO.getEmail(), user.getEmail());
    }

    @Test
    @DisplayName("Should map User to UserDTO")
    void shouldMapUserToUserDto() {
        Role role = new Role();
        role.setName("admin");
        User user = new User();
        user.setUserName("seyi");
        user.setEmail("seyi@gmail.com");
        user.setEnabled(true);
        user.setFirstName("Seyi");
        user.setLastName("Ola");
        user.setPassword("secret");
        user.setRoles(Set.of(role));
        UserDTO userDTO = userMapper.toDto(user);
        assertNotNull(userDTO);
        assertEquals(user.getUserName(), userDTO.getUserName());
        assertEquals(user.getEmail(), userDTO.getEmail());
        assertEquals(1, userDTO.getRoles().size());
    }
}


















































































































































































































