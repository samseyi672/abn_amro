package com.abn_amro.usermanagment.dto.request;

import com.abn_amro.usermanagment.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

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
            description = "Only details required.pass zero if user is new"
    )
    private Long id;
    @Schema(
            name = "firstname",
            description = "firstname of the user"
    )
    @NotEmpty(message = "firstname cannot be empty")
    private String firstName;
    @Schema(
            name = "lastname",
            description = "lastname of the user"
    )
    @NotEmpty(message = "lastname cannot be empty")
    private  String lastName;
    @Schema(
            name = "username",
            description = "Unique username"
    )
    @NotEmpty(message = "username cannot be empty")
    private String userName;
    @Schema(
            name = "email",
            description = "Unique email needed"
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
    private boolean enabled;

    @Schema(
            name = "roles",
            description = "user roles"
    )
    private List<RoleDTO> roles;
//    public List<RoleDTO> getRoles() {
//        return this.roles;
//    }
//    public Long getId() {
//        return id;
//    }
}





































































