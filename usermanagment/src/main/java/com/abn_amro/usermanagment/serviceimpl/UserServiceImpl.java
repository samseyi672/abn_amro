package com.abn_amro.usermanagment.serviceimpl;

import com.abn_amro.usermanagment.dto.request.UserDTO;
import com.abn_amro.usermanagment.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    @Override
    public String createUser(UserDTO userDTO) {
        return null;
    }

    @Override
    public UserDTO getAllUsers() {
        return null;
    }

    @Override
    public UserDTO getUserById(Long id) {
        return null;
    }

    @Override
    public String deleteUserById(Long id) {
        return null;
    }

    @Override
    public List<UserDTO> searchUserByUserName(String usernme) {
        return null;
    }

    @Override
    public boolean verifyUsername(String username) {
        return false;
    }
}
