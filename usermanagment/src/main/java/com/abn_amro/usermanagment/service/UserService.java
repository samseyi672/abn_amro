package com.abn_amro.usermanagment.service;


import com.abn_amro.usermanagment.dto.request.UserDTO;

import java.util.List;

public interface UserService {
    String createUser(UserDTO userDTO);
    UserDTO getAllUsers();
    UserDTO getUserById(Long id);
    String deleteUserById(Long id);
    List<UserDTO> searchUserByUserName(String usernme);
    boolean verifyUsername(String username);
}
