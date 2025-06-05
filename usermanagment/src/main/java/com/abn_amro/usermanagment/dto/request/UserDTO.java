package com.abn_amro.usermanagment.dto.request;


import com.abn_amro.usermanagment.model.Permission;
import com.abn_amro.usermanagment.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@ToString
@Schema(
        name = "User",
        description = "users details"
)
@Builder
public class UserDTO {
    @Schema(
            name = "id",
            description = "Only details required.pass zero if id is new"
    )
    @NotNull(message = "id cannot be null")
    private Long id;
    @Schema(
            name = "username",
            description = "Unique username"
    )
    @NotEmpty(message = "username cannot be empty")
    private String username;
    @Schema(
            name = "email",
            description = "Unique email"
    )
    private String email;
    @Schema(
            name = "password",
            description = "user password"
    )
    @NotEmpty(message = "password cannot be empty")
    private String password;
    @Schema(
            name = "enabled",
            description = "users activation",
            hidden = true
    )
    private boolean enabled; // this will be hidden by default
}





































































