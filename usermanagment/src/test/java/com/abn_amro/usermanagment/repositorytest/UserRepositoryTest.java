package com.abn_amro.usermanagment.repositorytest;


import com.abn_amro.usermanagment.model.User;
import com.abn_amro.usermanagment.repositories.UserRepositories;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class UserRepositoryTest {

    @Autowired
   private UserRepositories userRepositories ;

    @Test
    void firstTest(){
        assertNotNull(userRepositories);
    }

    @Test
    @Sql("/testData.sql")
    void testData(){

    }

    @Test
    @DisplayName("Giving a searching by Username when email and firstname are null return List of users are")
    void  givenUserNameWhenEmailAndFirstNameIsNullAndEnabledTrueThenReturnPagedOfUsers(){
        Pageable pageable = PageRequest.of(0,10) ;
        String username = "ade";
        String email = null;
        String firstName = null;
        Page<User> users= userRepositories.
               findByEnabledTrueAndUserNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrFirstNameContainingIgnoreCase(username,
                       email,firstName,pageable);
        log.info("users {}",users);
    }

   // @Test
    @ParameterizedTest
    @MethodSource("searchParamsProvider")
    @DisplayName("search a user by the given parameters")
    void  givenAUserWhenParametersAreSetThenReturnPagedOfUsers(String username, String email, String firstName){
        email = (email != null && email.isBlank()) ? null : email;
        username = (username != null && username.isBlank()) ? null : username;
        firstName = (firstName != null && firstName.isBlank()) ? null : firstName;
        Pageable pageable = PageRequest.of(0,10) ;
        Page<User> users= userRepositories.
                findByEnabledTrueAndUserNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrFirstNameContainingIgnoreCase(username,
                        email,firstName,pageable);
        log.info("users {}",users);
        System.out.printf("users "+users);
        assertNotNull(users);
        int counter =0;
        log.info("counter {}",counter++);
    }

    private static Stream<Arguments> searchParamsProvider() {
        return Stream.of(
                Arguments.of("ade", null, null),
                Arguments.of(null, "john@example.com", null),
                Arguments.of(null, "seun@gmail.com", null),
                Arguments.of(null,null,"adam"));
    }
  //  boolean existsByUserName(String username);
  //  Optional<User> findByActivationToken(String token);
      @ParameterizedTest
      @MethodSource("isUserExistsByUserName")
      @DisplayName("Check if a user exists by username")
     void givenAUserNameWhenTestingUserThenTrueAreReturned(String userName){
         boolean isUserExists =  userRepositories.existsByUserName(userName) ;
         assertTrue(isUserExists,"User exists");
     }
    @ParameterizedTest
    @MethodSource("isUserExistsByUserName")
    @DisplayName("Check if a user does not exists by username")
    void givenAUserNameWhenTestingUserThenFalseAreReturned(String userName){
        boolean isUserExists =  userRepositories.existsByUserName(userName) ;
        System.out.printf("isUserExists "+isUserExists);
        assertFalse(isUserExists,"User does not exists");
    }
    private static Stream<Arguments> isUserExistsByUserName() {
        return Stream.of(Arguments.of("ade123"));
    }

    @ParameterizedTest
    @MethodSource("activationTokenProvider")
    @DisplayName("testing activation token on user registration")
    void givenATokenWhenTestingUserTokenActivationThenCheckWhatIsInsideOptional(String userName){
        User user  = new User();
        user.setEmail("testme@gmail.com");
        user.setActivationToken("12345");
        user.setEnabled(false);
        user.setFirstName("testme");
        user.setLastName("testmelastname");
        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedBy("samson");
        //user.setId(4l);
        User user1 = userRepositories.save(user);
        System.out.printf("user1 "+user1);
        boolean isUserExists =  userRepositories.existsByUserName(userName) ;
        System.out.printf("isUserExists "+isUserExists);
        assertFalse(isUserExists,"User  exists");
        Optional<User>  isActivationToken = userRepositories.findByActivationToken(user1.getActivationToken());
        System.out.printf("isActivationToken present "+isActivationToken.isPresent());
        assertEquals("12345",user.getActivationToken());
    }

    private static Stream<Arguments> activationTokenProvider() {
        return Stream.of(Arguments.of("12345"));
    }
}





























































































































