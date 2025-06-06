package com.abn_amro.usermanagment.serviceimpl;

import com.abn_amro.usermanagment.dto.request.UserDTO;
import com.abn_amro.usermanagment.dto.response.ResponseConstants;
import com.abn_amro.usermanagment.event.UserRegisteredEvent;
import com.abn_amro.usermanagment.exceptions.UserNotFoundException;
import com.abn_amro.usermanagment.mapper.UserMapper;
import com.abn_amro.usermanagment.model.User;
import com.abn_amro.usermanagment.repositories.UserRepositories;
import com.abn_amro.usermanagment.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private UserRepositories userRepositories ;
    private UserMapper userMapper ;
    private ApplicationEventPublisher eventPublisherAware;

    @Transactional
    @Override
    public void createUser(UserDTO userDTO) {
       User user =  userMapper.toEntity(userDTO) ;
       log.info("user {}",user);
       User user1 = userRepositories.save(user);
       user1.setPassword(null);
       log.info("created user "+user1);
       UserRegisteredEvent event = new UserRegisteredEvent(user1.getId(),
               user1.getFirstname(),user1.getFirstname(),
               user1.getEmail());
       eventPublisherAware.publishEvent(event);
      // return user;
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
    public Page<UserDTO> searchUserByUserName(String username, int page, int size, boolean isEnabled) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users;
        if (isEnabled) {
            users = userRepositories.findByUsernameContainingIgnoreCaseAndEnabledTrue(username, pageable);
        } else {
            users = userRepositories.findByUsernameContainingIgnoreCase(username, pageable);
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
        return userRepositories.existsByUsername(username);
    }
}







