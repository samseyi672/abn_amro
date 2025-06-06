package com.abn_amro.usermanagment.controller;


import com.abn_amro.usermanagment.config.LogRequestResponse;
import com.abn_amro.usermanagment.dto.request.UserDTO;
import com.abn_amro.usermanagment.dto.response.ApiResponse;
import com.abn_amro.usermanagment.dto.response.ResponseConstants;
import com.abn_amro.usermanagment.exceptions.ErrorResponseDto;
import com.abn_amro.usermanagment.model.User;
import com.abn_amro.usermanagment.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@Validated
@LogRequestResponse(excludeFields = {"password"})
@RequestMapping("/api/v1/user")
@Tag(name = "User Management Controller", description = "User Management crud operations")
@Slf4j
public class UserController {

    private  final UserService userService ;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(description = "search either active or inactive user endpoint")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "20o",
                    description = "HTTP Status GET"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<UserDTO>>> searchByUsername(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam boolean active) {
        ApiResponse<Page<UserDTO>> pageApiResponse = ApiResponse.success(userService.searchUserByUserName(keyword, page, size,active),
                null, ResponseConstants.STATUS_201,ResponseConstants.MESSAGE_201);
        return ResponseEntity.ok(pageApiResponse);
    }

    @Operation(
            summary = "Create User REST API",
            description = "User creation endpoint")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/create_user")
    public ResponseEntity<ApiResponse<Long>>  createUser(@Valid @RequestBody UserDTO userDTO){
        User user = userService.createUser(userDTO);
        ApiResponse<Long> response = ApiResponse.success(user.getId(),"",ResponseConstants.STATUS_201,
                ResponseConstants.MESSAGE_201);;
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(location)
                .body(response);
    }

    @Operation(
            summary = "Get All Users REST API",
            description = "Endpoint to get all users")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<Page<UserDTO>>> getAllUsers( @RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size){
        Page<UserDTO> pagedUser = userService.getAllUsers(page, size);
        ApiResponse<Page<UserDTO>> response = ApiResponse.success(pagedUser,null,ResponseConstants.STATUS_200,ResponseConstants.MESSAGE_200);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get A User By Id REST API",
            description = "Endpoint to get a user")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/user/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserById(@PathVariable(name = "id") @Pattern(regexp = "\\d++", message = "only int is expected") Long id){
      UserDTO userDTO =   userService.getUserById(id);
      ApiResponse<UserDTO> apiResponse = ApiResponse.success(userDTO,null,ResponseConstants.STATUS_200,ResponseConstants.MESSAGE_200);
      return ResponseEntity.ok(apiResponse);
    }

    @Operation(
            summary = "Delete By Id REST API",
            description = "Endpoint to delete a user")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "417",
                    description = "HTTP no content"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @DeleteMapping("/user/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUserById(@PathVariable(name = "id") @Pattern(regexp = "\\d++", message = "only int is expected") Long id){
      userService.deleteUserById(id);
      ApiResponse<String> response = ApiResponse.success(null,null,ResponseConstants.STATUS_417,ResponseConstants.MESSAGE_417_DELETE);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @Operation(
            summary = "check if a user exists by username REST API",
            description = "Endpoint to check if a user exist")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "HTTP Ok"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/is_user_exist/{id}")
    public ResponseEntity<ApiResponse<String>> verifyUsername(String username){
       boolean isUserExists  =  userService.verifyUsername(username) ;
       ApiResponse<String> response = ApiResponse.success("user exists",null,ResponseConstants.STATUS_200,ResponseConstants.MESSAGE_200);
        ApiResponse<String> falseresponse = ApiResponse.success("user does not exists",null,ResponseConstants.STATUS_200,ResponseConstants.MESSAGE_200);
       return isUserExists?ResponseEntity.ok(response):ResponseEntity.ok(falseresponse);
    }


    @Operation(
            summary = "Update a user REST API",
            description = "Endpoint to update a user")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "HTTP Created"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Long>> updateUser(
            @PathVariable Long id,
            @RequestBody @Valid UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(id, userDTO);
        ApiResponse<Long> response = ApiResponse.success(updatedUser.getId(),"",ResponseConstants.STATUS_201,
                ResponseConstants.MESSAGE_201);;
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(updatedUser.getId())
                .toUri();
        return ResponseEntity.created(location)
                .body(response);
    }

}














































































