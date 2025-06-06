package com.abn_amro.usermanagment.service;


import com.abn_amro.usermanagment.dto.request.UserDTO;
import com.abn_amro.usermanagment.model.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    User createUser(UserDTO userDTO);
    Page<UserDTO> getAllUsers(int page , int size);
    UserDTO getUserById(Long id);
    void deleteUserById(Long id);
    Page<UserDTO> searchUserByUserName(String usernme,int page, int size,boolean isEnabled);
    boolean verifyUsername(String username);
    UserDTO updateUser(Long id, UserDTO userDTO);
    void activateUserByToken(String token);
}
