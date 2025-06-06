package com.abn_amro.usermanagment.repositories;


import com.abn_amro.usermanagment.dto.request.UserDTO;
import com.abn_amro.usermanagment.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositories extends JpaRepository<User,Long> {
    Page<User> findByUsernameContainingIgnoreCase(String keyword, Pageable pageable); //search all users
    Page<User> findByUsernameContainingIgnoreCaseAndEnabledTrue(String keyword, Pageable pageable);//for active users
    boolean existsByUsername(String username);
    Optional<User> findByActivationToken(String token);
    Page<User> findByEnabledTrue(Pageable pageable);
    Page<UserDTO> searchByUsernameOrEmailOrFirstName(String username, String email, String firstname, int page, int size, boolean isEnabled);
    Page<User> findByEnabledTrueAndUsernameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrFirstnameContainingIgnoreCase(String userName, String email, String firstName, Pageable pageable);
    Page<User> findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrFirstnameContainingIgnoreCase(String userName, String email, String firstName, Pageable pageable);
}
