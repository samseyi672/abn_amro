package com.abn_amro.usermanagment.serviceimpl;

import com.abn_amro.usermanagment.dto.request.UserDTO;
import com.abn_amro.usermanagment.dto.response.ResponseConstants;
import com.abn_amro.usermanagment.event.UserRegisteredEvent;
import com.abn_amro.usermanagment.exceptions.UserNotFoundException;
import com.abn_amro.usermanagment.mapper.UserMapper;
import com.abn_amro.usermanagment.model.User;
import com.abn_amro.usermanagment.repositories.UserRepository;
import com.abn_amro.usermanagment.service.UserService;
import com.abn_amro.usermanagment.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private UserRepository userRepositories ;
    private UserMapper userMapper ;
    private ApplicationEventPublisher eventPublisherAware;

    @Transactional
    @Override
    public User createUser(UserDTO userDTO) {
       User user =  userMapper.toEntity(userDTO) ;
       user.setPassword(PasswordUtil.hashPassword(user.getPassword()));
       log.info("user {}",user);
       User user1 = userRepositories.save(user);
       user1.setPassword(null);
       log.info("created user "+user1);
       UserRegisteredEvent event = new UserRegisteredEvent(user1.getId(),
               user1.getFirstName(),user1.getFirstName(),
               user1.getEmail());
       eventPublisherAware.publishEvent(event);
      return user;
    }

    @Override
    public Page<UserDTO> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page,size) ;
        Page<UserDTO> pagedUser= userRepositories
                   .findAll(pageable)
                   .map(userMapper::toDto)
                   .map(userDTO -> {
                       userDTO.setPassword(null);
                       return userDTO;
                   });
        return pagedUser;
    }

    @Override
    public UserDTO getUserById(Long id) {
        return userRepositories
                .findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException(ResponseConstants.MESSAGE_NOT_FOUND)) ;
    }

    @Override
    public void deleteUserById(Long id) {
        User user = userRepositories.findById(id).orElseThrow(() -> new UserNotFoundException(ResponseConstants.MESSAGE_NOT_FOUND));
        userRepositories.delete(user);
        //return null;
    }

    @Override
    public Page<UserDTO> searchByUsernameOrEmailOrFirstName(String userName, String email, String firstName, int page, int size, boolean isEnabled) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users;
        if ((userName == null || userName.isBlank()) &&
                (email == null || email.isBlank()) &&
                (firstName == null || firstName.isBlank())) {
            users = isEnabled ?
                    userRepositories.findByEnabledTrue(pageable) :
                    userRepositories.findAll(pageable);

        } else {
            users = isEnabled ?
                    userRepositories.findByEnabledTrueAndUserNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrFirstNameContainingIgnoreCase(
                            userName != null ? userName : "",
                            email != null ? email : "",
                            firstName != null ? firstName : "",
                            pageable
                    ) :
                    userRepositories.findByUserNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrFirstNameContainingIgnoreCase(
                            userName != null ? userName : "",
                            email != null ? email : "",
                            firstName != null ? firstName : "",
                            pageable
                    );
        }
        if (users.isEmpty()) {
            throw new UserNotFoundException(ResponseConstants.MESSAGE_NOT_FOUND);
        }
        return users.map(user -> {
            UserDTO dto = userMapper.toDto(user);
            dto.setPassword(null); // mask password
            return dto;
        });
    }
    @Override
    public boolean verifyUsername(String username) {
        return userRepositories.existsByUserName(username);
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepositories.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
        user.setUserName(userDTO.getUserName());
        user.setEmail(userDTO.getEmail());
        user.setEnabled(userDTO.isEnabled());
        if (userDTO.getPassword() != null && !userDTO.getPassword().isBlank()) {
            user.setPassword(PasswordUtil.hashPassword(userDTO.getPassword())); // Optional
        }
        if (userDTO.getRoles() != null) {
            user.setRoles(new HashSet<>(userMapper.mapRoleDtosToSet(userDTO.getRoles())));
        }
        user.setEnabled(false);
        user.setActivationToken(UUID.randomUUID().toString());
        User saved = userRepositories.save(user);
        UserDTO dto = userMapper.toDto(saved);
        dto.setPassword(null);
        return dto;
    }

    public void activateUserByToken(String token) {
        User user = userRepositories.findByActivationToken(token)
                .orElseThrow(() -> new UserNotFoundException("Invalid or expired activation token."));
        if (user.isEnabled()) {
            throw new IllegalStateException("User is already activated.");
        }
        user.setEnabled(true);
        user.setActivationToken(null);
        userRepositories.save(user);
    }
}







