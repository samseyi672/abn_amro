package com.abn_amro.usermanagment.repositories;


import com.abn_amro.usermanagment.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Page<User> findByUserNameContainingIgnoreCase(String keyword, Pageable pageable); //search all users
    Page<User> findByUserNameContainingIgnoreCaseAndEnabledTrue(String keyword, Pageable pageable);//for active users
    boolean existsByUserName(String username);
    Optional<User> findByActivationToken(String token);
    Page<User> findByEnabledTrue(Pageable pageable);
   // Page<UserDTO> searchByUserNameOrEmailOrFirstName(String username, String email, String firstname, int page, int size, boolean isEnabled);
    Page<User> findByEnabledTrueAndUserNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrFirstNameContainingIgnoreCase(String userName, String email, String firstName, Pageable pageable);
    @Query("""
    SELECT u FROM User u 
    WHERE u.enabled = true AND (
          LOWER(u.userName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
        LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
        LOWER(u.firstName) LIKE LOWER(CONCAT('%', :keyword, '%'))
    )
         """)
    Page<User> searchUser(@Param("keyword") String keyword, Pageable pageable);

    Page<User> findByUserNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrFirstNameContainingIgnoreCase(String userName, String email, String firstName, Pageable pageable);

    Optional<User> findByUserNameOrEmail(String username,String email);

    Optional<User> findByEmail(String email);
}
